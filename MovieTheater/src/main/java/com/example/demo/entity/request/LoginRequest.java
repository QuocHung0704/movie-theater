package com.example.demo.entity.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Tên đăng nhập/email/số điện thoại không được để trống")
    private String identifier;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, max = 20, message = "Mật khẩu phải từ 8 đến 20 kí tự")
    private String password;
}
