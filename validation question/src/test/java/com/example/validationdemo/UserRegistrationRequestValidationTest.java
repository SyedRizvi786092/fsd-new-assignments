
// src/test/java/com/example/validationdemo/UserRegistrationRequestValidationTest.java
package com.example.validationdemo;

import com.example.validationdemo.dto.UserRegistrationRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRegistrationRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private UserRegistrationRequest validRequest() {
        UserRegistrationRequest req = new UserRegistrationRequest();
        req.setUsername("ganesh_123");
        req.setPassword("Aa1@Strong");
        req.setEmail("ganesh@example.com");
        req.setAge(25);
        req.setSalary(new BigDecimal("50000.00"));
        req.setDiscount(new BigDecimal("0.10"));
        req.setDateOfBirth(LocalDate.now().minusYears(25));
        req.setMembershipStart(LocalDate.now());
        req.setProfileUrl("https://example.com/profile/ganesh");
        req.setCreditCard("4111 1111 1111 1111");


        return req;
    }

    @Test
    void valid_request_should_have_no_violations() {
        UserRegistrationRequest req = validRequest();
        Set<ConstraintViolation<UserRegistrationRequest>> violations = validator.validate(req);
        assertThat(violations).isEmpty();
    }

    @Test
    void blank_username_should_fail() {
        UserRegistrationRequest req = validRequest();
        req.setUsername("  ");
        Set<ConstraintViolation<UserRegistrationRequest>> violations = validator.validate(req);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("username"));
    }

    @Test
    void invalid_email_should_fail() {
        UserRegistrationRequest req = validRequest();
        req.setEmail("not-an-email");
        Set<ConstraintViolation<UserRegistrationRequest>> violations = validator.validate(req);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    }

    @Test
    void age_below_min_should_fail() {
        UserRegistrationRequest req = validRequest();
        req.setAge(17);
        Set<ConstraintViolation<UserRegistrationRequest>> violations = validator.validate(req);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("age"));
    }

    @Test
    void negative_salary_should_fail() {
        UserRegistrationRequest req = validRequest();
        req.setSalary(new BigDecimal("-10.00"));
        Set<ConstraintViolation<UserRegistrationRequest>> violations = validator.validate(req);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("salary"));
    }

    @Test
    void discount_out_of_range_should_fail() {
        UserRegistrationRequest req = validRequest();
        req.setDiscount(new BigDecimal("0.60"));
        Set<ConstraintViolation<UserRegistrationRequest>> violations = validator.validate(req);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("discount"));
    }

    @Test
    void future_dob_should_fail() {
        UserRegistrationRequest req = validRequest();
        req.setDateOfBirth(LocalDate.now().plusDays(1));
        Set<ConstraintViolation<UserRegistrationRequest>> violations = validator.validate(req);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("dateOfBirth"));
    }

    @Test
    void membership_in_past_should_fail() {
        UserRegistrationRequest req = validRequest();
        req.setMembershipStart(LocalDate.now().minusDays(1));
        Set<ConstraintViolation<UserRegistrationRequest>> violations = validator.validate(req);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("membershipStart"));
    }


    @Test
    void invalid_url_should_fail() {
        UserRegistrationRequest req = validRequest();
        req.setProfileUrl("htp:/bad");
        Set<ConstraintViolation<UserRegistrationRequest>> violations = validator.validate(req);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("profileUrl"));
    }

    @Test
    void invalid_credit_card_should_fail() {
        UserRegistrationRequest req = validRequest();
        req.setCreditCard("1234 5678 9012 3456");
        Set<ConstraintViolation<UserRegistrationRequest>> violations = validator.validate(req);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("creditCard"));
    }


    
}
