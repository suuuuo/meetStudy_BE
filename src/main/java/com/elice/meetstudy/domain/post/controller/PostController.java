package com.elice.meetstudy.domain.post.controller;

import com.elice.meetstudy.domain.post.dto.PostCreate;
import com.elice.meetstudy.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "게시글", description = "게시글 관련 API 입니다.")
public class PostController {

  private static final Logger logger = LoggerFactory.getLogger(PostController.class);

  private final PostService postService;

  @Autowired
  public PostController (PostService postService) {
    this.postService = postService;
  }

  @Operation(summary = "게시글을 작성합니다.")
  @PostMapping("/post")
  public ResponseEntity<Long> post(@RequestBody @Valid PostCreate postCreate) {
    postService.write(postCreate);
    return ResponseEntity.ok().build();
  }




}

