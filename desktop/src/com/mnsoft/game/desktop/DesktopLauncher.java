package com.mnsoft.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mnsoft.game.WallDefenderGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Wall Defender";
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new WallDefenderGame(), config);
	}
}
