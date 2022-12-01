package com.nashtech.rookies.services.interfaces;

import com.nashtech.rookies.dto.request.assignment.CreateAssignmentDto;
import com.nashtech.rookies.dto.response.assignment.AssignmentResponseDto;

import java.util.List;

public interface AssignmentService {
	AssignmentResponseDto createAssignment(CreateAssignmentDto dto);

    List<AssignmentResponseDto> getListAssignmentofUser();
}
