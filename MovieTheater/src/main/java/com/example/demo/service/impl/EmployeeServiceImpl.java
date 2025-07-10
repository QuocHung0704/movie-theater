package com.example.demo.service.impl;

import com.example.demo.entity.Account;
import com.example.demo.entity.Employee;
import com.example.demo.entity.request.EmployeeRequest;
import com.example.demo.entity.response.EmployeeResponse;
import com.example.demo.enums.UserRoleEnums;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public Employee deleteEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found");
        } else {
            employee.getAccount().setStatus(false);
            employeeRepository.save(employee);
            return employee;
        }
    }

    @Override
    public byte[] exportEmployeesToExcel() {
        List<EmployeeResponse> employees = getAllEmployees();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Employees");

            // Create header row
            String[] headers = {"Employee ID", "Username", "Full Name", "Email", "Phone Number", "Date of Birth", "Role"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Populate data rows
            int rowIdx = 1;
            for (EmployeeResponse employee : employees) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(employee.getEmployeeId());
                row.createCell(1).setCellValue(employee.getUsername());
                row.createCell(2).setCellValue(employee.getFullName());
                row.createCell(3).setCellValue(employee.getEmail());
                row.createCell(4).setCellValue(employee.getPhoneNumber());
                row.createCell(5).setCellValue(employee.getDateOfBirth() != null ? employee.getDateOfBirth().toString() : "");
                row.createCell(6).setCellValue(employee.getAccountRole() != null ? employee.getAccountRole().name() : "");
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to Excel: " + e.getMessage());
        }
    }

    @Override
    public void importDataFromExcel(MultipartFile file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua header

                String username = formatter.formatCellValue(row.getCell(0)).trim();
                String fullName = formatter.formatCellValue(row.getCell(1)).trim();
                String email = formatter.formatCellValue(row.getCell(2)).trim();
                String phoneNumber = formatter.formatCellValue(row.getCell(3)).trim();
                String identityCard = formatter.formatCellValue(row.getCell(4)).trim();
                String dobString = formatter.formatCellValue(row.getCell(5)).trim();
                String password = formatter.formatCellValue(row.getCell(6)).trim();
                String confirmPassword = formatter.formatCellValue(row.getCell(7)).trim();

                // Nếu có ô nào bị trống thì bỏ qua dòng
                if (username.isEmpty() || fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() ||
                        identityCard.isEmpty() || dobString.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    continue;
                }

                // Chuyển chuỗi ngày sinh sang LocalDate
                LocalDate dateOfBirth;
                try {
                    dateOfBirth = LocalDate.parse(dobString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (DateTimeParseException e) {
                    continue; // Bỏ qua nếu sai định dạng ngày
                }

                EmployeeRequest employeeRequest = EmployeeRequest.builder()
                        .username(username)
                        .fullName(fullName)
                        .email(email)
                        .phoneNumber(phoneNumber)
                        .identityCard(identityCard)
                        .dateOfBirth(dateOfBirth)
                        .password(password)
                        .confirmPassword(confirmPassword)
                        .build();

                createEmployee(employeeRequest);
            }
        } catch (Exception e) {
            throw new IOException("Failed to import data from Excel: " + e.getMessage(), e);
        }
    }

}
