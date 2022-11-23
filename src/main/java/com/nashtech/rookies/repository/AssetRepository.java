package com.nashtech.rookies.repository;

import com.nashtech.rookies.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
}