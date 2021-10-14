package com.gorvi.ledger.controller

import com.gorvi.ledger.dto.WithdrawalDTO
import com.gorvi.ledger.dto.response.OperationResponseDTO
import com.gorvi.ledger.service.OperationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/accounts/{accountId}/operations/withdrawals")

class WithdrawalController(val service: OperationService) {

    @PostMapping
    fun addWithdrawal(@PathVariable accountId: Long, @RequestBody dto: WithdrawalDTO): OperationResponseDTO {
        return OperationResponseDTO(service.createWithdrawal(accountId, dto).id)
    }

}
