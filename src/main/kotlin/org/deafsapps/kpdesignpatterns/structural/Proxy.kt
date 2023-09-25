package org.deafsapps.kpdesignpatterns.structural

import java.util.*

/**
 * Proxy is a structural design pattern that lets you provide a substitute or placeholder for another object. A proxy
 * controls access to the original object, allowing you to perform something either before or after the request gets
 * through to the original object.
 *
 * Problem:
 * Control access to an object which consumes a vast amount of system resources. You need it from time to time, but not
 * always. For instance, a database
 *
 * Solution:
 * The Proxy pattern suggests that you create a new proxy class with the **same interface** as an original service object.
 * Then you update your app so that it passes the proxy object to all the original object’s clients.
 *
 * Example:
 * Creating a validator for bank accounts which perform some additional checks to assess whether an operation can be
 * carried out or not.
 */

fun main() {
    val myBankAccount: BankAccount = TriodosBankAccount(money = 200.25)
    val bankAccountValidator: BankAccountValidator = BankAccountValidator(bankAccount = myBankAccount)
    val operationOneResult = Banker.makePayment(bankAccount = bankAccountValidator, amount = 210.34)
    println("Operation accomplished: $operationOneResult")
    val operationTwoResult = Banker.makePayment(bankAccount = bankAccountValidator, amount = 110.00)
    println("Operation accomplished: $operationTwoResult")
}

interface BankAccount {
    val number: String
    val bankIban: String
    val swiftCode: String
    val money: Double

    fun makePayment(amount: Double): Boolean
}

class TriodosBankAccount(override val money: Double) : BankAccount {
    override val number: String = "3456789"
    override val bankIban: String = "ES123456789"
    override val swiftCode: String = "TRIOESMMXXX"

    override fun makePayment(amount: Double): Boolean {
        println("Triodos bank account payment made")
        return true
    }
}

/**
 * This is the 'proxy class'. It has a reference field that points to a [BankAccount] object. After the proxy finishes
 * its processing, it passes the request to the service object.
 */
class BankAccountValidator(private val bankAccount: BankAccount) : BankAccount {
    override val money: Double = bankAccount.money
    override val number: String = bankAccount.number
    override val bankIban: String = bankAccount.bankIban
    override val swiftCode: String = bankAccount.swiftCode

    override fun makePayment(amount: Double): Boolean =
        if (bankAccount.isValid() && bankAccount.hasEnoughCredit(amount = amount)) {
            bankAccount.makePayment(amount = amount)
        } else {
            println("Error: payment not made!!")
            false
        }

    private fun BankAccount.isValid(): Boolean = bankIbanIsCorrect() && swiftCodeIsValidated()

    private fun BankAccount.bankIbanIsCorrect(): Boolean = bankIban.contains("""${bankAccount.number}$""".toRegex())

    private fun BankAccount.swiftCodeIsValidated(): Boolean =
        swiftCode == swiftCode.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    private fun BankAccount.hasEnoughCredit(amount: Double) = amount <= money
}

object Banker {
    fun makePayment(bankAccount: BankAccountValidator, amount: Double): Boolean =
        bankAccount.makePayment(amount = amount)
}
