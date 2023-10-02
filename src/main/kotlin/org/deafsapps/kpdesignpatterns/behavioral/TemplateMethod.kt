package org.deafsapps.kpdesignpatterns.behavioral

/**
 * Template Method is a behavioral design pattern that defines the skeleton of an algorithm in the superclass but lets
 * subclasses override specific steps of the algorithm without changing its structure.
 *
 * Problem:
 * Imagine you're working on a problem which is using some classes to handle distinct but rather similar scenarios. The
 * application keeps growing and some more classes are required, which make you feel you're repeating the same code way
 * too many times [DRY](https://en.wikipedia.org/wiki/Don%27t_repeat_yourself#:~:text=%22Don't%20repeat%20yourself%22,redundancy%20in%20the%20first%20place.).
 *
 * Solution:
 * The Template Method pattern suggests breaking down an algorithm into a series of steps, turning these steps into
 * methods, and putting a series of calls to these methods inside a single template method. The steps may either be
 * abstract, or have some default implementation.
 *
 * Android Example:
 * The Template Method design pattern is not very common at all in current Android development for two main reasons:
 * - There's little algorithm coding in a standard application nowadays
 * - This design pattern relies on inheritance, which is a bit cumbersome and scales badly, making it difficult to
 * maintain. The current trend is using composition (interface), so you can try 'Strategy' instead.
 */

fun main() {
    val zoo: List<Animal> = listOf(Zebra(), Whale(), Hummingbird())
    zoo.forEach { animal ->
        animal.goHunting()
    }
}

abstract class Animal {
    /*
     * This is the actual Template Method which invokes the steps involved in the process
     * It must not be overridden by any child class
     */
    fun goHunting() {
        move()
        eat()
        sleep()
    }
    /*
     * Although not mandatory, it makes sense the following functions are 'protected', so they can only be invoked by
     * the Template Method
     */
    protected open fun eat(): String = "Eating!"
    protected open fun sleep(): String = "Sleeping!"
    protected abstract fun move(): String
}

class Zebra : Animal() {
    override fun move(): String = "Walking!"
}

class Whale : Animal() {
    override fun sleep(): String = "Barely sleeping!"
    override fun move(): String = "Swimming!"
}

class Hummingbird : Animal() {
    override fun move(): String = "Flying!"
}
