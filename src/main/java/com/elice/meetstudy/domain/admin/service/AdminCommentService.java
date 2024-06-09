package com.elice.meetstudy.domain.admin.service;

import com.elice.meetstudy.domain.comment.domain.Comment;
import com.elice.meetstudy.domain.comment.dto.CommentResponseDTO;
import com.elice.meetstudy.domain.comment.repository.CommentRepository;
import com.elice.meetstudy.domain.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCommentService {

    private final CommentRepository commentRepository;

    /** 전체 댓글 조회 (관리자 페이지) */
    public List<CommentResponseDTO> getAllComments(int page, int size) {
        Pageable defaultPageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Comment> commentList = commentRepository.findAllByOrderByCreatedAtDesc(defaultPageable);
        return commentList.stream().map(CommentResponseDTO::new).collect(Collectors.toList());
    }

    /** 특정 회원이 작성한 댓글 조회 */
    public List<CommentResponseDTO> getCommentsByUser(Long userId, int page, int size) {
        Pageable defaultPageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Comment> commentList = commentRepository.findAllByUserIdOrderByCreatedAtDesc(userId, defaultPageable);
        return commentList.stream().map(CommentResponseDTO::new).collect(Collectors.toList());
    }

    /** 전체 댓글 내 키워드 검색 (관리자 페이지) */
    public List<CommentResponseDTO> getCommentByKeyword(String keyword, int page, int size) {
        Pageable defaultPageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Comment> commentList =
                commentRepository.findAllByKeywordOrderByCreatedAtDesc(keyword, defaultPageable);
        return commentList.stream().map(CommentResponseDTO::new).collect(Collectors.toList());
    }

    /** 댓글 삭제 */
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}