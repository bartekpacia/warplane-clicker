package pl.baftek.clickergame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.viewport.StretchViewport


abstract class AbstractScreen constructor(var game: ClickerGame) : Screen {

    protected val camera: OrthographicCamera
    protected var stage: Stage
    protected val spriteBatch: SpriteBatch

    private val font: BitmapFont
    protected val whiteLabelStyle: LabelStyle
    protected val redLabelStyle: LabelStyle

    init {
        camera = OrthographicCamera()
        camera.setToOrtho(false, game.width, game.height)
        camera.update()

        stage = Stage(StretchViewport(game.width, game.height, camera))
        spriteBatch = SpriteBatch()

        font = BitmapFont()
        whiteLabelStyle = LabelStyle(font, Color.WHITE)
        redLabelStyle = LabelStyle(font, Color.RED)

        Gdx.input.inputProcessor = stage
    }

    /**
     * Here table and groups should be initialized and buttons/labels placed.
     */
    abstract fun buildUI()

    override fun render(delta: Float) {
        clearScreen()
        camera.update()
        spriteBatch.projectionMatrix = camera.combined
        stage.act(delta)
    }

    private fun clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun pause() {
        game.paused = true
    }

    override fun resume() {
        game.paused = false
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun hide() {
    }

    override fun show() {
    }

    override fun dispose() {
        game.dispose()

    }

}
