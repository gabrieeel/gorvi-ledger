package com.gorvi.ledger.persistence

import com.gorvi.ledger.model.Operation
import org.springframework.data.repository.CrudRepository

interface OperationRepository : CrudRepository<Operation, Long>{
}
