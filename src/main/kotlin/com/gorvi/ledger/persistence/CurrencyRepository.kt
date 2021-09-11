package com.gorvi.ledger.persistence

import com.gorvi.ledger.model.Currency
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CurrencyRepository : CrudRepository<Currency, Long> {

    fun findByCodeEquals(code: String) : Optional<Currency>

}
