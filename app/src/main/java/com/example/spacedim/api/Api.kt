package com.example.spacedim.api

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONArray
import org.json.JSONObject


class SocketListener : WebSocketListener() {

    val moshi = Moshi.Builder().build()

    override fun onOpen(webSocket: WebSocket, response: Response) {
        println(response)
    }

    override fun onMessage(webSocket: WebSocket, response: String) {
        println("onMessage")
        //println(response)

        val resp = JSONObject(response)
        val argType = resp.get("type")
        //println(argType)

        when (argType) {
            "WAITING_FOR_PLAYER" -> {

                val playerJsonAdapter = moshi.adapter(Waiting::class.java)
                val waiting = playerJsonAdapter.fromJson(response)

                println(waiting?.userList)
            }
            else -> { // Else type
                print(argType)
            }
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        println(t.message)
    }
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

@JsonClass(generateAdapter = true)
data class User(val id: Int, val name: String, val avatar: String, var score: Int, var state: State = State.OVER)

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

@JsonClass(generateAdapter = true)
data class Waiting(val type: String, val userList: List<User>)
