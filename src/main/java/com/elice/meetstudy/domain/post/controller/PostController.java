package com.elice.meetstudy.domain.post.controller;

import com.elice.meetstudy.domain.post.dto.PostResponseDTO;
import com.elice.meetstudy.domain.post.dto.PostWriteDTO;
import com.elice.meetstudy.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
@Tag(name = "게시글", description = "게시글 관련 API 입니다.")
@Slf4j
public class PostController {

  private final PostService postService;

  @Operation(summary = "게시글 작성")
  @PostMapping
  public ResponseEntity<PostResponseDTO> createPost(
      @RequestBody @Valid PostWriteDTO postCreate,
      @RequestHeader("Authentication") String jwtToken) {
    return ResponseEntity.ok().body(postService.write(postCreate, jwtToken));
  }

  @Operation(summary = "게시글 수정")
  @PatchMapping("/{postId}")
  public ResponseEntity<PostResponseDTO> editPost(
      @PathVariable Long postId,
      @RequestBody @Valid PostWriteDTO postEdit,
      @RequestHeader("Authentication") String jwtToken) {
    return ResponseEntity.ok().body(postService.edit(postId, postEdit, jwtToken));
  }

  @Operation(summary = "게시글 삭제 - (이미 삭제된 게시글이어도 204)")
  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deletePost(
      @PathVariable Long postId, @RequestHeader("Authentication") String jwtToken) {
    postService.delete(postId, jwtToken);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "전체 게시글 조회 - (최근 작성된 순으로)")
  @GetMapping
  public ResponseEntity<List<PostResponseDTO>> getPostAll(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(postService.getPostAll(pageable));
  }

  @Operation(summary = "게시글 상세 조회(postId) - (사용자가 게시글 제목을 클릭했을때)")
  @GetMapping("/{postId}")
  public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long postId) {
    return ResponseEntity.ok(postService.getPost(postId));
  }

  @Operation(summary = "전체 게시판 내 게시글 검색 - (최근 작성된 순으로)")
  @GetMapping("/search")
  public ResponseEntity<List<PostResponseDTO>> searchPost(
      @RequestParam String keyword, @PageableDefault Pageable pageable) {
    return ResponseEntity.ok(postService.searchPost(keyword, pageable));
  }
}
