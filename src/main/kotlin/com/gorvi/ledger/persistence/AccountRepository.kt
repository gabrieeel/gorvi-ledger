package com.gorvi.ledger.persistence

import com.gorvi.ledger.model.Account
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account, Long> {
}
