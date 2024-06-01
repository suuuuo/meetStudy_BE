package com.elice.meetstudy.domain.calendar.service;

import com.elice.meetstudy.domain.calendar.domain.Calendar;
import com.elice.meetstudy.domain.calendar.dto.DeleteRequestCalendarDetail;
import com.elice.meetstudy.domain.calendar.repository.CalendarDetailRepository;
import com.elice.meetstudy.domain.calendar.repository.CalendarRepository;
import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import com.elice.meetstudy.domain.studyroom.repository.StudyRoomRepository;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
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
            //리포지토리에서 유저 아이디로 캘린더 있나 확인
            userCalendar = calendarRepository.findByUserIdAndStudyRoomIsNull(userId);

            if (userCalendar.isEmpty()) { // 없으면
                Optional<User> user = userRepository.findById(userId);
                Calendar findUserCalendar = new Calendar(user.get()); // 생성
                calendarRepository.save(findUserCalendar); // 저장
                return findUserCalendar;
            } else{
                return userCalendar.get();
            }
        } else {// 스터디룸 아이디 있음 -> 공용 캘린더 조회
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
                return studyCalendar.get();
            }

        }
    }

    @Transactional
    public void deleteCalendar(Long userId){
        Optional<Calendar> calendar = calendarRepository.findByUserIdAndStudyRoomIsNull(userId);
        if(calendar.isPresent()){
            calendarDetailRepository.deleteAllByCalendar(calendar.get());
            calendarRepository.deleteById(calendar.get().getId());
        }
    }

    @Transactional
    public void deleteStudyCalendar(Long studyRoomId){
        Optional<Calendar> calendar = calendarRepository.findByStudyRoomId(studyRoomId);
        if(calendar.isPresent()){
            calendarDetailRepository.deleteAllByCalendar(calendar.get());
            calendarRepository.deleteById(calendar.get().getId());
        }
    }

    @Transactional
    public String findFirstDay(String year, String month){


        LocalDate firstDayOfMonth = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1);
        //월요일이 1, 일요일이 7
        DayOfWeek dayOfWeek = firstDayOfMonth.getDayOfWeek();
        return dayOfWeek.toString();
    }

    @Transactional
    public String findLastDay(String year, String month){

        YearMonth yearMonth = YearMonth.of(Integer.parseInt(year),Integer.parseInt(month));
        int day = yearMonth.lengthOfMonth();
        return String.valueOf(day);
    }
}
