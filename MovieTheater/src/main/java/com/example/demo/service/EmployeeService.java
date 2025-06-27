package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.entity.request.EmployeeRequest;
import com.example.demo.entity.response.EmployeeResponse;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    Employee createEmployee(@Valid EmployeeRequest employeeRequest);

    List<EmployeeResponse> getAllEmployees();

    Employee deleteEmployee(long employeeId);

    byte[] exportEmployeesToExcel();

    void importDataFromExcel(MultipartFile file) throws IOException;
}
