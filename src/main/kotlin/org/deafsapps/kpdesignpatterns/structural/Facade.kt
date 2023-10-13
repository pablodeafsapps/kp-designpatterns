package org.deafsapps.kpdesignpatterns.structural

/**
 * Facade is a structural design pattern that provides a simplified interface to a library, a framework, or any other
 * complex set of classes.
 *
 * Problem:
 * Imagine that you must make your code work with a broad set of objects that belong to a sophisticated library or
 * framework. Ordinarily, you’d need to initialize all of those objects, keep track of dependencies, execute methods in
 * the correct order, and so on. As a result, the business logic of your classes would become tightly coupled to the
 * implementation details of 3rd-party classes, making it hard to comprehend and maintain.
 *
 * Solution:
 * A facade is a class that provides a simple interface to a complex subsystem which contains lots of moving parts. A
 * facade might provide limited functionality in comparison to working with the subsystem directly. However, it includes
 * only those features that clients really care about.
 *
 * Android Example:
 * Undoubtedly, use-cases are entities which make use of the Facade design pattern. Normally, the client (generally a
 * view-model) simply executes the use-case, with no insights about what's going on in it. Internally, use-cases may
 * use several repositories (subsystems) to orchestrate some logic, but that's irrelevant to the client, since it only
 * expects a result out of it.
 */

fun main() {
    val robot: Robot<Dish> = CookRobot()
    val dish = robot.run()
    println(dish)
}

interface Robot<T> {
    fun run(): T
}

class CookRobot : Robot<Dish> {

    private val chopper: Chopper = Chopper
    private val frier: Frier = Frier
    private val boiler: Boiler = Boiler
    private val oven: Oven = Oven

    override fun run(): Dish {
        chopper.chop()
        boiler.boil()
        frier.fry()
        oven.bake()
        return Dish(name = "Fried potatoes", calories = 210.0f)
    }

}

data class Dish(private val name: String, private val calories: Float)

object Chopper {
    fun chop(): String = "Chopping!"
}

object Boiler {
    fun boil(): String = "Boiling!"
}

object Frier {
    fun fry(): String = "Frying!"
}

object Oven {
    fun bake(): String = "Baking!"
}
