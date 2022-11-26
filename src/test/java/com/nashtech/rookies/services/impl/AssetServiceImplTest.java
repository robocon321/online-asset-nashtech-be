package com.nashtech.rookies.services.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nashtech.rookies.dto.request.asset.CreateAssetRequestDto;
import com.nashtech.rookies.dto.request.asset.UpdateAssetRequestDto;
import com.nashtech.rookies.dto.response.asset.AssetResponseDto;
import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.entity.Category;
import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.exceptions.InvalidDataInputException;
import com.nashtech.rookies.jwt.JwtProvider;
import com.nashtech.rookies.mapper.AssetMapper;
import com.nashtech.rookies.mapper.CategoryMapper;
import com.nashtech.rookies.repository.AssetRepository;
import com.nashtech.rookies.repository.AssignmentRepository;
import com.nashtech.rookies.repository.CategoryRepository;
import com.nashtech.rookies.repository.UsersRepository;
import com.nashtech.rookies.security.userprincal.UserPrinciple;
import com.nashtech.rookies.services.interfaces.AssetService;
import com.nashtech.rookies.utils.AssetUtil;
import com.nashtech.rookies.utils.UserUtil;

public class AssetServiceImplTest {
	AssetRepository assetRepository;
	CategoryRepository categoryRepository;
	AssignmentRepository assignmentRepository;
	CategoryMapper categoryMapper;
	AssetMapper assetMapper;
	UserUtil userUtil;
	AssetUtil assetUtil;
	UsersRepository usersRepository;
	AssetServiceImpl assetServiceImpl;
	AuthenticationManager authenticationManager;
	JwtProvider jwtProvider;
	AssetService assetService;
	UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
	Authentication authentication;


	UserPrinciple userPrinciple;
	AuthServiceImpl authServiceImpl;
	Asset initAsset;


	@BeforeEach
	void beforeEach() {
		assetRepository = mock(AssetRepository.class);
		categoryRepository = mock(CategoryRepository.class);
		assignmentRepository = mock(AssignmentRepository.class);
		categoryMapper = mock(CategoryMapper.class);
		assetMapper = mock(AssetMapper.class);
		userUtil = mock(UserUtil.class);

		assetUtil = mock(AssetUtil.class);

		usersRepository = mock(UsersRepository.class);

		assetServiceImpl = new AssetServiceImpl(assetRepository, userUtil, categoryRepository, categoryMapper,
				assetMapper, assetUtil, assignmentRepository, usersRepository);

		initAsset = mock(Asset.class);

		initAsset = Asset.builder().name("Name").id(1L).state("Available").build();

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
		Users user = mock(Users.class);

		CreateAssetRequestDto dto = CreateAssetRequestDto.builder().installedDate("22/02/1992").state("Available")
				.categoryName("category").categoryCode("categoryCode").build();

		when(userUtil.isValidDate(dto.getInstalledDate())).thenReturn(true);
		when(categoryRepository.findByName(dto.getCategoryName())).thenReturn(Optional.empty());

		when(categoryMapper.mapToCategory(dto.getCategoryName(), dto.getCategoryCode())).thenReturn(category);
		when(categoryRepository.save(category)).thenReturn(category);

		when(userUtil.convertStrDateToObDate(dto.getInstalledDate())).thenReturn(installedDate);

		when(userUtil.getAddressFromUserPrinciple()).thenReturn("TPHCM");

		when(userUtil.getIdFromUserPrinciple()).thenReturn(2l);

		when(usersRepository.findUsersById(2l)).thenReturn(user);

		when(assetMapper.mapToAsset(dto.getName(), dto.getCategoryCode() + "000001", dto.getSpecification(),
				dto.getState(), "TPHCM", installedDate, category)).thenReturn(expectedAsset);

		when(assetRepository.save(expectedAsset)).thenReturn(expectedAsset);

		Asset actualAsset = assetServiceImpl.createAsset(dto);

		verify(expectedAsset).setUsers(user);

		assertThat(expectedAsset, is(actualAsset));
	}

	@Test
	void createAsset_ShouldReturnAsset_WhenCategoryIsAvailable() {
		Category category = mock(Category.class);
		Date installedDate = mock(Date.class);
		Asset expectedAsset = mock(Asset.class);
		@SuppressWarnings("unchecked")
		List<Asset> listAsset = mock(List.class);
		Users user = mock(Users.class);

		CreateAssetRequestDto dto = CreateAssetRequestDto.builder().installedDate("22/02/1992").state("Not available")
				.categoryName("category").categoryCode("categoryCode").build();

		when(userUtil.isValidDate(dto.getInstalledDate())).thenReturn(true);
		when(categoryRepository.findByName(dto.getCategoryName())).thenReturn(Optional.of(category));

		when(assetRepository.findByCategory(category)).thenReturn(listAsset);
		when(assetUtil.generateAssetCode(listAsset, category.getCode())).thenReturn("categoryCode123");

		when(userUtil.convertStrDateToObDate(dto.getInstalledDate())).thenReturn(installedDate);

		when(userUtil.getAddressFromUserPrinciple()).thenReturn("TPHCM");

		when(userUtil.getIdFromUserPrinciple()).thenReturn(2l);

		when(usersRepository.findUsersById(2l)).thenReturn(user);

		when(assetMapper.mapToAsset(dto.getName(), "categoryCode123", dto.getSpecification(), dto.getState(), "TPHCM",
				installedDate, category)).thenReturn(expectedAsset);

		when(assetRepository.save(expectedAsset)).thenReturn(expectedAsset);

		Asset actualAsset = assetServiceImpl.createAsset(dto);

		verify(expectedAsset).setUsers(user);

		assertThat(expectedAsset, is(actualAsset));
	}

	// update

	@Test
	void updateAsset_ShouldThrowInvalidDataInputException_WhenInstalledDateInValid() {

		UpdateAssetRequestDto dto = UpdateAssetRequestDto.builder().installedDate("30/02/2020").build();

		when(userUtil.isValidDate("30/02/2022")).thenReturn(false);

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assetServiceImpl.updateAsset(dto);
		});
		assertEquals("Install date is invalid", actualException.getMessage());
	}

	@Test
	void updateAsset_ShouldThrowInvalidDataInputException_WhenIdInValid() {
		UpdateAssetRequestDto dto = UpdateAssetRequestDto.builder().id(2l).installedDate("18/02/2020").build();
		when(userUtil.isValidDate(dto.getInstalledDate())).thenReturn(true);

		when(assetRepository.findById(2l)).thenReturn(Optional.empty());

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assetServiceImpl.updateAsset(dto);
		});
		assertEquals("Asset not found", actualException.getMessage());
	}

	@Test
	void updateAsset_ShouldReturnAsset_WhenDataValid() {
		UpdateAssetRequestDto dto = UpdateAssetRequestDto.builder().id(2l).installedDate("18/02/2020").name("Name")
				.specification("Specification").state("Available").build();

		Asset asset = mock(Asset.class);
		
		AssetResponseDto expectedAsset = mock(AssetResponseDto.class);
		Date installedDate = mock(Date.class);

		when(userUtil.isValidDate(dto.getInstalledDate())).thenReturn(true);

		when(assetRepository.findById(2l)).thenReturn(Optional.of(asset));

		when(userUtil.convertStrDateToObDate(dto.getInstalledDate())).thenReturn(installedDate);

		when(assetRepository.save(asset)).thenReturn(asset);

		when(assetMapper.mapToDto(asset)).thenReturn(expectedAsset);

		AssetResponseDto actualAsset = assetServiceImpl.updateAsset(dto);

		verify(asset).setName("Name");
		
		verify(asset).setSpecification("Specification");

		verify(asset).setState("Available");

		verify(asset).setInstalledDate(installedDate);

		assertThat(expectedAsset, is(actualAsset));

	}

	// get asset by id
	@Test
	void getAssetById_ShouldThrowInvalidDataInputException_WhenIdInValid() {

		when(assetRepository.findById(2l)).thenReturn(Optional.empty());

		InvalidDataInputException actualException = assertThrows(InvalidDataInputException.class, () -> {
			assetServiceImpl.getAssetById(2l);
		});
		assertEquals("Asset not found", actualException.getMessage());
	}

	@Test
	void getAssetById_ShouldReturnAsset_WhenDataValid() {
		Asset asset = mock(Asset.class);
		AssetResponseDto expectedAsset = mock(AssetResponseDto.class);

		when(assetRepository.findById(2l)).thenReturn(Optional.of(asset));

		when(assetMapper.mapToDto(asset)).thenReturn(expectedAsset);

		AssetResponseDto actualAsset = assetServiceImpl.getAssetById(2l);

		assertThat(expectedAsset, is(actualAsset));
	}

	// delete
	@Test
	void deleteAsset_ShouldRemoveAssets_WhenAssetsIsAvailable() throws Exception {
		when(assetRepository.findAssetById(1L)).thenReturn(initAsset);
		when(assignmentRepository.existsAssignmentByAsset_Id(1L)).thenReturn(false);

		assetServiceImpl.deleteAsset(1L);
		verify(assetRepository).delete(initAsset);
	}

	@Test
	void deleteAssets_ShouldThrowException_WhenNotFound() {
		when(assetRepository.findAssetById(1L)).thenReturn(null);

		Exception exception = assertThrows(Exception.class, () -> {
			assetServiceImpl.deleteAsset(1L);
		});
		assertEquals("Asset not found", exception.getMessage());
	}

	@Test
	void deleteAssets_ShouldThrowException_WhenAssetIsAssigned() {
		when(assetRepository.findAssetById(1L)).thenReturn(initAsset);
		when(assignmentRepository.existsAssignmentByAsset_Id(1L)).thenReturn(true);

		Exception exception = assertThrows(Exception.class, () -> {
			assetServiceImpl.deleteAsset(1L);
		});
		assertEquals("Cannot delete the asset because it belongs to one or more historical assignments.",
				exception.getMessage());
	}

	@Test
	void deleteAssets_ShouldThrowException_WhenStateIsAssigned() {
		initAsset = Asset.builder().name("Name").id(1L).state("Assigned").build();

		when(assetRepository.findAssetById(1L)).thenReturn(initAsset);
		when(assignmentRepository.existsAssignmentByAsset_Id(1L)).thenReturn(false);

		Exception exception = assertThrows(Exception.class, () -> {
			assetServiceImpl.deleteAsset(1L);
		});
		assertEquals("Cannot delete the asset because it is assigned to one or more users.", exception.getMessage());
	}
	
	@Test
	void getAllAssets_ShouldReturnAllAssetsManagedByUser() {
		Users user = new Users();
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		UserPrinciple userPrinciple1 = new UserPrinciple();
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(authentication.getPrincipal()).thenReturn(userPrinciple1);
		when(usersRepository.findUsersById(userPrinciple1.getId())).thenReturn(user);
		List<Asset> assetList = new ArrayList<>();
		when(assetRepository.findByUsers(user)).thenReturn(assetList);
		List<Asset> actual = assetServiceImpl.showAll();
		assertEquals(assetList, actual);
	}

}
