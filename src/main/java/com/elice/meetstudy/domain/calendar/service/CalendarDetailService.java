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
import com.elice.meetstudy.domain.user.domain.UserPrinciple;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    } else throw new EntityNotFoundException("캘린더가 존재하지 않습니다.");
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
    //접근한 유저 정보 가져오는 로직
    long userId = getUserId();

    List<Calendar_detail> calendarDetailList =
        getCalendarDetailsFromCalendar(userId, studyRoomId, year, month);
    List<ResponseCalendarDetail> responseCalendarDetails = new ArrayList<>();
    for (Calendar_detail calendarDetail : calendarDetailList) {
      responseCalendarDetails.add(calendarDetailMapper.toResponseCalendarDetail(calendarDetail));
    }
    return responseCalendarDetails;
  }

// 개인 캘린더, 공용 캘린더 통합 리턴
  @Transactional
  public List<ResponseAllCalendarDetail> getAllCalendarDetailFromAll(
      String year, String month) {
    List<ResponseAllCalendarDetail> responseCalendarDetails = new ArrayList<>();
    //접근한 유저 정보 가져오는 로직
    long userId = getUserId();
    Optional<User> user = userRepository.findById(userId);
    List<UserStudyRoom> byUser = userStudyRoomRepository.findByUser(user.get());

    getCalendarDetails(responseCalendarDetails, year, month, 0L, userId);
    for(UserStudyRoom userStudyRoom : byUser){
     getCalendarDetails(responseCalendarDetails,year, month,
          userStudyRoom.getStudyRoom().getId(), userId);
    }
    return responseCalendarDetails;
  }

  /**
   * 개별 일정 조회
   * @param id
   * @return
   */
  @Transactional
  public ResponseCalendarDetail getCalendarDetail(long id) {
    Optional<Calendar_detail> calendarDetail = calendarDetailRepository.findById(id);
    if (calendarDetail.isPresent()) {
      return calendarDetailMapper.toResponseCalendarDetail(calendarDetail.get());
    } else throw new EntityNotFoundException("일정이 존재하지 않습니다.");
  }

  /**
   * 일정 추가
   *
   * @param requestCalendarDetail
   * @param studyRoomId
   * @return
   */
  @Transactional
  public ResponseCalendarDetail saveCalendarDetail(
      RequestCalendarDetail requestCalendarDetail, Long studyRoomId) { // request로 받으면
    //접근한 유저 정보 가져오는 로직
    long userId = getUserId();
    Calendar_detail calendarDetail = calendarDetailMapper.toCalendarDetail(requestCalendarDetail);
    Calendar calendar = calendarService.findCalendar(userId, studyRoomId); // 캘린더 찾아서
    calendarDetail.setCalendar(calendar); // 캘린더 추가해주고
    calendarDetailRepository.save(calendarDetail); // 저장
    return calendarDetailMapper.toResponseCalendarDetail(calendarDetail); // 반환
  }

  /**
   * 일정 수정
   *
   * @param re
   * @param calendarDetailId
   * @return
   */
  @Transactional
  public ResponseCalendarDetail putCalendarDetail(RequestCalendarDetail re,
      long calendarDetailId) {
    Optional<Calendar_detail> originCalendarDetail = calendarDetailRepository.findById(calendarDetailId);

    if (originCalendarDetail.isPresent()) {
      Calendar_detail calendarDetail = originCalendarDetail.get();
      calendarDetail.update( re.title(), re.content(), re.startDay(), re.endDay(),
          re.startTime(), re.endTime());
      return calendarDetailMapper.toResponseCalendarDetail(calendarDetail);
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

  @Transactional
  public long getUserId(){
    //접근한 유저 정보 가져오는 로직
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserPrinciple userPrinciple = (UserPrinciple)authentication.getPrincipal();
    return Long.parseLong(userPrinciple.getUserId());
  }

  //캘린더 찾아서 해당 캘린더 한 달 리스트를 반환
  public List<Calendar_detail> getCalendarDetailsFromCalendar(long userId, long studyRoomId,
      String year, String month){
    Calendar calendar = calendarService.findCalendar(userId, studyRoomId); // 캘린더 찾아서
    try {
      saveHolidays(year, month, calendar.getId()); // 공휴일 일정 등록
    }catch (EntityNotFoundException e){
      throw new EntityNotFoundException("캘린더가 존재하지 않습니다.");
    }
    String Month = String.format("%02d", Integer.parseInt(month));
    String date = year + Month;

    List<Calendar_detail> calendarDetailList =
        calendarDetailRepository.findByStartDayContainingAndCalendar(date, calendar); // 해당 캘린더의 한 달 일정들을 리스트로 출력

    return calendarDetailList;
  }

  // 한 달 리스트를 responseAll로 반환
  public List<ResponseAllCalendarDetail> getCalendarDetails(
      List<ResponseAllCalendarDetail> responseCalendarDetails,
      String year, String month, long studyRoomId, long userId){

    List<Calendar_detail> calendarDetailList =
        getCalendarDetailsFromCalendar(userId, studyRoomId, year, month);
    System.out.println(calendarDetailList.size());

    for (Calendar_detail calendarDetail : calendarDetailList) {
      responseCalendarDetails.add(
          new ResponseAllCalendarDetail(calendarDetail));
    }
    return  responseCalendarDetails;
  }
}
