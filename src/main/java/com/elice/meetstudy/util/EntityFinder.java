package com.elice.meetstudy.util;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.category.repository.CategoryRepository;
import com.elice.meetstudy.domain.comment.domain.Comment;
import com.elice.meetstudy.domain.comment.repository.CommentRepository;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.post.domain.PostLike;
import com.elice.meetstudy.domain.post.repository.PostLikeRepository;
import com.elice.meetstudy.domain.post.repository.PostRepository;
import com.elice.meetstudy.domain.scrap.domain.Scrap;
import com.elice.meetstudy.domain.scrap.repository.ScrapRepository;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.domain.UserPrinciple;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntityFinder {

  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final PostRepository postRepository;
  private final PostLikeRepository postLikeRepository;
  private final CommentRepository commentRepository;
  private final ScrapRepository scrapRepository;

  /** 카테고리 찾는 메서드 */
  public Category findCategoryById(Long categoryId) {
    return categoryRepository
        .findById(categoryId)
        .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 X."));
  }

  /** 게시글 찾는 메서드 */
  public Post findPost(Long postId) {
    return postRepository
        .findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 X."));
  }

  /** 댓글 찾는 메서드 */
  public Comment findComment(Long commentId) {
    return commentRepository
        .findById(commentId)
        .orElseThrow(() -> new IllegalArgumentException("댓글 찾을 수 X."));
  }

  /** 이미 좋아요한 게시글인지 확인하기 위한 메서드 */
  public Optional<PostLike> findLike(Long postId) {
    return postLikeRepository.findByUserIdAndPostId(getUser().getId(), postId);
  }

  /** 이미 스크랩한 게시글인지 확인하기 위한 메서드 */
  public Optional<Scrap> findScrapPost(Long postId) {
    return scrapRepository.findByUserIdAndPostId(getUser().getId(), postId);
  }

  /** 이미 스크랩한 게시판인지 확인하기 위한 메서드 */
  public Optional<Scrap> findScrapCategory(Long categoryId) {
    return scrapRepository.findByUserIdAndCategoryId(getUser().getId(), categoryId);
  }

  /** user객체 찾는 메서드 */
  public User getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
    Long userId = Long.valueOf(userPrinciple.getUserId());
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 X " + userId));
  }
}
