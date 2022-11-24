package com.nashtech.rookies.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nashtech.rookies.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Optional<Category> findByName(String name);

}