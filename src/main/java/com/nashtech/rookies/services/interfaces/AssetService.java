package com.nashtech.rookies.services.interfaces;

import java.util.List;
import com.nashtech.rookies.dto.request.asset.CreateAssetRequestDto;
import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.entity.Users;

public interface AssetService {
	Asset createAsset(CreateAssetRequestDto dto);
    void deleteAsset(Long id) throws Exception;
    
    List<Asset> showAll();
}
