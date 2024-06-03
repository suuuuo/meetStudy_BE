package com.elice.meetstudy.domain.calendar.controller;

import com.elice.meetstudy.domain.calendar.dto.RequestCalendarDetail;
import com.elice.meetstudy.domain.calendar.service.CalendarDetailService;
import com.elice.meetstudy.domain.calendar.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@Tag(name = "캘린더", description = "캘린더 관련 API 입니다.")
public class CalendarController {

    private final CalendarService calendarService;
    private final CalendarDetailService calendarDetailService;

    @Autowired
    public CalendarController(
            CalendarService calendarService, CalendarDetailService calendarDetailService) {
        this.calendarService = calendarService;
        this.calendarDetailService = calendarDetailService;
    }

    /**
     * 개인 캘린더 전체 조회(공휴일 포함)
     *
     * @param year
     * @param month
     * @return
     */
    @Operation(summary = "캘린더 조회", description = "개인 캘린더 조회입니다. 공휴일도 함께 조회됩니다.")
    @GetMapping("/calendar")
    public ResponseEntity<?> getCalendarDetails(
            @RequestHeader("year") String year, @RequestHeader("month") String month) {
        return calendarDetailService.getAllCalendarDetail(year, month, 0L);
    }

    /**
     * 공용 캘린더 전체 조회 (공휴일 포함)
     *
     * @param year
     * @param month
     * @param study_room_id
     * @return
     */
    @Operation(summary = "캘린더 조회", description = "공용 캘린더 조회입니다. 공휴일도 함께 조회됩니다.")
    @GetMapping("/calendar/{study_room_id}")
    public ResponseEntity<?> getCalendarDetails(
            @RequestHeader("year") String year,
            @RequestHeader("month") String month,
            @PathVariable long study_room_id) {
        return calendarDetailService.getAllCalendarDetail(year, month, study_room_id);
    }

    /**
     * 일정 개별 조회
     *
     * @param calendar_detail_id
     * @return
     */
    @Operation(summary = "캘린더 일정 조회", description = "개별 일정 조회입니다.")
    @GetMapping("/calendar_detail/{calendar_detail_id}")
    public ResponseEntity<?> getCalendarDetail(@PathVariable long calendar_detail_id) {
        return calendarDetailService.getCalendarDetail(calendar_detail_id);
    }

    /**
     * 개인 캘린더 일정 추가
     *
     * @param requestCalendarDetail
     * @return
     */
    @Operation(summary = "개인 캘린더 일정 등록", description = "개인 캘린더에 일정을 등록합니다.")
    @PostMapping("/calendar")
    public ResponseEntity<?> postCalendarDetail(
            @RequestBody @Valid RequestCalendarDetail requestCalendarDetail) {
        return calendarDetailService.saveCalendarDetail(requestCalendarDetail, 0L);
    }

    /**
     * 공용 캘린더 일정 추가
     *
     * @param requestCalendarDetail
     * @param study_room_id
     * @return
     */
    @Operation(summary = "공용 캘린더 일정 등록", description = "공용 캘린더에 일정을 등록합니다.")
    @PostMapping("/calendar/{study_room_id}")
    public ResponseEntity<?> postCalendarDetail(
            @RequestBody @Valid RequestCalendarDetail requestCalendarDetail,
            @PathVariable long study_room_id) {
        return calendarDetailService.saveCalendarDetail(requestCalendarDetail, study_room_id);
    }

    /**
     * 캘린더 일정 수정
     *
     * @param requestCalendarDetail
     * @param calendar_detail_id
     * @return
     */
    @Operation(summary = "캘린더 일정 수정", description = "일정을 수정합니다.")
    @PutMapping("/calendar_detail/{calendar_detail_id}")
    public ResponseEntity<?> putCalendarDetail(
            @RequestBody @Valid RequestCalendarDetail requestCalendarDetail,
            @PathVariable long calendar_detail_id) {
        return calendarDetailService.putCalendarDetail(requestCalendarDetail, calendar_detail_id);
    }

    /**
     * 일정 삭제
     *
     * @param calendar_detail_id
     * @return
     */
    @Operation(summary = "캘린더 일정 삭제", description = "일정을 삭제합니다.")
    @DeleteMapping("/calendar_detail/{calendar_detail_id}")
    public ResponseEntity<?> deleteCalendarDetail(@PathVariable long calendar_detail_id) {
        return calendarDetailService.deleteCalendarDetail(calendar_detail_id);
    }

    /**
     * 개인 캘린더 삭제
     *
     * @return
     */
    @Operation(summary = "개인 캘린더 삭제", description = "개인 캘린더를 삭제합니다.")
    @DeleteMapping("/calendar")
    public ResponseEntity<?> deleteUserCalendar() {
        return calendarService.deleteCalendar();
    }

    /**
     * 스터디룸 캘린더 삭제
     *
     * @param study_room_id
     * @return
     */
    @Operation(summary = "공용 캘린더 삭제", description = "공용 캘린더를 삭제합니다.")
    @DeleteMapping("/calendar/{study_room_id}")
    public ResponseEntity<?> deleteStudyCalendar(@PathVariable long study_room_id) {
        return calendarService.deleteStudyCalendar(study_room_id);
    }
}
