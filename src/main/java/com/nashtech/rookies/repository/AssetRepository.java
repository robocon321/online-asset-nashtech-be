package com.nashtech.rookies.repository;

import com.nashtech.rookies.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
//    Show information of asset by id
    Asset findAssetById(Long id);

//    Create assets

//    Update assets

//    Delete assets

}