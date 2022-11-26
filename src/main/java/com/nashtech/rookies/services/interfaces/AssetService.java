package com.nashtech.rookies.services.interfaces;

import com.nashtech.rookies.dto.request.asset.CreateAssetRequestDto;
import com.nashtech.rookies.dto.request.asset.UpdateAssetRequestDto;
import com.nashtech.rookies.dto.response.asset.AssetResponseDto;
import com.nashtech.rookies.entity.Asset;

public interface AssetService {
	Asset createAsset(CreateAssetRequestDto dto);

	void deleteAsset(Long id) throws Exception;

	AssetResponseDto getAssetById(Long id);
	
	AssetResponseDto updateAsset(UpdateAssetRequestDto dto);
}
