package com.elice.meetstudy.domain.comment.service;

import com.elice.meetstudy.domain.comment.domain.Comment;
import com.elice.meetstudy.domain.comment.dto.CommentCreate;
import com.elice.meetstudy.domain.comment.dto.CommentEditor;
import com.elice.meetstudy.domain.comment.dto.CommentResponse;
import com.elice.meetstudy.domain.comment.repository.CommentRepository;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.post.repository.PostRepository;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  @Autowired
  public CommentService(
      CommentRepository commentRepository,
      PostRepository postRepository,
      UserRepository userRepository) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  /* 댓글 작성 */
  public CommentResponse write(Long id, CommentCreate commentCreate) {
    // 예외 발생시
    Post post = findPostById(id);
    User user = findUserById(commentCreate.getUserId());

    // post, user 있으면 댓글 생성
    Comment newComment =
        Comment.builder().post(post).user(user).content(commentCreate.getContent()).build();

    // 댓글 DB에 저장, 응답 객체 반환
    return new CommentResponse(commentRepository.save(newComment));
  }

  /* 댓글 수정 */
  public CommentResponse edit(Long id, CommentCreate editRequest) {
    // 예외 발생시
    Comment comment = findCommentById(id);

    // 위에서 조회된 Comment로 CommentEditorBuilder 생성 (현재 상태를 기반으로 한 빌더 객체)
    CommentEditor.CommentEditorBuilder commentEditorBuilder = comment.toEditor();

    // 수정된 내용으로 CommentEditor 객체 생성
    CommentEditor editComment =
        commentEditorBuilder
            .content(editRequest.getContent()) // 수정할 내용을 설정
            .build();

    // Comment 엔티티 수정
    comment.edit(editComment);

    // 수정된 comment 엔티티 저장
    commentRepository.save(comment);

    // return 값 반환
    return new CommentResponse(comment);
  }

  /* 댓글 삭제 */
  public void delete(Long id) {
    // 예외 발생시
    Comment comment = findCommentById(id);
    commentRepository.deleteById(id);
  }

  /* 게시글에 달린 댓글 조회 */
//  public ResponseEntity<List<CommentResponse>> getByPost(Long postId, Pageable pageable) {
//
//    return;
//  }

  /* 게시글 내 특정 댓글 조회 */

  /* 전체 댓글 조회 (관리자 페이지) */

  /* 전체 댓글 내 키워드 검색 (관리자 페이지) */

  /** 게시글 찾는 메서드 */
  private Post findPostById(Long postId) {
    return postRepository
        .findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 X."));
  }

  /** 회원 찾는 메서드 */
  private User findUserById(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 X."));
  }

  /** 댓글 찾는 메서드 */
  private Comment findCommentById(Long commentId) {
    return commentRepository
        .findById(commentId)
        .orElseThrow(() -> new IllegalArgumentException("댓글 찾을 수 X."));
  }
}
