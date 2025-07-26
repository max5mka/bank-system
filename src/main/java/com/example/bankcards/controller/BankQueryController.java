package com.example.bankcards.controller;

import com.example.bankcards.controller.api.request.BankQueryRequest;
import com.example.bankcards.controller.api.response.bankquery.ReviewedBankQueryResponse;
import com.example.bankcards.controller.api.response.bankquery.BankQueryResponseForAdmin;
import com.example.bankcards.dao.dto.BankQueryDto;
import com.example.bankcards.dao.enums.BankQueryStatus;
import com.example.bankcards.dao.mapping.BankQueryMapping;
import com.example.bankcards.service.BankQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/queries")
@AllArgsConstructor
public class BankQueryController {

    private BankQueryService bankQueryService;
    private BankQueryMapping bankQueryMapping;

    @GetMapping("/{bankQueryId}")
    @Operation(summary = "Вывести банковский запрос по id")
    @ResponseStatus(HttpStatus.OK)
    public BankQueryResponseForAdmin getBankQueryById(@PathVariable("bankQueryId") Long bankQueryId) {
        BankQueryDto dto = bankQueryService.getById(bankQueryId);
        return bankQueryMapping.toAdminResponse(dto);
    }

    @GetMapping
    @Operation(summary = "Вывести все банковские запросы")
    @ResponseStatus(HttpStatus.OK)
    public List<BankQueryResponseForAdmin> getAllBankQueries() {
        return bankQueryService.getAll().stream()
                .map(bankQueryMapping::toAdminResponse).toList();
    }

    @PostMapping
    @Operation(summary = "Создать исправленный банковский запрос")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewedBankQueryResponse createCorrectBankQuery(@RequestBody BankQueryRequest bankQueryRequest) {
        BankQueryDto dto = bankQueryService.create(bankQueryRequest);
        return bankQueryMapping.toReviewedResponse(dto);
    }

    @PatchMapping("/{bankQueryId}")
    @Operation(summary = "Изменить статус банковского запроса")
    @ResponseStatus(HttpStatus.OK)
    public ReviewedBankQueryResponse changeBankQueryStatus(@PathVariable("bankQueryId") Long bankQueryId,
                                                           @RequestParam BankQueryStatus bankQueryStatus) {
        BankQueryDto dto = bankQueryService.changeStatus(bankQueryId, bankQueryStatus);
        return bankQueryMapping.toReviewedResponse(dto);
    }
}
