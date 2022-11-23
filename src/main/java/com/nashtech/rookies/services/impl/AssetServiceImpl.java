package com.nashtech.rookies.services.impl;

import com.nashtech.rookies.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetServiceImpl implements com.nashtech.rookies.services.interfaces.AssetService {

    AssetRepository assetRepository;

    @Autowired
    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

//    Create new asset

//    Update asset

//    Delete asset


}
