package com.nashtech.rookies.dto.request.user;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {
	
	@NotBlank(message = "FirstName is required.")
	@Size(min = 1, max = 10)
	private String firstName;

	@NotBlank(message = "LastName is required.")
	@Size(min = 1, max = 50)
	private String lastName;
	
	@NotBlank(message = "Date of birth is required.")
	private Date dob;

	@NotBlank(message = "Gender is required.")
	private boolean gender;
	
	@NotBlank(message = "Role is required.")
	private String role;
	
	@NotBlank(message = "Join Date is required.")
	private Date joinedDate;
}
