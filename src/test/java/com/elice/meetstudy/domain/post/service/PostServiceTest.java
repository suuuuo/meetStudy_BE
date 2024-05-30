package com.elice.meetstudy.domain.post.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.category.repository.CategoryRepository;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.post.dto.PostCreate;
import com.elice.meetstudy.domain.post.dto.PostResponse;
import com.elice.meetstudy.domain.post.repository.PostRepository;
import com.elice.meetstudy.domain.user.domain.Role;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import com.elice.meetstudy.util.EntityFinder;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PostServiceTest {

  @Mock private PostRepository postRepository;

  @Mock private UserRepository userRepository;

  @Mock private CategoryRepository categoryRepository;

  @Mock private EntityFinder entityFinder;

  @InjectMocks private PostService postService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  //  @Test
  //  @DisplayName("게시글 작성 테스트")
  //  void 게시글작성() {
  //    // Given
  //    Long userId = 1L;
  //    Long categoryId = 2L;
  //    String title = "테스트 제목";
  //    String content = "테스트 내용";
  //
  //    // EntityFinder가 유저와 카테고리를 찾을 때 리턴할 값 설정
  //    User user =
  //        new User(
  //            "test@test.com", "password", "username", "닉넴", LocalDateTime.now(), null,
  // Role.USER);
  //    when(entityFinder.findUserById(userId)).thenReturn(user);
  //
  //    Category category = new Category("카테고리 이름", "카테고리 설명");
  //    when(entityFinder.findCategoryById(categoryId)).thenReturn(category);
  //
  //    // When
  //    PostCreate postCreate = new PostCreate(userId, categoryId, title, content);
  //    Post savedPost = new Post(category, user, "테스트 제목", "테스트 내용");
  //    when(postRepository.save(any())).thenReturn(savedPost);
  //    PostResponse postResponse = postService.write(postCreate);
  //
  //    // Then
  //    assertNotNull(postResponse);
  //    assertEquals(savedPost.getUser().getId(), postResponse.getUserId());
  //    assertEquals(savedPost.getId(), postResponse.getId());
  //    assertEquals(title, postResponse.getTitle());
  //    assertEquals(content, postResponse.getContent());
  //  }
}
