package com.elice.meetstudy.domain.category.repository;

import com.elice.meetstudy.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}