package org.deafsapps.kpdesignpatterns.creational

/**
 * Builder is a creational design pattern that lets you construct complex objects step by step. The pattern allows you
 * to produce different types and representations of an object using the same construction code.
 *
 * Problem:
 * Imagine a complex object that requires laborious, step-by-step initialization of many fields and nested objects. Such
 * initialization code is usually buried inside a monstrous constructor with lots of parameters. Or even worse: scattered
 * all over the client code.
 *
 * Solution:
 * The Builder pattern suggests that you extract the object construction code out of its own class and move it to separate
 * objects called builders.
 *
 * Android Example:
 * The Builder design pattern is regularly used in Android Development, for instance when creating and configuring
 * notifications
 */

fun main() {
    val cheeseBurger: Hamburger = Hamburger.Builder(name = "Cheeseburger")
        .meat(meatType = MeatType.BEEF)
        .hasCheese()
        .hasOnion()
        .build()
    val chickenBurger: Hamburger = Hamburger.Builder(name = "Chicken burger")
        .meat(meatType = MeatType.CHICKEN)
        .hasCheese()
        .hasLettuce()
        .hasTomato()
        .hasOnion()
        .build()

    listOf(cheeseBurger, chickenBurger).run(::println)
}

class Hamburger private constructor(
    private val name: String,
    private val meatType: MeatType,
    private val hasCheese: Boolean,
    private val hasLettuce: Boolean,
    private val hasTomato: Boolean,
    private val hasOnion: Boolean
) {

    override fun toString(): String = "$name: $meatType hamburger with " +
            (if (hasCheese) "cheese" else "") +
            (if (hasLettuce) ", lettuce" else "") +
            (if (hasTomato) ", tomato" else "") +
            (if (hasOnion) ", onion" else "")

    class Builder(
        private val name: String,
        private var meatType: MeatType = MeatType.BEEF,
        private var hasCheese: Boolean = false,
        private var hasLettuce: Boolean = false,
        private var hasTomato: Boolean = false,
        private var hasOnion: Boolean = false
    ) {

        fun meat(meatType: MeatType) = apply { this.meatType = meatType }
        fun hasCheese() = apply { this.hasCheese = true }
        fun hasLettuce() = apply { this.hasLettuce = true }
        fun hasTomato() = apply { this.hasTomato = true }
        fun hasOnion() = apply { this.hasOnion = true }

        fun build(): Hamburger =
            Hamburger(
                name = name,
                meatType = meatType,
                hasCheese = hasCheese,
                hasLettuce = hasLettuce,
                hasTomato = hasTomato,
                hasOnion = hasOnion
            )
    }
}

enum class MeatType {
    CHICKEN, BEEF, LAMB, PORK
}
