package com.elice.meetstudy.domain.post.controller;

import com.elice.meetstudy.domain.post.dto.PostCreate;
import com.elice.meetstudy.domain.post.dto.PostEdit;
import com.elice.meetstudy.domain.post.dto.PostResponse;
import com.elice.meetstudy.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post")
@Tag(name = "게시글", description = "게시글 관련 API 입니다.")
public class PostController {

  private static final Logger logger = LoggerFactory.getLogger(PostController.class);

  private final PostService postService;

  @Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

  @Operation(summary = "게시글 작성", description = "userId는 추후 jwt에서 추출하여 header로 전달.")
  @PostMapping
  public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostCreate postCreate) {
    return ResponseEntity.ok().body(postService.write(postCreate));
  }

  @Operation(summary = "게시글 수정")
  @PatchMapping("/{postId}")
  public ResponseEntity<PostResponse> editPost(
      @PathVariable Long postId, @RequestBody @Valid PostEdit request) {
    return ResponseEntity.ok().body(postService.edit(postId, request));
  }

  @Operation(summary = "게시글 삭제")
  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
    postService.delete(postId);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "전체 게시글 조회")
  @GetMapping
  public ResponseEntity<List<PostResponse>> getPostAll(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(postService.getPostAll(pageable));
  }

  @Operation(summary = "게시글 상세 조회(postId) - (사용자가 게시글 제목을 클릭했을때)")
  @GetMapping("/{postId}")
  public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
    return ResponseEntity.ok(postService.getPost(postId));
  }

  @Operation(summary = "전체 게시판 내 게시글 검색")
  @GetMapping("/search")
  public ResponseEntity<List<PostResponse>> searchPost(@RequestParam String keyword) {
    return ResponseEntity.ok(postService.searchPost(keyword));
  }
}
