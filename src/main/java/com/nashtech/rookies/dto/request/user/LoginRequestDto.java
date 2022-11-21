package com.nashtech.rookies.dto.request.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginRequestDto {
	@Size(max = 50, message = "Max length is 50")
	@NotBlank(message = "Username is required")
	private String username;

	@Size(max = 50, message = "Max length is 50")
	@NotBlank(message = "Password is required")
	private String password;
}
