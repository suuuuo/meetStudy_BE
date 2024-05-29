package com.elice.meetstudy.domain.comment.controller;

import com.elice.meetstudy.domain.comment.dto.CommentCreate;
import com.elice.meetstudy.domain.comment.dto.CommentResponse;
import com.elice.meetstudy.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
@Tag(name = "댓글", description = "댓글 관련 API 입니다.")
public class CommentController {

  private final CommentService commentService;

  @Autowired
  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @Operation(summary = "댓글 작성")
  @PostMapping("/{commentId}")
  public ResponseEntity<CommentResponse> createComment(
      @PathVariable Long commentId, @RequestBody @Valid CommentCreate commentCreate) {
    return ResponseEntity.ok().body(commentService.write(commentId, commentCreate));
  }

  @Operation(summary = "댓글 수정")
  @PatchMapping("/{commentId}")
  public ResponseEntity<CommentResponse> editComment(
      @PathVariable Long commentId, @RequestBody @Valid CommentCreate commentCreate) {
    return ResponseEntity.ok().body(commentService.edit(commentId, commentCreate));
  }

  @Operation(summary = "댓글 삭제")
  @DeleteMapping("/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
    commentService.delete(commentId);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "게시글에 달린 댓글 조회")
  @GetMapping("/{postId}")
  public ResponseEntity<List<CommentResponse>> getCommentByPost(
      @PathVariable Long postId, @PageableDefault Pageable pageable) {
    return ResponseEntity.ok().body(commentService.getByPost(postId, pageable));
  }
}
