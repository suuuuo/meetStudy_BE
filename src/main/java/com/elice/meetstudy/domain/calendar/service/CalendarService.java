package com.elice.meetstudy.domain.calendar.service;

import com.elice.meetstudy.domain.calendar.domain.Calendar;
import com.elice.meetstudy.domain.calendar.repository.CalendarDetailRepository;
import com.elice.meetstudy.domain.calendar.repository.CalendarRepository;
import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import com.elice.meetstudy.domain.studyroom.repository.StudyRoomRepository;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalendarService {

  private final CalendarRepository calendarRepository;
  private final UserRepository userRepository;
  private final StudyRoomRepository studyRoomRepository;

  public CalendarService(
      CalendarRepository calendarRepository,
      UserRepository userRepository,
      StudyRoomRepository studyRoomRepository) {
    this.calendarRepository = calendarRepository;
    this.userRepository = userRepository;
    this.studyRoomRepository = studyRoomRepository;
  }

  /**
   * 캘린더 조회(생성 )
   *
   * @param userId
   * @param studyRoomId
   * @return Calendar
   */
  @Transactional
  public Calendar findCalendar(long userId, long studyRoomId) {
    Optional<Calendar> userCalendar;
    Optional<Calendar> studyCalendar;

    if (studyRoomId == 0L) {
      /** 스터디룸 아이디 없음 -> 개인 캘린더 */
      userCalendar = calendarRepository.findByUserIdAndStudyRoomIsNull(userId);

      if (userCalendar.isEmpty()) { // 없으면
        Optional<User> user = userRepository.findById(userId);
        Calendar findUserCalendar = new Calendar(user.get()); // 생성
        calendarRepository.save(findUserCalendar); // 저장
        return findUserCalendar; // 리턴
      } else return userCalendar.get();
    } else {
      /** 스터디룸 아이디 있음 -> 공용 캘린더 조회 */
      studyCalendar = calendarRepository.findByStudyRoomId(studyRoomId);

      if (studyCalendar.isEmpty()) {
        Optional<User> user = userRepository.findById(userId);
        Optional<StudyRoom> studyRoom = studyRoomRepository.findById(studyRoomId);
        Calendar newStudyCalendar = new Calendar(user.get(), studyRoom.get()); // 생성
        calendarRepository.save(newStudyCalendar); // 저장
        return newStudyCalendar;
      } else return studyCalendar.get();
    }
  }

  /**
   * 개인 캘린더 삭제
   *
   * @param userId
   */
  @Transactional
  public ResponseEntity<?> deleteCalendar(Long userId) {
    Optional<Calendar> calendar = calendarRepository.findByUserIdAndStudyRoomIsNull(userId);
    calendarRepository.deleteById(calendar.get().getId());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * 공용 캘린더 삭제
   *
   * @param studyRoomId
   */
  @Transactional
  public ResponseEntity<?> deleteStudyCalendar(Long studyRoomId) {
    Optional<Calendar> calendar = calendarRepository.findByStudyRoomId(studyRoomId);
    calendarRepository.deleteById(calendar.get().getId());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Transactional
  public String findFirstDay(String year, String month) {
    LocalDate firstDayOfMonth = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1);
    DayOfWeek dayOfWeek = firstDayOfMonth.getDayOfWeek();
    return dayOfWeek.toString();
  }

  @Transactional
  public String findLastDay(String year, String month) {
    YearMonth yearMonth = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month));
    int day = yearMonth.lengthOfMonth();
    return String.valueOf(day);
  }
}
