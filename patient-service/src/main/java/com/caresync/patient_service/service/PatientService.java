package com.caresync.patient_service.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caresync.patient_service.dto.PatientRequestDTO;
import com.caresync.patient_service.dto.PatientResponseDTO;
import com.caresync.patient_service.exception.EmailAlreadyExistsException;
import com.caresync.patient_service.exception.PatientNotFoundException;
import com.caresync.patient_service.grpc.BillingServiceGRPCClient;
import com.caresync.patient_service.mapper.PatientMapper;
import com.caresync.patient_service.model.Patient;
import com.caresync.patient_service.repo.PatientRepository;

@Service
public class PatientService {
    
    private final PatientRepository patientRepository;
    private final BillingServiceGRPCClient billingServiceGRPCClient;

    public PatientService(PatientRepository patientRepository, BillingServiceGRPCClient billingServiceGRPCClient){
        this.patientRepository = patientRepository;
        this.billingServiceGRPCClient = billingServiceGRPCClient;
    }

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with the email already exists: " + patientRequestDTO.getEmail());
        }
        
        Patient patient = patientRepository.save(PatientMapper.toPatient(patientRequestDTO));

        billingServiceGRPCClient.createBillingAccount(patient.getId().toString(), patient.getName(), patient.getEmail());

        return PatientMapper.toDTO(patient);
    }

    
  public PatientResponseDTO updatePatient(UUID id,
      PatientRequestDTO patientRequestDTO) {

    Patient patient = patientRepository.findById(id).orElseThrow(
        () -> new PatientNotFoundException("Patient not found with ID: " + id));

    if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),
        id)) {
      throw new EmailAlreadyExistsException(
          "A patient with this email " + "already exists"
              + patientRequestDTO.getEmail());
    }

    patient.setName(patientRequestDTO.getName());
    patient.setAddress(patientRequestDTO.getAddress());
    patient.setEmail(patientRequestDTO.getEmail());
    patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

    Patient updatedPatient = patientRepository.save(patient);
    return PatientMapper.toDTO(updatedPatient);
  }

  public void deletePatient(UUID id) {
    patientRepository.deleteById(id);
  }
}
