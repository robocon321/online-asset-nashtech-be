package com.nashtech.rookies.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.dto.request.user.UserRequestDto;
import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.exceptions.InvalidDataInputException;
import com.nashtech.rookies.mapper.UserMapper;
import com.nashtech.rookies.repository.UserRepository;
import com.nashtech.rookies.services.interfaces.AuthService;
import com.nashtech.rookies.utils.UserUtil;

@Service
public class AuthServiceImpl implements AuthService {

	UserUtil userUtil;
	UserRepository userRepository;
	UserMapper userMapper;

	@Autowired
	public AuthServiceImpl(UserUtil userUtil, UserRepository userRepository, UserMapper userMapper) {
		this.userUtil = userUtil;
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@Override
	public void createUser(UserRequestDto dto) {

		if (!dto.getRole().equals("ADMIN") && !dto.getRole().equals("STAFF")) {
			throw new InvalidDataInputException("Role is invalid");
		}

		if (!userUtil.isValidDate(dto.getDob())) {
			throw new InvalidDataInputException("Date of birth is invalid");
		}

		if (!userUtil.isValidDate(dto.getJoinedDate())) {
			throw new InvalidDataInputException("Join date is invalid");
		}

		Date dobDate = userUtil.convertStrDateToObDate(dto.getDob());

		Date joinedDate = userUtil.convertStrDateToObDate(dto.getJoinedDate());

		if (!userUtil.isValidAge(dobDate)) {
			throw new InvalidDataInputException("User is under 18");
		}

		if (joinedDate.before(dobDate)) {
			throw new InvalidDataInputException("Joined date is not later than Date of Birth");
		}
		
		dto.setFirstName(userUtil.capitalizeWord(dto.getFirstName()));
		dto.setLastName(userUtil.capitalizeWord(dto.getLastName()));

		String username = userUtil.generatePrefixUsername(dto.getFirstName(), dto.getLastName());

		List<Users> users = userRepository.findByUsernameContaining(username);

		if (!users.isEmpty()) {
			String suffix = userUtil.generateSuffixUsername(users, username);
			username = username + suffix;
		}
		String password = userUtil.generatePassword(username, dto.getDob());

		Long count = userRepository.count();

		String code = userUtil.generateCode(count);

		Users user = userMapper.mapUserUpdateDtoToUser(dto, username, dobDate, joinedDate, code);

		user.setLocation("HCM");

		user.setPassword(password);

		user = userRepository.save(user);

	}

}
