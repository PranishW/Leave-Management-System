package com.main.leave.Interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.main.leave.models.LeaveBalance;

@Repository
public interface LeaveBalanceRepository extends CrudRepository<LeaveBalance,Long>{

}
