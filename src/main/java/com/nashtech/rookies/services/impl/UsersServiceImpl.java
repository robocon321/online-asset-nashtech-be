package com.nashtech.rookies.services.impl;

import com.nashtech.rookies.dto.request.user.UserRequestDto;
import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.exceptions.InvalidDataInputException;
import com.nashtech.rookies.mapper.UserMapper;
import com.nashtech.rookies.repository.UsersRepository;
import com.nashtech.rookies.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements com.nashtech.rookies.services.interfaces.UsersService {

	UsersRepository usersRepository;
	UserMapper userMapper;
	UserUtil userUtil;
	PasswordEncoder passwordEncoder;

	@Autowired
	public UsersServiceImpl(UserUtil userUtil, UsersRepository usersRepository, UserMapper userMapper,
			PasswordEncoder passwordEncoder) {
		this.userUtil = userUtil;
		this.usersRepository = usersRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	public String getLocation() {
		UserDetails users = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Users> user = usersRepository.findByUsername(users.getUsername());
		return user.get().getLocation();
	}

//    region Show information
//    Find all users by admin locations
	@Override
	public List<Users> showAll() {
		return usersRepository.findByLocation(getLocation());
	}

//    Show information of user by id
	@Override
	public Users findByUserId(Long userId) throws Exception {
		if (userId == 0) {
			throw new Exception("UserID must not be empty");
		}

		Users users = usersRepository.findUsersById(userId);

		if (users == null) {
			throw new Exception("Can't find any user with id: " + userId);
		} else {
			return users;
		}
	}

//    Sort users by JoinedDate
	@Override
	public List<Users> sortByJoinedDateDesc() {
		return usersRepository.findByLocationOrderByJoinedDateDesc(getLocation());
	}

	@Override
	public List<Users> sortByJoinedDateAsc() {
		return usersRepository.findByLocationOrderByJoinedDateAsc(getLocation());
	}

//    Sort users by code
	@Override
	public List<Users> sortByCodeDesc() {
		return usersRepository.findByLocationOrderByCodeDesc(getLocation());
	}

	@Override
	public List<Users> sortByCodeAsc() {
		return usersRepository.findByLocationOrderByCodeAsc(getLocation());
	}

//    Sort users by full name
	@Override
	public List<Users> sortByFullNameDesc() {
		return usersRepository.findByLocationOrderByFullNameDesc(getLocation());
	}

	@Override
	public List<Users> sortByFullNameAsc() {
		return usersRepository.findByLocationOrderByFullNameAsc(getLocation());
	}

//    Sort users by role
	@Override
	public List<Users> sortByRoleDesc() {
		return usersRepository.findByLocationOrderByRoleDesc(getLocation());
	}
    @Override
    public List<Users> sortByRoleAsc() {
        return usersRepository.findByLocationOrderByRoleAsc(getLocation());
    }
//    endregion

//    region Create user
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

		List<Users> users = usersRepository.findByUsernameContaining(username);

		if (!users.isEmpty()) {
			String suffix = userUtil.generateSuffixUsername(users, username);
			username = username + suffix;
		}
		String password = userUtil.generatePassword(username, dto.getDob());

		Long count = usersRepository.count();

		String code = userUtil.generateCode(count);

		Users user = userMapper.mapUserUpdateDtoToUser(dto, username, dobDate, joinedDate, code);

		user.setLocation(userUtil.getAddressFromUserPrinciple());

		user.setPassword(passwordEncoder.encode(password));
		
		user.setEnabled(false);

		user = usersRepository.save(user);

	}

    
//    endregion

}
