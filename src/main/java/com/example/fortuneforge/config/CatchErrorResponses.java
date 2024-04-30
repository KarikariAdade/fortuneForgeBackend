package com.example.fortuneforge.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CatchErrorResponses {

    public static ResponseEntity<ApiResponse> catchErrors(String x, Exception exception) {
        String errorMessage = x + exception.getMessage();

        ApiResponse errorResponse = new ApiResponse(errorMessage, null, null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
