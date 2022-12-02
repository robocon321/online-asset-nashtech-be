package com.nashtech.rookies.dto.response.assignment;

import java.util.Date;

import com.nashtech.rookies.dto.response.asset.AssetResponseDto;
import com.nashtech.rookies.entity.Users;

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
public class AssignmentUpdateResponseDto {
	private Long id;
	private Users user;
	private AssetResponseDto asset;
	private Date assignedDate;
	private String note;
}
