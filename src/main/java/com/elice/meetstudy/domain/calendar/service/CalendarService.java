package com.elice.meetstudy.domain.calendar.service;

import com.elice.meetstudy.domain.calendar.domain.Calendar;
import com.elice.meetstudy.domain.calendar.dto.DeleteRequestCalendarDetail;
import com.elice.meetstudy.domain.calendar.repository.CalendarDetailRepository;
import com.elice.meetstudy.domain.calendar.repository.CalendarRepository;
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

    //캘린더 조회(생성)
    @Transactional
    public Calendar findCalendar(long userId, long studyRoomId) {
        Optional<Calendar> userCalendar;
        Calendar studyCalendar;

        if (studyRoomId == 0L) {//스터디룸 아이디 없음 -> 개인 캘린더
            System.out.println("개인 캘린더 탐색을 시작합니다.");
            userCalendar = calendarRepository.findByUserId(userId);

            if (userCalendar.isEmpty()) {
                System.out.println("캘린더가 없으므로 생성을 진행합니다.");
                Optional<User> user = userRepository.findById(userId);
                Calendar findUserCalendar = new Calendar(user.get());
                calendarRepository.save(findUserCalendar);
                return findUserCalendar;
            } else{
                System.out.println("이미 존재하는 캘린더를 반환합니다.");
                return userCalendar.get();
            }


        } else {// 스터디룸 아이디 있음 -> 공용 캘린더 조회
//            studyCalendar = calendarRepository.findByRoomId(studyRoomId);
//
//            if (studyCalendar == null) {
//                Optional<User> user = userRepository.findById(userId);
//                StudyRoom studyRoom = StudyRoomRepository.findById(studyRoomId);
//                studyCalendar = new Calendar(user.get(), studyRoom);
//                calendarRepository.save(studyCalendar);
//                return studyCalendar;
//            } else
//                return studyCalendar;
            return null;

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
    @Transactional
    public void deleteCalendar(Long userId){
        Optional<Calendar> calendar = calendarRepository.findByUserId(userId);
        if(calendar.isPresent()){
            calendarDetailRepository.deleteAllByCalendar(calendar.get());
            calendarRepository.deleteById(calendar.get().getId());
        }
    }
}
