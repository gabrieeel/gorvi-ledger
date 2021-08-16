package com.gorvi.ledger.model

import java.util.*
import javax.persistence.*

/**
 * The account of the logged in user in some fintech company
 */
@Entity
class Account {
    @Id
    var id: Long = 0

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    var balances: List<Balance> = ArrayList<Balance>()

    @OneToOne
    @JoinColumn(name = "ledger_user", referencedColumnName = "id")
    var user: User = User()

    @OneToOne
    @JoinColumn(name = "company", referencedColumnName = "id")
    var company: Company = Company();

}
