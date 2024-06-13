package com.elice.meetstudy.domain.admin.service;

import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.post.dto.PostResponseDTO;
import com.elice.meetstudy.domain.post.repository.PostRepository;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.util.EntityFinder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminPostService {

    private final PostRepository postRepository;
    private final EntityFinder entityFinder;

    @Autowired
    public AdminPostService(PostRepository postRepository, EntityFinder entityFinder) {
        this.postRepository = postRepository;
        this.entityFinder = entityFinder;
    }

    // 모든 게시글 조회
    public List<PostResponseDTO> getAllPosts(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());
    }

    // 회원별로 작성한 게시글 조회
    public List<PostResponseDTO> getPostsByUser(Long userId, Pageable pageable) {
        return postRepository.findByUserId(userId, pageable)
                .stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());
    }

    // 특정 게시글 조회
    public PostResponseDTO getPost(Long postId) {
        return new PostResponseDTO(entityFinder.findPost(postId));
    }

    // 게시글 삭제
    public void deletePost(Long id) {
        Post foundPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 게시물을 찾을 수 없습니다. [ID: " + id + "]"));

        postRepository.delete(foundPost);
    }
}