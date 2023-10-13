package org.deafsapps.kpdesignpatterns.structural

/**
 * Bridge is a structural design pattern that lets you split a large class or a set of closely related classes into two
 * separate hierarchies—abstraction and implementation—which can be developed independently of each other.
 *
 * Problem:
 * When
 *
 * Solution:
 * The Bridge pattern attempts to solve the problem raised when you try to extend a class hierarchy to add some new
 * features, by switching from inheritance to the object composition. Thus, you extract one of the dimensions into a
 * separate class hierarchy, so that the original classes will reference an object of the new hierarchy, instead of
 * having all of its state and behaviors within one class.
 *
 *
 */

fun main() {
    val redCircle: Shape = Circle(100, 100, 10, RedCircleAPI())
    val greenCircle: Shape = Circle(100, 100, 10, GreenCircleAPI())

    redCircle.draw()
    greenCircle.draw()
}


interface DrawCircleAPI {
    fun drawCircle(radius: Int, x: Int, y: Int)
}

class RedCircleAPI : DrawCircleAPI {
    override fun drawCircle(radius: Int, x: Int, y: Int) {
        println("Drawing Circle[color: red, radius: $radius, x: $x, $y]")
    }
}

class GreenCircleAPI : DrawCircleAPI {
    override fun drawCircle(radius: Int, x: Int, y: Int) {
        println("Drawing Circle[color: green, radius: $radius, x: $x, $y]")
    }
}

abstract class Shape(protected val drawCircleAPI: DrawCircleAPI) {
    abstract fun draw()
}

class Circle(private val x: Int, private val y: Int, private val radius: Int, drawCircleAPI: DrawCircleAPI) : Shape(drawCircleAPI) {
    override fun draw() {
        drawCircleAPI.drawCircle(radius, x, y)
    }
}
