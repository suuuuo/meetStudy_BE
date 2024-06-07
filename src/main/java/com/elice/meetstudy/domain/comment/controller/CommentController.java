package com.elice.meetstudy.domain.comment.controller;

import com.elice.meetstudy.domain.comment.dto.CommentResponseDTO;
import com.elice.meetstudy.domain.comment.dto.CommentWriteDTO;
import com.elice.meetstudy.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Tag(name = "G. 댓글", description = "댓글 관련 API 입니다.")
public class CommentController {

  private final CommentService commentService;

  @Operation(summary = "댓글 작성")
  @PostMapping("/{postId}")
  public ResponseEntity<CommentResponseDTO> createComment(
      @PathVariable Long postId, @RequestBody @Valid CommentWriteDTO commentCreate) {
    return ResponseEntity.ok().body(commentService.write(postId, commentCreate));
  }

  @Operation(summary = "댓글 수정")
  @PatchMapping("/{commentId}")
  public ResponseEntity<CommentResponseDTO> editComment(
      @PathVariable Long commentId, @RequestBody @Valid CommentWriteDTO commentCreate) {
    return ResponseEntity.ok().body(commentService.edit(commentId, commentCreate));
  }

  @Operation(summary = "댓글 삭제")
  @DeleteMapping("/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
    commentService.delete(commentId);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "게시글에 달린 댓글 조회")
  @GetMapping("/public/{postId}")
  public ResponseEntity<List<CommentResponseDTO>> getCommentByPost(
      @PathVariable Long postId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "8") int size) {
    return ResponseEntity.ok(commentService.getByPost(postId, page, size));
  }

  @Operation(summary = "게시글에 달린 댓글을 키워드로 조회")
  @GetMapping("/public/{postId}/search")
  public ResponseEntity<List<CommentResponseDTO>> getCommentByKeyword(
      @PathVariable Long postId,
      @RequestParam String keyword,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "8") int size) {
    return ResponseEntity.ok(commentService.getByPostAndKeyword(postId, keyword, page, size));
  }

  //  /** 추후 AdminController로 이동을 고려! API endpoint 변경 예정 */
  //  @Operation(summary = "(관리자 페이지 - 전체 댓글 모니터링) 전체 댓글 조회 - (최근 작성된 순으로)")
  //  @GetMapping
  //  public ResponseEntity<List<CommentResponseDTO>> getComment(
  //      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
  //    return ResponseEntity.ok(commentService.getAll(page, size));
  //  }
  //
  //  @Operation(summary = "(관리자 페이지 - 전체 댓글 모니터링) 전체 댓글 내 키워드 검색 - (최근 작성된 순으로)")
  //  @GetMapping("/search")
  //  public ResponseEntity<List<CommentResponseDTO>> getAllCommentByKeyword(
  //      @RequestParam String keyword,
  //      @RequestParam(defaultValue = "0") int page,
  //      @RequestParam(defaultValue = "10") int size) {
  //    return ResponseEntity.ok(commentService.getByKeyword(keyword, page, size));
  //  }
}
