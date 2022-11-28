package com.nashtech.rookies.dto.response.asset;

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
	private String assignedTo;
	private String assignedBy;
	private Date returnDate;
	private Date assignedDate;
}
