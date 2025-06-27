package com.example.demo.service.impl;

import com.example.demo.entity.Account;
import com.example.demo.entity.Employee;
import com.example.demo.entity.request.EmployeeRequest;
import com.example.demo.entity.response.EmployeeResponse;
import com.example.demo.enums.UserRoleEnums;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Employee createEmployee(EmployeeRequest employeeRequest) {
        if (employeeRequest == null || !employeeRequest.getPassword().equals(employeeRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Invalid password confirmation");
        }

        Account account = Account.builder()
                .username(employeeRequest.getUsername())
                .fullName(employeeRequest.getFullName())
                .password(passwordEncoder.encode(employeeRequest.getPassword()))
                .identityCard(employeeRequest.getIdentityCard())
                .phoneNumber(employeeRequest.getPhoneNumber())
                .email(employeeRequest.getEmail())
                .accountRole(UserRoleEnums.EMPLOYEE)
                .status(true)
                .emailVerified(false)
                .dateOfBirth(employeeRequest.getDateOfBirth())
                .emailVerified(true)
                .build();

        Employee employee = new Employee();
        employee.setAccount(account);

        employee = employeeRepository.save(employee);
        return employee;

    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findByAccountStatus(true);
        if (employees.isEmpty()) {
            throw new IllegalArgumentException("No employees found");
        } else {
            List<EmployeeResponse> employeeResponses = new ArrayList<>();
            for (Employee employee : employees) {
                if (employee.getAccount() != null) {
                    EmployeeResponse employeeResponse = EmployeeResponse.builder()
                            .employeeId(employee.getEmployeeId())
                            .username(employee.getAccount().getUsername())
                            .fullName(employee.getAccount().getFullName())
                            .accountRole(employee.getAccount().getAccountRole())
                            .status(employee.getAccount().isStatus())
                            .email(employee.getAccount().getEmail())
                            .dateOfBirth(employee.getAccount().getDateOfBirth())
                            .avatar(employee.getAccount().getAvatar())
                            .phoneNumber(employee.getAccount().getPhoneNumber())
                            .build();
                    employeeResponses.add(employeeResponse);
                }
            }
            return employeeResponses;
        }
    }
}
