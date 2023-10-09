package org.deafsapps.kpdesignpatterns.structural

import org.deafsapps.kpdesignpatterns.structural.FlyweightCarFactory.getRaceCar

/**
 * Flyweight is a structural design pattern that lets you fit more objects into the available amount of RAM by sharing
 * common parts of state between multiple objects instead of keeping all the data in each object.
 *
 * Problem:
 * Designing objects to the lowest levels of system “granularity” promote flexibility in the application but as a good
 * programmer you think about performance of the application and resource required to run it. Creating objects requires
 * more memory to store them and CPU cycles to initialise the objects.
 *
 * Solution:
 * The Flyweight pattern suggests that you stop storing the extrinsic state inside the object. Instead, you should pass
 * this state to specific methods which rely on it. Only the intrinsic state stays within the object, letting you reuse
 * it in different contexts. As a result, you’d need fewer of these objects since they only differ in the intrinsic state,
 * which has much fewer variations than the extrinsic.
 * For instance, imagine you need 1000 car objects in an online car racing game, and the car objects vary only in their
 * current position on the racetrack. Instead of creating 1000 car objects and adding more as users join in, you can
 * create a single car object with common data and a client object to maintain the state (position of a car).
 *
 * Further Details:
 * It's important to understand that the Flyweight design pattern categorises the internal state of an entity into:
 * - Intrinsic data: information that is stored in the flyweight object. The information is independent of the
 * flyweight’s context, thereby making it sharable. While applying the pattern, you take all the objects that have the
 * same intrinsic data and replace them with a single flyweight.
 * - Extrinsic data: information that depends on the flyweight’s context and hence cannot be shared. Client objects
 * stores and computes extrinsic data and passes it to the flyweight when it needs it.
 *
 * Android Example:
 * There's not any clear use-case in classic Android Development where Flyweight design pattern fits. However, it may
 * make sense to use it when a "heavy" class is instantiated many times and some info is shared across all of them.
 */

fun main() {
    val raceCars = arrayOf(
        RaceCarClient("Midget"),
        RaceCarClient("Midget"),
        RaceCarClient("Midget"),
        RaceCarClient("Sprint"),
        RaceCarClient("Sprint"),
        RaceCarClient("Sprint")
    )
    raceCars[0].moveCar(29, 3112)
    raceCars[1].moveCar(39, 2002)
    raceCars[2].moveCar(49, 1985)
    raceCars[3].moveCar(59, 2543)
    raceCars[4].moveCar(69, 2322)
    raceCars[5].moveCar(79, 2135)
    /*Output and observe the number of instances created*/
    println("Midget Car Instances: " + FlyweightMidgetCar.num)
    println("Sprint Car Instances: " + FlyweightSprintCar.num)
}

abstract class RaceCar {
    /*
     * Intrinsic data is stored and shared in the Flyweight object
     */
    abstract val name: String?
    abstract val speed: Float
    abstract val horsePower: Float
    /**
     * Extrinsic data is stored or computed by client objects, and passed to the Flyweight object
     */
    abstract fun moveCar(currentX: Int, currentY: Int, newX: Int, newY: Int)
}

class FlyweightMidgetCar(
    override val name: String?,
    override val speed: Float,
    override val horsePower: Float
) : RaceCar() {

    init {
        num++
    }

    /*
     * This method accepts car location (extrinsic). No reference to current
     * or new location is maintained inside the flyweight implementation
     */
    override fun moveCar(currentX: Int, currentY: Int, newX: Int, newY: Int) {
        println("New location of $name is X$newX - Y$newY")
    }

    companion object {
        /*
         * Track number of flyweight instantiation
         */
        var num = 0
    }
}

class FlyweightSprintCar(
    override val name: String?,
    override val speed: Float,
    override val horsePower: Float
) : RaceCar() {

    init {
        num++
    }

    /*
     * This method accepts car location (extrinsic). No reference to current
     * or new location is maintained inside the flyweight implementation
     */
    override fun moveCar(currentX: Int, currentY: Int, newX: Int, newY: Int) {
        println("New location of $name is X$newX - Y$newY")
    }

    companion object {
        /*
         * Track number of flyweight instantiation
         */
        var num = 0
    }
}

/*
 * To manage our flyweight objects, we need a factory – a class that uses a pool (implemented as some data structure) to
 * store flyweight objects.
 * When client request a flyweight object for the first time, the factory instantiates a flyweight, initializes it with
 * its intrinsic data, and puts it in the pool, before returning the flyweight to the client.
 * For subsequent requests, the factory retrieves the flyweight from the pool and returns it to the client
 */
object FlyweightCarFactory {

    private val flyweights: MutableMap<String, RaceCar> = HashMap()

    /*
     * If key exist, return flyweight from Map
     */
    fun getRaceCar(key: String): RaceCar? = if (flyweights.containsKey(key)) {
        flyweights[key]
    } else {
        when (key) {
            "Midget" -> {
                FlyweightMidgetCar(
                    name = "Midget Car",
                    speed = 140f,
                    horsePower = 400f
                )
            }
            "Sprint" -> {
                FlyweightSprintCar(
                    name = "Sprint Car",
                    speed = 160f,
                    horsePower = 1000f
                )
            }
            else -> throw IllegalArgumentException("Unsupported car type.")
        }.also { raceCar ->
            flyweights[key] = raceCar
        }
    }
}

/*
 * We will also need a client class, 'RaceCarClient', that will retrieve a flyweight (already initialized with its
 * intrinsic data) from the factory and pass the extrinsic data to the flyweight. In the context of our example, the
 * client will manage and pass the position (extrinsic data) of a car by calling the moveCar(int currentX, int currentY,
 * int newX ,int newY) method of the flyweight.
 * Generally speaking, this class acts like a wrapper around any Flyweight. The idea is getting many instances of this
 * entity rather than Flyweights, since this lightweight class contains the variable information of the entity.
 */
class RaceCarClient(name: String) {

    private val raceCar: RaceCar?

    /**
     * The extrinsic state of the flyweight is maintained by the client
     */
    private var currentX = 0
    private var currentY = 0

    init {
        /**
         * Ask factory for a RaceCar (in practice, a flyweight)
         */
        raceCar = getRaceCar(name)
    }

    fun moveCar(newX: Int, newY: Int) {
        /**
         * Car movement is handled by the flyweight object
         */
        if (raceCar != null) {
            raceCar.moveCar(
                currentX = currentX,
                currentY = currentY,
                newX = newX,
                newY = newY
            )
            currentX = newX
            currentY = newY
        }
    }
}
