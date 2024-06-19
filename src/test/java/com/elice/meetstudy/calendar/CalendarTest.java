package com.elice.meetstudy.calendar;

import com.elice.meetstudy.domain.calendar.domain.Calendar;
import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import com.elice.meetstudy.domain.calendar.dto.ResponseCalendarDetail;
import com.elice.meetstudy.domain.calendar.mapper.CalendarDetailMapper;
import com.elice.meetstudy.domain.calendar.repository.CalendarDetailRepository;
import com.elice.meetstudy.domain.calendar.service.CalendarDetailService;
import com.elice.meetstudy.domain.calendar.service.CalendarService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class CalendarTest {

    @Autowired
    CalendarService calendarService;

    @Autowired
    CalendarDetailService calendarDetailService;

    @Autowired
    CalendarDetailMapper calendarDetailMapper;

    @Autowired
    CalendarDetailRepository calendarDetailRepository;


//    @Test
//    @DisplayName("개인 캘린더 (생성)조회하기")
//    void getUserCalendar(){
//        long userId = 1L;
//
//        Calendar calendar = calendarService.findCalendar(userId, 0L);
//
//        assertThat(calendar.getUser().getId()).isEqualTo(userId);
//    }
//
//    @Test
//    @DisplayName("공용 캘린더 (생성)조회하기")
//    void getStudyCalendar(){
//        long userId = 1L;
//        long studyId = 1L;
//
//        Calendar calendar = calendarService.findCalendar(userId, studyId);
//
//        assertThat(calendar.getUser().getId()).isEqualTo(userId);
//        assertThat(calendar.getStudyRoom().getId()).isEqualTo(studyId);
//    }
//
//    @Test
//    @DisplayName("개인 캘린더 일정 추가 - 조회하기")
//    void postCalendarDetailAndGetCalendarDetail(){
//        long userId = 1L;
//
//        Calendar calendar = calendarService.findCalendar(userId, 0L);
//        Calendar_detail calendarDetail = new Calendar_detail("테스트 일정",
//            "테스트 일정입니다", "20240527", "20240527",
//            "0000", "1159", false);
//
//        ResponseCalendarDetail responseCalendarDetail =
//            calendarDetailService.saveCalendarDetail(calendarDetail, userId, 0L);
//        ResponseCalendarDetail res = calendarDetailService.getCalendarDetail(responseCalendarDetail.id());
//        Optional<Calendar_detail> calendarDetail1
//            = calendarDetailRepository.findById(responseCalendarDetail.id());
//
//        assertThat(res.title()).isEqualTo("테스트 일정");
//        assertThat(calendarDetail1.get().getCalendar().getId()).isEqualTo(calendar.getId());
//
//    }
//
//    @Test
//    @DisplayName("공용 캘린더 일정 추가 - 조회하기")
//    void postCalendarDetailAndGetCalendarDetail2(){
//        long userId = 1L;
//        long studyRoomId = 1L;
//
//        Calendar calendar = calendarService.findCalendar(userId, studyRoomId);
//        Calendar_detail calendarDetail = new Calendar_detail("테스트 일정",
//            "테스트 일정입니다", "20240527", "20240527",
//            "0000", "1159", false);
//
//        ResponseCalendarDetail responseCalendarDetail =
//            calendarDetailService.saveCalendarDetail(calendarDetail, userId, studyRoomId);
//        ResponseCalendarDetail res = calendarDetailService.getCalendarDetail(responseCalendarDetail.id());
//        Optional<Calendar_detail> calendarDetail1
//            = calendarDetailRepository.findById(responseCalendarDetail.id());
//
//        assertThat(res.title()).isEqualTo("테스트 일정");
//        assertThat(calendarDetail1.get().getCalendar().getId()).isEqualTo(calendar.getId());
//
//    }
//
//    @Test
//    @DisplayName("개인 캘린더 일정 삭제하기")
//    void deleteCalendarDetail(){
//        long userId = 1L;
//
//        Calendar_detail calendarDetail = new Calendar_detail("테스트 일정",
//            "테스트 일정입니다", "20240527", "20240527",
//            "0000", "1159", false);
//        ResponseCalendarDetail responseCalendarDetail =
//            calendarDetailService.saveCalendarDetail(calendarDetail, userId, 0L);
//
//        List<ResponseCalendarDetail> allCalendarDetail = calendarDetailService.getAllCalendarDetail(
//            "2024", "05", userId, 0L);
//        int listSize = allCalendarDetail.size();
//
//        calendarDetailService.deleteCalendarDetail(responseCalendarDetail.id());
//
//        List<ResponseCalendarDetail> allCalendarDetail1 = calendarDetailService.getAllCalendarDetail(
//            "2024", "05", userId, 0L);
//        int listSize1 = allCalendarDetail1.size();
//
//        assertThat(listSize1).isEqualTo(listSize-1);
//
//    }
//
//    @Test
//    @DisplayName("공용 캘린더 일정 삭제하기")
//    void deleteCalendarDetail2(){
//        long userId = 1L;
//        long studyRoomId = 1L;
//
//        Calendar_detail calendarDetail = new Calendar_detail("테스트 일정",
//            "테스트 일정입니다", "20240527", "20240527",
//            "0000", "1159", false);
//        ResponseCalendarDetail responseCalendarDetail =
//            calendarDetailService.saveCalendarDetail(calendarDetail, userId, studyRoomId);
//
//        List<ResponseCalendarDetail> allCalendarDetail = calendarDetailService.getAllCalendarDetail(
//            "2024", "05", userId, studyRoomId);
//        int listSize = allCalendarDetail.size();
//
//        calendarDetailService.deleteCalendarDetail(responseCalendarDetail.id());
//
//        List<ResponseCalendarDetail> allCalendarDetail1 = calendarDetailService.getAllCalendarDetail(
//            "2024", "05", userId, studyRoomId);
//        int listSize1 = allCalendarDetail1.size();
//
//        assertThat(listSize1).isEqualTo(listSize-1);
//
//    }


}
