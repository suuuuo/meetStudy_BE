package com.elice.meetstudy.domain.post.service;

import com.elice.meetstudy.domain.category.repository.CategoryRepository;
import com.elice.meetstudy.domain.post.repository.PostRepository;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import com.elice.meetstudy.util.EntityFinder;
import org.junit.jupiter.api.BeforeEach;
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
  //    String jwtToken = "fakeToken";
  //    String title = "테스트 제목";
  //    String content = "테스트 내용";
  //
  //    // EntityFinder가 유저와 카테고리를 찾을 때 리턴할 값 설정
  //    User user = new User("test@test.com", "password", "username", "닉넴", Role.USER);
  //    when(tokenUtility.getUserIdFromToken(jwtToken)).thenReturn(userId);
  //    when(entityFinder.findUserById(userId)).thenReturn(user);
  //
  //    Category category = new Category("카테고리 이름", "카테고리 설명");
  //    when(entityFinder.findCategoryById(categoryId)).thenReturn(category);
  //
  //    // When
  //    PostWriteDTO postCreate = new PostWriteDTO(categoryId, title, content);
  ////    Post newPost =
  ////        Post.builder().user(user).category(category).title(title).content(content).build();
  ////    Post savedPost = newPost;
  //
  //    //when(postRepository.save(any(Post.class))).thenReturn(savedPost);
  //
  //    PostResponseDTO postResponse = postService.write(postCreate);
  //
  //    // Then
  //    assertNotNull(postResponse);
  //    // assertEquals(savedPost.getUser().getId(), postResponse.getUserId());
  //    //assertEquals(savedPost.getId(), postResponse.getId());
  //    assertEquals(title, postResponse.getTitle());
  //    assertEquals(content, postResponse.getContent());
  //  }
}
