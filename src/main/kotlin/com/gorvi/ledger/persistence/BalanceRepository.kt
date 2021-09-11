package com.gorvi.ledger.persistence

import com.gorvi.ledger.model.Account
import com.gorvi.ledger.model.Balance
import com.gorvi.ledger.model.Currency
import org.springframework.data.repository.CrudRepository
import java.util.*

interface BalanceRepository : CrudRepository<Balance, Long> {

    fun findByAccountEqualsAndCurrencyEquals(account: Account, currency: Currency) : Optional<Balance>

    fun findAllByCurrencyEquals(currency: Currency) : List<Balance>
}
