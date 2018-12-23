package pl.baftek.clickergame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import pl.baftek.clickergame.ClickerGame;

public class DesktopLauncher
{
    public static void main(String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        ClickerGame game = new ClickerGame();
        config.width = (int) game.getWidth();
        config.height = (int) game.getHeight();
        config.resizable = false;
        new LwjglApplication(game, config);
    }
}
