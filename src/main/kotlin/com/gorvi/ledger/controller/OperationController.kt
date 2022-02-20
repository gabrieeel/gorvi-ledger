package com.gorvi.ledger.controller

import com.gorvi.ledger.dto.response.OperationResponseDTO
import com.gorvi.ledger.service.OperationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/operations")
class OperationController(var operationService: OperationService) {
    @GetMapping
    fun getAll(): List<OperationResponseDTO> {
        var operations = operationService.getAll()
        return operations.map {
            OperationResponseDTO(it.id)
        }
    }

}