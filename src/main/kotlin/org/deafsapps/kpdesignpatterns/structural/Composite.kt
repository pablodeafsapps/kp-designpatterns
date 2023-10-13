package org.deafsapps.kpdesignpatterns.structural

/**
 * Composite is a structural design pattern that lets you compose objects into tree structures and then work with these
 * structures as if they were individual objects.
 *
 * Problem:
 * In software and the real world, there are objects with different structures. Some of them are primitive or simple and
 * some others consist of other objects (Children). Many of these children consist of other objects as well, and that is
 * ok. However, the problem appears when we need the same task to be done by the primitive and the container objects. In
 * this case, the primitive object will give us a result directly but the container object needs to access its children
 * and collect their results after checking their type, knowing that one child could be a container object.
 *
 * Solution:
 * Using the Composite pattern makes sense only when the core model of your app can be represented as a binary tree.
 * The Composite pattern suggests that you work with two types of entities: node/composite and leaf. The former can contain
 * either a node/composite or a leaf. Both entities share a common interface (component) which includes certain operations,
 * handled differently according to the type.
 *
 * Android Example:
 * The classic Android View system can be thought as a Composite design pattern example, since all widgets are nested in
 * sort of complex hierarchies, all of them implementing View, which will be the 'Component'.
 *
 * additional resource: https://narbase.com/2020/07/14/design-patterns-composite-with-kotlin-examples/
 */

fun main() {
    val menuItems = mutableListOf<MenuItem>()

    val burger = Meal(name = "Burger" , price = 100.0)
    val fries = Meal(name = "Fries", price = 50.0)
    val pizza = Meal(name = "Pizza", price = 200.0)
    val burgerOrder = MealOrder(name = "Burger combo meal")
    val awesomeOrder = MealOrder(name = "Awesome combo meal")

    burgerOrder.apply {
        addItem(burger)
        addItem(fries)
    }

    awesomeOrder.apply {
        addItem(burgerOrder)
        addItem(pizza)
    }

    menuItems.addAll(listOf(burger, pizza, burgerOrder, awesomeOrder))

    menuItems.forEach {
        it.printItem()
    }
}

// Component
interface MenuItem {
    fun getPrice(): Double
    fun printItem()
}

// Leaf
class Meal(private val name: String, private val price: Double) : MenuItem {

    override fun getPrice(): Double = price

    override fun printItem() {
        println("$name - $price")
    }
}

// Composite
class MealOrder(private val name: String) : MenuItem {

    private val items = mutableListOf<MenuItem>()

    override fun getPrice(): Double {
        var total = 0.0
        items.forEach {
            total += it.getPrice()
        }
        return total
    }

    override fun printItem() {
        println("$name - ${getPrice()}")
    }

    fun addItem(menuItem: MenuItem) {
        items.add(menuItem)
    }

    fun removeItem(menuItem: MenuItem) {
        items.remove(menuItem)
    }
}
