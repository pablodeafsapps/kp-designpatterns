package org.deafsapps.kpdesignpatterns.behavioral

/**
 * Memento is a behavioral design pattern that lets you save and restore the previous state of an object without
 * revealing the details of its implementation.
 * The purpose of the memento design pattern is to provide the ability to execute an undo action in order to restore an
 * object to a previous state. The Memento pattern is also known as Token.
 *
 * Problem:
 *
 *
 * Solution:
 * Memento design pattern comprises several entities:
 * - Originator: the object for which the state is to be saved. It creates the memento and uses it in the future to
 * undo.
 * - Memento: the object that is going to maintain the state of the originator. It is just a POJO.
 * - Caretaker: the object that keeps track of multiple memento. Like maintaining save points.
 * Thus, the originator will store the state information in the memento object and retrieve old state information when
 * it needs to backtrack. The memento just stores what the originator gives to it.
 * Memento object is unreachable for other objects in the application.
 *
 * Android Example:
 * There's no common use-case for the Memento design pattern in Android Development, although it can be easily be
 * combined with the Command pattern when implementing editors (text, images, etc.).
 * The implementation shown in the following example uses a 'Memento' interface to isolate the Caretaker from any other
 * originator and its state. The only way the 'Caretaker' (e.g. Editor) can track states is through the mementos it's
 * receiving when doing something.
 *
 * additional resource: https://chercher.tech/kotlin/momento-design-pattern-kotlin
 */

fun main() {
    val originator = ConcreteOriginator()
    val caretaker = Caretaker()
    println("Originator Current State: $originator")
    caretaker.`do`(memento = ConcreteMemento(state = State("Ironman"), originator = originator))
    println("Originator Current State: $originator")
    caretaker.`do`(memento = ConcreteMemento(state = State("Hulk"), originator = originator))
    println("Originator Current State: $originator")
    caretaker.`do`(memento = ConcreteMemento(state = State("Wonder Woman"), originator = originator))
    println("Originator Current State: $originator")
    println("Originator restoring to previous state...")
    caretaker.undo()
    println("Originator Current State: $originator")
    println("Again restoring to previous state...")
    caretaker.undo()
    println("Originator Current State: $originator")
    println("Again restoring to previous state...")
    caretaker.undo()
    println("Originator Current State: $originator")
}

data class State(private val value: String)

interface Memento {
    fun save()
    fun restore()
}

class ConcreteMemento(
    private val state: State,
    private val originator: ConcreteOriginator
) : Memento {

    override fun save() {
        originator.setState(state = state)
    }

    override fun restore() {
        originator.setState(state = state)
    }
}

class ConcreteOriginator {

    private var state: State? = null

    override fun toString(): String =
        if (state != null) "Current state: $state" else "No state available"

    fun setState(state: State) {
        this.state = state
    }
}

class Caretaker {

    private val statesList = ArrayList<Memento>()

    fun `do`(memento: Memento) {
        statesList.add(memento)
        memento.save()
    }

    fun undo() {
        statesList.removeLastOrNull()?.run {
            statesList.takeIf { it.isNotEmpty() }?.last()?.restore()
        }
    }
}
