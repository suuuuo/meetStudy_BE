package com.elice.meetstudy.domain.calendar.controller;

import com.elice.meetstudy.domain.calendar.dto.RequestCalendarDetail;
import com.elice.meetstudy.domain.calendar.dto.ResponseAllCalendarDetail;
import com.elice.meetstudy.domain.calendar.dto.ResponseCalendarDetail;
import com.elice.meetstudy.domain.calendar.service.CalendarDetailService;
import com.elice.meetstudy.domain.calendar.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "E. 캘린더", description = "캘린더 관련 API 입니다.")
public class CalendarController {

  private final CalendarService calendarService;
  private final CalendarDetailService calendarDetailService;

  /**
   * 개인 캘린더 전체 조회(공휴일 포함)
   *
   * @param year
   * @param month
   * @return
   */
  @Operation(summary = "캘린더 조회", description = "jwt 토큰을 기반으로 접근한 유저의 개인 캘린더를 조회합니다. "
      + "공휴일도 함께 조회됩니다.")
  @GetMapping("/calendar")
  public ResponseEntity<List<ResponseCalendarDetail>> getCalendarDetails(
      @Valid @RequestHeader("year") String year, @Valid @RequestHeader("month") String month) {
    return new ResponseEntity<>(
        calendarDetailService.getAllCalendarDetail(year, month, 0L), HttpStatus.OK);
  }

  /**
   * 공용 캘린더 전체 조회 (공휴일 포함)
   *
   * @param year
   * @param month
   * @param studyRoomId
   * @return
   */
  @Operation(summary = "캘린더 조회", description = "studyRoomId로 공용 캘린더를 조회합니다. "
      + "공휴일도 함께 조회됩니다.")
  @GetMapping("/calendar/{studyRoomId}")
  public ResponseEntity<List<ResponseCalendarDetail>> getCalendarDetails(
      @RequestHeader("year") String year,
      @RequestHeader("month") String month,
      @PathVariable long studyRoomId) {
    return new ResponseEntity<>(
        calendarDetailService.getAllCalendarDetail(year, month, studyRoomId), HttpStatus.OK);
  }

  /**
   * 통합 조회
   * @param year
   * @param month
   * @return
   */
  @Operation(summary = "캘린더 통합 조회", description = "jwt 토큰을 기반으로 접근한 유저의 개인, 공용 캘린더를 통합으로 조회합니다. "
      + "공휴일도 함께 조회됩니다.")
  @GetMapping("/calendarAll")
  public ResponseEntity<Set<ResponseAllCalendarDetail>> getAllCalendarDetails(
      @RequestHeader("year") String year, @RequestHeader("month") String month) {
    return new ResponseEntity<>(
        calendarDetailService.getAllCalendarDetailFromAll(year, month), HttpStatus.OK);
  }

  /**
   * 일정 개별 조회
   *
   * @param calendarDetailId
   * @return
   */
  @Operation(summary = "캘린더 일정 조회", description = "calendarId 로 일정을 개별 조회합니다.")
  @GetMapping("/calendarDetail/{calendarDetailId}")
  public ResponseEntity<ResponseCalendarDetail> getCalendarDetail(
      @PathVariable long calendarDetailId) {
    return new ResponseEntity<>(
        calendarDetailService.getCalendarDetail(calendarDetailId), HttpStatus.OK);
  }

  /**
   * 개인 캘린더 일정 추가
   *
   * @param requestCalendarDetail
   * @return
   */
  @Operation(summary = "개인 캘린더 일정 등록", description = "개인 캘린더에 새로운 일정을 등록합니다.")
  @PostMapping("/calendar")
  public ResponseEntity<ResponseCalendarDetail> postCalendarDetail(
      @RequestBody @Valid RequestCalendarDetail requestCalendarDetail) {
    return new ResponseEntity<>(
        calendarDetailService.saveCalendarDetail(requestCalendarDetail, 0L), HttpStatus.OK);
  }

  /**
   * 공용 캘린더 일정 추가
   *
   * @param requestCalendarDetail
   * @param studyRoomId
   * @return
   */
  @Operation(summary = "공용 캘린더 일정 등록", description = "공용 캘린더에 새로운 일정을 등록합니다.")
  @PostMapping("/calendar/{studyRoomId}")
  public ResponseEntity<ResponseCalendarDetail> postCalendarDetail(
      @RequestBody @Valid RequestCalendarDetail requestCalendarDetail,
      @PathVariable long studyRoomId) {
    return new ResponseEntity<>(
        calendarDetailService.saveCalendarDetail(requestCalendarDetail, studyRoomId),
        HttpStatus.OK);
  }

  /**
   * 캘린더 일정 수정
   *
   * @param requestCalendarDetail
   * @param calendarDetailId
   * @return
   */
  @Operation(summary = "캘린더 일정 수정", description = "calendarDetailId 로 기존 일정을 찾아 수정합니다.")
  @PutMapping("/calendarDetail/{calendarDetailId}")
  public ResponseEntity<ResponseCalendarDetail> putCalendarDetail(
      @RequestBody @Valid RequestCalendarDetail requestCalendarDetail,
      @PathVariable long calendarDetailId) {
    return new ResponseEntity<>(
        calendarDetailService.putCalendarDetail(requestCalendarDetail, calendarDetailId),
        HttpStatus.OK);
  }

  /**
   * 일정 삭제
   *
   * @param calendarDetailId
   * @return
   */
  @Operation(summary = "캘린더 일정 삭제", description = "calendarDetailId 로 기존 일정을 찾아 삭제합니다.")
  @DeleteMapping("/calendarDetail/{calendarDetailId}")
  public ResponseEntity<HttpStatus> deleteCalendarDetail(@PathVariable long calendarDetailId) {
    calendarDetailService.deleteCalendarDetail(calendarDetailId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * 개인 캘린더 삭제
   *
   * @return
   */
  @Operation(summary = "개인 캘린더 삭제", description = "접근한 유저의 개인 캘린더를 삭제합니다.")
  @DeleteMapping("/calendar")
  public ResponseEntity<HttpStatus> deleteUserCalendar() {
    calendarService.deleteCalendar();
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * 스터디룸 캘린더 삭제
   *
   * @param studyRoomId
   * @return
   */
  @Operation(summary = "공용 캘린더 삭제", description = "studyRoomId 에 해당하는 스터디룸의 공용 캘린더를 삭제합니다.")
  @DeleteMapping("/calendar/{studyRoomId}")
  public ResponseEntity<HttpStatus> deleteStudyCalendar(@PathVariable long studyRoomId) {
    calendarService.deleteStudyCalendar(studyRoomId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
