package com.nashtech.rookies.services.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nashtech.rookies.dto.request.assignment.CreateAssignmentDto;
import com.nashtech.rookies.dto.response.assignment.AssignmentResponseDto;
import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.entity.Assignment;
import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.exceptions.InvalidDataInputException;
import com.nashtech.rookies.mapper.AssignmentMapper;
import com.nashtech.rookies.repository.AssetRepository;
import com.nashtech.rookies.repository.AssignmentRepository;
import com.nashtech.rookies.repository.UsersRepository;
import com.nashtech.rookies.utils.UserUtil;

public class AssignmentServiceImplTest {
	AssignmentRepository assignmentRepository;
	UsersRepository usersRepository;
	AssetRepository assetRepository;

	UserUtil userUtil;
	AssignmentMapper assignmentMapper;

	AssignmentServiceImpl assignmentServiceImpl;

	@BeforeEach
	void beforeEach() {
		assignmentRepository = mock(AssignmentRepository.class);
		usersRepository = mock(UsersRepository.class);
		assetRepository = mock(AssetRepository.class);

		userUtil = mock(UserUtil.class);
		assignmentMapper = mock(AssignmentMapper.class);

		assignmentServiceImpl = new AssignmentServiceImpl(assignmentRepository, usersRepository, assetRepository,
				userUtil, assignmentMapper);
	}

	@Test
	void createAssignment_ShouldThrowInvalidDataInputException_WhenAssignedDateInValid() {

		CreateAssignmentDto dto = CreateAssignmentDto.builder().assignedDate("30/02/2022").build();

		when(userUtil.isValidDate("30/02/2022")).thenReturn(false);

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.createAssignment(dto);
		});
		assertEquals("AssignedDate is invalid", actualException.getMessage());
	}

	@Test
	void createAssignment_ShouldThrowInvalidDataInputException_WhenAssignedDateBeforeNowDate() {
		Date assignedDate = mock(Date.class);
		Date nowDate = mock(Date.class);

		CreateAssignmentDto dto = CreateAssignmentDto.builder().assignedDate("28/02/2022").build();

		when(userUtil.isValidDate("28/02/2022")).thenReturn(true);

		when(userUtil.convertStrDateToObDate(dto.getAssignedDate())).thenReturn(assignedDate);

		when(userUtil.generateFormatNowDay()).thenReturn(nowDate);

		when(assignedDate.before(nowDate)).thenReturn(true);

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.createAssignment(dto);
		});
		assertEquals("AssignedDate must be after NowDate", actualException.getMessage());

	}

	@Test
	void createAssignment_ShouldThrowInvalidDataInputException_WhenUserIdInvalid() {
		Date assignedDate = mock(Date.class);
		Date nowDate = mock(Date.class);

		CreateAssignmentDto dto = CreateAssignmentDto.builder().assignedDate("28/02/2022").userId(2l).build();

		when(userUtil.isValidDate("28/02/2022")).thenReturn(true);

		when(userUtil.convertStrDateToObDate(dto.getAssignedDate())).thenReturn(assignedDate);

		when(userUtil.generateFormatNowDay()).thenReturn(nowDate);

		when(assignedDate.before(nowDate)).thenReturn(false);

		when(usersRepository.findById(dto.getUserId())).thenReturn(Optional.empty());

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.createAssignment(dto);
		});
		assertEquals("User not found", actualException.getMessage());

	}

	@Test
	void createAssignment_ShouldThrowInvalidDataInputException_WhenAssetIdInvalid() {
		Date assignedDate = mock(Date.class);
		Date nowDate = mock(Date.class);

		Users user = mock(Users.class);

		CreateAssignmentDto dto = CreateAssignmentDto.builder().assignedDate("28/02/2022").userId(2l).assetId(2l)
				.build();

		when(userUtil.isValidDate("28/02/2022")).thenReturn(true);

		when(userUtil.convertStrDateToObDate(dto.getAssignedDate())).thenReturn(assignedDate);

		when(userUtil.generateFormatNowDay()).thenReturn(nowDate);

		when(assignedDate.before(nowDate)).thenReturn(false);

		when(usersRepository.findById(2l)).thenReturn(Optional.of(user));

		when(assetRepository.findById(dto.getAssetId())).thenReturn(Optional.empty());

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.createAssignment(dto);
		});
		assertEquals("Asset not found", actualException.getMessage());

	}

	@Test
	void createAssignment_ShouldReturnDto_WhenDataValid() {
		String state = "Waiting for acceptance";

		Date assignedDate = mock(Date.class);

		Date nowDate = mock(Date.class);

		Users user = mock(Users.class);

		Asset asset = mock(Asset.class);

		Users admin = mock(Users.class);

		Assignment assignment = mock(Assignment.class);

		AssignmentResponseDto expectAssignment = mock(AssignmentResponseDto.class);

		CreateAssignmentDto dto = CreateAssignmentDto.builder().assignedDate("28/02/2022").userId(2l).assetId(2l)
				.build();

		when(userUtil.isValidDate("28/02/2022")).thenReturn(true);

		when(userUtil.convertStrDateToObDate(dto.getAssignedDate())).thenReturn(assignedDate);

		when(userUtil.generateFormatNowDay()).thenReturn(nowDate);

		when(assignedDate.before(nowDate)).thenReturn(false);

		when(usersRepository.findById(2l)).thenReturn(Optional.of(user));

		when(assetRepository.findById(dto.getAssetId())).thenReturn(Optional.of(asset));

		when(userUtil.getIdFromUserPrinciple()).thenReturn(2l);

		when(usersRepository.findUsersById(2l)).thenReturn(admin);

		when(assignmentMapper.mapToAssignment(asset, admin, user, dto.getNote(), state, assignedDate, nowDate))
				.thenReturn(assignment);
		when(assignment.getAsset()).thenReturn(asset);

		when(assignmentRepository.save(assignment)).thenReturn(assignment);

		when(assignmentMapper.mapToResponseAssignment(assignment)).thenReturn(expectAssignment);

		AssignmentResponseDto actualAssignment = assignmentServiceImpl.createAssignment(dto);

		verify(asset).setState("Not available");

		assertThat(expectAssignment, is(actualAssignment));

	}

}
