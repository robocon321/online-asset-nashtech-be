package com.nashtech.rookies.controller;

import com.nashtech.rookies.dto.request.asset.CreateAssetRequestDto;
import com.nashtech.rookies.repository.AssetRepository;
import com.nashtech.rookies.services.interfaces.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import javax.validation.Valid;

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
