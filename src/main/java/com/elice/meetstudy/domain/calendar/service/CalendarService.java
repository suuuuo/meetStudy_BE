package com.elice.meetstudy.domain.calendar.service;

import com.elice.meetstudy.domain.calendar.domain.Calendar;
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

    //캘린더 조회(생성) - 작성 완료, 테스트 전
    @Transactional
    public Calendar findCalendar(long userId, long studyRoomId) {
        Calendar userCalendar;
        Calendar studyCalendar;

        if (studyRoomId == 0) {//스터디룸 아이디 없음 -> 개인 캘린더
            userCalendar = calendarRepository.findByUserIdAndRoomIdIsNull(userId);

            if (userCalendar == null) {
                Optional<User> user = userRepository.findById(userId);
                userCalendar = new Calendar(user.get());
                calendarRepository.save(userCalendar);
                return userCalendar;
            } else
                return userCalendar;

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






}
