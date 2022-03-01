package com.gorvi.ledger.model

import org.hibernate.annotations.TypeDef
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@TypeDef(name = "operation_type", typeClass = PostgreSQLEnumType::class)
class Operation {

    enum class Type {
        DEPOSIT, PURCHASE, WITHDRAWAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    var created: LocalDateTime = LocalDateTime.now()

    @Enumerated(EnumType.STRING)
    @org.hibernate.annotations.Type(type = "operation_type")
    var type: Type = Type.DEPOSIT;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operation")
    var movements: MutableList<Movement> = mutableListOf()

    @OneToOne
    @JoinColumn(name = "source_operation_id")
    var sourceOperation: Operation? = null

    @Column(name = "reference_id")
    var referenceId: String? = null

    var notes: String? = null

    fun getAmount() : BigDecimal {
        // sumo todos los movements,
        // resto todos los fees

        return BigDecimal.ZERO
    }

}

