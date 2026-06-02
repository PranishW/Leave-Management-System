package com.main.leave.Interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.main.leave.models.LeaveRequest;

@Repository
public interface LeaveRequestsRepository extends JpaRepository<LeaveRequest,Long>{
	public List<LeaveRequest> findByEmployeeId(long empId);
	@Query("""
			SELECT lr
			FROM leaveRequests lr
			WHERE lr.assignedManagerId = :managerId
			ORDER BY
			    CASE WHEN lr.status = 'Pending' THEN 0 ELSE 1 END,
			    lr.leaveRequestId DESC
			""")
	public Page<LeaveRequest> findByAssignedManagerId(long managerId,Pageable pageable);
}
