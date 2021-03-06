package com.gorvi.ledger.model

import java.math.BigDecimal
import javax.persistence.*

/**
 * Example: USD Balance of PayPal
 */
@Entity
class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    var movements: MutableList<Movement> = mutableListOf()

    fun addMovements(movements: List<Movement>) {
        movements.forEach{
            this.currentValue = currentValue.add(it.amountIn).subtract(it.amountOut)
            this.movements.add(it)
        }
    }

}
