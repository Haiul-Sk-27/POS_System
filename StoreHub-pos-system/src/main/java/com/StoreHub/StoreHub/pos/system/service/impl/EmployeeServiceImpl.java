package com.StoreHub.StoreHub.pos.system.service.impl;

import com.StoreHub.StoreHub.pos.system.domain.UserRole;
import com.StoreHub.StoreHub.pos.system.mapper.UserMapper;
import com.StoreHub.StoreHub.pos.system.model.Branch;
import com.StoreHub.StoreHub.pos.system.model.Store;
import com.StoreHub.StoreHub.pos.system.model.User;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.UserDto;
import com.StoreHub.StoreHub.pos.system.repository.BranchRepository;
import com.StoreHub.StoreHub.pos.system.repository.StoreRepository;
import com.StoreHub.StoreHub.pos.system.repository.UserRepository;
import com.StoreHub.StoreHub.pos.system.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private  final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createStoreEmployee(UserDto employee, Long storeId) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(
                ()->new Exception("Store not found")
        );

        Branch branch = null;

        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER){
            if(employee.getBranchId()==null){
                throw  new Exception("Branch id is required to create branch manager");
            }

            branch = branchRepository.findById(employee.getBranchId()).orElseThrow(
                    ()->new Exception("Branch not found")
            );
        }

        User user = UserMapper.toEntity(employee);
        user.setStore(store);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(employee.getPassword()));
        user.setCreateAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());

        User savedEmployee = userRepository.save(user);
        if(employee.getRole() == UserRole.ROLE_BRANCH_MANAGER && branch != null){
            branch.setManager(savedEmployee);
            branchRepository.save(branch);
        }
        return UserMapper.toDTO(savedEmployee);
    }



    @Override
    public User updateEmployee(Long employeeId, UserDto employeeDetails) throws Exception {

        User existingEmployee = userRepository.findById(employeeId).orElseThrow(
                ()-> new Exception("Employee not exist with given id")
        );

        Branch branch = branchRepository.findById(employeeDetails.getBranchId()).orElseThrow(
                ()->new ExpressionException("Branch not found")
        );

        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setFullName(employeeDetails.getFullName());
        existingEmployee.setPassword(employeeDetails.getPassword());
        existingEmployee.setRole(employeeDetails.getRole());
        existingEmployee.setBranch(branch);
        return userRepository.save(existingEmployee);
    }

    @Override
    public UserDto createBranchEmployee(UserDto employee, Long branchId) throws Exception {

        Branch branch = branchRepository.findById(branchId).orElseThrow(
                ()->new Exception("Branch not found")
        );

        //ADMIN

        if(employee.getRole() == UserRole.ROLE_BRANCH_CASHIER ||
        employee.getRole() == UserRole.ROLE_BRANCH_MANAGER){
            User user = UserMapper.toEntity(employee);
            user.setBranch(branch);
            user.setStore(branch.getStore());
            user.setCreateAt(LocalDateTime.now());
            user.setUpdateAt(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(employee.getPassword()));
            return UserMapper.toDTO(userRepository.save(user));
        }
        throw  new Exception("Branch role not Supported");
    }

    @Override
    public void deleteEmployee(Long employeeId) throws Exception {
        User employee = userRepository.findById(employeeId).orElseThrow(
                ()->new Exception("employee not found")
        );

        userRepository.delete(employee);
    }



    @Override
    public List<User> findStoreEmployees(Long storeId, UserRole role) throws Exception {

        Store store = storeRepository.findById(storeId).orElseThrow(
                ()->new Exception("Store not found")
        );

        return userRepository.findByStore(store)
                .stream().filter(
                        user -> role==null || user.getRole()==role
                ).collect(Collectors.toList());
    }

    @Override
    public List<User> findBranchEmployees(Long branchId, UserRole role) throws Exception {

        Branch branch = branchRepository.findById(branchId).orElseThrow(
                ()->new Exception("Branch not found")
        );

        List<User> employee = userRepository.findByBranchId(branchId)
                .stream().filter(
                        user -> role==null || user.getRole()==role
                ).collect(Collectors.toList());
        return employee;
    }
}
