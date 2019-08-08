package pl.baftek.clickergame.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

import pl.baftek.clickergame.ClickerGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        val game = ClickerGame()
        config.width = game.width.toInt()
        config.height = game.height.toInt()
        config.resizable = false
        LwjglApplication(game, config)
    }
}
