package com.nashtech.rookies.controller;

import com.nashtech.rookies.services.interfaces.ReturnRequestService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

@RestController
@RequestMapping("/api/v1/return-requests")
public class ReturnRequestController {
	@Autowired
	ReturnRequestService returnRequestService;
//    Get information of return request

	@GetMapping
	ResponseEntity<?> getAllReturnRequests() {
		return ResponseEntity.ok().body(returnRequestService.getAllReturnRequests());
	}

//    Create new return request
	@PostMapping("/{id}")
	ResponseEntity<?> createReturnRequest(@PathVariable Long id) {
		return ResponseEntity.ok().body(returnRequestService.createReturnRequest(id));
	}
//    Update return request
	@PostMapping(consumes =  {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	ResponseEntity<?> updateReturnRequest(Long id) {
		return ResponseEntity.ok().body(returnRequestService.updateReturnRequest(id));
	}
	
//    Delete return request
	@DeleteMapping()
	ResponseEntity<?> deleteReturnRequest(@RequestParam Long id) {
		returnRequestService.deleteReturnRequest(id);
		return ResponseEntity.ok().body("Delete successfully");
	}
}
