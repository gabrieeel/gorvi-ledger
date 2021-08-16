package com.gorvi.ledger.model

import java.math.BigDecimal
import javax.persistence.*

/**
 * Increment or decrement of the balance
 */
@Entity
class Movement {
    @Id
    var id: Long = 0

    @Column(name = "amount_in")
    var amountIn: BigDecimal = BigDecimal.ZERO

    @Column(name = "amount_out")
    var amountOut: BigDecimal = BigDecimal.ZERO

    @Column(name = "is_fee")
    var fee: Boolean = false

    @ManyToOne
    @JoinColumn(name = "balance_id", referencedColumnName = "id")
    var balance: Balance = Balance()

    @ManyToOne
    @JoinColumn(name = "operation_id", referencedColumnName = "id")
    var operation: Operation = Operation()

}
