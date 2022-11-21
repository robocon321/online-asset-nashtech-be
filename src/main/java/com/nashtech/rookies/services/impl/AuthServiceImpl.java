package com.nashtech.rookies.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.dto.request.user.LoginRequestDto;
import com.nashtech.rookies.dto.response.user.LoginResponseDto;
import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.exceptions.InvalidDataInputException;
import com.nashtech.rookies.jwt.JwtProvider;
import com.nashtech.rookies.repository.UsersRepository;
import com.nashtech.rookies.services.interfaces.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	AuthenticationManager authenticationManager;
	JwtProvider jwtProvider;
	UsersRepository usersRepository;
	PasswordEncoder passwordEncoder;

	@Autowired
	public AuthServiceImpl(AuthenticationManager authenticationManager, JwtProvider jwtProvider,
			UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public LoginResponseDto login(LoginRequestDto dto) {

		Optional<Users> userOptional = usersRepository.findByUsername(dto.getUsername());

		if (userOptional.isEmpty()) {
			throw new InvalidDataInputException("username or password is incorrect");
		}

		Users user = userOptional.get();

		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new InvalidDataInputException("username or password is incorrect");
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.createToken(authentication);

		LoginResponseDto loginResponseDto = new LoginResponseDto(user.getUsername(), user.getFullName(), user.getRole(),
				user.getLocation(), token, user.isEnabled());

		return loginResponseDto;
	}

}
