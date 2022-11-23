package com.nashtech.rookies.repository;

import com.nashtech.rookies.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}