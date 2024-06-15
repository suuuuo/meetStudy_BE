package com.elice.meetstudy.domain.calendar.service;

import com.elice.meetstudy.domain.calendar.domain.Calendar;
import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import com.elice.meetstudy.domain.calendar.dto.RequestCalendarDetail;
import com.elice.meetstudy.domain.calendar.dto.ResponseAllCalendarDetail;
import com.elice.meetstudy.domain.calendar.dto.ResponseCalendarDetail;
import com.elice.meetstudy.domain.calendar.holiday.domain.Holiday;
import com.elice.meetstudy.domain.calendar.holiday.service.HolidayService;
import com.elice.meetstudy.domain.calendar.mapper.CalendarDetailMapper;
import com.elice.meetstudy.domain.calendar.repository.CalendarDetailRepository;
import com.elice.meetstudy.domain.calendar.repository.CalendarRepository;
import com.elice.meetstudy.domain.studyroom.entity.UserStudyRoom;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.domain.studyroom.repository.UserStudyRoomRepository;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import com.elice.meetstudy.util.EntityFinder;
import io.lettuce.core.dynamic.annotation.CommandNaming.Strategy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CalendarDetailService {

  private final CalendarDetailRepository calendarDetailRepository;
  private final CalendarRepository calendarRepository;
  private final CalendarService calendarService;
  private final HolidayService holidayService;
  private final CalendarDetailMapper calendarDetailMapper;
  private final UserRepository userRepository;
  private final UserStudyRoomRepository userStudyRoomRepository;
  private final EntityFinder entityFinder;

  /**
   * 캘린더 공휴일 자동 등록 -> 조회 시 공휴일 표시
   *
   * @param year
   * @param month
   * @param calendarId
   */
  @Transactional
  public void saveHolidays(String year, String month, long calendarId)
      throws EntityNotFoundException {
    List<Holiday> holidayList = holidayService.Holiday(year, month);
    Calendar calendar = calendarRepository
        .findById(calendarId)
        .orElseThrow(()->new EntityNotFoundException("캘린더가 존재하지 않습니다."));

      for (Holiday holiday : holidayList) {
      if (calendarDetailRepository.existsByStartDayAndCalendarIdAndIsHolidayIsTrue(
          holiday.getDate(), calendarId)) {
      } else { // 현재 캘린더에 공휴일 정보가 없으면 등록함
        Calendar_detail c = new Calendar_detail();
        c.setTitle(holiday.getName());
        c.setStartDay(holiday.getDate());
        c.setHoliday(true);
        c.setCalendar(calendar);
        calendarDetailRepository.save(c);
      }
    }
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
  public List<ResponseCalendarDetail> getAllCalendarDetail(
      String year, String month, Long studyRoomId) {

    Long userId = entityFinder.getUser().getId();

    List<Calendar_detail> calendarDetailList =
        getCalendarDetailsFromCalendar(userId, studyRoomId, year, month);
    List<ResponseCalendarDetail> responseCalendarDetails = new ArrayList<>();
    for (Calendar_detail calendarDetail : calendarDetailList) {
      List<LocalDateTime> times = localDateTimes(calendarDetail);
      responseCalendarDetails.add(
          new ResponseCalendarDetail(calendarDetail, times.get(0), times.get(1)));
    }
    return responseCalendarDetails;
  }

  // 개인 캘린더, 공용 캘린더 통합 리턴
  @Transactional
  public Set<ResponseAllCalendarDetail> getAllCalendarDetailFromAll(String year, String month) {
    List<ResponseAllCalendarDetail> responseCalendarDetails = new ArrayList<>();

    Long userId = entityFinder.getUser().getId();

    User user = userRepository
        .findById(userId)
        .orElseThrow(()->new EntityNotFoundException("사용자가 존재하지 않습니다."));
    List<UserStudyRoom> byUser = userStudyRoomRepository.findByUser(user);

    getCalendarDetails(responseCalendarDetails, year, month, 0L, userId);
    for (UserStudyRoom userStudyRoom : byUser) {
      getCalendarDetails(
          responseCalendarDetails, year, month, userStudyRoom.getStudyRoom().getId(), userId);
    }
    return new HashSet<>(responseCalendarDetails);
  }

  /**
   * 개별 일정 조회
   *
   * @param id
   * @return
   */
  @Transactional
  public ResponseCalendarDetail getCalendarDetail(long id) {
    Calendar_detail calendarDetail =
        calendarDetailRepository
            .findById(id)
            .orElseThrow(()->new EntityNotFoundException("일정이 존재하지 않습니다."));

      List<LocalDateTime> times = localDateTimes(calendarDetail);
      return new ResponseCalendarDetail(calendarDetail, times.get(0), times.get(1));
  }

  /**
   * 일정 추가
   *
   * @param re
   * @param studyRoomId
   * @return
   */
  @Transactional
  public ResponseCalendarDetail saveCalendarDetail(RequestCalendarDetail re, Long studyRoomId) {
    Long userId = entityFinder.getUser().getId();
    Calendar_detail firstCalendarDetail = null;

    LocalDate startDate = LocalDate.parse(re.startDay(), DateTimeFormatter.BASIC_ISO_DATE);
    LocalDate endDate = LocalDate.parse(re.endDay(), DateTimeFormatter.BASIC_ISO_DATE);

    List<Calendar_detail> calendarDetails = new ArrayList<>();

    for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
      Calendar_detail calendarDetail = calendarDetailMapper.toCalendarDetail(re);
      Calendar calendar = calendarService.findCalendar(userId, studyRoomId); // 캘린더 찾아서
      calendarDetail.setCalendar(calendar); // 캘린더 추가해주고
      calendarDetail.setStartDay(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
      calendarDetail.setEndDay(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

      calendarDetails.add(calendarDetail);
    }

    calendarDetailRepository.saveAll(calendarDetails);

    firstCalendarDetail = calendarDetails.get(0);
    List<LocalDateTime> times = localDateTimes(firstCalendarDetail);
    return new ResponseCalendarDetail(firstCalendarDetail, times.get(0), times.get(1));
  }

  /**
   * 일정 수정
   *
   * @param re
   * @param calendarDetailId
   * @return
   */
  @Transactional
  public ResponseCalendarDetail putCalendarDetail(RequestCalendarDetail re, long calendarDetailId) {
    Optional<Calendar_detail> originCalendarDetail =
        calendarDetailRepository.findById(calendarDetailId);

    if (originCalendarDetail.isPresent()) {
      Calendar_detail calendarDetail = originCalendarDetail.get();
      calendarDetail.update(
          re.title(), re.content(), re.startDay(), re.endDay(), re.startTime(), re.endTime());

      List<LocalDateTime> times = localDateTimes(calendarDetail);
      return new ResponseCalendarDetail(calendarDetail, times.get(0), times.get(1));

    } else throw new EntityNotFoundException("일정이 존재하지 않습니다.");
  }

  /**
   * 일정 삭제
   *
   * @param id
   * @return
   */
  @Transactional
  public void deleteCalendarDetail(long id) {
    calendarDetailRepository.deleteById(id);
  }

  // 캘린더 찾아서 해당 캘린더 한 달 리스트를 반환
  public List<Calendar_detail> getCalendarDetailsFromCalendar(
      long userId, long studyRoomId, String year, String month) {
    Calendar calendar = calendarService.findCalendar(userId, studyRoomId); // 캘린더 찾아서
    saveHolidays(year, month, calendar.getId()); // 공휴일 일정 등록
    String Month = String.format("%02d", Integer.parseInt(month));
    String date = year + Month;

    // 해당 캘린더의 한 달 일정들을 리스트로 출력
    List<Calendar_detail> calendarDetailList =
        calendarDetailRepository.findByStartDayContainingAndCalendar(date, calendar);

    return calendarDetailList;
  }

  // 한 달 리스트를 responseAll로 반환
  public List<ResponseAllCalendarDetail> getCalendarDetails(
      List<ResponseAllCalendarDetail> responseCalendarDetails,
      String year,
      String month,
      long studyRoomId,
      long userId) {

    List<Calendar_detail> calendarDetailList =
        getCalendarDetailsFromCalendar(userId, studyRoomId, year, month);

    for (Calendar_detail calendarDetail : calendarDetailList) {
      List<LocalDateTime> times = localDateTimes(calendarDetail);

      responseCalendarDetails.add(new ResponseAllCalendarDetail(calendarDetail, times.get(0), times.get(1)));
    }
    return responseCalendarDetails;
  }

  public List<LocalDateTime> localDateTimes(Calendar_detail calendarDetail) {
    String startDay = calendarDetail.getStartDay();
    String startTime = calendarDetail.getStartTime();
    String endDay = calendarDetail.getEndDay();
    String endTime = calendarDetail.getEndTime();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");

    if (endDay == null) endDay = startDay;
    if (startTime == null) startTime = "00:00:00";
    if (endTime == null) endTime = "23:59:59";

    LocalDateTime startDateTime = LocalDateTime.parse(startDay + " " + startTime, formatter);
    LocalDateTime endDateTime = LocalDateTime.parse(endDay + " " + endTime, formatter);

    List<LocalDateTime> dateTimeList = new ArrayList<>();
    dateTimeList.add(startDateTime);
    dateTimeList.add(endDateTime);

    return dateTimeList;
  }
}
