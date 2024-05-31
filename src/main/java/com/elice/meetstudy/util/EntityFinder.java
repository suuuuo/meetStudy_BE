package com.elice.meetstudy.util;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.category.repository.CategoryRepository;
import com.elice.meetstudy.domain.comment.domain.Comment;
import com.elice.meetstudy.domain.comment.repository.CommentRepository;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.post.repository.PostRepository;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityFinder {

  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;

  /** 회원 찾는 메서드 */
  public User findUserById(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 X."));
  }

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

  /** 게시글 찾는 메서드(Optional) */
  public Optional<Post> findPostById(Long postId) {
    return postRepository.findById(postId);
  }

  /** 댓글 찾는 메서드 */
  public Comment findComment(Long commentId) {
    return commentRepository
        .findById(commentId)
        .orElseThrow(() -> new IllegalArgumentException("댓글 찾을 수 X."));
  }

  /** 댓글 찾는 메서드 (Optional) */
  public Optional<Comment> findCommentById(Long commentId) {
    return commentRepository.findById(commentId);
  }
}
