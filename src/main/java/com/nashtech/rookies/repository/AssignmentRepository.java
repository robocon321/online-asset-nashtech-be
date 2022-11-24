package com.nashtech.rookies.repository;

import com.nashtech.rookies.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}