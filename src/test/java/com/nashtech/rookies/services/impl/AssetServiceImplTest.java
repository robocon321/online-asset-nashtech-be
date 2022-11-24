package com.nashtech.rookies.services.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nashtech.rookies.dto.request.asset.CreateAssetRequestDto;
import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.entity.Category;
import com.nashtech.rookies.exceptions.InvalidDataInputException;
import com.nashtech.rookies.mapper.AssetMapper;
import com.nashtech.rookies.mapper.CategoryMapper;
import com.nashtech.rookies.repository.AssetRepository;
import com.nashtech.rookies.repository.CategoryRepository;
import com.nashtech.rookies.utils.AssetUtil;
import com.nashtech.rookies.utils.UserUtil;

public class AssetServiceImplTest {
	AssetRepository assetRepository;
	CategoryRepository categoryRepository;
	CategoryMapper categoryMapper;
	AssetMapper assetMapper;
	UserUtil userUtil;
	AssetUtil assetUtil;

	AssetServiceImpl assetServiceImpl;

	@BeforeEach
	void beforeEach() {
		assetRepository = mock(AssetRepository.class);
		categoryRepository = mock(CategoryRepository.class);

		categoryMapper = mock(CategoryMapper.class);
		assetMapper = mock(AssetMapper.class);
		userUtil = mock(UserUtil.class);

		assetUtil = mock(AssetUtil.class);

		assetServiceImpl = new AssetServiceImpl(assetRepository, userUtil, categoryRepository, categoryMapper,
				assetMapper, assetUtil);

	}

	@Test
	void createAsset_ShouldThrowInvalidDataInputException_WhenInstalledDateInValid() {

		CreateAssetRequestDto dto = CreateAssetRequestDto.builder().installedDate("30/02/2020").build();

		when(userUtil.isValidDate("30/02/2022")).thenReturn(false);

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assetServiceImpl.createAsset(dto);
		});
		assertEquals("Install date is invalid", actualException.getMessage());
	}

	@Test
	void createAsset_ShouldThrowInvalidDataInputException_WhenStateInValid() {

		CreateAssetRequestDto dto = CreateAssetRequestDto.builder().installedDate("22/02/1992").state("abcd").build();

		when(userUtil.isValidDate(dto.getInstalledDate())).thenReturn(true);

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assetServiceImpl.createAsset(dto);
		});
		assertEquals("State is invalid", actualException.getMessage());
	}

	@Test
	void createAsset_ShouldReturnAsset_WhenCategoryIsNew() {
		Category category = mock(Category.class);
		Date installedDate = mock(Date.class);
		Asset expectedAsset = mock(Asset.class);

		CreateAssetRequestDto dto = CreateAssetRequestDto.builder().installedDate("22/02/1992").state("Available")
				.categoryName("category").categoryCode("categoryCode").build();

		when(userUtil.isValidDate(dto.getInstalledDate())).thenReturn(true);
		when(categoryRepository.findByName(dto.getCategoryName())).thenReturn(Optional.empty());

		when(categoryMapper.mapToCategory(dto.getCategoryName(), dto.getCategoryCode())).thenReturn(category);
		when(categoryRepository.save(category)).thenReturn(category);

		when(userUtil.convertStrDateToObDate(dto.getInstalledDate())).thenReturn(installedDate);

		when(userUtil.getAddressFromUserPrinciple()).thenReturn("TPHCM");

		when(assetMapper.mapToAsset(dto.getName(), dto.getCategoryCode() + "000001", dto.getSpecification(),
				dto.getState(), "TPHCM", installedDate, category)).thenReturn(expectedAsset);

		when(assetRepository.save(expectedAsset)).thenReturn(expectedAsset);

		Asset actualAsset = assetServiceImpl.createAsset(dto);

		assertThat(expectedAsset, is(actualAsset));
	}

	@Test
	void createAsset_ShouldReturnAsset_WhenCategoryIsAvailable() {
		Category category = mock(Category.class);
		Date installedDate = mock(Date.class);
		Asset expectedAsset = mock(Asset.class);
		@SuppressWarnings("unchecked")
		List<Asset> listAsset = mock(List.class);

		CreateAssetRequestDto dto = CreateAssetRequestDto.builder().installedDate("22/02/1992").state("Not available")
				.categoryName("category").categoryCode("categoryCode").build();

		when(userUtil.isValidDate(dto.getInstalledDate())).thenReturn(true);
		when(categoryRepository.findByName(dto.getCategoryName())).thenReturn(Optional.of(category));

		when(assetRepository.findByCategory(category)).thenReturn(listAsset);
		when(assetUtil.generateAssetCode(listAsset, category.getCode())).thenReturn("categoryCode123");

		when(userUtil.convertStrDateToObDate(dto.getInstalledDate())).thenReturn(installedDate);

		when(userUtil.getAddressFromUserPrinciple()).thenReturn("TPHCM");

		when(assetMapper.mapToAsset(dto.getName(), "categoryCode123", dto.getSpecification(), dto.getState(), "TPHCM",
				installedDate, category)).thenReturn(expectedAsset);

		when(assetRepository.save(expectedAsset)).thenReturn(expectedAsset);

		Asset actualAsset = assetServiceImpl.createAsset(dto);

		assertThat(expectedAsset, is(actualAsset));
	}

}
