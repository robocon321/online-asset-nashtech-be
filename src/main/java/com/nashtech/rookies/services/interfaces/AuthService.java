package com.nashtech.rookies.services.interfaces;

import com.nashtech.rookies.dto.request.user.ChangePasswordRequestDto;
import com.nashtech.rookies.dto.request.user.LoginRequestDto;
import com.nashtech.rookies.dto.response.user.LoginResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

	LoginResponseDto login(LoginRequestDto dto);

    ResponseEntity<?> changePassword(ChangePasswordRequestDto dto);
}
