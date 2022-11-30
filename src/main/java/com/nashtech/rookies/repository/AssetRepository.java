package com.nashtech.rookies.repository;

import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.entity.Category;
import com.nashtech.rookies.entity.Users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
//    Show information of asset by id
    Asset findAssetById(Long id);

    List<Asset> findByCategory(Category category);
    
    List<Asset> findByUsers(Users users);
    
    List<Asset> findByStateAndUsers(String state, Users users);


//    Create assets

//    Update assets

//    Delete assets

}