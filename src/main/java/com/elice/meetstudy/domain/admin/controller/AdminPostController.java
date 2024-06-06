package com.elice.meetstudy.domain.admin.controller;

import com.elice.meetstudy.domain.admin.service.AdminPostService;
import com.elice.meetstudy.domain.post.dto.PostResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/posts")
@Tag(name = "C. 관리자")
public class AdminPostController{

    private final AdminPostService adminPostService;

    public AdminPostController(AdminPostService adminPostService) {
        this.adminPostService = adminPostService;
    }

    @Operation(summary = "전체 게시글 조회 - (최근 작성된 순으로)")
    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(adminPostService.getAllPosts(pageable));
    }

    @Operation(summary = "특정 회원이 작성한 게시글 조회 - (최근 작성된 순으로)")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(adminPostService.getPostsByUser(userId, pageable));
    }

    @Operation(summary = "특정 게시글 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(adminPostService.getPost(postId));
    }

    @Operation(summary = "게시글 삭제 - (이미 삭제된 게시글이어도 204)")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        adminPostService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}