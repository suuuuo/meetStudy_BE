package com.elice.meetstudy.domain.comment.service;

import com.elice.meetstudy.domain.comment.domain.Comment;
import com.elice.meetstudy.domain.comment.dto.CommentCreate;
import com.elice.meetstudy.domain.comment.dto.CommentEditor;
import com.elice.meetstudy.domain.comment.dto.CommentResponse;
import com.elice.meetstudy.domain.comment.repository.CommentRepository;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.util.EntityFinder;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {

  private final CommentRepository commentRepository;
  private final EntityFinder entityFinder;

  @Autowired
  public CommentService(CommentRepository commentRepository, EntityFinder entityFinder) {
    this.commentRepository = commentRepository;
    this.entityFinder = entityFinder;
  }

  /* 댓글 작성 */
  public CommentResponse write(Long postId, CommentCreate commentCreate) {
    // 예외 발생시
    Post post = entityFinder.findPostById(postId);
    User user = entityFinder.findUserById(commentCreate.getUserId());

    // post, user 있으면 댓글 생성
    Comment newComment =
        Comment.builder().post(post).user(user).content(commentCreate.getContent()).build();

    // 댓글 DB에 저장, 응답 객체 반환
    return new CommentResponse(commentRepository.save(newComment));
  }

  /* 댓글 수정 */
  public CommentResponse edit(Long id, CommentCreate editRequest) {
    // 예외 발생시
    Comment comment = entityFinder.findCommentById(id);

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
  public void delete(Long commentId) {
    entityFinder.findCommentById(commentId);
    commentRepository.deleteById(commentId);
  }

  /** 게시글에 달린 댓글 조회 */
  public List<CommentResponse> getByPost(Long postId, Pageable pageable) {
    // 게시글을 조회
    entityFinder.findPostById(postId);

    // 댓글 조회
    List<Comment> commentsList = commentRepository.findAllByPostId(postId, pageable);

    // 댓글을 응답객체로 변환
    return commentsList.stream().map(CommentResponse::new).collect(Collectors.toList());
  }

  /** 게시글에 달린 댓글을 키워드로 조회 */
  public List<CommentResponse> getByPostAndKeyword(Long postId, String keyword, Pageable pageable) {
    // 게시글 조회
    entityFinder.findPostById(postId);

    // 댓글 조회
    List<Comment> commentList =
        commentRepository.findAllByPostIdAndKeyword(postId, keyword, pageable);

    // 댓글을 응답객체로 변환
    return commentList.stream().map(CommentResponse::new).collect(Collectors.toList());
  }

  /** 전체 댓글 조회 (관리자 페이지) */
  public List<CommentResponse> getAll(Pageable pageable) {
    List<Comment> commentList = commentRepository.findAllByOrderByCreatedAtDesc(pageable);
    return commentList.stream().map(CommentResponse::new).collect(Collectors.toList());
  }

  /** 전체 댓글 내 키워드 검색 (관리자 페이지) */
  public List<CommentResponse> getByKeyword(String keyword, Pageable pageable) {
    List<Comment> commentList =
        commentRepository.findAllByKeywordOrderByCreatedAtDesc(keyword, pageable);
    return commentList.stream().map(CommentResponse::new).collect(Collectors.toList());
  }
}
