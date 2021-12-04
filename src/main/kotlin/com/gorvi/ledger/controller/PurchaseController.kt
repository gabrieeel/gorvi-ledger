package com.gorvi.ledger.controller

import com.gorvi.ledger.dto.PurchaseDTO
import com.gorvi.ledger.dto.response.OperationResponseDTO
import com.gorvi.ledger.service.OperationService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/accounts/{accountId}/operations/purchases")

class PurchaseController(val service: OperationService) {

    @PostMapping
    fun createPurchase(@PathVariable accountId: Long, @RequestBody dto: PurchaseDTO) : OperationResponseDTO {
        return OperationResponseDTO(service.createPurchase(accountId, dto).id)
    }

}
