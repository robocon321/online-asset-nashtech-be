package com.nashtech.rookies.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nashtech.rookies.repository.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nashtech.rookies.dto.request.user.UserRequestDto;
import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.exceptions.InvalidDataInputException;
import com.nashtech.rookies.mapper.UserMapper;
import com.nashtech.rookies.utils.UserUtil;

public class UsersServiceImplTest {

	UserUtil userUtil;
	UsersRepository usersRepository;
	UserMapper userMapper;

	UsersServiceImpl usersServiceImpl;

	Users initUsers;
	Users expectedUsers;
	List<Users> mockUsers = new ArrayList<>();

	@BeforeEach
	void beforeEach() {
		userUtil = mock(UserUtil.class);
		usersRepository = mock(UsersRepository.class);
		userMapper = mock(UserMapper.class);
		initUsers = mock(Users.class);
		expectedUsers = mock(Users.class);


		usersServiceImpl = new UsersServiceImpl(userUtil, usersRepository, userMapper);

		for(int i = 0; i < 10; i++){
			mockUsers.add(
					new Users()
			);
		}
	}

	//region	Test show all users
	@Test
	public void ShowAllByLocation_ShouldReturnAllUsers_WhenLocationFromAdminValid() throws Exception {
		when(usersRepository.findByLocation("HCM")).thenReturn(mockUsers);
		List<Users> actualUsers = usersServiceImpl.showAll();
		assertEquals(mockUsers.size(), actualUsers.size());
		verify(usersRepository).findByLocation("HCM");
	}

	@Test
	public void findByUserId_ShouldReturnUsers_WhenFoundId() throws Exception {
		when(usersRepository.findUsersById(1L)).thenReturn(initUsers);
		Users actualUsers = usersServiceImpl.findByUserId(1L);
		assertEquals(initUsers, actualUsers);
		verify(usersRepository).findUsersById(1L);
	}

	@Test
	public void findByUserId_ShouldThrowException_WhenNotFoundId() throws Exception {
		Exception expectedException = assertThrows(Exception.class, () -> usersServiceImpl.findByUserId(1L));
		assertEquals("Can't find any user with id: 1", expectedException.getMessage());
	}

	@Test
	public void testSortByJoinedDateDesc() {
		when(usersRepository.findByLocationOrderByJoinedDateDesc("HCM")).thenReturn(mockUsers);
		List<Users> actualUsers = usersServiceImpl.sortByJoinedDateDesc();
		assertEquals(mockUsers.size(), actualUsers.size());
		verify(usersRepository).findByLocationOrderByJoinedDateDesc("HCM");
	}
	@Test
	public void testSortByJoinedDateAsc() {
		when(usersRepository.findByLocationOrderByJoinedDateAsc("HCM")).thenReturn(mockUsers);
		List<Users> actualUsers = usersServiceImpl.sortByJoinedDateAsc();
		assertEquals(mockUsers.size(), actualUsers.size());
		verify(usersRepository).findByLocationOrderByJoinedDateAsc("HCM");
	}
	@Test
	public void testSortByCodeDesc() {
		when(usersRepository.findByLocationOrderByCodeDesc("HCM")).thenReturn(mockUsers);
		List<Users> actualUsers = usersServiceImpl.sortByCodeDesc();
		assertEquals(mockUsers.size(), actualUsers.size());
		verify(usersRepository).findByLocationOrderByCodeDesc("HCM");
	}
	@Test
	public void testSortByCodeAsc() {
		when(usersRepository.findByLocationOrderByCodeAsc("HCM")).thenReturn(mockUsers);
		List<Users> actualUsers = usersServiceImpl.sortByCodeAsc();
		assertEquals(mockUsers.size(), actualUsers.size());
		verify(usersRepository).findByLocationOrderByCodeAsc("HCM");
	}
	@Test
	public void testSortByFullNameDesc() {
		when(usersRepository.findByLocationOrderByFullNameDesc("HCM")).thenReturn(mockUsers);
		List<Users> actualUsers = usersServiceImpl.sortByFullNameDesc();
		assertEquals(mockUsers.size(), actualUsers.size());
		verify(usersRepository).findByLocationOrderByFullNameDesc("HCM");
	}
	@Test
	public void testSortByFullNameAsc() {
		when(usersRepository.findByLocationOrderByFullNameAsc("HCM")).thenReturn(mockUsers);
		List<Users> actualUsers = usersServiceImpl.sortByFullNameAsc();
		assertEquals(mockUsers.size(), actualUsers.size());
		verify(usersRepository).findByLocationOrderByFullNameAsc("HCM");
	}
	@Test
	public void testSortByRoleDesc() {
		when(usersRepository.findByLocationOrderByRoleDesc("HCM")).thenReturn(mockUsers);
		List<Users> actualUsers = usersServiceImpl.sortByRoleDesc();
		assertEquals(mockUsers.size(), actualUsers.size());
		verify(usersRepository).findByLocationOrderByRoleDesc("HCM");
	}
	@Test
	public void testSortByRoleAsc() {
		when(usersRepository.findByLocationOrderByRoleAsc("HCM")).thenReturn(mockUsers);
		List<Users> actualUsers = usersServiceImpl.sortByRoleAsc();
		assertEquals(mockUsers.size(), actualUsers.size());
		verify(usersRepository).findByLocationOrderByRoleAsc("HCM");
	}
//	endregion

	//	region Test create user
	@Test
	void createUser_ShouldThrowInvalidDataInputException_WhenRoleInValid() {
		UserRequestDto dto = UserRequestDto.builder().role("USER").build();

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			usersServiceImpl.createUser(dto);
		});
		assertEquals("Role is invalid", actualException.getMessage());
	}

	@Test
	void createUser_ShouldThrowInvalidDataInputException_WhenDobInValid() {
		UserRequestDto dto = UserRequestDto.builder().dob("30/02/2020").role("ADMIN").build();

		when(userUtil.isValidDate(dto.getDob())).thenReturn(false);

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			usersServiceImpl.createUser(dto);
		});
		assertEquals("Date of birth is invalid", actualException.getMessage());
	}

	@Test
	void createUser_ShouldThrowInvalidDataInputException_WhenJoinedDateInValid() {
		UserRequestDto dto = UserRequestDto.builder().dob("28/02/1990").joinedDate("28/02/2020").role("ADMIN").build();

		when(userUtil.isValidDate(dto.getDob())).thenReturn(true);

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			usersServiceImpl.createUser(dto);
		});
		assertEquals("Join date is invalid", actualException.getMessage());
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

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			usersServiceImpl.createUser(dto);
		});
		assertEquals("User is under 18", actualException.getMessage());
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

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			usersServiceImpl.createUser(dto);
		});
		assertEquals("Joined date is not later than Date of Birth", actualException.getMessage());
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

		when(usersRepository.findByUsernameContaining("trongbt")).thenReturn(null);

		when(userUtil.generatePassword("trongbt", dto.getDob())).thenReturn("trongbt@28021990");

		when(usersRepository.count()).thenReturn(0l);

		when(userUtil.generateCode(1l)).thenReturn(code);

		when(userMapper.mapUserUpdateDtoToUser(dto, "trongbt", dobDate, joinedDate, code)).thenReturn(user);

		when(usersRepository.save(user)).thenReturn(user);
	}

//	endregion

}
