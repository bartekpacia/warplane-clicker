package pl.baftek.clickergame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ExtendViewport


abstract class AbstractScreen constructor(val game: ClickerGame) : Screen {

    protected val camera: OrthographicCamera = OrthographicCamera()
    protected val stage: Stage
    protected val spriteBatch = SpriteBatch()

    init {
        camera.setToOrtho(false, game.width, game.height)
        camera.update()

        stage = Stage(ExtendViewport(game.width, game.height, camera))

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
