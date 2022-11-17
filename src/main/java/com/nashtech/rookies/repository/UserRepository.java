package com.nashtech.rookies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nashtech.rookies.entity.Users;

public interface UserRepository extends JpaRepository<Long, Users>{
	public Users save(Users user);
}
