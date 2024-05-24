package com.elice.meetstudy.domain.post.service;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.category.repository.CategoryRepository;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.post.dto.PostCreate;
import com.elice.meetstudy.domain.post.repository.PostRepository;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;

  @Autowired
  public PostService(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
  }

  public Post write(PostCreate postCreate) {
    // User와 Category를 가져와서 엔티티로 설정
    User user = userRepository.findById(postCreate.getUserId())
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 X."));
    Category category = categoryRepository.findById(postCreate.getCategoryId())
        .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 X."));

    // Post 엔티티 생성
    Post post = Post.builder()
        .user(user)
        .category(category)
        .title(postCreate.getTitle())
        .content(postCreate.getContent())
        .build();

    return postRepository.save(post);
  }
}
