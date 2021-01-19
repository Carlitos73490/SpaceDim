package com.example.spaceteamllacdev.Models

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


object PolymorphicAdapter {

    private val moshiEventSerializer: Moshi = with(Moshi.Builder()) {
        add(
            PolymorphicJsonAdapterFactory.of(Event::class.java,"type")
                .withSubtype(Event.Ready::class.java, EventType.READY.name)
                .withSubtype(Event.Error::class.java, EventType.ERROR.name)
                .withSubtype(Event.GameOver::class.java, EventType.GAME_OVER.name)
                .withSubtype(Event.GameStarted::class.java, EventType.GAME_STARTED.name)
                .withSubtype(Event.NextAction::class.java, EventType.NEXT_ACTION.name)
                .withSubtype(Event.NextLevel::class.java, EventType.NEXT_LEVEL.name)
                .withSubtype(Event.PlayerAction::class.java, EventType.PLAYER_ACTION.name)
                .withSubtype(Event.WaitingForPlayer::class.java, EventType.WAITING_FOR_PLAYER.name)
        )

        add(
            PolymorphicJsonAdapterFactory.of(UIElement::class.java,"type")
                .withSubtype(UIElement.Button::class.java, UIType.BUTTON.name)
                .withSubtype(UIElement.Shake::class.java, UIType.SHAKE.name)
                .withSubtype(UIElement.Switch::class.java, UIType.SWITCH.name)
        )


        add(KotlinJsonAdapterFactory())
        build()
    }

    /*private val moshiUIelementSerializer: Moshi = with(Moshi.Builder()) {
        add(
            PolymorphicJsonAdapterFactory.of(UIElement::class.java,"type")
                .withSubtype(UIElement.Button::class.java, UIType.BUTTON.name)
                .withSubtype(UIElement.Shake::class.java, UIType.SHAKE.name)
                .withSubtype(UIElement.Switch::class.java, UIType.SWITCH.name)
        )

        add(KotlinJsonAdapterFactory())
        build()
    }*/

    val eventGameParser: JsonAdapter<Event> = moshiEventSerializer.adapter(Event::class.java)

    //val UIElementParser: JsonAdapter<UIElement> = moshiUIelementSerializer.adapter(UIElement::class.java)
}

enum class State(val value: Int) {
    WAITING(0), READY(1), IN_GAME(2), OVER(3)
}

enum class UIType {
    BUTTON, SWITCH, SHAKE
}

interface IElement {
    var id: Int
    val content: String
}

sealed class UIElement(val type: UIType) : IElement {
    data class Button(override var id: Int, override val content: String) : UIElement(UIType.BUTTON)
    data class Switch(override var id: Int, override val content: String) : UIElement(UIType.SWITCH)
    data class Shake(override var id: Int, override val content: String) : UIElement(UIType.SHAKE)
}

data class Action(
    val sentence: String,
    val uiElement: UIElement,
    val time: Long = 8000
)

enum class EventType() {
    GAME_STARTED(), GAME_OVER(), ERROR(), READY(), NEXT_ACTION(),
    NEXT_LEVEL(), WAITING_FOR_PLAYER(), PLAYER_ACTION()
}

sealed class Event(val type: EventType) {
    data class NextAction(val action: Action) : Event(EventType.NEXT_ACTION)
    data class GameStarted(val uiElementList: List<UIElement>): Event(EventType.GAME_STARTED)
    data class GameOver(val score: Int, val win: Boolean, val level: Int): Event(EventType.GAME_OVER)
    data class NextLevel(val uiElementList: List<UIElement>, val level: Int) : Event(EventType.NEXT_LEVEL)
    data class WaitingForPlayer(val userList: List<User>) : Event(EventType.WAITING_FOR_PLAYER)
    data class Error(val message: String) : Event(EventType.ERROR)
    data class Ready(val value: Boolean) : Event(EventType.READY)
    data class PlayerAction(val uiElement: UIElement): Event(EventType.PLAYER_ACTION)
}

data class User(val id: Int, val name: String, val avatar: String, var score: Int, var state: State = State.OVER)