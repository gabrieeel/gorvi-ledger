package com.gorvi.ledger.service

import com.gorvi.ledger.dto.DepositDTO
import com.gorvi.ledger.dto.WithdrawalDTO
import com.gorvi.ledger.model.Movement
import com.gorvi.ledger.model.Operation
import com.gorvi.ledger.persistence.MovementRepository
import com.gorvi.ledger.persistence.OperationRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

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

        balance.addMovements(listOf(movement))
        balanceService.update(balance)


//        movement.balance = balance
//        movement.operation = operation
//        movementRepository.save(movement)

        return operation
    }

    // TODO
    // fun createBaseOperation

    @Transactional
    fun createWithdrawal(accountId: Long, dto: WithdrawalDTO) : Operation {
        val account = accountService.getAccount(accountId)
        val balance = balanceService.getBalance(account, dto.currency)

        val operation = Operation()
        operation.type = Operation.Type.WITHDRAWAL
        operation.created = dto.created.toLocalDateTime()
        operation.notes = dto.notes
        operation.referenceId = dto.referenceId
        operationRepository.save(operation)

        val movement = Movement()
        movement.amountOut = dto.amount
        movement.balance = balance
        movement.operation = operation
        movementRepository.save(movement)
        balance.addMovements(listOf(movement))

        dto.fee?.let {
            val feeBalance = balanceService.getBalance(account, it.currency)
            val feeMovement = Movement()
            feeMovement.fee = true
            feeMovement.amountOut = it.amount
            feeMovement.balance = feeBalance
            feeMovement.operation = operation
            movementRepository.save(feeMovement)
            balance.addMovements(listOf(feeMovement))
        }


        balanceService.update(balance)

        return operation
    }

}
