package com.gorvi.ledger.model

import java.math.BigDecimal
import java.util.*
import javax.persistence.*

/**
 * Example: USD Balance of Payoneer
 */
@Entity
class Balance {
    @Id
    var id: Long = 0

    @Column(name = "current_value")
    var currentValue: BigDecimal = BigDecimal.ZERO

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    var account: Account = Account()

    @OneToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    var currency: Currency = Currency()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "balance")
    var movements: List<Movement> = ArrayList<Movement>()
}
