package com.elice.meetstudy.domain.category.service;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.category.repository.CategoryRepository;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.post.repository.PostRepository;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    // 모든 카테고리를 조회
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    // id에 해당하는 카테고리 조회
    public Category findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 카테고리를 찾을 수 없습니다. [ID: " + id + "]"));
    }

    // 카테고리 생성
    public Category createCategory(String name, String description) {
        Category category = new Category(name, description);
        return categoryRepository.save(category);
    }

    // 카테고리 수정
    public Category updateCategory(Long id, String newName, String newDescription) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 카테고리를 찾을 수 없습니다. [ID: " + id + "]"));
        category.setName(newName);
        category.setDescription(newDescription);
        return categoryRepository.save(category);
    }

    // 카테고리 삭제
    public void deleteCategory(Long categoryId) {
        Category foundCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 카테고리를 찾을 수 없습니다."));

        // 페이지네이션을 위한 Pageable 객체 생성
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);

        // 해당 카테고리에 속한 게시글 삭제
        List<Post> posts = postRepository.findByCategoryId(categoryId, pageable);
        for (Post post : posts) {
            postRepository.deleteByIdAndUserId(post.getId(), post.getUser().getId());
        }

        // 카테고리 삭제
        categoryRepository.delete(foundCategory);
    }
}