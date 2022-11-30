package com.nashtech.rookies.services.interfaces;

import com.nashtech.rookies.dto.request.assignment.CreateAssignmentDto;
import com.nashtech.rookies.dto.response.assignment.AssignmentResponseDto;
import com.nashtech.rookies.dto.response.assignment.AssignmentUpdateResponseDto;

public interface AssignmentService {
	AssignmentResponseDto createAssignment(CreateAssignmentDto dto);

	AssignmentUpdateResponseDto getUpdateAssignmentById(Long id);
}
