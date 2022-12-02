package com.nashtech.rookies.services.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.nashtech.rookies.dto.response.assignment.AssignmentDetailResponseDto;
import com.nashtech.rookies.security.userprincal.UserPrinciple;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.dto.request.assignment.CreateAssignmentDto;
import com.nashtech.rookies.dto.request.assignment.UpdateAssignmentDto;
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
import com.nashtech.rookies.services.interfaces.AssignmentService;
import com.nashtech.rookies.utils.UserUtil;

@Service
public class AssignmentServiceImpl implements AssignmentService {

	AssignmentRepository assignmentRepository;
	UsersRepository usersRepository;
	AssetRepository assetRepository;

	UserUtil userUtil;
	AssignmentMapper assignmentMapper;

	public AssignmentServiceImpl(AssignmentRepository assignmentRepository, UsersRepository usersRepository,
			AssetRepository assetRepository, UserUtil userUtil, AssignmentMapper assignmentMapper) {
		this.assignmentRepository = assignmentRepository;
		this.usersRepository = usersRepository;
		this.assetRepository = assetRepository;
		this.userUtil = userUtil;
		this.assignmentMapper = assignmentMapper;
	}

	@Override
	public AssignmentResponseDto createAssignment(CreateAssignmentDto dto) {
		if (!userUtil.isValidDate(dto.getAssignedDate())) {
			throw new InvalidDataInputException("AssignedDate is invalid");
		}

		Date assignedDate = userUtil.convertStrDateToObDate(dto.getAssignedDate());

		Date nowDate = userUtil.generateFormatNowDay();

		if (assignedDate.before(nowDate)) {
			throw new InvalidDataInputException("AssignedDate must be after NowDate");
		}

		Optional<Users> userOptional = usersRepository.findById(dto.getUserId());

		if (userOptional.isEmpty()) {
			throw new InvalidDataInputException("User not found");
		}

		Optional<Asset> assetOptional = assetRepository.findById(dto.getAssetId());

		if (assetOptional.isEmpty()) {
			throw new InvalidDataInputException("Asset not found");
		}

		Long adminId = userUtil.getIdFromUserPrinciple();

		Users admin = usersRepository.findUsersById(adminId);

		String state = "Waiting for acceptance";

		Assignment assignment = assignmentMapper.mapToAssignment(assetOptional.get(), admin, userOptional.get(),
				dto.getNote(), state, assignedDate, nowDate);

		assignment.getAsset().setState("Not available");

		assignment = assignmentRepository.save(assignment);

		return assignmentMapper.mapToResponseAssignment(assignment);
	}

	@Override
	public AssignmentUpdateResponseDto getUpdateAssignmentById(Long id) {
		Optional<Assignment> assignmentOptional = assignmentRepository.findById(id);

		if (assignmentOptional.isEmpty()) {
			throw new InvalidDataInputException("Assignment not found");
		}

		Assignment assignment = assignmentOptional.get();

		if (!assignment.getState().equals("Waiting for acceptance")) {
			throw new InvalidDataInputException("Assignment state must be Waiting for acceptance");
		}

		return assignmentMapper.mapToUpdateResponseAssignment(assignment);
	}

	@Override
	public AssignmentResponseDto updateAssignment(UpdateAssignmentDto dto) {

		if (!userUtil.isValidDate(dto.getAssignedDate())) {
			throw new InvalidDataInputException("AssignedDate is invalid");
		}

		Optional<Assignment> assignmentOptional = assignmentRepository.findById(dto.getId());

		if (assignmentOptional.isEmpty()) {
			throw new InvalidDataInputException("Assignment not found");
		}

		Optional<Users> userOptional = usersRepository.findById(dto.getUserId());

		if (userOptional.isEmpty()) {
			throw new InvalidDataInputException("User not found");
		}

		Optional<Asset> assetOptional = assetRepository.findById(dto.getAssetId());

		if (assetOptional.isEmpty()) {
			throw new InvalidDataInputException("Asset not found");
		}

		Asset asset = assetOptional.get();

		if (!asset.getState().equals("Available")
				&& (dto.getAssetId() != assignmentOptional.get().getAsset().getId())) {
			throw new InvalidDataInputException("Asset state must be Available");
		}

		assignmentOptional.get().getAsset().setState("Available");

		asset.setState("Not available");

		Long adminId = userUtil.getIdFromUserPrinciple();

		Users admin = usersRepository.findUsersById(adminId);

		Assignment assignment = assignmentOptional.get();

		Date assignedDate = userUtil.convertStrDateToObDate(dto.getAssignedDate());

		assignment.setAsset(assetOptional.get());
		assignment.setAssignedTo(userOptional.get());
		assignment.setAssignedBy(admin);
		assignment.setAssignedDate(assignedDate);
		assignment.setNote(dto.getNote());

		assignment = assignmentRepository.save(assignment);

		return assignmentMapper.mapToResponseAssignment(assignment);
	}

	@Override
	public List<AssignmentResponseDto> getListAssignmentofUser(){
		UserPrinciple userPrinciple= (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		List<Assignment> assignmentList = assignmentRepository.getAllAssignmentOfUser(timestamp,userPrinciple.getId());
		if(assignmentList.size()>0){
			return assignmentMapper.mapListAssignmentEntityToDto(assignmentList);
		}
		else {
			throw new InvalidDataInputException("Not found assignment");
		}
	}

	@Override
	public AssignmentDetailResponseDto getAssignmentDetail(Long id){
		Optional<Assignment> checkAssignment = assignmentRepository.findById(id);
		if(!checkAssignment.isPresent()){
			throw new InvalidDataInputException("Not found this assignment");
		}
		Assignment assignment = checkAssignment.get();
		if(assignment.isDeleted()){
			throw new InvalidDataInputException("This assignment is deleted");
		}
		return assignmentMapper.mapToResponseAssigmentDetail(assignment);
	}

}
