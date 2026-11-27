package com.onedao.backend.dto;

import com.onedao.backend.model.UserType;
import jakarta.validation.constraints.*;

public record SignupRequest(
        @NotBlank
        @Size(min = 8, max = 50)
        String username,

        @NotBlank
        @Size(max = 150)
        @Pattern(regexp = "^(?=.*@)(?=.*_)[^\\s]+$",
                 message = "Email must contain @ and _")
        String email,

        @NotBlank
        @Size(min = 8, max = 12)
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,12}$",
                message = "Password must contain 1 uppercase, 1 lowercase, 1 number and 1 special character")
        String password,

        @NotNull
        UserType userType) {
}
