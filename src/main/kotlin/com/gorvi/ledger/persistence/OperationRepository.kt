package com.gorvi.ledger.persistence

import com.gorvi.ledger.model.Currency
import com.gorvi.ledger.model.Operation
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface OperationRepository : CrudRepository<Operation, Long> {

    fun findAllByOrderByCreatedDesc() : List<Operation>

    @Query("select distinct o from Operation as o" +
            "   join o.movements as m" +
            "   join m.balance as b" +
            "   where o.type='PURCHASE'" +
            "   and m.amountIn > 0" +
            "   and b.currency = :currency" +
            "   order by o.created desc")
    fun findPurchaseOperationsByCurrency(currency: Currency) : List<Operation>
}
