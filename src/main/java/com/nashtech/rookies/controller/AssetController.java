package com.nashtech.rookies.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.dto.request.asset.CreateAssetRequestDto;
import com.nashtech.rookies.dto.request.asset.AssetRequestDto;
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

	@GetMapping("/{id}")
	public ResponseEntity<?> getAssetById(@PathVariable Long id) {
		return ResponseEntity.ok().body(assetService.getAssetById(id));
	}
	
	@PutMapping("/")
	public ResponseEntity<?> updateAsset(@Valid @RequestBody AssetRequestDto dto) {
		return ResponseEntity.ok().body("");
	}

	@DeleteMapping("")
	public ResponseEntity<?> deleteAsset(@RequestParam Long id) throws Exception {
		assetService.deleteAsset(id);
		return ResponseEntity.ok().body("Delete asset successfully");
	}
}
