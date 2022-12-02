package com.nashtech.rookies.services.interfaces;

import com.nashtech.rookies.dto.response.returnRequest.ReturnRequestDto;

public interface ReturnRequestService {
    //    Create new return request
    ReturnRequestDto createReturnRequest(Long id);
}
