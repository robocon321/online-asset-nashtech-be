package com.nashtech.rookies.services.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nashtech.rookies.dto.request.assignment.CreateAssignmentDto;
import com.nashtech.rookies.dto.request.assignment.UpdateAssignmentDto;
import com.nashtech.rookies.dto.response.assignment.AssignmentDetailResponseDto;
import com.nashtech.rookies.dto.response.assignment.AssignmentResponseDto;
import com.nashtech.rookies.dto.response.assignment.AssignmentUpdateResponseDto;
import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.entity.Assignment;
import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.exceptions.InvalidDataInputException;
import com.nashtech.rookies.mapper.AssignmentMapper;
import com.nashtech.rookies.repository.AssetRepository;
import com.nashtech.rookies.repository.AssignmentRepository;
import com.nashtech.rookies.repository.UsersRepository;
import com.nashtech.rookies.security.userprincal.UserPrinciple;
import com.nashtech.rookies.utils.UserUtil;

public class AssignmentServiceImplTest {
	AssignmentRepository assignmentRepository;
	UsersRepository usersRepository;
	AssetRepository assetRepository;
	Authentication authentication;

	UserPrinciple userPrinciple;
	UserUtil userUtil;
	AssignmentMapper assignmentMapper;

	AssignmentServiceImpl assignmentServiceImpl;

	@BeforeEach
	void beforeEach() {
		assignmentRepository = mock(AssignmentRepository.class);
		usersRepository = mock(UsersRepository.class);
		assetRepository = mock(AssetRepository.class);

		authentication = mock(Authentication.class);
		userPrinciple = mock(UserPrinciple.class);
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
	void createAssignment_ShouldThrowInvalidDataInputException_WhenUserIsDisable() {
		Date assignedDate = mock(Date.class);
		Date nowDate = mock(Date.class);
		Users user = Users.builder().disabled(true).build();

		CreateAssignmentDto dto = CreateAssignmentDto.builder().assignedDate("28/02/2022").userId(2l).build();

		when(userUtil.isValidDate("28/02/2022")).thenReturn(true);

		when(userUtil.convertStrDateToObDate(dto.getAssignedDate())).thenReturn(assignedDate);

		when(userUtil.generateFormatNowDay()).thenReturn(nowDate);

		when(assignedDate.before(nowDate)).thenReturn(false);

		when(usersRepository.findById(dto.getUserId())).thenReturn(Optional.of(user));

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.createAssignment(dto);
		});
		assertEquals("User account is blocked", actualException.getMessage());

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
	void createAssignment_ShouldThrowInvalidDataInputException_WhenAssetStateInvalid() {
		Date assignedDate = mock(Date.class);
		Date nowDate = mock(Date.class);

		Users user = mock(Users.class);
		Asset asset = Asset.builder().state("ABC").build();

		CreateAssignmentDto dto = CreateAssignmentDto.builder().assignedDate("28/02/2022").userId(2l).assetId(2l)
				.build();

		when(userUtil.isValidDate("28/02/2022")).thenReturn(true);

		when(userUtil.convertStrDateToObDate(dto.getAssignedDate())).thenReturn(assignedDate);

		when(userUtil.generateFormatNowDay()).thenReturn(nowDate);

		when(assignedDate.before(nowDate)).thenReturn(false);

		when(usersRepository.findById(2l)).thenReturn(Optional.of(user));

		when(assetRepository.findById(dto.getAssetId())).thenReturn(Optional.of(asset));

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.createAssignment(dto);
		});
		assertEquals("Asset state must be Available", actualException.getMessage());

	}

	@Test
	void createAssignment_ShouldReturnDto_WhenDataValid() {
		String state = "Waiting for acceptance";

		Date assignedDate = mock(Date.class);

		Date nowDate = mock(Date.class);

		Users user = mock(Users.class);

		Asset asset = Asset.builder().state("Available").build();

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

		assertThat(expectAssignment, is(actualAssignment));

	}

	@Test
	void whenUserNotHaveAnyAssignment() {
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		UserPrinciple userPrinciple1 = new UserPrinciple();
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(authentication.getPrincipal()).thenReturn(userPrinciple1);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.getListAssignmentofUser();
		});
		assertEquals("Not found assignment", actualException.getMessage());
	}

	@Test
	void whenUserGetListAsmShouldReturnList() {
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		UserPrinciple userPrinciple1 = new UserPrinciple();
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(authentication.getPrincipal()).thenReturn(userPrinciple1);
		List<Assignment> assignmentList = new ArrayList<>();
		Assignment assignment = new Assignment();
		assignmentList.add(assignment);
		assignmentList.add(assignment);
		when(assignmentRepository.getAllAssignmentOfUser(Mockito.any(Timestamp.class), Mockito.any()))
				.thenReturn(assignmentList);

		List<AssignmentResponseDto> assignmentResponseDtos = new ArrayList<>();
		when(assignmentMapper.mapListAssignmentEntityToDto(assignmentList)).thenReturn(assignmentResponseDtos);
		assertEquals(assignmentResponseDtos, assignmentServiceImpl.getListAssignmentofUser());

	}

	@Test
	void whenGetAssignmentDetailNotFoundShouldReturnException() {
		Long id = 1L;
//		Assignment assignment = new Assignment();
//		Optional<Assignment> checkAssignment= Optional.of(assignment);
//		when( assignmentRepository.findById(id)).thenReturn(checkAssignment);
		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.getAssignmentDetail(id);
		});
		assertEquals("Not found this assignment", actualException.getMessage());

	}

	@Test
	void whenFoundAsmValid() {
		Long id = 1L;
		Assignment assignment = new Assignment();
		Optional<Assignment> checkAssignment = Optional.of(assignment);
		when(assignmentRepository.findById(id)).thenReturn(checkAssignment);
		assignment = checkAssignment.get();
		assignment.setComplete(false);
		AssignmentDetailResponseDto dto = new AssignmentDetailResponseDto();
		when(assignmentMapper.mapToResponseAssigmentDetail(assignment)).thenReturn(dto);
		assertEquals(dto, assignmentServiceImpl.getAssignmentDetail(id));
	}

	@Test
	void getUpdateAssignmentById_ShouldThrowInvalidDataInputException_WhenIdInValid() {

		when(assignmentRepository.findById(2l)).thenReturn(Optional.empty());

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.getUpdateAssignmentById(2l);
		});
		assertEquals("Assignment not found", actualException.getMessage());
	}

	@Test
	void getUpdateAssignmentById_ShouldThrowInvalidDataInputException_WhenStatusInValid() {

		Assignment assignment = Assignment.builder().state("Hello state").build();

		when(assignmentRepository.findById(2l)).thenReturn(Optional.of(assignment));

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.getUpdateAssignmentById(2l);
		});
		assertEquals("Assignment state must be Waiting for acceptance", actualException.getMessage());
	}

	@Test
	void getUpdateAssignmentById_ShouldReturnData_WhenDataValid() {

		Assignment assignment = Assignment.builder().state("Waiting for acceptance").build();
		AssignmentUpdateResponseDto expectedAssignment = mock(AssignmentUpdateResponseDto.class);
		when(assignmentRepository.findById(2l)).thenReturn(Optional.of(assignment));

		when(assignmentMapper.mapToUpdateResponseAssignment(assignment)).thenReturn(expectedAssignment);

		AssignmentUpdateResponseDto actualAssignment = assignmentServiceImpl.getUpdateAssignmentById(2l);

		assertThat(expectedAssignment, is(actualAssignment));

	}

	@Test
	void updateAssignment_ShouldThrowInvalidDataInputException_WhenAssignedDateInValid() {

		UpdateAssignmentDto dto = UpdateAssignmentDto.builder().assignedDate("26/02/2022").build();

		when(userUtil.isValidDate("26/02/2022")).thenReturn(false);

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.updateAssignment(dto);
		});
		assertEquals("AssignedDate is invalid", actualException.getMessage());
	}

	@Test
	void updateAssignment_ShouldThrowInvalidDataInputException_WhenAssignmentInValid() {

		UpdateAssignmentDto dto = UpdateAssignmentDto.builder().assignedDate("26/02/2022").build();

		when(userUtil.isValidDate("26/02/2022")).thenReturn(true);

		when(assignmentRepository.findById(dto.getId())).thenReturn(Optional.empty());

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.updateAssignment(dto);
		});
		assertEquals("Assignment not found", actualException.getMessage());
	}

	@Test
	void updateAssignment_ShouldThrowInvalidDataInputException_WhenUserInValid() {

		Assignment assignment = mock(Assignment.class);

		UpdateAssignmentDto dto = UpdateAssignmentDto.builder().assignedDate("26/02/2022").build();

		when(userUtil.isValidDate("26/02/2022")).thenReturn(true);

		when(assignmentRepository.findById(dto.getId())).thenReturn(Optional.of(assignment));

		when(usersRepository.findById(dto.getUserId())).thenReturn(Optional.empty());

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.updateAssignment(dto);
		});
		assertEquals("User not found", actualException.getMessage());
	}

	@Test
	void updateAssignment_ShouldThrowInvalidDataInputException_WhenAssetInValid() {

		Assignment assignment = mock(Assignment.class);
		Users user = mock(Users.class);

		UpdateAssignmentDto dto = UpdateAssignmentDto.builder().assignedDate("26/02/2022").assetId(2l).build();

		when(userUtil.isValidDate("26/02/2022")).thenReturn(true);

		when(assignmentRepository.findById(dto.getId())).thenReturn(Optional.of(assignment));

		when(usersRepository.findById(dto.getUserId())).thenReturn(Optional.of(user));

		when(assetRepository.findById(dto.getAssetId())).thenReturn(Optional.empty());

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.updateAssignment(dto);
		});
		assertEquals("Asset not found", actualException.getMessage());
	}

	@Test
	void updateAssignment_ShouldThrowInvalidDataInputException_WhenAssetStateInValid() {
		Asset ass = Asset.builder().id(10l).build();

		Assignment assignment = Assignment.builder().asset(ass).build();
		Users user = mock(Users.class);
		Asset asset = Asset.builder().state("Not Available").id(1l).build();

		UpdateAssignmentDto dto = UpdateAssignmentDto.builder().assignedDate("26/02/2022").assetId(2l).build();

		when(userUtil.isValidDate("26/02/2022")).thenReturn(true);

		when(assignmentRepository.findById(dto.getId())).thenReturn(Optional.of(assignment));

		when(usersRepository.findById(dto.getUserId())).thenReturn(Optional.of(user));

		when(assetRepository.findById(dto.getAssetId())).thenReturn(Optional.of(asset));

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.updateAssignment(dto);
		});
		assertEquals("Asset state must be Available", actualException.getMessage());
	}

	@Test
	void updateAssignment_ShouldReturnData_WhenDataValid() {
		Asset ass = Asset.builder().id(1l).build();

		Assignment assignment = Assignment.builder().asset(ass).build();
		Users user = mock(Users.class);
		Asset asset = Asset.builder().state("Available").id(1l).build();

		Users admin = mock(Users.class);
		Date assignedDate = mock(Date.class);
		AssignmentResponseDto expected = mock(AssignmentResponseDto.class);

		UpdateAssignmentDto dto = UpdateAssignmentDto.builder().assignedDate("26/02/2022").assetId(1l).userId(2l)
				.note("Note").id(3l).build();

		when(userUtil.isValidDate("26/02/2022")).thenReturn(true);

		when(assignmentRepository.findById(3l)).thenReturn(Optional.of(assignment));

		when(usersRepository.findById(2l)).thenReturn(Optional.of(user));

		when(assetRepository.findById(1l)).thenReturn(Optional.of(asset));

		when(userUtil.getIdFromUserPrinciple()).thenReturn(3l);

		when(usersRepository.findUsersById(3l)).thenReturn(admin);

		when(userUtil.convertStrDateToObDate(dto.getAssignedDate())).thenReturn(assignedDate);

		when(assignmentRepository.save(assignment)).thenReturn(assignment);

		when(assignmentMapper.mapToResponseAssignment(assignment)).thenReturn(expected);

		AssignmentResponseDto actual = assignmentServiceImpl.updateAssignment(dto);

		assertThat(expected, is(actual));
	}

	@Test
	void getListAssignmentofAdmin_ShouldThrowInvalidDataInputException_WhenAssignmentIsEmpty() {
		List<Assignment> assignmentList = new ArrayList<Assignment>();

		when(userUtil.getAddressFromUserPrinciple()).thenReturn("HCM");

		when(assignmentRepository.findByAssetLocation("HCM")).thenReturn(assignmentList);

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assignmentServiceImpl.getListAssignmentofAdmin();
		});
		assertEquals("Assignment not found", actualException.getMessage());
	}

	@Test
	void getListAssignmentofAdmin_ShouldReturnAssignmentList_WhenDatavalid() {

		List<Assignment> assignmentList = new ArrayList<Assignment>();
		List<AssignmentResponseDto> expected = new ArrayList<AssignmentResponseDto>();

		Assignment assignment = Assignment.builder().id(2l).build();
		assignmentList.add(assignment);

		when(userUtil.getAddressFromUserPrinciple()).thenReturn("HCM");

		when(assignmentRepository.findByAssetLocation("HCM")).thenReturn(assignmentList);

		when(assignmentMapper.mapListAssignmentEntityToDto(assignmentList)).thenReturn(expected);

		List<AssignmentResponseDto> actual = assignmentServiceImpl.getListAssignmentofAdmin();

		assertThat(expected, is(actual));

	}

}
