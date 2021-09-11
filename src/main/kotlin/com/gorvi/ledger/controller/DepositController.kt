package com.gorvi.ledger.controller

import com.gorvi.ledger.dto.DepositDTO
import com.gorvi.ledger.dto.response.OperationResponseDTO
import com.gorvi.ledger.service.OperationService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/accounts/{accountId}/operations/deposits")
class DepositController(val service: OperationService) {

    @PostMapping
    fun createDeposit(@PathVariable accountId: Long, @RequestBody dto: DepositDTO): OperationResponseDTO {
        return OperationResponseDTO(service.createDeposit(accountId, dto).id)
    }

}
