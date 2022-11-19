package com.nashtech.rookies.services.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nashtech.rookies.dto.request.user.UserRequestDto;
import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.exceptions.InvalidDataInputException;
import com.nashtech.rookies.mapper.UserMapper;
import com.nashtech.rookies.repository.UserRepository;
import com.nashtech.rookies.utils.UserUtil;

public class AuthServiceImplTest {

	UserUtil userUtil;
	UserRepository userRepository;
	UserMapper userMapper;

	AuthServiceImpl authServiceImpl;

	@BeforeEach
	void beforeEach() {
		userUtil = mock(UserUtil.class);
		userRepository = mock(UserRepository.class);
		userMapper = mock(UserMapper.class);

		authServiceImpl = new AuthServiceImpl(userUtil, userRepository, userMapper);
	}

	@Test
	void createUser_ShouldThrowInvalidDataInputException_WhenRoleInValid() {
		UserRequestDto dto = UserRequestDto.builder().role("USER").build();

		InvalidDataInputException actualException = Assertions.assertThrows(InvalidDataInputException.class, () -> {
			authServiceImpl.createUser(dto);
		});
		Assertions.assertEquals("Role is invalid", actualException.getMessage());
	}

	@Test
	void createUser_ShouldThrowInvalidDataInputException_WhenDobInValid() {
		UserRequestDto dto = UserRequestDto.builder().dob("30/02/2020").role("ADMIN").build();

		when(userUtil.isValidDate(dto.getDob())).thenReturn(false);

		InvalidDataInputException actualException = Assertions.assertThrows(InvalidDataInputException.class, () -> {
			authServiceImpl.createUser(dto);
		});
		Assertions.assertEquals("Date of birth is invalid", actualException.getMessage());
	}

	@Test
	void createUser_ShouldThrowInvalidDataInputException_WhenJoinedDateInValid() {
		UserRequestDto dto = UserRequestDto.builder().dob("28/02/1990").joinedDate("28/02/2020").role("ADMIN").build();

		when(userUtil.isValidDate(dto.getDob())).thenReturn(true);

		InvalidDataInputException actualException = Assertions.assertThrows(InvalidDataInputException.class, () -> {
			authServiceImpl.createUser(dto);
		});
		Assertions.assertEquals("Join date is invalid", actualException.getMessage());
	}

	@Test
	void createUser_ShouldThrowInvalidDataInputException_WhenDobUnder18() {
		Date dobDate = mock(Date.class);
		Date joinedDate = mock(Date.class);

		UserRequestDto dto = UserRequestDto.builder().dob("28/02/1990").joinedDate("28/02/2020").role("ADMIN").build();

		when(userUtil.isValidDate(dto.getDob())).thenReturn(true);
		when(userUtil.isValidDate(dto.getJoinedDate())).thenReturn(true);

		System.out.println(dto.getDob());
		System.out.println(dto.getJoinedDate());

		when(userUtil.convertStrDateToObDate(dto.getDob())).thenReturn(dobDate);
		when(userUtil.convertStrDateToObDate(dto.getDob())).thenReturn(joinedDate);

		when(userUtil.isValidAge(dobDate)).thenReturn(false);

		InvalidDataInputException actualException = Assertions.assertThrows(InvalidDataInputException.class, () -> {
			authServiceImpl.createUser(dto);
		});
		Assertions.assertEquals("User is under 18", actualException.getMessage());
	}

	@Test
	void createUser_ShouldThrowInvalidDataInputException_WhenJoinedDateBeforeDob() {
		Date dobDate = mock(Date.class);
		Date joinedDate = mock(Date.class);

		UserRequestDto dto = UserRequestDto.builder().dob("28/02/1990").joinedDate("28/02/2020").role("ADMIN").build();

		when(userUtil.isValidDate("28/02/1990")).thenReturn(true);
		when(userUtil.isValidDate("28/02/2020")).thenReturn(true);

		when(userUtil.convertStrDateToObDate("28/02/1990")).thenReturn(dobDate);
		when(userUtil.convertStrDateToObDate("28/02/2020")).thenReturn(joinedDate);

		when(userUtil.isValidAge(dobDate)).thenReturn(true);

		when(joinedDate.before(dobDate)).thenReturn(true);

		InvalidDataInputException actualException = Assertions.assertThrows(InvalidDataInputException.class, () -> {
			authServiceImpl.createUser(dto);
		});
		Assertions.assertEquals("Joined date is not later than Date of Birth", actualException.getMessage());
	}

	@Test
	void createUser_ShouldSuccess_WhenDataValid() {
		Date dobDate = mock(Date.class);
		Date joinedDate = mock(Date.class);
		Users user = mock(Users.class);
		String code = "SD0001";

		UserRequestDto dto = UserRequestDto.builder().dob("28/02/1990").joinedDate("28/02/2020").role("ADMIN")
				.firstName("trong  ").lastName("bach  thanh").build();

		when(userUtil.isValidDate("28/02/1990")).thenReturn(true);
		when(userUtil.isValidDate("28/02/2020")).thenReturn(true);

		when(userUtil.convertStrDateToObDate("28/02/1990")).thenReturn(dobDate);
		when(userUtil.convertStrDateToObDate("28/02/2020")).thenReturn(joinedDate);

		when(userUtil.isValidAge(dobDate)).thenReturn(true);

		when(joinedDate.before(dobDate)).thenReturn(false);

		when(userUtil.capitalizeWord("trong  ")).thenReturn("Trong");
		when(userUtil.capitalizeWord("bach  thanh")).thenReturn("Bach Thanh");

		when(userUtil.generatePrefixUsername(dto.getFirstName(), dto.getLastName())).thenReturn("trongbt");

		when(userRepository.findByUsernameContaining("trongbt")).thenReturn(null);

		when(userUtil.generatePassword("trongbt", dto.getDob())).thenReturn("trongbt@28021990");

		when(userRepository.count()).thenReturn(0l);

		when(userUtil.generateCode(1l)).thenReturn(code);

		when(userMapper.mapUserUpdateDtoToUser(dto, "trongbt", dobDate, joinedDate, code)).thenReturn(user);

		when(userRepository.save(user)).thenReturn(user);

//		authServiceImpl.createUser(dto);
	}

}
