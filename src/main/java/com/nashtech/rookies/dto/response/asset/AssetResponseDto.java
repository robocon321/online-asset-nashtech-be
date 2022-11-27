package com.nashtech.rookies.dto.response.asset;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class AssetResponseDto {
	private Long id;
	private String name;
	private String categoryName;
	private String specification;
	private Date installedDate;
	private String state;
	private String code;
}
