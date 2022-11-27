package com.nashtech.rookies.dto.response.asset;

import java.util.Date;
import java.util.List;

import com.nashtech.rookies.entity.Assignment;

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
public class AssetDetailResponseDto {
	private Long id;
	private String name;
	private String categoryName;
	private String specification;
	private Date installedDate;
	private String state;
	private String code;
	private String location;
	private List<AssignmentResponseDto> assignments;
}
