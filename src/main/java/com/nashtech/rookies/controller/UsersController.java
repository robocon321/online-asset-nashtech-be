package com.nashtech.rookies.controller;

import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.services.interfaces.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/api/v1/users")
@RestController
public class UsersController {

    private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);
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
        return switch (type) {
            case "joinedDate" -> ResponseEntity.ok().body(usersService.sortByJoinedDateAsc());
            case "code" -> ResponseEntity.ok().body(usersService.sortByCodeAsc());
            case "fullName" -> ResponseEntity.ok().body(usersService.sortByFullNameAsc());
            case "role" -> ResponseEntity.ok().body(usersService.sortByRoleAsc());
            default -> ResponseEntity.badRequest().body("Invalid type");
        };
    }

    @GetMapping("/sort-desc")
    public ResponseEntity<?> sortDesc(@RequestParam String type){
        return switch (type) {
            case "joinedDate" -> ResponseEntity.ok().body(usersService.sortByJoinedDateDesc());
            case "code" -> ResponseEntity.ok().body(usersService.sortByCodeDesc());
            case "fullName" -> ResponseEntity.ok().body(usersService.sortByFullNameDesc());
            case "role" -> ResponseEntity.ok().body(usersService.sortByRoleDesc());
            default -> ResponseEntity.badRequest().body("Invalid type");
        };
    }


}
