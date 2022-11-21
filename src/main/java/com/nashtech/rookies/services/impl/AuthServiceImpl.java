package com.nashtech.rookies.services.impl;

import com.nashtech.rookies.dto.request.user.ChangePasswordRequestDto;
import com.nashtech.rookies.dto.request.user.LoginRequestDto;
import com.nashtech.rookies.dto.response.user.LoginResponseDto;
import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.exceptions.InvalidDataInputException;
import com.nashtech.rookies.jwt.JwtProvider;
import com.nashtech.rookies.repository.UsersRepository;
import com.nashtech.rookies.security.userprincal.UserPrinciple;
import com.nashtech.rookies.services.interfaces.AuthService;
import com.nashtech.rookies.utils.UserUtil;
import com.nashtech.rookies.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

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

	@Override
	public ResponseEntity<?> changePassword(ChangePasswordRequestDto dto){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		UserPrinciple userPrinciple= (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Users user = usersRepository.findUsersById(Utils.getIdFromUserPrinciple());
		boolean checkRegex = Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", dto.getNewPassword());

		if(!checkRegex){
			map.put("message","Minimum eight characters, at least one letter, one number and one special character");
			return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
		}

		if(!user.isEnabled()){
			user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
			user.setEnabled(true);
			usersRepository.save(user);
			map.put("message","Successfully");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}

		if(dto.getOldPassword()==null){
			map.put("message","Old password not empty");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}

		if(!passwordEncoder.matches(dto.getOldPassword(),user.getPassword())){
			map.put("message","Old Password incorrect");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
		else{
			user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
			usersRepository.save(user);
			map.put("message","Successfully");
			return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}


}
