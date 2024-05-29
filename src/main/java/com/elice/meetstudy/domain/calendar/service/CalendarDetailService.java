package com.elice.meetstudy.domain.calendar.service;

import com.elice.meetstudy.domain.calendar.domain.Calendar;
import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import com.elice.meetstudy.domain.calendar.dto.ResponseCalendarDetail;
import com.elice.meetstudy.domain.calendar.holiday.domain.Holiday;
import com.elice.meetstudy.domain.calendar.holiday.service.HolidayService;
import com.elice.meetstudy.domain.calendar.mapper.CalendarDetailMapper;
import com.elice.meetstudy.domain.calendar.repository.CalendarDetailRepository;
import com.elice.meetstudy.domain.calendar.repository.CalendarRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalendarDetailService {

  private final CalendarDetailRepository calendarDetailRepository;
  private final CalendarRepository calendarRepository;
  private final CalendarService calendarService;
  private final HolidayService holidayService;
  private final CalendarDetailMapper calendarDetailMapper;

  public CalendarDetailService(
      CalendarDetailRepository calendarDetailRepository, CalendarRepository calendarRepository,
      CalendarService calendarService,
      HolidayService holidayService,
      CalendarDetailMapper calendarDetailMapper) {
    this.calendarDetailRepository = calendarDetailRepository;
      this.calendarRepository = calendarRepository;
      this.calendarService = calendarService;
    this.holidayService = holidayService;
    this.calendarDetailMapper = calendarDetailMapper;
  }

  // 캘린더 공휴일 일정 자동 등록(조회 시 공휴일 표시)
  @Transactional
  public void saveHolidays(String year, String month, long calendarId) {
    List<Holiday> holidayList = holidayService.Holiday(year, month);

    for (Holiday holiday : holidayList) {
      if (calendarDetailRepository.existsByStartDayAndCalendarIdAndIsHolidayIsTrue(
          holiday.getDate(), calendarId)) {
      } else { // 현재 캘린더에 공휴일 정보가 없으면 등록함
        Optional<Calendar> calendar = calendarRepository.findById(calendarId);
        Calendar_detail c = new Calendar_detail();
        c.setTitle(holiday.getName());
        c.setStartDay(holiday.getDate());
        c.setHoliday(true);
        c.setCalendar(calendar.get());
        calendarDetailRepository.save(c);
      }
    }
  }

  // 한 달 일정 리스트로 조회
  @Transactional
  public List<ResponseCalendarDetail> getAllCalendarDetail(
      String year, String month, Long userId, Long studyRoomId) {
    Calendar calendar = calendarService.findCalendar(userId, studyRoomId); // 캘린더 찾아서
    saveHolidays(year, month, calendar.getId()); // 공휴일 일정 등록
    List<Calendar_detail> calendarDetailList =
        calendarDetailRepository.findAllByCalendar(calendar); // 해당 캘린더의 한 달 일정들을 리스트로 출력

    List<ResponseCalendarDetail> responseCalendarDetails = new ArrayList<>();
    for (Calendar_detail calendarDetail : calendarDetailList) {
      responseCalendarDetails.add(calendarDetailMapper.toResponseCalendarDetail(calendarDetail));
    }
    return responseCalendarDetails;
  }

  // 개별 일정 조회
  @Transactional
  public ResponseCalendarDetail getCalendarDetail(long id) {
    Optional<Calendar_detail> calendarDetail = calendarDetailRepository.findById(id);
    return calendarDetailMapper.toResponseCalendarDetail(calendarDetail.get());
  }

  // request 받아서 일정 추가
  @Transactional
  public ResponseCalendarDetail saveCalendarDetail(
      Calendar_detail calendarDetail, Long userId, Long studyRoomId) { // request로 받으면
    Calendar calendar = calendarService.findCalendar(userId, studyRoomId); // 캘린더 찾아서
    calendarDetail.setCalendar(calendar); // 캘린더 추가해주고
    calendarDetailRepository.save(calendarDetail); // 저장
    return calendarDetailMapper.toResponseCalendarDetail(calendarDetail); // 반환
  }

  // 일정 수정 - put
  @Transactional
  public ResponseCalendarDetail putCalendarDetail(Calendar_detail c, long calendarDetailId) {
    Optional<Calendar_detail> originCalendarDetail = calendarDetailRepository.findById(calendarDetailId);
    if (originCalendarDetail.isPresent()) {
      Calendar_detail calendarDetail = originCalendarDetail.get();
      calendarDetail.update( c.getTitle(), c.getContent(),
          c.getStartDay(), c.getEndDay(), c.getStartTime(), c.getEndTime());
      return calendarDetailMapper.toResponseCalendarDetail(calendarDetail);
    } else {
      throw new IllegalArgumentException("존재하는 일정이 아닙니다.");
    }
  }

  // 일정 삭제 - delete
  @Transactional
  public void deleteCalendarDetail(long id) {
    Optional<Calendar_detail> originCalendarDetail = calendarDetailRepository.findById(id);
    if (originCalendarDetail.isPresent()) {
      calendarDetailRepository.deleteById(id);
    } else {
      throw new IllegalArgumentException("존재하는 일정이 아닙니다.");
    }
  }
}
