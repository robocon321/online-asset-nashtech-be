package com.nashtech.rookies.services.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

	UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
	Authentication authentication;

	AuthServiceImpl authServiceImpl;

	@BeforeEach
	void beforeEach() {
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

}
