package com.example.fortuneforge.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SmsService {
    @Value("${sms.ssl}")
    private boolean smsSSL;

    @Value("${sms.endpoint}")
    private String smsEndpoint;

    @Value("${sms.key}")
    private String smsKey;

    @Value("${sms.sender.id}")
    private String smsSenderId;

    @Async
    public void sendSms(String phoneNumber, String message) {
        if (phoneNumber != null) {
            try {
                RestTemplate restTemplate = new RestTemplate();

                // Construct the endpoint with smsEndpoint?key=smskey query parameter
                String url = UriComponentsBuilder.fromHttpUrl(smsEndpoint)
                        .queryParam("key", smsKey)
                        .toUriString();

                System.out.println(url + "sms url");

                // Array map of api parameters
                Map<String, Object> smsData = new HashMap<>();
                smsData.put("recipient", new String[]{phoneNumber});
                smsData.put("sender", smsSenderId);
                smsData.put("message", message);

                System.out.println(smsData.toString());

                HttpHeaders headers = new HttpHeaders();

                // Set content type header as "application/json"
                headers.setContentType(MediaType.APPLICATION_JSON);

                // create a request body
                HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(smsData, headers);

                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

                log.info("Response from SMS API: {}", response.getBody());

                System.out.println(response.getBody());
            } catch (Exception e) {

                log.error("Failed to send SMS: {}", e.getMessage());

            }

        }
    }
}
