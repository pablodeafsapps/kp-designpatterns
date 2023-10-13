package org.deafsapps.kpdesignpatterns.behavioral

import java.lang.IllegalStateException

/**
 * Mediator is a behavioral design pattern that lets you reduce chaotic dependencies between objects. The pattern
 * restricts direct communications between the objects and forces them to collaborate only via a mediator object.
 *
 * Problem:
 * When working in a feature upgrade you may find yourself in a situation where dependency relationships are too and
 * rather complex, which makes the whole solution difficult to work with and almost impossible to upgrade nicely.
 *
 * Solution:
 * The Mediator pattern suggests that you should cease all direct communication between the components which you want to
 * make independent of each other. Instead, these components must collaborate indirectly, by calling a special mediator
 * object that redirects the calls to appropriate components. As a result, the components depend only on a single mediator
 * class instead of being coupled to dozens of their colleagues.
 *
 * Android Example:
 * In current Android development, it's rather common to use ViewModels which, to some extent, are kind of mediators with
 * respect to any kind of available View.
 */

fun main() {
    val textMediator: EditorMediator = TextEditorMediator()
    val copyComponent = CopyComponent(mediator = textMediator)
    val pasteComponent = PasteComponent(mediator = textMediator)

    copyComponent.hover()
    pasteComponent.hover()
    pasteComponent.keyPress()
    copyComponent.keyPress()
    copyComponent.click()
    pasteComponent.longClick()
}

interface EditorMediator {
    fun notify(sender: EditorComponent, event: EditorComponent.Event)
}

class TextEditorMediator : EditorMediator {
    override fun notify(sender: EditorComponent, event: EditorComponent.Event) {
        val output: String = when {
            sender is CopyComponent && event is EditorComponent.Event.Click -> "Copy clicked"
            sender is CopyComponent && event is EditorComponent.Event.KeyPress -> "Copy key pressed"
            sender is CopyComponent && event is EditorComponent.Event.Hover -> "Hovering over copy"
            sender is PasteComponent && event is EditorComponent.Event.Click -> "Paste click"
            sender is PasteComponent && event is EditorComponent.Event.KeyPress -> "Paste key press"
            sender is PasteComponent && event is EditorComponent.Event.Hover -> "Hovering over paste"
            sender is PasteComponent && event is PasteComponent.Event.LongClick -> "Paste long clicked"
            else -> throw IllegalStateException("No action recognised!")
        }
        println("Logging action: $output")
    }
}

abstract class EditorComponent {

    protected abstract val mediator: EditorMediator

    fun click() {
        mediator.notify(sender = this, event = Event.Click)
    }

    fun keyPress() {
        mediator.notify(sender = this, event = Event.KeyPress)
    }
    fun hover() {
        mediator.notify(sender = this, event = Event.Hover)
    }

    sealed class Event {
        object Click : Event()
        object KeyPress : Event()
        object Hover : Event()
    }
}

class CopyComponent(override val mediator: EditorMediator) : EditorComponent()

class PasteComponent(override val mediator: EditorMediator) : EditorComponent() {

    fun longClick() {
        mediator.notify(sender = this, event = Event.LongClick)
    }

    sealed class Event : EditorComponent.Event() {
        object LongClick : Event()
    }
}


