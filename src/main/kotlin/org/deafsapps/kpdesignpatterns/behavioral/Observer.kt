package org.deafsapps.kpdesignpatterns.behavioral

/**
 * Observer is a behavioral design pattern that let you define a subscription mechanism to notify multiple objects about
 * any events that happen to the object they’re observing.
 *
 * Problem:
 * Sometimes you want certain changes to get broadcast to several classes or entities without setting up any coupling or
 * holding strong references.
 *
 * Solution:
 * The Observer pattern suggests that you add a subscription mechanism to the publisher class so individual objects can
 * subscribe to or unsubscribe from a stream of events coming from that publisher.
 *
 * Android Example:
 * In current Android Development, the Observer design pattern is rather popular since it's when implementing MVVM. Either
 * LiveData or StateFlow are good examples of observers to get subscribed to.
 */

fun main() {
    val newsPublisher = NewsPublisher()
    val newspaper = Newspaper()
    val radioSubscriber = RadioStation()
    val tvChannel = TvChannel()

    newsPublisher.notifySubscribers(msg = "Initial message")

    newsPublisher.apply {
        subscribeAll(newspaper, radioSubscriber, tvChannel)
        notifySubscribers(msg = "Second message")
        unsubscribe(radioSubscriber)
        notifySubscribers(msg = "Final message")
    }
}

interface Subscriber {
    fun notify(msg: String)
}

class Newspaper : Subscriber {
    override fun notify(msg: String) {
        println("Notification received to 'Newspaper' - $msg")
    }
}

class RadioStation : Subscriber {
    override fun notify(msg: String) {
        println("Notification received to 'RadioStation' - $msg")
    }
}

class TvChannel : Subscriber {
    override fun notify(msg: String) {
        println("Notification received to 'TvChannel' - $msg")
    }
}

abstract class Publisher {

    protected abstract val subscribers: MutableList<Subscriber>

    fun subscribe(subscriber: Subscriber) {
        subscribers += subscriber
    }

    fun subscribeAll(vararg subscribers: Subscriber) {
        subscribers.forEach { subscriber -> subscribe(subscriber = subscriber) }
    }

    fun unsubscribe(subscriber: Subscriber) {
        subscribers -= subscriber
    }

    fun unsubscribeAll(vararg subscribers: Subscriber) {
        subscribers.forEach { subscriber -> unsubscribe(subscriber = subscriber) }
    }

    fun clearSubscribers() {
        subscribers.clear()
    }

    fun notifySubscribers(msg: String) {
        subscribers.forEach { subscriber -> subscriber.notify(msg = msg) }
    }
}

class NewsPublisher : Publisher() {
    override val subscribers: MutableList<Subscriber> = mutableListOf()
}
