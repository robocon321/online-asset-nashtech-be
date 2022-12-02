package com.nashtech.rookies.mapper;

import com.nashtech.rookies.dto.response.returnRequest.ReturnRequestDto;
import com.nashtech.rookies.entity.ReturnRequest;
import org.springframework.stereotype.Component;

@Component
public class ReturnRequestMapper {
    public ReturnRequestDto mapToReturnRequestDto(ReturnRequest returnRequest){
        return ReturnRequestDto.builder()
                .id(returnRequest.getId())
                .assetCode(returnRequest.getAssignment().getAsset().getCode())
                .assetName(returnRequest.getAssignment().getAsset().getName())
                .requestedBy(returnRequest.getAssignment().getAssignedTo().getUsername())
                .acceptedBy(null)
                .assignedDate(returnRequest.getAssignment().getAssignedDate())
                .returnedDate(returnRequest.getReturnDate())
                .state(returnRequest.getState())
                .build();
    }
}
