package com.nashtech.rookies.services.interfaces;

import com.nashtech.rookies.dto.request.asset.CreateAssetRequestDto;

import com.nashtech.rookies.dto.request.asset.UpdateAssetRequestDto;
import com.nashtech.rookies.dto.response.asset.AssetResponseDto;

import com.nashtech.rookies.entity.Asset;

public interface AssetService {
	AssetDetailResponseDto getAssetDetailById(Long id);
	AssetResponseDto createAsset(CreateAssetRequestDto dto);
    
    List<AssetResponseDto> showAll();

	void deleteAsset(Long id) throws Exception;

	AssetResponseDto updateAsset(UpdateAssetRequestDto dto);
	


}
