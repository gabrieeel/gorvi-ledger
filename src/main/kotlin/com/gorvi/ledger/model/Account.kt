package com.gorvi.ledger.model

import java.util.*
import javax.persistence.*

/**
 * The account of the logged in user in some fintech company
 */
@Entity
class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    var balances: List<Balance> = ArrayList<Balance>()

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User = User()

    @OneToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    var company: Company = Company();

}
