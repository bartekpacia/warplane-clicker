package pl.baftek.clickergame.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import pl.baftek.clickergame.Assets

class Enemy constructor(x: Float, y: Float) : Rectangle(), Entity {
    var texture: Texture
    val speed = 800

    init {
        texture = Assets.manager.get(Assets.bf109)
        this.x = x
        this.y = y
        this.width = texture.width.toFloat()
        this.height = texture.height.toFloat()
    }

    override fun move() {
        y = y - speed * Gdx.graphics.deltaTime
    }

    override fun onTouch() {

    }
}
