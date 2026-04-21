package com.caresync.patient_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.caresync.patient_service.model.Patient;

import patient.events.PatientEvent;

@Service
public class PatientKafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(PatientKafkaProducer.class);
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public PatientKafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient){
        PatientEvent patientEvent = PatientEvent.newBuilder().setPatientId(patient.getId().toString()).setName(patient.getName()).setEmail(patient.getEmail()).setEventType("PATIENT_CREATED").build();
        
        try {
            kafkaTemplate.send("patient", patientEvent.toByteArray());
        } catch (Exception e) {
            log.error("Error sending PatientCreated event: {}", patientEvent, e);
        }
    }
}
