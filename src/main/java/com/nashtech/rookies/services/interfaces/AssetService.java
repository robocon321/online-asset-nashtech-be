package com.nashtech.rookies.services.interfaces;

import java.util.List;
import com.nashtech.rookies.dto.request.asset.CreateAssetRequestDto;

import com.nashtech.rookies.dto.request.asset.UpdateAssetRequestDto;
import com.nashtech.rookies.dto.response.asset.AssetDetailResponseDto;
import com.nashtech.rookies.dto.response.asset.AssetResponseDto;

import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.entity.Users;

public interface AssetService {
	AssetResponseDto createAsset(CreateAssetRequestDto dto);

	List<AssetResponseDto> showAll();

	boolean checkHasExistAssignment(Long id);
	void deleteAsset(Long id) throws Exception;

	AssetResponseDto updateAsset(UpdateAssetRequestDto dto);

	AssetDetailResponseDto getAssetDetailById(Long id);

	List<AssetResponseDto> getAllAssetsByStateAndUser(String state);

}
