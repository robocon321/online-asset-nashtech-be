package com.nashtech.rookies.repository;

import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.entity.Assignment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
//    Show information of assignment by id
    Assignment findAssignmentById(Long id);
    boolean existsAssignmentByAsset_Id(Long id);
    List<Assignment> findByAsset(Asset asset);
}