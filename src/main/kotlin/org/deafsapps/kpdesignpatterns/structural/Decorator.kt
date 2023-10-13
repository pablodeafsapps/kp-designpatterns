package org.deafsapps.kpdesignpatterns.structural

/**
 * Decorator is a structural design pattern that lets you attach new behaviors to objects by placing them inside special
 * wrapper objects that contain the behaviors.
 *
 * Problem:
 * When instantiating complex objects which may involve may different configuration attributes (size, color, audience,...)
 * you may think of creating many subclasses addressing each option. This solution, obviously, scales badly and it's
 * difficult to maintain.
 *
 * Solution:
 * A Decorator pattern can be used to attach additional responsibilities to an object either statically or dynamically.
 * A Decorator provides an enhanced interface to the original object.
 *
 * Android Example:
 * In modern Android Development, the Decorator design pattern is heavily used in Jetpack Compose, specifically on
 * Composable Modifiers. These entities define the Composable and can be seamlessly concatenated and applied sequentially.
 */

fun main() {
    val triangle: Form = Triangle(base = 2f, height = 3f)

    val enlargedTriangle: Form = EnlargeFormDecorator(Triangle(base = 2f, height = 3f))

    val redEnlargedTriangle: Form = RedFormDecorator(EnlargeFormDecorator(Triangle(base = 2f, height = 3f)))

    val redRectangle: Form = RedFormDecorator(Rectangle(base = 4f, height = 5f))

    val enlargedRedRectangle: Form = EnlargeFormDecorator(RedFormDecorator(Rectangle(base = 4f, height = 5f)))

    println("Triangle with normal border")
    triangle.draw()

    println("\nTriangle enlarged")
    enlargedTriangle.draw()

    println("\nTriangle of red border and enlarged")
    redEnlargedTriangle.draw()

    println("\nRectangle of red border")
    redRectangle.draw()

    println("\nRectangle enlarged and red bordered")
    enlargedRedRectangle.draw()
}

interface Form {

    var area: Float
    var borderColor: String

    fun draw()
}

class Rectangle(base: Float, height: Float) : Form {

    override var area: Float = base * height
    override var borderColor: String = "transparent"

    override fun draw() {
        println("Form: Rectangle - border color = $borderColor, area = $area")
    }
}

class Triangle(base: Float, height: Float) : Form {

    override var area: Float = base * height / 2
    override var borderColor: String = "transparent"

    override fun draw() {
        println("Form: Triangle - border color = $borderColor, area = $area")
    }
}

class RedFormDecorator(private val decoratedForm: Form) : Form by decoratedForm {

    override fun draw() {
        setRedBorder()
        decoratedForm.draw()
    }

    private fun setRedBorder() {
        decoratedForm.borderColor = "red"
    }
}

class EnlargeFormDecorator(private val decoratedForm: Form) : Form by decoratedForm {

    override fun draw() {
        scaleUpForm()
        decoratedForm.draw()
    }

    private fun scaleUpForm() {
        decoratedForm.area *= 2
    }
}
