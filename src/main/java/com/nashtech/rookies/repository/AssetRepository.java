package com.nashtech.rookies.repository;

import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.entity.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {

	List<Asset> findByCategory(Category category);

//    Create assets

//    Update assets

//    Delete assets


}