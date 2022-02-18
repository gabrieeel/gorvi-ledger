package com.gorvi.ledger.service

import com.gorvi.ledger.model.Account
import com.gorvi.ledger.persistence.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService(val accountRepository: AccountRepository) {

    fun getAccount(id: Long) : Account {
        return accountRepository.findById(id).orElseThrow { RuntimeException("Account does not exist") }

    }

}
