package com.gorvi.ledger.service

import com.gorvi.ledger.dto.AmountDTO
import com.gorvi.ledger.dto.DepositDTO
import com.gorvi.ledger.dto.WithdrawalDTO
import com.gorvi.ledger.model.Account
import com.gorvi.ledger.model.Balance
import com.gorvi.ledger.model.Operation
import com.gorvi.ledger.persistence.CurrencyRepository
import com.gorvi.ledger.persistence.MovementRepository
import com.gorvi.ledger.persistence.OperationRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.OffsetDateTime

class OperationServiceTest {
    val accountService: AccountService = mockk()
    val balanceService: BalanceService = mockk()
    val currencyRepository: CurrencyRepository = mockk()
    val movementRepository: MovementRepository = mockk()
    val operationRepository: OperationRepository = mockk()

    val operationService = OperationService(accountService, balanceService, currencyRepository, movementRepository, operationRepository)

    @Test
    fun withdrawal_with_fee() {
        var account = Account()
        var balance = Balance()

        var dto = WithdrawalDTO()
        dto.amount = BigDecimal.TEN
        dto.created = OffsetDateTime.now()
        dto.currency = "USD"
        dto.referenceId = "r123"
        dto.fee = AmountDTO()
        dto.fee?.amount = BigDecimal.ONE
        dto.fee?.currency = "USD"

        //given
        every { accountService.getAccount(1) } returns account
        every { balanceService.getBalance(account, "USD") } returns balance
        every { operationRepository.save(any())} returns mockk()
        every { movementRepository.save(any())} returns mockk()
        every { balanceService.update(any())} returns mockk()

        //when
        val result = operationService.createWithdrawal(1, dto)

        //then
        verify(exactly = 1) { accountService.getAccount(1) }

        assertEquals(dto.created.toLocalDateTime(), result.created)
        assertEquals(Operation.Type.WITHDRAWAL, result.type)
        assertEquals(2, result.movements.size)
        assertEquals(dto.amount, result.movements[0].amountOut)
        assertEquals(dto.fee!!.amount, result.movements[1].amountOut)
        assertFalse(result.movements[0].fee)
        assertTrue(result.movements[1].fee)
    }

//    @Test
//    fun withdrawal_with_fee_in_another_currency() {
//        TODO("is this possible?")
//    }

    @Test
    fun deposit_with_no_fee() {
        var account = Account()
        var balance = Balance()

        var dto = DepositDTO()
        dto.amount = BigDecimal.TEN
        dto.created = OffsetDateTime.now()
        dto.currency = "USD"
        dto.referenceId = "r123"

        //given
        every { accountService.getAccount(1) } returns account
        every { balanceService.getBalance(account, "USD") } returns balance
        every { operationRepository.save(any())} returns mockk()
        every { movementRepository.save(any())} returns mockk()
        every { balanceService.update(any())} returns mockk()

        //when
        val result = operationService.createDeposit(1, dto)

        //then
        verify(exactly = 1) { accountService.getAccount(1) }

        assertEquals(dto.created.toLocalDateTime(), result.created)
        assertEquals(Operation.Type.DEPOSIT, result.type)
        assertEquals(1, result.movements.size)
        assertEquals(dto.amount, result.movements.get(0).amountIn)
        assertFalse(result.movements.get(0).fee)
    }

//    @Test
//    fun deposit_with_fee() {
//
//        ejemplo
//        deposito 100.8, pero en la cuenta solo me quedan 100
//
//        validar que operation.movements sean 2, uno sea fee
//
//    }
}