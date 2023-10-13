package org.deafsapps.kpdesignpatterns.behavioral

import java.lang.IllegalStateException

/**
 * Strategy is a behavioral design pattern that lets you define a family of algorithms, put each of them into a separate
 * class, and make their objects interchangeable.
 *
 * Problem:
 *
 * Solution:
 * The Strategy design pattern suggests taking a class that does something specific in a lot of different ways and
 * extract all of these algorithms into separate classes called strategies.
 * The original class, called context, must have a field for storing a reference to one of the strategies. The context
 * delegates the work to a linked strategy object instead of executing it on its own.
 *
 * Android Example:
 * The Strategy design pattern is extensively used in software development, including Android, of course. It relies on
 * composition, which is preferred over inheritance (like the Template design pattern does). For example, when using a
 * RecyclerView with different RecyclerView.ViewHolder entities involved, we normally make use of an interface
 */

fun main() {
    val context: AnimalSoundContext = AnimalSoundContext()

    context.apply {
        setStrategy(strategy = DogStrategy)
        makeSound().run(::println)
        setStrategy(strategy = CatStrategy)
        makeSound().run(::println)
        setStrategy(strategy = LionStrategy)
        makeSound().run(::println)
        setStrategy(strategy = BirdStrategy)
        makeSound().run(::println)
        setStrategy(strategy = null)
        runCatching { makeSound() }.run(::println)
    }
}

/*
 * The context defines the interface of interest to clients.
 * The context maintains a reference to one of the strategy objects. The context doesn't know the concrete class of a
 * strategy. It should work with all strategies via the strategy interface.
 *
 * Bear in mind we could avoid having a 'setStrategy' function and make 'soundStrategy' immutable, so it gets
 * defined when instantiating the context. Both approaches are valid. In other words:
 * class AnimalSoundContext(private val soundStrategy: AnimalSoundStrategy? = null)
 */
class AnimalSoundContext(private var soundStrategy: AnimalSoundStrategy? = null) {

    fun setStrategy(strategy: AnimalSoundStrategy?) {
        soundStrategy = strategy
    }

    fun makeSound(): String = soundStrategy?.execute() ?: throw IllegalStateException("Strategy not defined")
}


interface AnimalSoundStrategy {
    fun execute(): String
}

object DogStrategy : AnimalSoundStrategy {
    override fun execute(): String = "Woof!"
}

object CatStrategy : AnimalSoundStrategy {
    override fun execute(): String = "Meow!"
}

object LionStrategy : AnimalSoundStrategy {
    override fun execute(): String = "Roar!"
}

object BirdStrategy : AnimalSoundStrategy {
    override fun execute(): String = "Chirp!"
}
