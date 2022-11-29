package com.nashtech.rookies.services.interfaces;

import com.nashtech.rookies.dto.request.assignment.CreateAssignmentDto;
import com.nashtech.rookies.dto.response.assignment.AssignmentResponseDto;

public interface AssignmentService {
	AssignmentResponseDto createAssignment(CreateAssignmentDto dto);
}
