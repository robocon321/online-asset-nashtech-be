package com.nashtech.rookies.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.dto.request.user.UserRequestDto;
import com.nashtech.rookies.services.interfaces.AuthService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
public class AuthController {
	AuthService authService;

	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/create")
	public void createUser(@Valid @RequestBody UserRequestDto dto) {
		authService.createUser(dto);
	}
}
