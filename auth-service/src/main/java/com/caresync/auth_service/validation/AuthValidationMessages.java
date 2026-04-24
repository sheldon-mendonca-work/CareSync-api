package com.caresync.auth_service.validation;

public class AuthValidationMessages {
    
    private AuthValidationMessages(){}

    public static final String loginEmailRequired = "Email is required";
    public static final String loginEmailInvalid = "Email should be a valid email address";
    public static final String loginPasswordRequired = "Password is required";
    public static final String loginPasswordInvalidMin = "Password should have minimum 8 characters";
    public static final String loginPasswordInvalidMax = "Password should have maximum 32 characters";
}
