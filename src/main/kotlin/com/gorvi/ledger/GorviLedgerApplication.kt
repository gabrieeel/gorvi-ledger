package com.gorvi.ledger

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GorviLedgerApplication

fun main(args: Array<String>) {
	runApplication<GorviLedgerApplication>(*args)
}
