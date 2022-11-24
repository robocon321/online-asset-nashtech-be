package com.nashtech.rookies.mapper;

import org.springframework.stereotype.Component;

import com.nashtech.rookies.entity.Category;

@Component
public class CategoryMapper {
	public Category mapToCategory(String name, String code) {		
		return Category.builder()
				.name(name)
				.code(code)
				.build();	
	}
}
