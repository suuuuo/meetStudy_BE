package com.elice.meetstudy.domain.comment.service;

import com.elice.meetstudy.domain.comment.domain.Comment;
import com.elice.meetstudy.domain.comment.dto.CommentEditDTO;
import com.elice.meetstudy.domain.comment.dto.CommentResponseDTO;
import com.elice.meetstudy.domain.comment.dto.CommentWriteDTO;
import com.elice.meetstudy.domain.comment.repository.CommentRepository;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.util.EntityFinder;
import com.elice.meetstudy.util.TokenUtility;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class CommentService {

  private final CommentRepository commentRepository;
  private final EntityFinder entityFinder;
  private final TokenUtility tokenUtility;

  @Autowired
  public CommentService(
      CommentRepository commentRepository, EntityFinder entityFinder, TokenUtility tokenUtility) {
    this.commentRepository = commentRepository;
    this.entityFinder = entityFinder;
    this.tokenUtility = tokenUtility;
  }

  /** 댓글 작성 */
  public CommentResponseDTO write(Long postId, CommentWriteDTO commentCreate, String jwtToken) {
    Long userId = tokenUtility.getUserIdFromToken(jwtToken);
    User user = entityFinder.findUserById(userId);
    Post post = entityFinder.findPost(postId);

    Comment newComment =
        Comment.builder().post(post).user(user).content(commentCreate.getContent()).build();

    // 댓글 DB에 저장
    return new CommentResponseDTO(commentRepository.save(newComment));
  }

  /** 댓글 수정 */
  public CommentResponseDTO edit(Long commentId, CommentWriteDTO editRequest, String jwtToken) {
    Long userId = tokenUtility.getUserIdFromToken(jwtToken);
    Comment comment = entityFinder.findComment(commentId);

    if (!userId.equals(comment.getUser().getId())) {
      throw new SecurityException("해당 댓글을 수정할 권한이 없습니다.");
    }

    // editRequest, comment로 분기처리하고 객체 생성
    CommentEditDTO editComment =
        comment
            .toEdit()
            .content(
                editRequest.getContent() != null ? editRequest.getContent() : comment.getContent())
            .build();

    // Comment 엔티티 수정
    comment.edit(editComment);

    // 저장
    return new CommentResponseDTO(commentRepository.save(comment));
  }

  /** 댓글 삭제 - (이미 삭제된 게시글이어도 204) */
  public void delete(Long commentId, String jwtToken) {
    Long userId = tokenUtility.getUserIdFromToken(jwtToken);

    Optional<Comment> optionalComment = entityFinder.findCommentById(commentId);

    // 댓글 있으면
    if (optionalComment.isPresent()) {
      Comment comment = optionalComment.get();

      if (userId.equals(comment.getUser().getId())) {
        commentRepository.deleteById(commentId);
      } else {
        throw new SecurityException("해당 게시글을 삭제할 권한이 없습니다.");
      }
    } else {
      // 게시글이 존재하지 않는 경우에도 성공으로 간주
      log.info("댓글 X ->  이미 삭제 commentId={}", commentId);
    }
  }

  /** 게시글에 달린 댓글 조회 정렬 기준은 ?.? */
  public List<CommentResponseDTO> getByPost(Long postId, Pageable pageable) {
    // 게시글을 조회
    entityFinder.findPostById(postId);

    // 댓글 조회
    List<Comment> commentsList = commentRepository.findAllByPostId(postId, pageable);

    // 댓글을 응답객체로 변환
    return commentsList.stream().map(CommentResponseDTO::new).collect(Collectors.toList());
  }

  /** 게시글에 달린 댓글을 키워드로 조회 */
  public List<CommentResponseDTO> getByPostAndKeyword(
      Long postId, String keyword, Pageable pageable) {
    // 게시글 조회
    entityFinder.findPostById(postId);

    // 댓글 조회
    List<Comment> commentList =
        commentRepository.findAllByPostIdAndKeyword(postId, keyword, pageable);

    // 댓글을 응답객체로 변환
    return commentList.stream().map(CommentResponseDTO::new).collect(Collectors.toList());
  }

  /** 전체 댓글 조회 (관리자 페이지) */
  public List<CommentResponseDTO> getAll(Pageable pageable) {
    List<Comment> commentList = commentRepository.findAllByOrderByCreatedAtDesc(pageable);
    return commentList.stream().map(CommentResponseDTO::new).collect(Collectors.toList());
  }

  /** 전체 댓글 내 키워드 검색 (관리자 페이지) */
  public List<CommentResponseDTO> getByKeyword(String keyword, Pageable pageable) {
    List<Comment> commentList =
        commentRepository.findAllByKeywordOrderByCreatedAtDesc(keyword, pageable);
    return commentList.stream().map(CommentResponseDTO::new).collect(Collectors.toList());
  }
}
