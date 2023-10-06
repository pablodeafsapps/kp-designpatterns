package org.deafsapps.kpdesignpatterns.behavioral

/**
 * Command is a behavioral design pattern that turns a request into a stand-alone object that contains all information
 * about the request. This transformation let you pass requests as a method arguments, delay or queue a request’s
 * execution, and support undoable operations.
 *
 * Problem:
 *
 *
 * Solution:
 * Essentially, the Command Design Pattern is a way to separate the requester of an action (the client) from the object
 * that performs the action (the receiver). By encapsulating the request itself as an object, we can pass it around and
 * manipulate it as needed. This allows us to parameterize the request with different arguments, queue it up for later
 * execution, or even undo it if needed.
 * There are several components to the Command Design Pattern:
 * - Command interface: This is an interface that defines the common methods for all commands. It usually has a single
 * method called 'execute()' that performs the action.
 * - Command class: This is a class that implements the Command interface and defines the behavior of a specific command.
 * It contains a reference to the object that will perform the action.
 * - Invoker: This is a class that requests an action to be performed by a command. It doesn't know how the action will
 * be performed or who will perform it.
 * - Receiver: This is a class that performs the actual action. The command object sends the request to the receiver
 * object, which then performs the action.
 *
 * Android Example:
 * There's no common use-case for the Command design pattern in Android Development, but it looks as a sensible option
 * when implementing editors (text, images, etc.).
 */

fun main() {
    val clipboard = Clipboard()
    val editor = TextEditor("Hello World!")
    val invoker = TextEditorInvoker()

    invoker.executeCommand(CutCommand(receiver = editor, clipboard = clipboard)) // cuts last character of initial content to clipboard
    invoker.executeCommand(CopyCommand(receiver = editor, clipboard = clipboard)) // copies current content to clipboard
    invoker.executeCommand(PasteCommand(receiver = editor, clipboard = clipboard)) // pastes current clipboard content to text editor

    editor.print() // prints "Hello WorldHello World"
    invoker.undo() // undoes the paste command
    editor.print() // prints "Hello World"
}

// Command
interface Command {
    fun execute()
    fun undo()
}

class CutCommand(private val receiver: TextEditor, private val clipboard: Clipboard) : Command {
    override fun execute() {
        clipboard.content = receiver.cut()
    }

    override fun undo() {
        receiver.write(clipboard.content)
        clipboard.content = ""
    }
}

class CopyCommand(private val receiver: TextEditor, private val clipboard: Clipboard) : Command {
    override fun execute() {
        clipboard.content = receiver.copy()
    }

    override fun undo() {
        clipboard.content = ""
    }
}

class PasteCommand(private val receiver: TextEditor, private val clipboard: Clipboard) : Command {
    override fun execute() {
        receiver.write(clipboard.content)
    }

    override fun undo() {
        receiver.delete(clipboard.content)
    }
}

// Invoker
class TextEditorInvoker {

    private val commands = mutableListOf<Command>()

    fun executeCommand(command: Command) {
        commands.add(command)
        command.execute()
    }

    fun undo() {
        if (commands.isNotEmpty()) {
            commands.removeLast().undo()
        }
    }
}

data class Clipboard(var content: String = "")

// Receiver
interface Editor {
    fun cut(): String
    fun copy(): String
    fun write(text: String)
    fun delete(text: String)
    fun print()
}

class TextEditor(initialContent: String) : Editor {

    private var content = initialContent

    override fun cut(): String {
        val cutContent = content.takeLast(1)
        content = content.dropLast(1)
        return cutContent
    }

    override fun copy(): String {
        return content
    }

    override fun write(text: String) {
        content += text
    }

    override fun delete(text: String) {
        content = content.removeSuffix(text)
    }

    override fun print() {
        println(content)
    }
}
