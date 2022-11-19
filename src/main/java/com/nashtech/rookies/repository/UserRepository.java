package com.nashtech.rookies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nashtech.rookies.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	
	List<Users> findByUsernameContaining(String username);

}
