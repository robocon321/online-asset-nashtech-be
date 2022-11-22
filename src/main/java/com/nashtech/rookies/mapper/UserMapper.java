package com.nashtech.rookies.mapper;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nashtech.rookies.dto.request.user.UserRequestDto;
import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.utils.UserUtil;

@Component
public class UserMapper {
	public Users mapUserUpdateDtoToUser(UserRequestDto dto, String username, Date dobDate, Date joinedDate, String code) {
		
		return Users.builder()
				.firstName(dto.getFirstName())
				.lastName(dto.getLastName())
				.fullName(dto.getFirstName() + " " + dto.getLastName())
				.dob(dobDate)
				.joinedDate(joinedDate)
				.createdDate(new Date())
				.username(username)
				.role(dto.getRole())
				.gender(dto.isGender())
				.code(code)
				.build();
	}
}
