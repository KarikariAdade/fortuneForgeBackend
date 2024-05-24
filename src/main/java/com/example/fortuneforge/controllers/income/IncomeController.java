package com.example.fortuneforge.controllers.income;

import com.example.fortuneforge.config.ApiResponse;
import com.example.fortuneforge.requests.income.IncomeRequest;
import com.example.fortuneforge.services.income.IncomeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("income")
@RequiredArgsConstructor
@Tag(name = "Income", description = "Income Management")
public class IncomeController {

    private final IncomeService incomeService;
    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllIncome(@RequestHeader("Authorization") String token) {

        return this.incomeService.getAllIncome(token);

    }


    @PostMapping("/store")
    public ResponseEntity<ApiResponse> addIncome(@RequestHeader("Authorization") String token, @RequestBody @Valid IncomeRequest request) {

        return this.incomeService.addIncome(token, request);

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateIncome(@RequestHeader("Authorization") String token, @PathVariable String id,  @RequestBody @Valid IncomeRequest request) {

        return this.incomeService.updateIncome(token, id, request);

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteIncome(@PathVariable String id) {

        return this.incomeService.deleteIncome(id);

    }

    @PostMapping("delete/bulk")
    public ResponseEntity<ApiResponse> deleteBulk(@RequestBody List<HashMap<?, Object>> requests) {
//        System.out.println(requests.stream().toString());
        return this.incomeService.deleteBulk(requests);
    }


}
