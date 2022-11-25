package com.nashtech.rookies.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.entity.Category;

@Component
public class AssetMapper {
	public Asset mapToAsset(String name, String code, String specification, String state, String location,
			Date installedDate, Category category) {
		
		return Asset.builder()
				.name(name)
				.code(code)
				.specification(specification)
				.state(state)
				.location(location)
				.installedDate(installedDate)
				.category(category)
				.build();
	}
	
}
