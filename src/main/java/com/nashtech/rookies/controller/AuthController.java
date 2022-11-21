package com.nashtech.rookies.controller;

import javax.validation.Valid;

import com.nashtech.rookies.dto.request.user.ChangePasswordRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nashtech.rookies.dto.request.user.LoginRequestDto;
import com.nashtech.rookies.dto.response.user.LoginResponseDto;
import com.nashtech.rookies.services.interfaces.AuthService;

@RestController
@RequestMapping("/api/v1/users")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {

		return ResponseEntity.status(HttpStatus.OK).body(authService.login(dto));
	}
	@PutMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequestDto dto){
		return authService.changePassword(dto);
	}
}
