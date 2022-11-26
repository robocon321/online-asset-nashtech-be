package com.nashtech.rookies.dto.request.asset;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class AssetRequestDto {

	@NotBlank(message = "Name is required.")
	private String name;

	@NotBlank(message = "Specification is required.")
	private String specification;

	@NotBlank(message = "State is required.")
	private String state;

	@NotBlank(message = "InstalledDate is required.")
	private String installedDate;
}
