package com.nashtech.rookies.dto.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
	public LoginResponseDto(String username, String fullName, String role,
			String location, String token) {
		this.username = username;
		this.fullName = fullName;
		this.role = role;
		this.location = location;
		this.token = token;
	}

	private String username;
	private String fullName;
	private String location;
	private String role;
	private String token;

}
