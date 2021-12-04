package com.gorvi.ledger.dto

import java.time.OffsetDateTime

data class PurchaseDTO(
    var created: OffsetDateTime = OffsetDateTime.now(),
    var purchased: AmountDTO = AmountDTO(),
    var spent: AmountDTO = AmountDTO(),
    var fee: AmountDTO? = null,
//    var exchangeRate: BigDecimal = BigDecimal.ZERO // should be a string? should be included or calculated?
    var referenceId: String? = null
//    var notes: String? = null
//    var sourceOperationId
)

