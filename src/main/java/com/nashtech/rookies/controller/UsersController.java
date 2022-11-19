package com.nashtech.rookies.controller;

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
    public ResponseEntity<?> showAllUsers(@RequestParam int page) throws Exception {
        return ResponseEntity.ok(usersService.showAll(page));
    }

//    @GetMapping("/search")
//    public ResponseEntity<?> search(@RequestParam String search, @RequestParam List<String> role, @RequestParam Integer pageNo) throws Exception {
////        LOG.error("Role from controller: " + role);
////        LOG.error("Role size from controller: " + role.size());
////        return ResponseEntity.ok().body("OKE");
//        return ResponseEntity.ok().body(usersService.search(search, role, pageNo));
//    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String search, @RequestParam List<String> role, int pageNo) throws Exception {
        return ResponseEntity.ok().body(usersService.search(search, role, pageNo));
    }



    @GetMapping("/id")
    public ResponseEntity<?> getUserById(@RequestParam Long id) throws Exception {
        return ResponseEntity.ok().body(usersService.findByUserId(id));
    }

    @GetMapping("/page")
    public ResponseEntity<?> paginateUsers(@RequestParam Integer page, @RequestParam String sort,
                                           @RequestParam List<String> roles, @RequestParam String search) throws Exception {
        return ResponseEntity.ok().body(usersService.showAll(page));
    }

    @GetMapping("/sort-asc")
    public ResponseEntity<?> sortAsc(@RequestParam String type, @RequestParam int pageNo) throws Exception {
        switch (type) {
            case "code":
                return ResponseEntity.ok().body(usersService.sortByCodeAsc(pageNo));
            case "joinDate":
                return ResponseEntity.ok().body(usersService.sortByJoinedDateAsc(pageNo));
            case "fullName":
                return ResponseEntity.ok().body(usersService.sortByFullNameAsc(pageNo));
            case "role":
                return ResponseEntity.ok().body(usersService.sortByRoleAsc(pageNo));
            default:
                return ResponseEntity.ok().body("Invalid type");
        }
    }

    @GetMapping("/sort-desc")
    public ResponseEntity<?> sortDesc(@RequestParam String type, int pageNo){
        switch (type) {
            case "joinedDate":
                return ResponseEntity.ok().body(usersService.sortByJoinedDateDesc(pageNo));
            case "code":
                return ResponseEntity.ok().body(usersService.sortByCodeDesc(pageNo));
            case "fullName":
                return ResponseEntity.ok().body(usersService.sortByFullNameDesc(pageNo));
            case "role":
                return ResponseEntity.ok().body(usersService.sortByRoleDesc(pageNo));
            default:
                return ResponseEntity.badRequest().body("Invalid type");
        }
    }


}
