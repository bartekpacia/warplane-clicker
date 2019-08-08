package pl.baftek.clickergame

import com.badlogic.gdx.Game

class ClickerGame : Game() {

    val GAME_NAME = "WarplaneClicker"

    val width = 480f
    val height = 720f
    var paused = false


    override fun create() {
        Assets.load()
        Assets.manager.finishLoading()
        setScreen(GameplayScreen(this))
    }

    override fun dispose() {
        Assets.dispose()
        super.dispose()
    }


}
