package com.mnsoft.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mnsoft.game.GameSettings.GameConst;

public class GameScreen implements Screen {
	private final WallDefenderGame game; 
	public OrthographicCamera camera;
	private WallDefenderWorld world;
	
	
	public GameScreen(final WallDefenderGame game) {
		this.game = game;
		camera = new OrthographicCamera(GameConst.camWidth, GameConst.camHeight);
		camera.position.set(GameConst.camWidth / 2, GameConst.camHeight / 2, 0);
		world = new WallDefenderWorld(game);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		world.render(delta, game.batch);
		Asset.GLYPHLAYOUT.setText(Asset.FONT, "Kills " + world.ken.kills);
		Asset.FONT.draw(game.batch, Asset.GLYPHLAYOUT, 1, 62);
		game.batch.end();
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
