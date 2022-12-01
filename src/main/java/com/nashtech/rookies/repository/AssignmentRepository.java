package com.nashtech.rookies.repository;

import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.entity.Assignment;
import com.nashtech.rookies.entity.Users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
//    Show information of assignment by id
    Assignment findAssignmentById(Long id);
    boolean existsAssignmentByAsset_Id(Long id);

   @Query(value = "select * from assignment a where a.assigned_by_id =?1 and a.state = 'Accepted'  and a.is_deleted = false",nativeQuery = true)
    List<Assignment> checkAssignmentUserAssignedBy(Long id);
   @Query(value = "select * from assignment a where a.assigned_to_id =?1 and a.state = 'Accepted'  and a.is_deleted = false",nativeQuery = true)
   List<Assignment> checkAssignmentUserAssignedTo(Long id);

    //    @Query(value = "select a from Assignment a where a.assigned_by_id = ?1 or a.assigned_to_id = ?2",nativeQuery = true)
    List<Assignment> findAllByAssignedByOrAssignedTo(Users userAsBy, Users userAsTo);

//    @Query(value = "select a from Assignment a where a.assigned_to_id = ?1",nativeQuery = true)
    List<Assignment> findAllByAssignedTo(Users userGetAsm);
    List<Assignment> findByAsset(Asset asset);
}