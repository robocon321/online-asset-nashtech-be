package com.nashtech.rookies.services.impl;

import com.nashtech.rookies.dto.request.asset.CreateAssetRequestDto;
import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.entity.Category;
import com.nashtech.rookies.exceptions.InvalidDataInputException;
import com.nashtech.rookies.mapper.AssetMapper;
import com.nashtech.rookies.mapper.CategoryMapper;
import com.nashtech.rookies.repository.AssetRepository;
import com.nashtech.rookies.repository.CategoryRepository;
import com.nashtech.rookies.services.interfaces.AssetService;
import com.nashtech.rookies.utils.AssetUtil;
import com.nashtech.rookies.utils.UserUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetServiceImpl implements AssetService {

	AssetRepository assetRepository;
	CategoryRepository categoryRepository;
	CategoryMapper categoryMapper;
	AssetMapper assetMapper;
	UserUtil userUtil;
	AssetUtil assetUtil;

	@Autowired
	public AssetServiceImpl(AssetRepository assetRepository, UserUtil userUtil, CategoryRepository categoryRepository,
			CategoryMapper categoryMapper, AssetMapper assetMapper, AssetUtil assetUtil) {
		this.assetRepository = assetRepository;
		this.userUtil = userUtil;
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
		this.assetMapper = assetMapper;
		this.assetUtil = assetUtil;
	}

	@Override
	public Asset createAsset(CreateAssetRequestDto dto) {

		if (!userUtil.isValidDate(dto.getInstalledDate())) {
			throw new InvalidDataInputException("Install date is invalid");
		}

		if (!("Available".equalsIgnoreCase(dto.getState())) && !("Not available".equalsIgnoreCase(dto.getState()))) {
			throw new InvalidDataInputException("State is invalid");
		}

		String code = "";

		Category category;

		Optional<Category> categoryOptional = categoryRepository.findByName(dto.getCategoryName());

		if (categoryOptional.isEmpty()) {
			category = categoryMapper.mapToCategory(dto.getCategoryName(), dto.getCategoryCode());

			category = categoryRepository.save(category);

			code = dto.getCategoryCode() + "000001";

		} else {
			category = categoryOptional.get();

			List<Asset> list = assetRepository.findByCategory(category);

			code = assetUtil.generateAssetCode(list, category.getCode());
		}

		Date installedDate = userUtil.convertStrDateToObDate(dto.getInstalledDate());

		String location = userUtil.getAddressFromUserPrinciple();

		Asset asset = assetMapper.mapToAsset(dto.getName(), code, dto.getSpecification(), dto.getState(), location,
				installedDate, category);
		asset = assetRepository.save(asset);

		return asset;
	}

//    Update asset

//    Delete asset

}
