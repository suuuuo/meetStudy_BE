package com.elice.meetstudy.domain.calendar.controller;

import com.elice.meetstudy.domain.calendar.domain.Calendar_detail;
import com.elice.meetstudy.domain.calendar.dto.RequestCalendarDetail;
import com.elice.meetstudy.domain.calendar.dto.ResponseCalendarDetail;
import com.elice.meetstudy.domain.calendar.mapper.CalendarDetailMapper;
import com.elice.meetstudy.domain.calendar.service.CalendarDetailService;
import com.elice.meetstudy.domain.calendar.service.CalendarService;
import jakarta.validation.Valid;
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

    private final CalendarService calendarService;
    private final CalendarDetailService calendarDetailService;
    private final CalendarDetailMapper calendarDetailMapper;
   //private final TokenProvider tokenProvider;

    @Autowired
    public CalendarController(CalendarService calendarService,
        CalendarDetailService calendarDetailService, CalendarDetailMapper calendarDetailMapper
        /*,TokenProvider tokenProvider*/) {
        this.calendarService = calendarService;
        this.calendarDetailService = calendarDetailService;
        this.calendarDetailMapper = calendarDetailMapper;
        //this.tokenProvider = tokenProvider;
    }

    //개인 캘린더 전체 조회
    @GetMapping("/calendar")
    public ResponseEntity<?> getCalendarDetails(
        @RequestHeader("year") String year, @RequestHeader("month") String month
        /*,@RequestHeader("access") String token*/) {

        //user Id 구하는 로직
        //TokenValidationResult validationResult = tokenProvider.validateToken(token);
        //임시
        long userId = 1L;

        if (userId == 1L /*validationResult.isValid() 유효한 토큰이면?*/) {
            //String loginId = validationResult.getLoginId();

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
        @PathVariable long study_room_id  /*@RequestHeader("access") String token 토큰 ..*/) {

        //user Id 구하는 로직, 아래는 임시
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
        /*@RequestHeader("access") String token 토큰 ..*/
        @PathVariable long calendar_detail_id) {
        ResponseCalendarDetail responseCalendarDetail = calendarDetailService.getCalendarDetail(
            calendar_detail_id);
        return ResponseEntity.ok(responseCalendarDetail);
    }

    //개인 캘린더 일정 추가
    @PostMapping("/calendar")
    public ResponseEntity<?> postCalendarDetail(
        @RequestBody @Valid RequestCalendarDetail requestCalendarDetail
        /*@RequestHeader("access") String token 토큰 ..*/) {

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
        @RequestBody @Valid RequestCalendarDetail requestCalendarDetail,
        @PathVariable long study_room_id
        /*@RequestHeader("access") String token 토큰 ..*/) {

        //user Id 구하는 로직
        Long userId = 1L;

        Calendar_detail calendarDetail = calendarDetailMapper.toCalendarDetail(requestCalendarDetail);

        ResponseCalendarDetail responseCalendarDetail =
            calendarDetailService.saveCalendarDetail(calendarDetail, userId, study_room_id);
        return ResponseEntity.ok(responseCalendarDetail);
    }


    //캘린더 일정 수정 - put
    @PutMapping("/calendar_detail/{calendar_detail_id}")
    public ResponseEntity<?> putCalendarDetail(
        @RequestBody @Valid RequestCalendarDetail requestCalendarDetail,
        @PathVariable long calendar_detail_id
        /*@RequestHeader("access") String token 토큰 ..*/) {

        if(requestCalendarDetail == null){
            Map<String, Object> response = new HashMap<>();
            response.put("message", "유효하지 않은 일정입니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }else{
            Calendar_detail calendarDetail = calendarDetailMapper.toCalendarDetail(requestCalendarDetail);
            ResponseCalendarDetail responseCalendarDetail =
                calendarDetailService.putCalendarDetail(calendarDetail, calendar_detail_id);
            return ResponseEntity.ok(responseCalendarDetail);
        }
    }

    //일정 삭제 - delete
    @DeleteMapping("/calendar_detail/{calendar_detail_id}")
    public void deleteCalendarDetail(
        /*@RequestHeader("access") String token 토큰 ..*/@PathVariable long calendar_detail_id){
        calendarDetailService.deleteCalendarDetail(calendar_detail_id);
    }

    //회원 삭제 - 개인 캘린더 삭제
    @DeleteMapping("/calendar")
    public void deleteUserCalendar(
        /*@RequestHeader("access") String token 토큰 ..*/){

        //임시 id
        long userId = 1L;
        calendarService.deleteCalendar(userId);
    }

    //회원 삭제 - 공용 캘린더 삭제
    @DeleteMapping("/calendar/{study_room_id}")
    public void deleteStudyCalendar(
        /*@RequestHeader("access") String token 토큰 ..*/ @PathVariable long study_room_id){

        //임시 id
        long userId = 1L; //회원인지만 확인
        calendarService.deleteStudyCalendar(study_room_id);
    }
}
