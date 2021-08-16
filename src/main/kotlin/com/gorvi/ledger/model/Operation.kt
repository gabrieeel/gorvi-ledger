package com.gorvi.ledger.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*


@Entity
class Operation {

    enum class Type {
        DEPOSIT //, WITHDRAWAL, PURCHASE, INVESTMENT?
    }

    @Id
    var id: Long = 0

    var created: LocalDateTime = LocalDateTime.now()

    var type: Type = Type.DEPOSIT;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operation")
    var movements: List<Movement> = ArrayList<Movement>()

    @OneToOne
    @JoinColumn(name = "source_operation_id")
    var sourceOperation: Operation? = null

    @Column(name = "reference_id")
    var referenceId: String? = null

    var notes: String? = null

    @Column(name = "exchange_rate")
    var exchangeRate: BigDecimal? = null

    fun getAmount() : BigDecimal {
        // sumo todos los movements,
        // resto todos los fees

        return BigDecimal.ZERO
    }

}
