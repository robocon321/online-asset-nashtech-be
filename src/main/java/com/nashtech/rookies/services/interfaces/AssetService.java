package com.nashtech.rookies.services.interfaces;

import com.nashtech.rookies.dto.request.asset.CreateAssetRequestDto;
import com.nashtech.rookies.entity.Asset;

public interface AssetService {
	Asset createAsset(CreateAssetRequestDto dto);
}
