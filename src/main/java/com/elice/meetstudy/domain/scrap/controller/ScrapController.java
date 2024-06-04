package com.elice.meetstudy.domain.scrap.controller;

import com.elice.meetstudy.domain.scrap.dto.ScrapResponseDTO;
import com.elice.meetstudy.domain.scrap.service.ScrapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scrap")
@RequiredArgsConstructor
@Tag(name = "스크랩", description = "(게시글과 게시판을 스크랩) 스크랩 관련 API 입니다.")
public class ScrapController {

  private final ScrapService scrapService;

  @Operation(summary = "게시글 스크랩", description = "이미 스크랩한 게시글이면 버튼이 '스크랩 취소' 로 바뀔 수 있을까요..?!")
  @PostMapping("/{postId}")
  public ResponseEntity<ScrapResponseDTO> scrapPost(@PathVariable Long postId) {
    return ResponseEntity.ok().body(scrapService.scrapPost(postId));
  }

  @Operation(
      summary = "게시글 스크랩 취소 (삭제)",
      description = "스크랩에서 삭제했을 경우, 버튼이 '스크랩'으로 다시 바뀔 수 있을까요...!?")
  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> scrapDeletePost(@PathVariable Long postId) {
    scrapService.scrapDeletePost(postId);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "게시판 즐겨찾기", description = "이미 스크랩한 게시판이면 버튼이 '스크랩 취소' 로 바뀔 수 있을까요..?!")
  @PostMapping("/category/{categoryId}")
  public ResponseEntity<ScrapResponseDTO> scrapCategory(@PathVariable Long categoryId) {
    return ResponseEntity.ok().body(scrapService.scrapCategory(categoryId));
  }

  @Operation(
      summary = "게시판 즐겨찾기 취소 (삭제)",
      description = "스크랩에서 삭제했을 경우, 버튼이 '스크랩'으로 다시 바뀔 수 있을까요...!?")
  @DeleteMapping("/category/{categoryId}")
  public ResponseEntity<Void> scrapDeleteCategory(@PathVariable Long categoryId) {
    scrapService.scrapDeleteCategory(categoryId);
    return ResponseEntity.noContent().build();
  }

  //  @Operation(summary = "게시판 즐겨찾기 목록 조회")
  //  @GetMapping
  //  public ResponseEntity<List<ScrapResponseDTO>> getScrap(
  //      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
  //    return ResponseEntity.ok(scrapService.getScrapAll(page, size));
  //  }

  // 게시글 즐겨찾기 목록은 User에서 진행

}