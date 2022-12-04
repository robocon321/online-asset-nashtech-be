package com.nashtech.rookies.dto.response.assignment;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentResponseDto {
	private Long id;
	private String assetCode;
	private String assetName;
	private String categoryName;
	private String assignedTo;
	private String assignedBy;
	private Date assignedDate;
	private boolean stateReturnRequest;
	private String state;
}
