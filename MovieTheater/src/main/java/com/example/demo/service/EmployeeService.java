package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.entity.request.EmployeeRequest;
import jakarta.validation.Valid;

public interface EmployeeService {
    Employee createEmployee(@Valid EmployeeRequest employeeRequest);

}
