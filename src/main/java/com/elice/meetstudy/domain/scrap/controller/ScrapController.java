package com.elice.meetstudy.domain.scrap.controller;

import com.elice.meetstudy.domain.scrap.dto.ScrapInsertDTO;
import com.elice.meetstudy.domain.scrap.dto.ScrapResponseDTO;
import com.elice.meetstudy.domain.scrap.service.ScrapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scrap")
@RequiredArgsConstructor
@Tag(name = "스크랩", description = "(게시글과 게시판을 스크랩) 스크랩 관련 API 입니다.")
public class ScrapController {

  private final ScrapService scrapService;

  @Operation(summary = "게시글 또는 게시판 스크랩")
  @PostMapping
  public ResponseEntity<ScrapResponseDTO> createScrap(
      @RequestBody @Valid ScrapInsertDTO scrapInsert) {
    //  return scrapService.saveScrap(scrapInsertDTO)
    return ResponseEntity.ok().body(scrapService.saveScrap(scrapInsert));
  }

  //  @Operation(summary = "게시글 스크랩")
  //  @PostMapping("/{postId}")
  //  public ResponseEntity<ScrapResponseDTO> scrapPost(@PathVariable Long postId) {
  //    return ResponseEntity.ok().body(scrapService.scrapPost(postId));
  //  }
  //
  //  @Operation(summary = "게시글 또는 게시판 스크랩")
  //  @PostMapping("/{postId}")
  //  public ResponseEntity<ScrapResponseDTO> scrapPost(@PathVariable Long postId) {
  //    return ResponseEntity.ok().body(scrapService.scrapPost(postId));
  //  }

  //  @Operation(summary = "게시글 스크랩 취소 (삭제)")
  //  @DeleteMapping("/{scrapId}")
  //  public ResponseEntity<Void> deleteScrap(@PathVariable Long scrapId) {
  //    scrapService.scrapDeletePost(scrapId);
  //    return ResponseEntity.noContent().build();
  //  }
  //
  //  @Operation(summary = "게시판 즐겨찾기")
  //  @PostMapping("/{categoryId}")
  //  public ResponseEntity<ScrapResponseDTO> scrapBoard(@PathVariable Long categoryId) {
  //    return ResponseEntity.ok().body(scrapService.scrapBoard(categoryId));
  //  }
  //
  //  @Operation(summary = "게시판 즐겨찾기 취소 (삭제)")
  //  @DeleteMapping("/{categoryId}")
  //  public ResponseEntity<Void> deleteScrap(@PathVariable Long categoryId) {
  //    scrapService.scrapDeleteBoard(categoryId);
  //    return ResponseEntity.noContent().build();
  //  }
  //
  //  @Operation(summary = "게시판 즐겨찾기 목록 조회")
  //  @GetMapping
  //  public ResponseEntity<List<ScrapResponseDTO>> getComment(
  //      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
  //    return ResponseEntity.ok(scrapService.getScrapAll(page, size));
  //  }

  // 게시글 즐겨찾기 목록은 User에서 진행

}
