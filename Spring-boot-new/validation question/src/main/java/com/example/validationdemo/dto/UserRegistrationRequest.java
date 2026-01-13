
// src/main/java/com/example/validationdemo/dto/UserRegistrationRequest.java
package com.example.validationdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {

	
	//Name should not be blank
	//accept only 3-20 character
	//name must contains only [a-z],[A-Z],[0,9],[-,_,.] (you can use this regex "^[a-zA-Z0-9_.-]+$")
    private String username;

    
    //Password should not be blank
    //It must contain 8-64 character
    private String password;


    //Email should not be blank
    //Proper Email Format
    private String email;

    
    //Age should not be null
    //Atleast 18
    //AtMax 120
    private Integer age;


    //Salary must be Positive (non-zero)
    //Salary must have up to 10 digits and 2 decimals
    private BigDecimal salary;


    //Discount must be > 0
    //Discount must be <= 0.50
    private BigDecimal discount;


    //Date of birth must be in the past
    private LocalDate dateOfBirth;

    
    //Membership start must be today or in the future
    private LocalDate membershipStart;
    
    
    
    
    
    //------------------Advanced Hibernate Validator annotations------------------------------


    //Profile URL must be valid
    private String profileUrl;

    //Credit card must be valid (Luhn)
    //ignore Non Digit Characters 
    private String creditCard;

}
