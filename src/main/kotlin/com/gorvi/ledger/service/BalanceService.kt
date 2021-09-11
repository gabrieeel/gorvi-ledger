package com.gorvi.ledger.service

import com.gorvi.ledger.exception.InsufficientBalanceException
import com.gorvi.ledger.model.Account
import com.gorvi.ledger.model.Balance
import com.gorvi.ledger.model.Currency
import com.gorvi.ledger.model.Transaction
import com.gorvi.ledger.persistence.BalanceRepository
import com.gorvi.ledger.persistence.CurrencyRepository
import com.gorvi.ledger.persistence.TransactionRepository
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.math.BigDecimal
import java.util.Optional

@Service
class BalanceService(val currencyRepository: CurrencyRepository,
                     val balanceRepository: BalanceRepository) {

    fun update(balance: Balance) {
        balanceRepository.save(balance)
    }

    fun getBalance(account: Account, currency: String): Balance {
        var currencyModel = currencyRepository.findByCodeEquals(currency).orElseThrow { RuntimeException("Currency does not exist") }
        return balanceRepository.findByAccountEqualsAndCurrencyEquals(account, currencyModel).orElseThrow{ RuntimeException("Balance does not exist") }
    }


}
