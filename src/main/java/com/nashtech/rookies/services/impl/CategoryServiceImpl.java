package com.nashtech.rookies.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.entity.Category;
import com.nashtech.rookies.repository.CategoryRepository;
import com.nashtech.rookies.services.interfaces.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	CategoryRepository categoryRepository;

	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}

}