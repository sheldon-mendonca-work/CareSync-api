package com.caresync.patient_service.validation;

public class PatientErrorMessage {
    
    private PatientErrorMessage(){}

    public static final String NAME_REQUIRED = "Name is required";
    public static final String NAME_EXCEEDED_MAX_LENGTH = "Name cannot exceed more than 100 characters";
    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String EMAIL_INVALID = "Email should be valid";
    public static final String ADDRESS_REQUIRED = "Address is required";
    public static final String DATE_OF_BIRTH_REQUIRED = "Date of birth is required";
    public static final String DATE_OF_BIRTH_INVALID = "Date of birth must be in yyyy-MM-dd format";
    public static final String REGISTERED_DATE_REQUIRED = "Registered date is required";
    public static final String REGISTERED_DATE_INVALID = "Registered date must be in yyyy-MM-dd format";
    public static final String ERROR_MESSAGE_KEY = "message";
    public static final String EMAIL_ALREADY_EXISTS = "A patient with this email address already exists";
    public static final String PATIENT_NOT_FOUND = "Patient not found";
}
