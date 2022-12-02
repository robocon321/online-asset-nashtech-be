package com.nashtech.rookies.repository;

import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.entity.Assignment;
import com.nashtech.rookies.entity.Users;

import java.util.List;
import java.sql.Timestamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
//    Show information of assignment by id
    Assignment findAssignmentById(Long id);
    boolean existsAssignmentByAsset_Id(Long id);

   @Query(value = "select * from assignment a where a.assigned_by_id =?1 and a.state = 'Accepted'  and a.is_complete = false",nativeQuery = true)
    List<Assignment> checkAssignmentUserAssignedBy(Long id);
   @Query(value = "select * from assignment a where a.assigned_to_id =?1 and a.state = 'Accepted'  and a.is_complete = false",nativeQuery = true)
   List<Assignment> checkAssignmentUserAssignedTo(Long id);
    List<Assignment> findAllByAssignedTo(Users userGetAsm);
    List<Assignment> findByAsset(Asset asset);

    @Query(value = "select a.* from assignment a where a.assigned_date <=  ?1  and a.assigned_to_id =?2 and a.state != 'Declined' ",nativeQuery = true)
    List<Assignment> getAllAssignmentOfUser(Timestamp date,Long id);

}