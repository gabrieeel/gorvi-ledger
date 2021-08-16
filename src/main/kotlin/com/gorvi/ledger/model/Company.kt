package com.gorvi.ledger.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Company {
    @Id
    var id: Long = 0

    var name: String = ""
}
