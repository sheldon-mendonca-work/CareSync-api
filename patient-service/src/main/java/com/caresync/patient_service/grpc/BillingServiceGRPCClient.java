
package com.caresync.patient_service.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class BillingServiceGRPCClient {

    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;
    private static final Logger log = LoggerFactory.getLogger(BillingServiceGRPCClient.class);

    public BillingServiceGRPCClient(@Value("${billing.service.address:localhost}") String serverAddress,
        @Value("${billing.service.grpc.port:9001}") int serverPort){
        log.info("Connecting to billing GRPC service at {}: {}", serverAddress, serverPort);
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(serverAddress, serverPort).usePlaintext().build();

        this.blockingStub = BillingServiceGrpc.newBlockingStub(managedChannel);
    }


    public BillingResponse createBillingAccount(String patientId, String name, String email){
        BillingRequest request = BillingRequest.newBuilder().setPatientId(patientId).setName(name).setEmail(email).build();

        BillingResponse response = blockingStub.createBillingAccount(request);

        log.info("Received response from billing GRPC response : {}", response);

        return response;
    }
    
}