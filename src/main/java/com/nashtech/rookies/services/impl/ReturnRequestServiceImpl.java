package com.nashtech.rookies.services.impl;

import com.nashtech.rookies.repository.ReturnRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReturnRequestServiceImpl implements com.nashtech.rookies.services.interfaces.ReturnRequestService {
    ReturnRequestRepository returnRequestRepository;

    @Autowired
    public ReturnRequestServiceImpl(ReturnRequestRepository returnRequestRepository) {
        this.returnRequestRepository = returnRequestRepository;
    }

//    Get information of return request

//    Create new return request

//    Update return request

//    Delete return request

}
