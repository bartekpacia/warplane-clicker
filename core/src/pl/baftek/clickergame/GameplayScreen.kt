package pl.baftek.clickergame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.TimeUtils
import pl.baftek.clickergame.entities.Enemy
import pl.baftek.clickergame.ui.Styles

class GameplayScreen constructor(game: ClickerGame) : AbstractScreen(game) {

    //environment
    private var score: Int = 0
    private var lost: Boolean = false
    private var lastEnemySpawnTime: Long = 0
    private val enemySpawnCooldown: Long = 250_000_000

    //behaviour
    private var touchPos: Vector3
    private val enemies: Array<Enemy> = Array()
    private val particleEffects: Array<ParticleEffect> = Array()
    private lateinit var enemiesIterator: MutableIterator<Enemy>
    private val referenceParticleEffect: ParticleEffect

    //ui
    private val table: Table
    private val scoreLabel: Label
    private val pauseButton: Image
    private val playDrawable: TextureRegionDrawable
    private val pauseDrawable: TextureRegionDrawable

    init {
        touchPos = Vector3(0f, 0f, 0f)
        table = Table()
        scoreLabel = Label("Score: " + score, LabelStyle(BitmapFont(), Color.WHITE))
        playDrawable = TextureRegionDrawable(TextureRegion(Assets.manager.get(Assets.play)))
        pauseDrawable = TextureRegionDrawable(TextureRegion(Assets.manager.get(Assets.pause)))

        referenceParticleEffect = ParticleEffect()
        referenceParticleEffect.load(Gdx.files.internal("explosion.p"), Gdx.files.internal(""))

        pauseButton = Image(pauseDrawable)
        pauseButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if (game.paused) {
                    game.paused = false
                    pauseButton.drawable = pauseDrawable
                } else {
                    game.paused = true
                    pauseButton.drawable = playDrawable
                }
                super.clicked(event, x, y)
            }
        })

        buildUI()
    }

    override fun buildUI() {
        scoreLabel.setFontScale(2f)

        val topHorizontalGroup = HorizontalGroup().space(200f).padTop(20f)
        topHorizontalGroup.addActor(scoreLabel)
        topHorizontalGroup.addActor(pauseButton)
        table.add(topHorizontalGroup).row()
        table.top()
        table.setFillParent(true)

        stage.addActor(table)
    }

    override fun render(delta: Float) {
        super.render(delta)

        spriteBatch.begin()

        if (!lost) {
            if (!game.paused) {
                update()
            }
        }

        for (enemy in enemies) {
            spriteBatch.draw(enemy.texture, enemy.x, enemy.y)
        }

        for (effect in particleEffects) {
            effect.draw(spriteBatch, delta)
        }

        spriteBatch.end()
        stage.draw()
    }

    private fun update() {
        spawnEnemy()

        touchPos = Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
        camera.unproject(touchPos)

        enemiesIterator = enemies.iterator()
        while (enemiesIterator.hasNext()) {
            val enemy = enemiesIterator.next()
            enemy.move()

            if (enemy.contains(touchPos.x, touchPos.y)) {
                destroyEnemy(enemy)
            }

            if (enemy.y < -100) {
                loseGame()
            }
        }
    }

    private fun destroyEnemy(enemy: Enemy) {
        score++
        scoreLabel.setText("Score: $score")

        val explosion = ParticleEffect(referenceParticleEffect)
        explosion.setPosition(enemy.x, enemy.y)
        explosion.start()

        val sound = Assets.manager.get(Assets.explosion)
        sound.play()

        particleEffects.add(explosion)

        enemiesIterator.remove()
    }

    private fun loseGame() {
        lost = true

        val label = Label("You lost!", Styles.redLabel)
        label.setFontScale(2.5f)
        val labelButton = Label("Click here to try again", Styles.whiteLabel)
        labelButton.setFontScale(2f)
        labelButton.addListener(object : ClickListener() {

            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                super.clicked(event, x, y)
                game.screen = GameplayScreen(game)
            }
        })

        val group = VerticalGroup()
        group.addActor(label)
        group.addActor(labelButton)
        group.center()
        group.space(100f)

        table.add(group).row()

        pauseButton.clearListeners()
    }

    private fun spawnEnemy() {
        if (TimeUtils.nanoTime() - lastEnemySpawnTime > enemySpawnCooldown) {
            val enemy = Enemy(random(0, 400).toFloat(), game.height + 300)
            enemies.add(enemy)
            lastEnemySpawnTime = TimeUtils.nanoTime()
        }
    }

    override fun dispose() {
        for (effect in particleEffects) {
            effect.dispose()
        }

        referenceParticleEffect.dispose()
        super.dispose()
    }
}