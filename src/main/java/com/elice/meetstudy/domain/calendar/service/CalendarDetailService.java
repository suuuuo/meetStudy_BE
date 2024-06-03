package com.elice.meetstudy.domain.calendar.service;

import com.elice.meetstudy.domain.calendar.domain.Calendar;
import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import com.elice.meetstudy.domain.calendar.dto.RequestCalendarDetail;
import com.elice.meetstudy.domain.calendar.dto.ResponseCalendarDetail;
import com.elice.meetstudy.domain.calendar.holiday.domain.Holiday;
import com.elice.meetstudy.domain.calendar.holiday.service.HolidayService;
import com.elice.meetstudy.domain.calendar.mapper.CalendarDetailMapper;
import com.elice.meetstudy.domain.calendar.repository.CalendarDetailRepository;
import com.elice.meetstudy.domain.calendar.repository.CalendarRepository;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.domain.UserPrinciple;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import com.elice.meetstudy.util.TokenUtility;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalendarDetailService {

  private final CalendarDetailRepository calendarDetailRepository;
  private final CalendarRepository calendarRepository;
  private final CalendarService calendarService;
  private final HolidayService holidayService;
  private final CalendarDetailMapper calendarDetailMapper;
  private final UserRepository userRepository;

  public CalendarDetailService(
      CalendarDetailRepository calendarDetailRepository, CalendarRepository calendarRepository,
      CalendarService calendarService,
      HolidayService holidayService,
      CalendarDetailMapper calendarDetailMapper,
      UserRepository userRepository) {
    this.calendarDetailRepository = calendarDetailRepository;
      this.calendarRepository = calendarRepository;
      this.calendarService = calendarService;
    this.holidayService = holidayService;
    this.calendarDetailMapper = calendarDetailMapper;
      this.userRepository = userRepository;
  }

  /**
   * 캘린더 공휴일 자동 등록 -> 조회 시 공휴일 표시
   *
   * @param year
   * @param month
   * @param calendarId
   */
  @Transactional
  public void saveHolidays(String year, String month, long calendarId) {
    List<Holiday> holidayList = holidayService.Holiday(year, month);
    Optional<Calendar> calendar = calendarRepository.findById(calendarId);

    if (calendar.isPresent()) {
      for (Holiday holiday : holidayList) {
        if (calendarDetailRepository.existsByStartDayAndCalendarIdAndIsHolidayIsTrue(
            holiday.getDate(), calendarId)) {
        } else { // 현재 캘린더에 공휴일 정보가 없으면 등록함
          Calendar_detail c = new Calendar_detail();
          c.setTitle(holiday.getName());
          c.setStartDay(holiday.getDate());
          c.setHoliday(true);
          c.setCalendar(calendar.get());
          calendarDetailRepository.save(c);
        }
      }
    } else throw new EntityNotFoundException("해당 캘린더가 존재하지 않습니다");
  }

  /**
   * 한 달 일정 리스트로 조회
   *
   * @param year
   * @param month
   * @param studyRoomId
   * @return
   */
  @Transactional
  public ResponseEntity<List<ResponseCalendarDetail>> getAllCalendarDetail(
      String year, String month, Long studyRoomId) {
    //접근한 유저 정보 가져오는 로직
    long userId = getUserId();

    Calendar calendar = calendarService.findCalendar(userId, studyRoomId); // 캘린더 찾아서
    saveHolidays(year, month, calendar.getId()); // 공휴일 일정 등록
    List<Calendar_detail> calendarDetailList =
        calendarDetailRepository.findAllByCalendar(calendar); // 해당 캘린더의 한 달 일정들을 리스트로 출력

    List<ResponseCalendarDetail> responseCalendarDetails = new ArrayList<>();
    for (Calendar_detail calendarDetail : calendarDetailList) {
      responseCalendarDetails.add(calendarDetailMapper.toResponseCalendarDetail(calendarDetail));
    }

    HttpHeaders headers = new HttpHeaders();
    headers.add("first-day", calendarService.findFirstDay(year, month));
    headers.add("last-day", calendarService.findLastDay(year, month));
    return new ResponseEntity<>(responseCalendarDetails, headers, HttpStatus.OK);

  }

  /**
   * 개별 일정 조회
   *
   * @param id
   * @return
   */
  @Transactional
  public ResponseEntity<ResponseCalendarDetail> getCalendarDetail(long id) {
    Optional<Calendar_detail> calendarDetail = calendarDetailRepository.findById(id);
    if (calendarDetail.isPresent()) {
      return new ResponseEntity<>(
          calendarDetailMapper.toResponseCalendarDetail(calendarDetail.get()), HttpStatus.OK);
    } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  /**
   * 일정 추가
   *
   * @param requestCalendarDetail
   * @param studyRoomId
   * @return
   */
  @Transactional
  public ResponseEntity<ResponseCalendarDetail> saveCalendarDetail(
      RequestCalendarDetail requestCalendarDetail, Long studyRoomId) { // request로 받으면
    //접근한 유저 정보 가져오는 로직
    long userId = getUserId();

    Calendar_detail calendarDetail = calendarDetailMapper.toCalendarDetail(requestCalendarDetail);
    Calendar calendar = calendarService.findCalendar(userId, studyRoomId); // 캘린더 찾아서
    calendarDetail.setCalendar(calendar); // 캘린더 추가해주고
    calendarDetailRepository.save(calendarDetail); // 저장
    return new ResponseEntity<>(
        calendarDetailMapper.toResponseCalendarDetail(calendarDetail), HttpStatus.OK); // 반환
  }

  /**
   * 일정 수정
   *
   * @param re
   * @param calendarDetailId
   * @return
   */
  @Transactional
  public ResponseEntity<ResponseCalendarDetail> putCalendarDetail(RequestCalendarDetail re,
      long calendarDetailId) {
    Optional<Calendar_detail> originCalendarDetail = calendarDetailRepository.findById(calendarDetailId);

    if (originCalendarDetail.isPresent()) {
      Calendar_detail calendarDetail = originCalendarDetail.get();
      calendarDetail.update( re.title(), re.content(), re.startDay(), re.endDay(),
          re.startTime(), re.endTime());
      return new ResponseEntity<>(
          calendarDetailMapper.toResponseCalendarDetail(calendarDetail), HttpStatus.OK);
    } else throw new IllegalArgumentException("존재하는 일정이 아닙니다.");
  }

  /**
   * 일정 삭제
   *
   * @param id
   * @return
   */
  @Transactional
  public ResponseEntity<?> deleteCalendarDetail(long id) {
    calendarDetailRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

  @Transactional
  public long getUserId(){
    //접근한 유저 정보 가져오는 로직
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserPrinciple userPrinciple = (UserPrinciple)authentication.getPrincipal();
    return Long.parseLong(userPrinciple.getEmail());
  }
}
