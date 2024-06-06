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
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Tag(name = "F. 게시글", description = "게시글 관련 API 입니다.")
@Slf4j
public class PostController {

  private final PostService postService;

  @Operation(summary = "게시글 작성")
  @PostMapping
  public ResponseEntity<PostResponseDTO> createPost(@RequestBody @Valid PostWriteDTO postCreate) {
    return ResponseEntity.ok().body(postService.write(postCreate));
  }

  @Operation(summary = "특정 게시판에서 게시글 바로 작성")
  @PostMapping("/category/direct/{categoryId}")
  public ResponseEntity<PostResponseDTO> createPostByCategory(
      @RequestBody @Valid PostWriteDTO postCreate, @PathVariable Long categoryId) {
    return ResponseEntity.ok().body(postService.writeByCategory(categoryId, postCreate));
  }

  @Operation(summary = "게시글 수정")
  @PatchMapping("/{postId}")
  public ResponseEntity<PostResponseDTO> editPost(
      @PathVariable Long postId, @RequestBody @Valid PostWriteDTO postEdit) {
    return ResponseEntity.ok().body(postService.edit(postId, postEdit));
  }

  @Operation(summary = "게시글 삭제 - (이미 삭제된 게시글이어도 204)")
  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
    postService.delete(postId);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "게시판 별 게시글 조회 - (공학게시판/교육게시판 등)")
  @GetMapping("public/category/{categoryId}")
  public ResponseEntity<List<PostResponseDTO>> getPostByCategory(
      @PathVariable Long categoryId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "15") int size) {
    return ResponseEntity.ok(postService.getPostBycategory(categoryId, page, size));
  }

  @Operation(summary = "내가 작성한 글 조회 - (최근 작성된 순으로)")
  @GetMapping("/user")
  public ResponseEntity<List<PostResponseDTO>> getPostByUser(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "15") int size) {
    return ResponseEntity.ok(postService.getPostByUser(page, size));
  }

  @Operation(summary = "전체 게시글 조회 - (최근 작성된 순으로)")
  @GetMapping("/public")
  public ResponseEntity<List<PostResponseDTO>> getPostAll(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "15") int size) {
    return ResponseEntity.ok(postService.getPostAll(page, size));
  }

  @Operation(summary = "게시글 상세 조회(postId) - (사용자가 게시글 제목을 클릭했을때)")
  @GetMapping("/public/{postId}")
  public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long postId) {
    return ResponseEntity.ok(postService.getPost(postId));
  }

  @Operation(summary = "특정 게시판 내 게시글 키워드 검색 - (최근 작성된 순으로)")
  @GetMapping("/public/category/{categoryId}/search")
  public ResponseEntity<List<PostResponseDTO>> searchPostInBoard(
      @PathVariable Long categoryId,
      @RequestParam String keyword,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "15") int size) {
    return ResponseEntity.ok(postService.searchPostInBoard(categoryId, keyword, page, size));
  }

  @Operation(summary = "전체 게시판 내 게시글 키워드 검색 - (최근 작성된 순으로)")
  @GetMapping("/public/search")
  public ResponseEntity<List<PostResponseDTO>> searchPost(
      @RequestParam String keyword,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "15") int size) {
    return ResponseEntity.ok(postService.searchPost(keyword, page, size));
  }
}
