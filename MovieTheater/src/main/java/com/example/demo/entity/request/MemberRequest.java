package com.example.demo.entity.request;

import com.example.demo.enums.UserRoleEnums;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequest {
    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ và tên phải từ 2 đến 100 ký tự")
    private String fullName;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 3, max = 20, message = "Tên đăng nhập phải từ 3 đến 20 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Tên đăng nhập chỉ được chứa chữ cái, số và dấu gạch dưới")
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10,11}$", message = "Số điện thoại phải có 10-11 chữ số")
    private String phoneNumber;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, max = 20, message = "Mật khẩu phải từ 8 đến 20 ký tự")
    private String password;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^\\d{12}$", message = "Số CMND/CCCD phải đúng 12 chữ số")
    private String identityCard;

    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự")
    private String address;

    private UserRoleEnums accountRole;

    @Min(value = 0, message = "Điểm tích lũy không được âm")
    private Long addScore;

    @Min(value = 0, message = "Điểm đã sử dụng không được âm")
    private Long useScore;

    // Total spent management (optional)
    @Min(value = 0, message = "Tổng chi tiêu không được âm")
    private Long totalSpent;

    // Initial points (optional)
    @Min(value = 0, message = "Điểm ban đầu không được âm")
    private Long initialPoints = 0L;

    // Initial spending amount (optional)
    @Min(value = 0, message = "Số tiền chi tiêu ban đầu không được âm")
    private Long initialSpent = 0L;

    private Boolean status;
}
