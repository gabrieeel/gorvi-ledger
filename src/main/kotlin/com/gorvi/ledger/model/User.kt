package com.gorvi.ledger.model

import javax.persistence.*

@Entity(name = "ledger_user")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    var username: String = ""

}
