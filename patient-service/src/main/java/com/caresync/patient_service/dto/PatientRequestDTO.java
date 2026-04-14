package com.caresync.patient_service.dto;

import com.caresync.patient_service.dto.validators.CreatePatientValidationGroup;
import com.caresync.patient_service.validation.PatientErrorMessage;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PatientRequestDTO {

    @NotBlank(message = PatientErrorMessage.NAME_REQUIRED)
    @Size(max = 100, message = PatientErrorMessage.NAME_EXCEEDED_MAX_LENGTH)
    private String name;

    @NotBlank(message = PatientErrorMessage.EMAIL_REQUIRED)
    @Email(message = PatientErrorMessage.EMAIL_INVALID)
    private String email;

    @NotBlank(message = PatientErrorMessage.ADDRESS_REQUIRED)
    private String address;

    @NotBlank(message = PatientErrorMessage.DATE_OF_BIRTH_REQUIRED)
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = PatientErrorMessage.DATE_OF_BIRTH_INVALID)
    private String dateOfBirth;

    @NotBlank(groups = CreatePatientValidationGroup.class, message = PatientErrorMessage.REGISTERED_DATE_REQUIRED)
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", groups = CreatePatientValidationGroup.class, message = PatientErrorMessage.REGISTERED_DATE_INVALID)
    private String registeredDate;

    public @NotBlank(message = PatientErrorMessage.NAME_REQUIRED) @Size(max = 100, message = PatientErrorMessage.NAME_EXCEEDED_MAX_LENGTH) String getName() {
        return name;
    }

    public void setName(
            @NotBlank(message = PatientErrorMessage.NAME_REQUIRED) @Size(max = 100, message = PatientErrorMessage.NAME_EXCEEDED_MAX_LENGTH) String name) {
        this.name = name;
    }

    public @NotBlank(message = PatientErrorMessage.EMAIL_REQUIRED) @Email(message = PatientErrorMessage.EMAIL_INVALID) String getEmail() {
        return email;
    }

    public void setEmail(
            @NotBlank(message = PatientErrorMessage.EMAIL_REQUIRED) @Email(message = PatientErrorMessage.EMAIL_INVALID) String email) {
        this.email = email;
    }

    public @NotBlank(message = PatientErrorMessage.ADDRESS_REQUIRED) String getAddress() {
        return address;
    }

    public void setAddress(
            @NotBlank(message = PatientErrorMessage.ADDRESS_REQUIRED) String address) {
        this.address = address;
    }

    public @NotBlank(message = PatientErrorMessage.DATE_OF_BIRTH_REQUIRED) @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = PatientErrorMessage.DATE_OF_BIRTH_INVALID) String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(
            @NotBlank(message = PatientErrorMessage.DATE_OF_BIRTH_REQUIRED) @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = PatientErrorMessage.DATE_OF_BIRTH_INVALID) String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getregisteredDate() {
        return registeredDate;
    }

    public void setregisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

}
