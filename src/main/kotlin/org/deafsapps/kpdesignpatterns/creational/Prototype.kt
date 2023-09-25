package org.deafsapps.kpdesignpatterns.creational

import kotlin.math.PI
import kotlin.math.pow

/**
 * Prototype is a creational design pattern that lets you copy existing objects without making your code dependent on
 * their classes.
 *
 * Problem:
 * If we need to create an object out of another one, we'll need to create a new object of the same class. Then we'll
 * have to go through all the fields of the original object and copy their values over to the new object. However, not
 * all objects can be copied that way because some of the object’s fields may be private and not visible from outside.
 *
 * Solution:
 * Simply delegate the "copy" operation to the object to be cloned.
 *
 * > [!IMPORTANT]
 * > This design pattern is less relevant in Kotlin since there are 'data class' entities which provide copies out of
 * the box.
 */

fun main() {

    val shapeRepository: MutableList<Shape> = mutableListOf()

    val circleBlueprint: Shape = Circle(color = "red", radius = 2.0)
    val squareBlueprint: Shape = Square(color = "green", side = 3.2)

    repeat(5) {
        shapeRepository.add(circleBlueprint.toClone())
        shapeRepository.add(squareBlueprint.toClone())
    }

    println(shapeRepository)
}

/**
 * This interface complies with the 'Prototype' design pattern; 'clone()' naming couldn't be used due to some odd bug.
 * It could also be called 'ClonablePrototype', for instance.
 */
interface Clonable<T> {
    fun toClone(): T
}

abstract class Shape : Clonable<Shape> {
    abstract val color: String
    abstract val area: Double
    abstract val perimeter: Double
}

class Square(override val color: String, private val side: Double) : Shape() {

    override val area: Double
        get() {
            require(side >= 0) { "'side' must be a positive number" }
            return side * side
        }
    override val perimeter: Double
        get() {
            require(side >= 0) { "'side' must be a positive number" }
            return 4 * side
        }

    private constructor(square: Square) : this(color = square.color, side = square.side)

    override fun toClone(): Shape = Square(square = this)

    override fun toString(): String = "This shape is a square: area = $area, perimeter = $perimeter"
}

class Circle(
    override val color: String,
    private val radius: Double
) : Shape() {

    override val area: Double
        get() {
            require(radius >= 0) { "'radius' must be a positive number" }
            return PI * radius.pow(2)
        }
    override val perimeter: Double
        get() {
            require(radius >= 0) { "'radius' must be a positive number" }
            return 2 * PI * radius
        }

    private constructor(circle: Circle) : this(color = circle.color, radius = circle.radius)

    override fun toClone(): Shape = Circle(circle = this)

    override fun toString(): String = "This shape is a circle: area = $area, perimeter = $perimeter"
}
