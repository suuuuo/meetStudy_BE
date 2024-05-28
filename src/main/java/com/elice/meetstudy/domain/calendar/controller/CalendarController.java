package com.elice.meetstudy.domain.calendar.controller;

import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import com.elice.meetstudy.domain.calendar.dto.DeleteRequestCalendarDetail;
import com.elice.meetstudy.domain.calendar.dto.RequestCalendarDetail;
import com.elice.meetstudy.domain.calendar.dto.ResponseCalendarDetail;
import com.elice.meetstudy.domain.calendar.mapper.CalendarDetailMapper;
import com.elice.meetstudy.domain.calendar.service.CalendarDetailService;
import com.elice.meetstudy.domain.calendar.service.CalendarService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CalendarController {

    @Autowired
    CalendarService calendarService;
    @Autowired
    CalendarDetailService calendarDetailService;

    private final CalendarDetailMapper calendarDetailMapper;

    public CalendarController(CalendarDetailMapper calendarDetailMapper) {
        this.calendarDetailMapper = calendarDetailMapper;
    }

    //개인 캘린더 전체 조회
    @GetMapping("/calendar") //userId 받아오는 건 추후에 추가
    public ResponseEntity<?> getCalendarDetails(
        @RequestHeader("year") String year, @RequestHeader("month") String month
        /*userId ..*/) {

        //user Id 구하는 로직, 임시
        long userId = 1L;

        if (userId == 1L /*유효한 유저 id라면 */) {
            // 캘린더 찾아서 year, month로 해당 월의 일정들 반환
            List<ResponseCalendarDetail> calendarDetailList =
                calendarDetailService.getAllCalendarDetail(year, month, userId, 0L);

            HttpHeaders headers = new HttpHeaders();
            headers.add("first-day", calendarService.findFirstDay(year,month));
            headers.add("last-day", calendarService.findLastDay(year,month));
            return new ResponseEntity<>(calendarDetailList, headers, HttpStatus.OK);

        } else {
            //유효한 유저 아님 에러 = 회원이 아니라 캘린더 조회할 수 없음
            Map<String, Object> response = new HashMap<>();
            response.put("message", "캘린더 조회 권한이 없습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    //공용 캘린더 전체 조회
    @GetMapping("/calendar/{study_room_id}")
    public ResponseEntity<?> getCalendarDetails(
        @RequestHeader("year") String year, @RequestHeader("month") String month,
        @PathVariable long study_room_id /*userId .. 헤더 액세스 jwt 토큰?*/) {

        //user Id 구하는 로직
        long userId = 1L;

        //userId, studyroomId로 캘린더 찾아서 year, month로 해당 월의 일정들 반환
        List<ResponseCalendarDetail> calendarDetailList =
            calendarDetailService.getAllCalendarDetail(year, month, userId, study_room_id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("first-day", calendarService.findFirstDay(year,month));
        headers.add("last-day", calendarService.findLastDay(year,month));
        return new ResponseEntity<>(calendarDetailList, headers, HttpStatus.OK);
    }

    //캘린더 - 일정 개별 조회
    @GetMapping("/calendar_detail/{calendar_detail_id}")
    public ResponseEntity<?> getCalendarDetail(
        /*userId ..*/
        @PathVariable long calendar_detail_id) {
        ResponseCalendarDetail responseCalendarDetail = calendarDetailService.getCalendarDetail(
            calendar_detail_id);
        return ResponseEntity.ok(responseCalendarDetail);
    }

    //개인 캘린더 일정 추가
    @PostMapping("/calendar")
    public ResponseEntity<?> postCalendarDetail(
        @RequestBody RequestCalendarDetail requestCalendarDetail
        /*userId .. */) {

        //user Id 구하는 로직
        Long userId = 1L;

        Calendar_detail calendarDetail = calendarDetailMapper.toCalendarDetail(requestCalendarDetail);

        ResponseCalendarDetail responseCalendarDetail =
            calendarDetailService.saveCalendarDetail(calendarDetail, userId, 0L);
        return ResponseEntity.ok(responseCalendarDetail);
    }

    //공용 캘린더 일정 추가
    @PostMapping("/calendar/{study_room_id}")
    public ResponseEntity<?> postCalendarDetail(
        @RequestBody RequestCalendarDetail requestCalendarDetail,
        @PathVariable long study_room_id
        /*userId .. 헤더 액세스 jwt 토큰?*/) {

        //user Id 구하는 로직
        Long userId = 1L;

        Calendar_detail calendarDetail = calendarDetailMapper.toCalendarDetail(requestCalendarDetail);

        ResponseCalendarDetail responseCalendarDetail =
            calendarDetailService.saveCalendarDetail(calendarDetail, userId, study_room_id);
        return ResponseEntity.ok(responseCalendarDetail);
    }


    //캘린더 일정 수정 - put
    @PutMapping("/calendar_detail")
    public ResponseEntity<?> putCalendarDetail(
        @RequestBody RequestCalendarDetail requestCalendarDetail
        /*userId .. 헤더 액세스 jwt 토큰?*/) {

        Calendar_detail calendarDetail = calendarDetailMapper.toCalendarDetail(requestCalendarDetail);

        ResponseCalendarDetail responseCalendarDetail =
            calendarDetailService.putCalendarDetail(calendarDetail);

        if(requestCalendarDetail == null){
            Map<String, Object> response = new HashMap<>();
            response.put("message", "바꾸려는 일정이 유효하지 않은 일정입니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } return ResponseEntity.ok(responseCalendarDetail);
    }

    //일정 삭제 - delete
    @DeleteMapping("/calendar_detail")
    public void deleteCalendarDetail(
        @RequestBody DeleteRequestCalendarDetail deleteRequestCalendarDetail
        /*userId .. 헤더 액세스 jwt 토큰?*/){



        calendarDetailService.deleteCalendarDetail(deleteRequestCalendarDetail.id());
    }

    //회원 삭제 - 개인 / 공용 캘린더 삭제 delete
    @DeleteMapping("/calendar")
    public void deleteCalendar(
        @RequestBody DeleteRequestCalendarDetail deleteRequestCalendarDetail
        /*userId .. 헤더 액세스 jwt 토큰?*/
    ){
        calendarService.deleteCalendar(deleteRequestCalendarDetail.id());
    }

}
