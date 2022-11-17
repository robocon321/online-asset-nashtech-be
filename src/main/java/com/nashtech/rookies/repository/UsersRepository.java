package com.nashtech.rookies.repository;

import com.nashtech.rookies.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {


}