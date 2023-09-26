package org.deafsapps.kpdesignpatterns.behavioral

/**
 * Chain of Responsibility is a behavioral design pattern that lets you pass requests along a chain of handlers. Upon
 * receiving a request, each handler decides either to process the request or to pass it to the next handler in the chain.
 * This pattern decouples sender and receiver of a request based on type of request.
 *
 * Problem:
 * When you need to process a request through several operations sequentially, but not all of them are mandatory, you
 * may be tempted to decide which operations to run based on some kind of logic based on the request type. This may work
 * fine initially, but it'll bring quite some overhead when your solution scales and more request types need to be
 * considered
 *
 * Solution:
 * The Chain of Responsibility design pattern allows you to create a chain (tree) in which each of the included entities
 * can decide autonomously whether to process the request or not and then pass it to the next handler of the chain.
 *
 * Android Example:
 * As depicted below, it's quite common to log what the app is doing at certain times. We could implement a logger which
 * abstracts out where messages should be written to according to a certain level of relevance.
 *
 * additional resource: https://www.tutorialspoint.com/design_pattern/chain_of_responsibility_pattern.htm
 */

fun main() {
    val messageLogger: AbstractLogger = getLoggerChain()

    messageLogger.logMessage(level = AbstractLogger.Level.INFO, message = "This is an information message.")
    messageLogger.logMessage(level = AbstractLogger.Level.DEBUG, message = "This is a debug level message.")
    messageLogger.logMessage(level = AbstractLogger.Level.ERROR, message = "This is an error message.")
}

private fun getLoggerChain(): AbstractLogger = ConsoleLogger(
    level = AbstractLogger.Level.INFO, nextLogger = FileLogger(
        level = AbstractLogger.Level.DEBUG, nextLogger = ErrorLogger(
            level = AbstractLogger.Level.ERROR
        )
    )
)

abstract class AbstractLogger {
    protected abstract val level: Level
    protected abstract val nextLogger: AbstractLogger?

    protected abstract fun write(message: String)

    fun logMessage(level: Level, message: String) {
        if (this.level.grade <= level.grade) {
            write(message)
        }
        nextLogger?.logMessage(level = level, message = message)
    }

    enum class Level(val grade: Int) {
        INFO(0), DEBUG(1), ERROR(2)
    }
}

class ConsoleLogger(override val level: Level, override val nextLogger: AbstractLogger? = null) : AbstractLogger() {
    override fun write(message: String) {
        println("Standard Console::Logger: $message")
    }
}

class ErrorLogger(override val level: Level, override val nextLogger: AbstractLogger? = null) : AbstractLogger() {
    override fun write(message: String) {
        println("Error Console::Logger: $message")
    }
}

class FileLogger(override val level: Level, override val nextLogger: AbstractLogger? = null) : AbstractLogger() {
    override fun write(message: String) {
        println("File::Logger: $message")
    }
}
