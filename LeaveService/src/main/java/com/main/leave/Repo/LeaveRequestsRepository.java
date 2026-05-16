package com.main.leave.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.main.leave.models.LeaveRequest;

@Repository
public interface LeaveRequestsRepository extends CrudRepository<LeaveRequest,Long>{

}
