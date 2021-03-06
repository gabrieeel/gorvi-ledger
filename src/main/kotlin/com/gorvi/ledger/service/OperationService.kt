package com.gorvi.ledger.service

import com.gorvi.ledger.dto.DepositDTO
import com.gorvi.ledger.dto.PurchaseDTO
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
        operation.movements = mutableListOf(movement)
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
        balanceService.update(balance)

        operation.movements = mutableListOf(movement)

        dto.fee?.let {
            val feeBalance = balanceService.getBalance(account, it.currency)
            val feeMovement = Movement()
            feeMovement.fee = true
            feeMovement.amountOut = it.amount
            feeMovement.balance = feeBalance
            feeMovement.operation = operation
            movementRepository.save(feeMovement)

            feeBalance.addMovements(listOf(feeMovement))
            balanceService.update(feeBalance)

            operation.movements.add(feeMovement)
        }

        return operation
    }

    @Transactional
    fun createPurchase(accountId: Long, dto: PurchaseDTO) : Operation {
        val account = accountService.getAccount(accountId)

        val operation = Operation()
        operation.type = Operation.Type.PURCHASE
        operation.created = dto.created.toLocalDateTime()
//        operation.notes = dto.notes
        operation.referenceId = dto.referenceId
        operationRepository.save(operation)

        val incomingBalance = balanceService.getBalance(account, dto.purchased.currency)
        val outgoingBalance = balanceService.getBalance(account, dto.spent.currency)

        val incomingMovement = Movement()
        incomingMovement.amountIn = dto.purchased.amount
        incomingMovement.balance = incomingBalance
        incomingMovement.operation = operation
        movementRepository.save(incomingMovement)
        incomingBalance.addMovements(listOf(incomingMovement))
        balanceService.update(incomingBalance)

        val outgoingMovement = Movement()
        outgoingMovement.amountOut = dto.spent.amount
        outgoingMovement.balance = outgoingBalance
        outgoingMovement.operation = operation
        movementRepository.save(outgoingMovement)
        outgoingBalance.addMovements(listOf(outgoingMovement))
        balanceService.update(outgoingBalance)

        dto.fee?.let {
            val feeBalance = balanceService.getBalance(account, it.currency)
            val feeMovement = Movement()
            feeMovement.fee = true
            feeMovement.amountOut = it.amount
            feeMovement.balance = feeBalance
            feeMovement.operation = operation
            movementRepository.save(feeMovement)
            feeBalance.addMovements(listOf(feeMovement))

            balanceService.update(feeBalance)
        }

        return operation
    }


    fun getAll() : List<Operation> {
        return operationRepository.findAllByOrderByCreatedDesc()
    }

}
