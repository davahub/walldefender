package com.mnsoft.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mnsoft.game.Asset;
import com.mnsoft.game.GameSettings.GameConst;
import com.mnsoft.game.MapManager;
import com.mnsoft.game.WallDefenderGame;
import com.mnsoft.objects.WallDefenderWorld;

public class GameScreen implements Screen {
	private final WallDefenderGame game; 
	public OrthographicCamera camera;
	private WallDefenderWorld world;
	private MapManager mapManager;
	
	public GameScreen(final WallDefenderGame game) {
		this.game = game;
		camera = new OrthographicCamera(GameConst.camWidth, GameConst.camHeight);
		camera.position.set(GameConst.camWidth / 2, GameConst.camHeight / 2, 0);
		world = new WallDefenderWorld();
		mapManager = new MapManager();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		mapManager.setView(camera);
		mapManager.renderBackground();
		game.batch.begin();
		game.font.draw(game.batch, "Kills: " + world.ken.kills, 0, 800);
		world.render(delta, game.batch);
		game.batch.end();
		mapManager.renderForeground();
		world.processInputs(camera);
	}
	
	@Override
	public void show() {
		Asset.playBackgroundMusic();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}
	
	@Override
	public void dispose() {
		game.batch.dispose();
	}
}
