package com.nashtech.rookies.services.impl;

import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.repository.AssetRepository;
import com.nashtech.rookies.repository.AssignmentRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetServiceImpl implements com.nashtech.rookies.services.interfaces.AssetService {

    AssetRepository assetRepository;
    AssignmentRepository assignmentRepository;

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AssetServiceImpl.class);

    @Autowired
    public AssetServiceImpl(AssetRepository assetRepository, AssignmentRepository assignmentRepository) {
        this.assetRepository = assetRepository;
        this.assignmentRepository = assignmentRepository;
    }

//    Create new asset

//    Update asset

//    Delete asset

    @Override
    public void deleteAsset(Long id) throws Exception {
        Asset asset = assetRepository.findAssetById(id);

        if (asset == null) {
            throw new Exception("Asset not found");
        }

        if (assignmentRepository.existsAssignmentByAsset_Id(id)) {
            throw new Exception("Cannot delete the asset because it belongs to one or more historical assignments.");
        }

        if(asset.getState().equals("Assigned")) {
            throw new Exception("Cannot delete the asset because it is assigned to one or more users.");
        }

        assetRepository.delete(asset);
    }

}
