package com.main.leave.Interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.main.leave.models.LeaveRequest;

@Repository
public interface LeaveRequestsRepository extends CrudRepository<LeaveRequest,Long>{

}
