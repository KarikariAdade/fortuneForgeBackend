package com.example.fortuneforge.config;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NonNull
public class ApiResponse {

    private String message;

    private Object data;

    private String token;



}
