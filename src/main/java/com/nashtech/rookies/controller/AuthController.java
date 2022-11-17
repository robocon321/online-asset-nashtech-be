package com.nashtech.rookies.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.dto.request.user.UserRequestDto;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class AuthController {
	
	@PostMapping("/register")
	public void createUser(UserRequestDto dto) {
		
	}
}
