package org.deafsapps.kpdesignpatterns.behavioral

/**
 * State is a behavioral design pattern that lets an object alter its behavior when its internal state changes. It
 * appears as if the object changed its class.
 *
 * Problem:
 * The main idea is that, at any given moment, there’s a finite number of states which a program can be in. Within any
 * unique state, the program behaves differently, and the program can be switched from one state to another instantaneously.
 * However, depending on a current state, the program may or may not switch to certain other states. These switching
 * rules, called transitions, are also finite and predetermined.
 *
 * Solution:
 * The State pattern suggests that you create new classes for all possible states of an object and extract all
 * state-specific behaviors into these classes. Instead of implementing all behaviors on its own, the original object,
 * called context, stores a reference to one of the state objects that represents its current state, and delegates all
 * the state-related work to that object.
 * The main drawback of the following implementation is that inner context operations ('startPlayback()' for instance)
 * are public and, thus, accessible from anywhere.
 *
 * Android Example:
 * This design pattern may be used when building audio/video players. However, Android does include built-in solutions
 * which address its own lifecycle and particularities.
 * ViewModels may be associated with some kind of state (as in MVI), so this design pattern may be a good candidate for
 * it.
 */

fun main() {
    AudioPlayer().apply {
        clickPlay()
        clickNext()
        clickLock()
        clickPlay()
        clickPrevious()
        clickLock()
        clickPlay()
        clickPrevious()
    }
}

/*
 * The AudioPlayer class acts as the context. It also maintains a reference to an instance of one of the state classes
 * that represents the current state of the audio player.
 */
class AudioPlayer {

    var isPlaying: Boolean = false
    private var state: State = ReadyState(player = this)

    // Other objects must be able to switch the audio player's active state.
    fun changeState(newState: State) {
        state = newState
    }

    // These are UI methods which delegate execution to the active state.
    fun clickLock() {
        state.clickLock()
    }

    fun clickPlay() {
        state.clickPlay()
    }

    fun clickNext() {
        state.clickNext()
    }

    fun clickPrevious() {
        state.clickPrevious()
    }

    // A state may call some service methods on the context.
    fun startPlayback() {
        isPlaying = true
        println("Start playback")
    }

    fun stopPlayback() {
        isPlaying = false
        println("Stop playback")
    }

    fun nextSong() {
        println("Next song!")
    }

    fun previousSong() {
        println("Previous song!")
    }

    fun fastForward(time: Int) {
        println("Fast forward $time seconds")
    }

    fun rewind(time: Int) {
        println("Rewind $time seconds")
    }

    /*
     * The base state class declares methods that all concrete states should implement and also provides a back-reference
     * to the context object associated with the state. States can use the back-reference to transition the context to
     * another state.
     */
    abstract class State {

        protected abstract val player: AudioPlayer

        abstract fun clickLock()
        abstract fun clickPlay()
        abstract fun clickNext()
        abstract fun clickPrevious()
    }
}

/*
 * Concrete states implement various behaviors associated with a state of the context.
 */
class LockedState(override val player: AudioPlayer) : AudioPlayer.State() {

    // When you unlock a locked player, it may assume one of two states
    override fun clickLock() {
        if (player.isPlaying) {
            player.changeState(newState = PlayingState(player = player))
        } else {
            player.changeState(newState = ReadyState(player = player))
        }
    }

    override fun clickPlay() {
        // Locked, so do nothing.
    }

    override fun clickNext() {
        // Locked, so do nothing.
    }

    override fun clickPrevious() {
        // Locked, so do nothing.
    }
}

class ReadyState(override val player: AudioPlayer) : AudioPlayer.State() {

    override fun clickLock() {
        player.changeState(newState = LockedState(player))
    }

    override fun clickPlay() {
        player.startPlayback()
        player.changeState(PlayingState(player = player))
    }

    override fun clickNext() {
        player.nextSong()
    }

    override fun clickPrevious() {
        player.previousSong()
    }
}

class PlayingState(override val player: AudioPlayer) : AudioPlayer.State() {

    override fun clickLock() {
        player.changeState(newState = LockedState(player))
    }

    override fun clickPlay() {
        player.stopPlayback()
        player.changeState(newState = ReadyState(player = player))
    }

    override fun clickNext() {
        player.fastForward(5)
    }

    override fun clickPrevious() {
        player.rewind(5)
    }
}
