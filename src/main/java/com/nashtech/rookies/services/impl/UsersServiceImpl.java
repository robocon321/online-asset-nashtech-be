package com.nashtech.rookies.services.impl;

import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UsersServiceImpl implements com.nashtech.rookies.services.interfaces.UsersService {

    private static final Logger LOG = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final UsersRepository usersRepository;
    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public static final String location = "HCM";

//    Find all users by admin locations
    @Override
    public Page<Users> showAll(int pageNo) throws Exception {

        Pageable paging = PageRequest.of(pageNo - 1, 20, Sort.by("code").ascending());

//        if(location.isBlank() || location.equals("null") || location.equals("undefined") || location.isEmpty()){
//            throw new Exception("Location must not be empty");
//        }
//
//
//        if (users.isEmpty()) {
//            throw new Exception("Can't find any user with location: " + location);
//        }else{
//
//        }

        return usersRepository.findByLocation(location, paging);
    }

    @Override
    public Page<Users> search(String search, List<String> role, int pageNo) throws Exception {

        Pageable paging = PageRequest.of(pageNo - 1, 20);

        if(search.equals("undefined") || role.contains("undefined")){
            throw new Exception("Search or role must not be undefined");
        }

//      Show all users
        if(search.equals("null") && role.contains("null") || role.contains("all")) {
            LOG.error("users list full size: " + usersRepository.findByLocation(location, paging).getTotalPages());
            return usersRepository.findByLocation(location, paging);
        }
//      Show all users by search username or code
        else if((Objects.equals(role.get(0), "null") || Objects.equals(role.get(0), "all")) && !search.equals("null")){
            LOG.error("users list size by search: " + usersRepository.findByLocationAndUsernameContainingOrCodeContaining(location, search, search, paging).getTotalPages());
            return usersRepository.findByLocationAndUsernameContainingOrCodeContaining(location, search, search, paging);
        }
//       Show all users list by role
        else if (!role.contains("all")  && search.equals("null")){
            LOG.error("users list size by role: " + usersRepository.findByLocationAndRoleIn(location, role, paging).getTotalPages());
            return usersRepository.findByLocationAndRoleIn(location, role, paging);
        }
//       Show all users list by role and (username or code)
        else{
            LOG.error("search in all size: " + usersRepository.search(location, role, search, paging).getTotalPages());
            return usersRepository.search(location, role, search, paging);
        }
    }

//    Show information of user by id
    @Override
    public Users findByUserId(Long userId) throws Exception {
        if(userId == 0){
            throw new Exception("UserID must not be empty");
        }

        Users users = usersRepository.findUsersById(userId);

        if(users == null){
            throw new Exception("Can't find any user with id: " + userId);
        }else{
            return users;
        }
    }

//    Sort users by JoinedDate
    @Override
    public Page<Users> sortByJoinedDateDesc(int pageNo) {
        Pageable paging = PageRequest.of(pageNo - 1, 20);
        return usersRepository.findByLocationOrderByJoinedDateDesc(location, paging);
    }

    @Override
    public Page<Users> sortByJoinedDateAsc(int pageNo) {
        Pageable paging = PageRequest.of(pageNo - 1, 20);
        return usersRepository.findByLocationOrderByJoinedDateAsc(location, paging);
    }

//    Sort users by code
    @Override
    public Page<Users> sortByCodeDesc(int pageNo) {
        Pageable paging = PageRequest.of(pageNo - 1, 20);
        return usersRepository.findByLocationOrderByCodeDesc(location, paging);
    }

    @Override
    public Page<Users> sortByCodeAsc(int pageNo) {
        Pageable paging = PageRequest.of(pageNo - 1, 20);
        return usersRepository.findByLocationOrderByCodeAsc(location, paging);
    }

//    Sort users by full name
    @Override
    public Page<Users> sortByFullNameDesc(int pageNo) {
        Pageable paging = PageRequest.of(pageNo - 1, 20);
        return usersRepository.findByLocationOrderByFullNameDesc(location, paging);
    }

    @Override
    public Page<Users> sortByFullNameAsc(int pageNo) {
        Pageable paging = PageRequest.of(pageNo - 1, 20);
        return usersRepository.findByLocationOrderByFullNameAsc(location, paging);
    }

//    Sort users by role
    @Override
    public Page<Users> sortByRoleDesc(int pageNo) {
        Pageable paging = PageRequest.of(pageNo - 1, 20);
        return usersRepository.findByLocationOrderByRoleDesc(location, paging);
    }

    @Override
    public Page<Users> sortByRoleAsc(int pageNo) {
        Pageable paging = PageRequest.of(pageNo - 1, 20);
        return usersRepository.findByLocationOrderByRoleAsc(location, paging);
    }

}
