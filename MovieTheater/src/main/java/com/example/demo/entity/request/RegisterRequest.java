package com.example.demo.entity.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotNull(message = "Ngày sinh không được để trống")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    @Pattern(regexp = "^\\d{12}$", message = "Số CMND/CCCD phải đúng 12 chữ số")
    private String identityCard;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, max = 20, message = "Mật khẩu phải từ 8 đến 20 kí tự")
    private String password;

    @NotBlank(message = "Vui lòng nhập xác nhận mật khẩu")
    @Size(min = 8, max = 20, message = "Mật khẩu phải từ 8 đến 20 kí tự")
    private String confirmPassword;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\+?\\d{9,15}$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;
}
