package pl.baftek.clickergame.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label

object Styles {
    val font = BitmapFont()
    val whiteLabel: Label.LabelStyle = Label.LabelStyle(font, Color.WHITE)
    val redLabel: Label.LabelStyle = Label.LabelStyle(font, Color.RED)

}