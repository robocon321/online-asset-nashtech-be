package com.nashtech.rookies.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.nashtech.rookies.dto.response.assignment.AssignmentResponseDto;
import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.entity.Assignment;
import com.nashtech.rookies.entity.Users;

@Component
public class AssignmentMapper {
	public Assignment mapToAssignment(Asset asset , Users assignedBy, Users assignedTo, String note, String state, Date assignedDate,
			Date createdDate) {
		System.out.println("Hello");
		return Assignment.builder().asset(asset).assignedBy(assignedBy).assignedTo(assignedTo).note(note).state(state)
				.assignedDate(assignedDate).createdDate(createdDate).build();
	}
	
	public AssignmentResponseDto mapToResponseAssignment(Assignment assignment) {
		return AssignmentResponseDto.builder()
				.id(assignment.getId())
				.assetCode(assignment.getAsset().getCode())
				.assetName(assignment.getAsset().getName())
				.assignedTo(assignment.getAssignedTo().getUsername())
				.assignedBy(assignment.getAssignedBy().getUsername())
				.assignedDate(assignment.getAssignedDate())
				.state(assignment.getState())
				.build();
	}
}
