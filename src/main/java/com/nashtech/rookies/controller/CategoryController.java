package com.nashtech.rookies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.services.interfaces.CategoryService;

@RequestMapping("/api/v1/categories")
@RestController
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;

	@RequestMapping("/")
	ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(categoryService.getAll());
	}
}
