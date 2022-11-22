package com.nashtech.rookies.controller;

import com.nashtech.rookies.dto.request.user.UpdateUserRequestDto;
import com.nashtech.rookies.dto.request.user.UserRequestDto;
import com.nashtech.rookies.services.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RequestMapping("/api/v1/users")
@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    @GetMapping("/")
    public ResponseEntity<?> showAllUsers() throws Exception {
        return ResponseEntity.ok(usersService.showAll());
    }

    @GetMapping("/id")
    public ResponseEntity<?> getUserById(@RequestParam Long id) throws Exception {
        return ResponseEntity.ok().body(usersService.findByUserId(id));
    }

    @GetMapping("/sort-asc")
    public ResponseEntity<?> sortAsc(@RequestParam String type) throws Exception {
        switch (type) {
            case "code":
                return ResponseEntity.ok().body(usersService.sortByCodeAsc());
            case "joinDate":
                return ResponseEntity.ok().body(usersService.sortByJoinedDateAsc());
            case "fullName":
                return ResponseEntity.ok().body(usersService.sortByFullNameAsc());
            case "role":
                return ResponseEntity.ok().body(usersService.sortByRoleAsc());
            default:
                return ResponseEntity.ok().body("Invalid type");
        }
    }

    @GetMapping("/sort-desc")
    public ResponseEntity<?> sortDesc(@RequestParam String type){
        switch (type) {
            case "joinedDate":
                return ResponseEntity.ok().body(usersService.sortByJoinedDateDesc());
            case "code":
                return ResponseEntity.ok().body(usersService.sortByCodeDesc());
            case "fullName":
                return ResponseEntity.ok().body(usersService.sortByFullNameDesc());
            case "role":
                return ResponseEntity.ok().body(usersService.sortByRoleDesc());
            default:
                return ResponseEntity.badRequest().body("Invalid type");
        }
    }

    @GetMapping("/updated")
    public ResponseEntity<?> sortUpdatedDateDesc(){
        return ResponseEntity.ok().body(usersService.sortByUpdatedDateDesc());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDto dto) {
    	return ResponseEntity.ok().body(usersService.createUser(dto));
    }

    @PutMapping("/update")
    public String updateUser(@Valid @RequestBody UpdateUserRequestDto dto){ return usersService.updateUser(dto);}
}
