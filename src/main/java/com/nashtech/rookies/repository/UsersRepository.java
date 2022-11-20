package com.nashtech.rookies.repository;

import com.nashtech.rookies.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findUsersById(Long id);

//    Search by username or code
    List<Users> findByLocationAndUsernameContainingOrCodeContaining(String location, String searchUsername, String searchCode);

//    Filter users by role and location and (by username or code)
    @Query("SELECT u FROM Users u WHERE u.location = ?1 AND u.role IN ?2 AND (u.username LIKE %?3% OR u.code LIKE %?3%)")
    List<Users> search(String location, List<String> role, String search);

//    Filter with List of roles
    List<Users> findByLocationAndRoleIn(String location , List<String> role);

//    Find all users by admin locations
    List<Users> findByLocation(String location);

//    Sort users by JoinedDate
    List<Users> findByLocationOrderByJoinedDateDesc(String location);
    List<Users> findByLocationOrderByJoinedDateAsc(String location);

//    Sort users by code
    List<Users> findByLocationOrderByCodeDesc(String location);
    List<Users> findByLocationOrderByCodeAsc(String location);

//    Sort users by full name
    List<Users> findByLocationOrderByFullNameDesc(String location);
    List<Users> findByLocationOrderByFullNameAsc(String location);

//    Sort users by role
    List<Users> findByLocationOrderByRoleDesc(String location);
    List<Users> findByLocationOrderByRoleAsc(String location);

}