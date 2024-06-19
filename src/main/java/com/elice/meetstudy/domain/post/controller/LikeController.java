package com.elice.meetstudy.domain.post.controller;

import com.elice.meetstudy.domain.post.service.PostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Tag(name = "F. 게시글", description = "게시글 관련 API 입니다.")
@Slf4j
public class LikeController {

  private final PostLikeService postLikeServicee;

  // 게시글 좋아요 (GET)
  @Operation(
      summary = "게시글 좋아요",
      description = "(회원 권한) 게시글에 있는 버튼을 누르면 좋아요가 추가(true). 이미 좋아요한 게시글이면 (false)")
  @PostMapping("/{postId}/like")
  public ResponseEntity<Boolean> insertLike(@PathVariable Long postId) {
    boolean likeInserted = postLikeServicee.insert(postId);
    return ResponseEntity.ok().body(likeInserted);
  }

  // 게시글 좋아요 취소 (DELETE)
  @Operation(
      summary = "좋아요한 게시글 취소 - (이미 취소했어도 204)",
      description = "(회원 권한) 이미 좋아요한 게시글에서 버튼을 다시 누르면 좋아요가 취소됩니다.")
  @DeleteMapping("/{postId}/like")
  public ResponseEntity<Void> delete(@PathVariable Long postId) {
    postLikeServicee.delete(postId);
    return ResponseEntity.noContent().build();
  }

  // 게시글 좋아요 수 조회 (GET)
  // countByPostId
  @Operation(
      summary = "특정 게시글의 좋아요 수 조회 (숫자로 반환)",
      description = "(권한 불필요) 게시글이 받은 좋아요 수를 확인할 수 있습니다. 값은 숫자로 반환됩니다!")
  @GetMapping("/public/{postId}/like")
  public ResponseEntity<Long> getLikeNumByPost(@PathVariable Long postId) {
    return ResponseEntity.ok().body(postLikeServicee.getLikeNumByPost(postId));
  }
}
