package com.StoreHub.StoreHub.pos.system.controller;

import com.StoreHub.StoreHub.pos.system.domain.UserRole;
import com.StoreHub.StoreHub.pos.system.mapper.UserMapper;
import com.StoreHub.StoreHub.pos.system.model.User;
import com.StoreHub.StoreHub.pos.system.payload.response.ApiResponse;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.UserDto;
import com.StoreHub.StoreHub.pos.system.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/store/{storeId}")
    public ResponseEntity<UserDto> createStoreEmployee(@PathVariable Long storeId,
            @RequestBody  UserDto userDto) throws Exception {
        UserDto employee = employeeService.createStoreEmployee(userDto,storeId);

        return ResponseEntity.ok(employee);
    }

    @PostMapping("/branch/{branchId}")
    public ResponseEntity<UserDto> createBrachEmployee(@PathVariable Long branchId,
                                                       @RequestBody  UserDto userDto) throws Exception {
        UserDto employee = employeeService.createBranchEmployee(userDto,branchId);

        return ResponseEntity.ok(employee);
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserDto> updateEmployee(
            @PathVariable Long id,
            @RequestBody UserDto userDto) throws Exception {

        User updatedUser = employeeService.updateEmployee(id, userDto);

        UserDto responseDto = UserMapper.toDTO(updatedUser);
        responseDto.setPassword(null);
        return ResponseEntity.ok(responseDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable Long id) throws Exception {
       employeeService.deleteEmployee(id);

       ApiResponse apiResponse = new ApiResponse();
       apiResponse.setMessage("Employee delete");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/store/{id}")
    public ResponseEntity<List<User>> storeEmployee(@PathVariable Long id,
                                              @RequestParam(required = false)UserRole userRole) throws Exception {
        List<User>employee = employeeService.findStoreEmployees(id,userRole);

        return ResponseEntity.ok(employee);
    }

    @GetMapping("/branch/{id}")
    public ResponseEntity<List<User>> branchEmployee(@PathVariable Long id,
                                                    @RequestParam(required = false)UserRole userRole) throws Exception {
        List<User>employee = employeeService.findBranchEmployees(id,userRole);

        return ResponseEntity.ok(employee);
    }
}
