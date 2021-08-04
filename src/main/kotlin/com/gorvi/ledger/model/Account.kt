package com.gorvi.ledger.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToMany

/**
 * Examples: Upwork, Binance
 */
@Entity
class Account {
    @Id
    var id: Long = 0
    var name: String = ""
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    var balances: List<Balance> = ArrayList<Balance>()
}
