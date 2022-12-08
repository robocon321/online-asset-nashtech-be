package com.nashtech.rookies.dto.request.assignment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CreateAssignmentDto {

	@NotNull(message = "User is required.")
	private Long userId;

	@NotNull(message = "Asset is required.")
	private Long assetId;

	private String note;

	@NotBlank(message = "AssignedDate is required.")
	private String assignedDate;
}
