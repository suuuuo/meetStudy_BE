package com.elice.meetstudy.domain.comment.service;

import com.elice.meetstudy.domain.comment.domain.Comment;
import com.elice.meetstudy.domain.comment.dto.CommentEditDTO;
import com.elice.meetstudy.domain.comment.dto.CommentResponseDTO;
import com.elice.meetstudy.domain.comment.dto.CommentWriteDTO;
import com.elice.meetstudy.domain.comment.repository.CommentRepository;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.util.EntityFinder;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final EntityFinder entityFinder;

  /** 댓글 작성 */
  public CommentResponseDTO write(Long postId, CommentWriteDTO commentCreate) {
    Post post = entityFinder.findPost(postId);

    Comment newComment =
        Comment.builder()
            .post(post)
            .user(entityFinder.getUser())
            .content(commentCreate.getContent().trim())
            .build();

    return new CommentResponseDTO(commentRepository.save(newComment));
  }

  /** 댓글 수정 */
  public CommentResponseDTO edit(Long commentId, CommentWriteDTO editRequest) {
    Comment comment = entityFinder.findComment(commentId);

    if (entityFinder.getUser().getId() != comment.getUser().getId()) {
      throw new EntityNotFoundException("해당 댓글을 수정할 권한이 없습니다.");
    } else {
      CommentEditDTO editComment =
          comment
              .toEdit()
              .content(
                  editRequest.getContent() != null
                      ? editRequest.getContent()
                      : comment.getContent().trim())
              .build();

      // Comment 엔티티 수정
      comment.edit(editComment);
      return new CommentResponseDTO(commentRepository.save(comment));
    }
  }

  /** 댓글 삭제 - (이미 삭제된 댓글이어도 204) */
  public void delete(Long commentId) {
    if (entityFinder.getUser().getId() != entityFinder.findComment(commentId).getId()) {
      throw new EntityNotFoundException("해당 댓글을 삭제할 권한이 없습니다");
    }
    commentRepository.deleteByIdAndUserId(commentId, entityFinder.getUser().getId());
  }

  /** 게시글에 달린 댓글 조회 */
  public List<CommentResponseDTO> getByPost(Long postId, int page, int size) {
    Pageable defaultPageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));

    Page<Comment> commentsPage = commentRepository.findAllByPostId(postId, defaultPageable);

    // 댓글 페이지를 CommentResponseDTO의 리스트로 변환
    List<CommentResponseDTO> commentResponse =
        commentsPage.getContent().stream()
            .map(CommentResponseDTO::new)
            .collect(Collectors.toList());

    return commentResponse;
  }

  /** 게시글에 달린 댓글을 키워드로 조회 */
  public List<CommentResponseDTO> getByPostAndKeyword(
      Long postId, String keyword, int page, int size) {
    Pageable defaultPageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));
    entityFinder.findPost(postId);

    List<Comment> commentList =
        commentRepository.findAllByPostIdAndKeyword(postId, keyword, defaultPageable);

    return commentList.stream().map(CommentResponseDTO::new).collect(Collectors.toList());
  }

  /** 전체 댓글 조회 (관리자 페이지) */
  public List<CommentResponseDTO> getAll(int page, int size) {
    Pageable defaultPageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));
    List<Comment> commentList = commentRepository.findAllByOrderByCreatedAtDesc(defaultPageable);
    return commentList.stream().map(CommentResponseDTO::new).collect(Collectors.toList());
  }

  /** 전체 댓글 내 키워드 검색 (관리자 페이지) */
  public List<CommentResponseDTO> getByKeyword(String keyword, int page, int size) {
    Pageable defaultPageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));
    List<Comment> commentList =
        commentRepository.findAllByKeywordOrderByCreatedAtDesc(keyword, defaultPageable);
    return commentList.stream().map(CommentResponseDTO::new).collect(Collectors.toList());
  }
}
