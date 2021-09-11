package com.gorvi.ledger.service

import com.gorvi.ledger.dto.DepositDTO
import com.gorvi.ledger.dto.PurchaseDTO
import com.gorvi.ledger.model.Movement
import com.gorvi.ledger.model.Operation
import com.gorvi.ledger.persistence.MovementRepository
import com.gorvi.ledger.persistence.OperationRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class OperationService(val accountService: AccountService,
                       val balanceService: BalanceService,
                       val movementRepository: MovementRepository,
                       val operationRepository: OperationRepository) {

    fun createDeposit(accountId : Long, dto : DepositDTO): Operation {
        val account = accountService.getAccount(accountId)
        val balance = balanceService.getBalance(account, dto.currency)

        val movement = Movement()
        movement.amountIn = dto.amount
        movement.balance = balance

        val operation = Operation()
        operation.type = Operation.Type.DEPOSIT
        operation.created = dto.created.toLocalDateTime()
        operation.notes = dto.notes
        operation.referenceId = dto.referenceId
        operation.movements = listOf(movement)
        operationRepository.save(operation)

        movement.operation = operation
        // si tiene fee, deberia crear otro movement, x ahora no hay deposits con fees
        movementRepository.save(movement)

        balance.addMovement(movement)
        balanceService.update(balance)

        return operation
    }

}
