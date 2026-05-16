package com.main.leave.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.main.leave.models.LeaveBalance;

@Repository
public interface LeaveBalanceRepository extends CrudRepository<LeaveBalance,Long>{

}
