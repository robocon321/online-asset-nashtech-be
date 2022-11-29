package com.nashtech.rookies.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.dto.request.assignment.CreateAssignmentDto;
import com.nashtech.rookies.services.interfaces.AssignmentService;

@RequestMapping("/api/v1/assignments")
@RestController
public class AssignmentController {

	@Autowired
	AssignmentService assignmentService;

//	Create assignment
	@PostMapping
	public ResponseEntity<?> createAssign(@Valid @RequestBody CreateAssignmentDto dto) {
		return ResponseEntity.ok().body(assignmentService.createAssignment(dto));
	}
}
