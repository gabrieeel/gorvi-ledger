package com.gorvi.ledger.model

import javax.persistence.Entity
import javax.persistence.Id

/**
 * Examples: USD, BTC
 */
@Entity
class Currency {
    @Id
    var id: Long = 0
    var code: String = ""
}
