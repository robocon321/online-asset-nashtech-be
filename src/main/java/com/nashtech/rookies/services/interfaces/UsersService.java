package com.nashtech.rookies.services.interfaces;

import com.nashtech.rookies.entity.Users;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UsersService {
    //    Find all users by admin locations
    Page<Users> showAll(int pageNo) throws Exception;

    //    Search by username or code
    Page<Users> search(String search, List<String> role, int pageNo) throws Exception;

    //    Show information of user by id
    Users findByUserId(Long userId) throws Exception;

    //    Sort users by JoinedDate
    Page<Users> sortByJoinedDateDesc(int pageNo);

    Page<Users> sortByJoinedDateAsc(int pageNo);

    //    Sort users by code
    Page<Users> sortByCodeDesc(int pageNo);

    Page<Users> sortByCodeAsc(int pageNo);

    //    Sort users by full name
    Page<Users> sortByFullNameDesc(int pageNo);

    Page<Users> sortByFullNameAsc(int pageNo);

    //    Sort users by role with locations

    Page<Users> sortByRoleDesc(int pageNo);

    Page<Users> sortByRoleAsc(int pageNo);
}
