package com.gorvi.ledger.persistence

import com.gorvi.ledger.model.Movement
import org.springframework.data.repository.CrudRepository

interface MovementRepository : CrudRepository<Movement, Long> {
}
