package com.gorvi.ledger.dto

import java.math.BigDecimal
import java.time.OffsetDateTime

class WithdrawalDTO(
    var created: OffsetDateTime = OffsetDateTime.now(),
    var currency: String = "USD",
    var amount: BigDecimal = BigDecimal.ZERO,
    var fee: AmountDTO? = AmountDTO(),
    var referenceId: String? = null,
    var notes: String? = null,
    var sourceOperationId: String? = null
)
