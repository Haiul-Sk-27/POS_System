package com.StoreHub.StoreHub.pos.system.service;

import com.StoreHub.StoreHub.pos.system.domain.UserRole;
import com.StoreHub.StoreHub.pos.system.model.User;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.UserDto;

import java.util.List;

public interface EmployeeService {

    UserDto createStoreEmployee(UserDto employee,Long storeId) throws Exception;
    User updateEmployee(Long employeeId, UserDto employeeDetails) throws Exception;
    UserDto createBranchEmployee(UserDto employee,Long branchId) throws Exception;
    void  deleteEmployee(Long employeeId) throws Exception;
    List<User> findStoreEmployees(Long storeId, UserRole role) throws Exception;
    List<User> findBranchEmployees(Long branchId,UserRole role) throws Exception;
}
