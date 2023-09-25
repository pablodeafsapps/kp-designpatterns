package org.deafsapps.kpdesignpatterns.creational

import java.lang.IllegalStateException

/**
 * Factory Method is a creational design pattern that provides an interface for creating objects in a superclass, but
 * allows subclasses to alter the type of objects that will be created.
 * The Factory Method is used to create objects without specifying the exact class of object that will be created. This
 * pattern is useful when we need to decouple the creation of an object from its implementation.
 *
 * Problem:
 * When we have to deal with certain related objects following an inheritance relationship, it's better to abstract out
 * how these objects get instantiated, so that we avoid coupling that code with the objects themselves. Bear in mind
 * some more child classes may be required in the future.
 *
 * Solution:
 * The Factory Method design pattern suggests that direct object construction calls can be replaced with calls to a
 * special **factory method**. Objects returned by a factory method are often referred to as products.
 */

fun main() {
    val customButtonFactory: CustomButtonFactory = CustomButtonFactory()

    val customButtons: ArrayList<CustomButton> = arrayListOf<CustomButton>().apply {
        add(customButtonFactory.createView(type = CustomButtonFactory.Square))
        add(customButtonFactory.createView(type = CustomButtonFactory.Round))
    }

    println(customButtons.map { cb -> cb.getRenderInformation() })
}

/**
 * It's common to define a factory interface to model how the factory method is presented. This actually is the foundation
 * of the Abstract Factory design pattern.
 */
interface ViewFactory<T> {
    fun createView(type: ViewType): T

    // We've decided to add a certain hierarchy here to facilitate factory's work
    sealed interface ViewType
}

class CustomButtonFactory : ViewFactory<CustomButton> {
    override fun createView(type: ViewFactory.ViewType): CustomButton =
        when (type) {
            Square -> SquareButton()
            Round -> RoundButton()
            else -> throw IllegalStateException()
        }

    object Square : ViewFactory.ViewType
    object Round : ViewFactory.ViewType
}

abstract class CustomButton {
    protected abstract val shapeDescription: String

    fun getRenderInformation(): String = shapeDescription
}

class RoundButton : CustomButton() {
    override val shapeDescription: String = "This is a round-shaped button"
}

class SquareButton : CustomButton() {
    override val shapeDescription: String = "This is a square-shaped button"
}
