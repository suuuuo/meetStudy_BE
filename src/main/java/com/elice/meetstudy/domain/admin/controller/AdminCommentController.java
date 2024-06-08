package com.elice.meetstudy.domain.admin.controller;

import com.elice.meetstudy.domain.admin.service.AdminCommentService;
import com.elice.meetstudy.domain.comment.dto.CommentResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/comments")
@Tag(name = "C. 관리자")
public class AdminCommentController {

    private final AdminCommentService adminCommentService;

    public AdminCommentController(AdminCommentService adminCommentService) {
        this.adminCommentService = adminCommentService;
    }

    @Operation(summary = "(관리자 페이지 - 전체 댓글 모니터링) 전체 댓글 조회 - (최근 작성된 순으로)")
    @GetMapping
    public ResponseEntity<List<CommentResponseDTO>> getComment(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(adminCommentService.getAllComments(page, size));
    }

    @Operation(summary = "(관리자 페이지 - 특정 회원의 댓글 모니터링) 특정 회원의 댓글 조회 - (최근 작성된 순으로)")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(adminCommentService.getCommentsByUser(userId, page, size));
    }

    @Operation(summary = "(관리자 페이지 - 전체 댓글 모니터링) 전체 댓글 내 키워드 검색 - (최근 작성된 순으로)")
    @GetMapping("/search")
    public ResponseEntity<List<CommentResponseDTO>> getAllCommentByKeyword(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(adminCommentService.getCommentByKeyword(keyword, page, size));
    }

    @Operation(summary = "(관리자 페이지 - 댓글 삭제) 특정 댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        adminCommentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}