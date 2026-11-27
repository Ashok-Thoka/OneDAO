package com.onedao.backend;

import com.onedao.backend.dto.SignupRequest;
import com.onedao.backend.model.UserType;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SignupValidationTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void acceptsMachineTestSignupValues() {
        SignupRequest request = new SignupRequest("ashok123", "ashok_demo@example.com", "Test@123", UserType.STANDARD_USER);
        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void rejectsPasswordWithoutRequiredCharacters() {
        SignupRequest request = new SignupRequest("ashok123", "ashok_demo@example.com", "password", UserType.STANDARD_USER);
        assertFalse(validator.validate(request).isEmpty());
    }
}
