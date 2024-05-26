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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalendarDetailService {

    @Autowired
    private CalendarDetailRepository calendarDetailRepository;
    @Autowired
    private CalendarRepository calendarRepository;
    @Autowired
    private CalendarService calendarService;
    @Autowired
    private HolidayService holidayService;

    private final CalendarDetailMapper calendarDetailMapper;

    public CalendarDetailService(CalendarDetailMapper calendarDetailMapper) {
        this.calendarDetailMapper = calendarDetailMapper;
    }

    //캘린더 공휴일 일정 자동 등록 - 작성 완료, 테스트 전
    @Transactional
    public void saveHolidays(String year, String month, long calendarId) {
        List<Holiday> holidayList = holidayService.Holiday(year, month);
        Optional<Calendar> calendar = calendarRepository.findById(calendarId);

        for (Holiday holiday : holidayList) {
            if (calendarDetailRepository.existsByStartDay(holiday.getDate())) {
                continue;
            } else { //현재 캘린더에 공휴일 정보가 없으면 등록함
                Calendar_detail c = Calendar_detail.builder()
                    .title(holiday.getName())
                    .content("")
                    .startDay(holiday.getDate())
                    .endDay(holiday.getDate())
                    .startTime("0000")
                    .endTime("2359")
                    .isHoliday(true)
                    .build();
                c.setCalendar(calendar.get());
                calendarDetailRepository.save(c);
            }
        }
    }

    //일정 리스트 출력 - 작성 완료, 테스트 전
    @Transactional
    public List<ResponseCalendarDetail> getAllCalendarDetail(String year, String month, Long userId,
        Long studyRoomId) {
        Calendar calendar = calendarService.findCalendar(userId, studyRoomId); //캘린더 찾아서
        saveHolidays(year, month, calendar.getId()); //공휴일 일정 등록
        List<Calendar_detail> calendarDetailList = calendarDetailRepository.findAllByCalendar(
            calendar); //해당 캘린더의 일정들 리스트로 출력

        List<ResponseCalendarDetail> responseCalendarDetails = new ArrayList<>();
        for (Calendar_detail calendarDetail : calendarDetailList) {
            responseCalendarDetails.add(
                calendarDetailMapper.toResponseCalendarDetail(calendarDetail));
        }
        return responseCalendarDetails;
    }

    @Transactional
    public ResponseCalendarDetail getCalendarDetail(long id){
        Optional<Calendar_detail> calendarDetail = calendarDetailRepository.findById(id);
        return calendarDetailMapper.toResponseCalendarDetail(calendarDetail.get());
    }

    //request 받아서 일정 추가 - 작성 완료, 테스트 전
    @Transactional
    public ResponseCalendarDetail saveCalendarDetail(RequestCalendarDetail requestCalendarDetail,
        Long userId, Long studyRoomId) { //request로 받으면
        Calendar_detail calendarDetail = calendarDetailMapper.toCalendarDetail(
            requestCalendarDetail);
        Calendar calendar = calendarService.findCalendar(userId, studyRoomId); //캘린더 찾아서
        calendarDetail.setCalendar(calendar); //캘린더 추가해주고
        calendarDetailRepository.save(calendarDetail); //저장
        return calendarDetailMapper.toResponseCalendarDetail(calendarDetail); //반환
    }

    //일정 수정 - put
    @Transactional
    public ResponseCalendarDetail putCalendarDetail(RequestCalendarDetail requestCalendarDetail) {
        Optional<Calendar_detail> originCalendarDetail = calendarDetailRepository.findById(
            requestCalendarDetail.id());
        if(originCalendarDetail.isPresent()){
            Calendar_detail calendarDetail = originCalendarDetail.get();
            if(requestCalendarDetail.title() != null) calendarDetail.setTitle(requestCalendarDetail.title());
            if(requestCalendarDetail.content() != null) calendarDetail.setContent(requestCalendarDetail.content());
            if(requestCalendarDetail.startDay() != null) calendarDetail.setStartDay(requestCalendarDetail.startDay());
            if(requestCalendarDetail.endDay() != null) calendarDetail.setEndDay(requestCalendarDetail.endDay());
            if(requestCalendarDetail.startTime() != null) calendarDetail.setStartTime(requestCalendarDetail.startTime());
            if(requestCalendarDetail.endTime() != null) calendarDetail.setEndDay(requestCalendarDetail.endTime());
            return calendarDetailMapper.toResponseCalendarDetail(calendarDetail);
        }
        return null;
    }

    //일정 삭제 - delete
    @Transactional
    public void deleteCalendarDetail(RequestCalendarDetail requestCalendarDetail) {
        Optional<Calendar_detail> originCalendarDetail = calendarDetailRepository.findById(
            requestCalendarDetail.id());
        if(originCalendarDetail.isPresent())
            calendarDetailRepository.deleteById(requestCalendarDetail.id());
    }

    //회원 삭제 - 개인 / 공용 캘린더 삭제 delete
    //스터디룸 삭제 - 공용 캘린더 삭제 delete

}
