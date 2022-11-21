package com.nashtech.rookies.services.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import com.nashtech.rookies.dto.request.user.ChangePasswordRequestDto;
import com.nashtech.rookies.security.userprincal.UserPrinciple;
import com.nashtech.rookies.utils.UserUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nashtech.rookies.dto.request.user.LoginRequestDto;
import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.exceptions.InvalidDataInputException;
import com.nashtech.rookies.jwt.JwtProvider;
import com.nashtech.rookies.repository.UsersRepository;

public class AuthServiceImplTest {
	AuthenticationManager authenticationManager;
	JwtProvider jwtProvider;
	UsersRepository usersRepository;
	PasswordEncoder passwordEncoder;

	UserUtil userUtil;
	UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
	Authentication authentication;

//	UserPrinciple userPrinciple;
	AuthServiceImpl authServiceImpl;

	@BeforeEach
	void beforeEach() {
		userUtil = mock(UserUtil.class);
		authenticationManager = mock(AuthenticationManager.class);
		jwtProvider = mock(JwtProvider.class);
		usersRepository = mock(UsersRepository.class);
		passwordEncoder = mock(PasswordEncoder.class);
		usernamePasswordAuthenticationToken = mock(UsernamePasswordAuthenticationToken.class);
		authentication = mock(Authentication.class);

		authServiceImpl = new AuthServiceImpl(authenticationManager, jwtProvider, usersRepository, passwordEncoder);
	}

	@Test
	void login_ShouldThrowInvalidDataInputException_WhenUsernameInvalid() {
		LoginRequestDto dto = LoginRequestDto.builder().username("trongbt123").password("123456").build();

		when(usersRepository.findByUsername("trongbt123")).thenReturn(Optional.empty());

		InvalidDataInputException actualException = Assertions.assertThrows(InvalidDataInputException.class, () -> {
			authServiceImpl.login(dto);
		});

		Assertions.assertEquals("username or password is incorrect", actualException.getMessage());
	}

	@Test
	void login_ShouldThrowInvalidDataInputException_WhenPasswordInvalid() {
		LoginRequestDto dto = LoginRequestDto.builder().username("trongbt123").password("123456").build();
		Users user = mock(Users.class);

		when(usersRepository.findByUsername("trongbt123")).thenReturn(Optional.of(user));

		when(passwordEncoder.matches("123456", user.getPassword())).thenReturn(false);

		InvalidDataInputException actualException = Assertions.assertThrows(InvalidDataInputException.class, () -> {
			authServiceImpl.login(dto);
		});

		Assertions.assertEquals("username or password is incorrect", actualException.getMessage());
	}

	@Test
	void login_ShouldReturnLoginResponseDto_WhenDatavalid() {
		LoginRequestDto dto = LoginRequestDto.builder().username("trongbt123").password("123456").build();
		Users user = mock(Users.class);

		when(usersRepository.findByUsername("trongbt123")).thenReturn(Optional.of(user));

		when(passwordEncoder.matches("123456", user.getPassword())).thenReturn(true);

		when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenReturn(authentication);
		
		
		authServiceImpl.login(dto);
	}

	@Test
	void changePassword_ShouldReturnErr_WhenDataNotValid(){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		ChangePasswordRequestDto dto = ChangePasswordRequestDto.builder().oldPassword("trongbt123").newPassword("123456").build();
		Users user = mock(Users.class);
		Long id = 1L;
		when(usersRepository.findUsersById(id)).thenReturn(user);
		map.put("message","Minimum eight characters, at least one letter, one number and one special character");
		ResponseEntity<?> expected = new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
		ResponseEntity<?> actual = authServiceImpl.changePassword(dto);
		Assertions.assertEquals(expected,actual);
	}

//	@Test
//	void changePassword_ShouldReturnErr_WhenRegexNotValid(){
//		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		ChangePasswordRequestDto dto = ChangePasswordRequestDto.builder().oldPassword("trongbt123").newPassword("123456").build();
//		Users user = mock(Users.class);
//		user.setEnabled(false);
//		Long id = 1L;
//		when(usersRepository.findUsersById(id)).thenReturn(user);
////		when(Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", dto.getNewPassword())).thenReturn(true);
//		Assert.assertTrue(Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",dto.getNewPassword()));
//		map.put("message","Successfully");
//		ResponseEntity<?> expected = new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
//		ResponseEntity<?> actual = authServiceImpl.changePassword(dto);
//		Assertions.assertEquals(expected,actual);
//	}

}
