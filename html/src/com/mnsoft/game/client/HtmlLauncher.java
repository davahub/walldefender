package com.mnsoft.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.mnsoft.game.WallDefenderGame;

public class HtmlLauncher extends GwtApplication {

	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 800);
	}

	@Override
	public ApplicationListener createApplicationListener () {
		return new WallDefenderGame();
	}
}