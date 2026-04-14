package com.caresync.patient_service.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caresync.patient_service.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID>{
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id);
}
