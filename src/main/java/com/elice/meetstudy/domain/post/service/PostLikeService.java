package com.elice.meetstudy.domain.post.service;

import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.post.domain.PostLike;
import com.elice.meetstudy.domain.post.repository.PostLikeRepository;
import com.elice.meetstudy.util.EntityFinder;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PostLikeService {

  private final PostLikeRepository postLikeRepository;
  private final EntityFinder entityFinder;

  // 게시글 좋아요
  public boolean insert(Long postId) {
    Post post = entityFinder.findPost(postId);
    Optional<PostLike> postLike = entityFinder.findLike(post.getId());

    PostLike newLike = PostLike.builder().post(post).user(entityFinder.getUser()).build();
    if (postLike.isPresent()) {
      return false;
    }
    postLikeRepository.save(newLike);
    return true;
  }

  // 게시글 좋아요 취소 - (이미 취소했어도 204)
  public void delete(Long postId) {
    postLikeRepository.deleteByUserIdAndPostId(entityFinder.getUser().getId(), postId);
  }

  // 게시글 좋아요 수 조회
  public Long getLikeNumByPost(Long postId) {
    return postLikeRepository.countByPostId(postId);
  }
}
