package com.nashtech.rookies.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nashtech.rookies.entity.Category;
import com.nashtech.rookies.repository.CategoryRepository;

public class CategoryServiceImplTest {
	CategoryRepository categoryRepository;
	CategoryServiceImpl categoryServiceImpl;

	List<Category> mockCategories = new ArrayList<>();

	@BeforeEach
	void beforeEach() {
		categoryRepository = mock(CategoryRepository.class);
		categoryServiceImpl = new CategoryServiceImpl(categoryRepository);

		for (int i = 0; i < 10; i++) {
			mockCategories.add(new Category());
		}

	}

	@Test
	public void getAll_ShouldReturnAllCategories_WhenDataValid() {
		when(categoryRepository.findAll()).thenReturn(mockCategories);
		List<Category> actualCategories = categoryServiceImpl.getAll();
		assertEquals(mockCategories.size(), actualCategories.size());
	}

}
