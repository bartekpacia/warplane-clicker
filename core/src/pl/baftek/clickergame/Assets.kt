package pl.baftek.clickergame

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture

class Assets {

    companion object {
        val manager: AssetManager = AssetManager()

        val bf109 = AssetDescriptor<Texture>("bf109_pixel.png", Texture::class.java)
        val play = AssetDescriptor<Texture>("play.png", Texture::class.java)
        val pause = AssetDescriptor<Texture>("pause.png", Texture::class.java)
        val explosion = AssetDescriptor<Sound>("explosion_1.mp3", Sound::class.java)

        fun load() {
            manager.load(bf109)
            manager.load(play)
            manager.load(pause)
            manager.load(explosion)
        }

        fun dispose() {
            manager.dispose()
        }
    }
}