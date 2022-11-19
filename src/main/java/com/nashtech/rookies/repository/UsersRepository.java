package com.nashtech.rookies.repository;

import com.nashtech.rookies.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findUsersById(Long id);

//    Search by username or code
    Page<Users> findByLocationAndUsernameContainingOrCodeContaining(String location, String searchUsername, String searchCode, Pageable pageable);

//    Filter users by role and location and (by username or code)
    @Query("SELECT u FROM Users u WHERE u.location = ?1 AND u.role IN ?2 AND (u.username LIKE %?3% OR u.code LIKE %?3%)")
    Page<Users> search(String location, List<String> role, String search, Pageable pageable);

//    Filter with List of roles
    Page<Users> findByLocationAndRoleIn(String location , List<String> role, Pageable pageable);

//    Find all users by admin locations
    Page<Users> findByLocation(String location, Pageable pageable);

//    Sort users by JoinedDate
    Page<Users> findByLocationOrderByJoinedDateDesc(String location, Pageable pageable);
    Page<Users> findByLocationOrderByJoinedDateAsc(String location, Pageable pageable);

//    Sort users by code
    Page<Users> findByLocationOrderByCodeDesc(String location, Pageable pageable);
    Page<Users> findByLocationOrderByCodeAsc(String location, Pageable pageable);

//    Sort users by full name
    Page<Users> findByLocationOrderByFullNameDesc(String location, Pageable pageable);
    Page<Users> findByLocationOrderByFullNameAsc(String location, Pageable pageable);

//    Sort users by role
    Page<Users> findByLocationOrderByRoleDesc(String location, Pageable pageable);
    Page<Users> findByLocationOrderByRoleAsc(String location,  Pageable pageable);

//    List<Users> paginateUsers(int offset, int limit);


}