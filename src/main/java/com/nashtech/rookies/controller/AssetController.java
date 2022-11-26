package com.nashtech.rookies.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.dto.request.asset.CreateAssetRequestDto;
import com.nashtech.rookies.services.interfaces.AssetService;

@RequestMapping("/api/v1/assets")
@RestController
public class AssetController {

	@Autowired
	AssetService assetService;

	@PostMapping("/")
	public ResponseEntity<?> createUser(@Valid @RequestBody CreateAssetRequestDto dto) {
		return ResponseEntity.ok().body(assetService.createAsset(dto));
	}

	@DeleteMapping("")
	public ResponseEntity<?> deleteAsset(@RequestParam Long id) throws Exception {
		assetService.deleteAsset(id);
		return ResponseEntity.ok().body("Delete asset successfully");
	}
}
