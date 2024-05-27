package com.elice.meetstudy.domain.calendar.service;

import com.elice.meetstudy.domain.calendar.domain.Calendar;
import com.elice.meetstudy.domain.calendar.dto.DeleteRequestCalendarDetail;
import com.elice.meetstudy.domain.calendar.repository.CalendarDetailRepository;
import com.elice.meetstudy.domain.calendar.repository.CalendarRepository;
import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import com.elice.meetstudy.domain.studyroom.repository.StudyRoomRepository;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalendarService {

    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CalendarDetailRepository calendarDetailRepository;
    @Autowired
    StudyRoomRepository studyRoomRepository;

    //캘린더 조회(생성)
    @Transactional
    public Calendar findCalendar(long userId, long studyRoomId) {
        Optional<Calendar> userCalendar;
        Optional<Calendar> studyCalendar;

        if (studyRoomId == 0L) {//스터디룸 아이디 없음 -> 개인 캘린더
            System.out.println("개인 캘린더를 탐색합니다.");
            //리포지토리에서 유저 아이디로 캘린더 있나 확인
            userCalendar = calendarRepository.findByUserIdAndStudyRoomIsNull(userId);

            if (userCalendar.isEmpty()) { // 없으면
                System.out.println("캘린더를 생성합니다.");
                Optional<User> user = userRepository.findById(userId);
                Calendar findUserCalendar = new Calendar(user.get()); // 생성
                calendarRepository.save(findUserCalendar); // 저장
                return findUserCalendar;
            } else{
                System.out.println("검색된 캘린더를 반환합니다.");
                return userCalendar.get();
            }
        } else {// 스터디룸 아이디 있음 -> 공용 캘린더 조회
            System.out.println("스터디룸 공용 캘린더를 탐색합니다.");
            //캘린더 리포지토리에서 스터디룸 아이디로 캘린더 조회
            studyCalendar = calendarRepository.findByStudyRoomId(studyRoomId);

            if (studyCalendar.isEmpty()) { // 없으면
                Optional<User> user = userRepository.findById(userId);
                Optional<StudyRoom> studyRoom = studyRoomRepository.findById(studyRoomId);
                System.out.println(studyRoom.get().getTitle());
                Calendar newStudyCalendar = new Calendar(user.get(), studyRoom.get()); // 생성
                calendarRepository.save(newStudyCalendar); // 저장
                return newStudyCalendar;
            } else{
                System.out.println("이미 있는 캘린더이므로 반환합니다.");
                return studyCalendar.get();

            }

        }
    }

    @Transactional
    public void deleteCalendar(DeleteRequestCalendarDetail deleteRequestCalendarDetail){
        Optional<Calendar> calendar = calendarRepository.findById(deleteRequestCalendarDetail.id());
        if(calendar.isPresent()){
            calendarDetailRepository.deleteAllByCalendar(calendar.get());
            calendarRepository.deleteById(deleteRequestCalendarDetail.id());
        }
    }

    //유저 삭제 시 같이 삭제되게? cascade 설정하면 공용 캘린더가 유저 한명만 삭제되어도 같이 삭제됨.
//    @Transactional
//    public void deleteCalendar(Long userId){
//        Optional<Calendar> calendar = calendarRepository.findByUserId(userId);
//        if(calendar.isPresent()){
//            calendarDetailRepository.deleteAllByCalendar(calendar.get());
//            calendarRepository.deleteById(calendar.get().getId());
//        }
//    }
}
