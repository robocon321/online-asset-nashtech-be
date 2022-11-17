package com.nashtech.rookies.services.interfaces;

import com.nashtech.rookies.dto.request.user.UserRequestDto;

public interface AuthService {
	void createUser(UserRequestDto dto);
}
