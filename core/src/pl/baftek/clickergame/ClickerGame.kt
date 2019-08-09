package pl.baftek.clickergame

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import ktx.scene2d.Scene2DSkin

class ClickerGame : Game() {

    val GAME_NAME = "WarplaneClicker"

    val width = 480f
    val height = 720f
    var paused = false


    override fun create() {
        Scene2DSkin.defaultSkin = Skin(Gdx.files.internal("skin/uiskin.json"))
        Assets.load()
        Assets.manager.finishLoading()
        setScreen(GameplayScreen(this))

    }

    override fun dispose() {
        Assets.dispose()
        super.dispose()
    }


}
