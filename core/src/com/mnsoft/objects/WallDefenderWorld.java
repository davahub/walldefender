package com.mnsoft.objects;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mnsoft.game.Asset;
import com.mnsoft.game.GameSettings.GameConst;
import com.mnsoft.game.PoolManager;
import com.mnsoft.game.WallDefenderGame;
import com.mnsoft.objects.Ken.State;
import com.mnsoft.screen.GameScreen;

public class WallDefenderWorld {
	public Ken ken;
	private EnemyArmy army;
	private WallDefenderGame game;
	Texture healthBackground;
	
	private boolean isReady;
	
	public WallDefenderWorld(WallDefenderGame game) {
		ken = new Ken();
		army = new EnemyArmy();
		this.game = game;
		Pixmap pixmapHealth = new Pixmap(width, height, Format.RGB565);
		pixmapHealth.setColor(Color.BLUE);
		healthBackground = new Texture(width, height, Format.RGB565);
		healthBackground.draw(pixmapHealth, 0, 0);
		isWon = false;
		isReady = false;
	}
	public boolean isWon;
	
	
	public void render(float delta, SpriteBatch batch) {
		render(batch);
		update(delta); 
		processCollisions();
		if (!isReady) {
			return;
		}
		if (army.level1EnemyCount > 0) {
			army.spawnLevel1Enemy();
		} else if (army.level1EnemyCount <= 0 && army.level2EnemyCount > 0) {
			army.spawnLevel2Enemy();
		} else if (army.level2EnemyCount <= 0 && army.level3EnemyCount > 0) {
			army.spawnLevel3Enemy();
		} 
		if (army.level1EnemyCount <= 0 && army.level2EnemyCount <= 0
				&& army.level3EnemyCount <= 0 
				&& !ken.isDead() && !ken.isDying() && army.enemies.isEmpty()) {
			isWon = true;
		}
 	}
	
	
	// ----------------------------------------------------------------
	// COLLISIONS
	// ----------------------------------------------------------------
	private void processCollisions() {
		processKenCollisions();
		processEnemyCollisions();
		processBulletCollisions();
	}
	
	private void processKenCollisions() {
		ken.stayWithinBounds();
	}
	
	private void processBulletCollisions() {
		Iterator<Bullet> bulletIter = ken.bulletList.iterator();
		while(bulletIter.hasNext()) {
			Bullet bullet = bulletIter.next();
			if (bullet.body.y > GameConst.camHeight) { 
				bulletIter.remove();
				PoolManager.BULLET_POOL.free(bullet);
				continue;
			}
			Iterator<Enemy> enemyIter = army.enemies.iterator();
			loop:
				while(enemyIter.hasNext()) {
					Enemy enemy = enemyIter.next();
					if (enemy.state == State.DYING) {
						continue;
					}
					if (bullet.body.overlaps(enemy.body)) {
						enemy.hit();
						bulletIter.remove();
						PoolManager.BULLET_POOL.free(bullet);
						break loop;
					}
				}
		}
	}
	
	private void processEnemyCollisions() {
		Iterator<Enemy> iter = army.enemies.iterator();
		while(iter.hasNext()) {
			Enemy enemy = iter.next();
			if (enemy.state == State.DEAD) {
				if (enemy.state == State.DEAD) {
					ken.kills++;
				}
				iter.remove();
				PoolManager.ENEMY_POOL.free(enemy);
				break;
			}
			if (enemy.body.y < 0) {
				ken.hit();
				iter.remove();
				PoolManager.ENEMY_POOL.free(enemy);
			}
		}
	}
	
	
	// ----------------------------------------------------------------
	// UPDATE
	// ----------------------------------------------------------------
	private void update(float delta) {
		if (!ken.isDead()) {
			updateEnemy(delta);
		}
		updateKen(delta);
		updateBullet(delta);
	}
	
	private void updateBullet(float delta) {
		int size = ken.bulletList.size();
		for (int i = 0; i < size; i++) {
			Bullet bullet = ken.bulletList.get(i);
			bullet.update(delta);
		}
	}
	
	private void updateEnemy(float delta) {
		int size = army.enemies.size();
		for (int i = 0; i < size; i++) {
			Enemy enemy = army.enemies.get(i);
			enemy.update(delta);
		}
	}
	
	private void updateKen(float delta) {
		ken.update(delta);
	}
	
	
	// ----------------------------------------------------------------
	// RENDER
	// ----------------------------------------------------------------
	private int width = 62;
	private int height = 10;
	
	private void render(SpriteBatch batch) {
		TextureRegion background = new TextureRegion(Asset.BACKGROUND);
		TextureRegion wall = new TextureRegion(Asset.WALL);
		TextureRegion end = new TextureRegion(Asset.THE_END);
		batch.draw(background, 0, 0);
		batch.draw(wall, 0, 1);
		ken.render(batch);
		army.render(batch);
		// DRAW END
		if (ken.isDead()) {
			batch.draw(end, (GameConst.camWidth / 2) - (end.getRegionWidth() / 2), (GameConst.camHeight / 2) + (end.getRegionHeight() / 2));
			Asset.GLYPHLAYOUT.setText(Asset.FONT, "R E T R Y");
			Asset.FONT.draw(batch, Asset.GLYPHLAYOUT, 17, 10);
//			batch.draw(healthBackground, 17, 5);
		}
		if (isWon) {
			Asset.GLYPHLAYOUT.setText(Asset.FONT_BIG, "W I N N E R");
			Asset.FONT_BIG.draw(batch, Asset.GLYPHLAYOUT, 1, 30);
		}
		if (!isReady) {
			Asset.GLYPHLAYOUT.setText(Asset.FONT_BIG, "R E A D Y ?");
			Asset.FONT_BIG.draw(batch, Asset.GLYPHLAYOUT, 1, 30);
//			batch.draw(healthBackground, 1, 20);
		}
	}
	
	
	// ----------------------------------------------------------------
	// INPUT
	// ----------------------------------------------------------------
	private final Rectangle retryBounds = new Rectangle(17, 5, 32, 5);
	private final Rectangle readyBounds = new Rectangle(1, 20, 62, 10);
	private final Vector3 touchPoint = new Vector3();
	
	public void processInputs(OrthographicCamera camera) {
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			ken.moveLeft();
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			ken.moveRight();
		}
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			ken.body.x = touchPos.x - ken.body.width / 2;
			processKenCollisions();
			ken.shoot();
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			ken.shoot();
		}
		if (ken.isDead() && Gdx.input.isTouched() && !isReady) {
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			if (retryBounds.contains(touchPoint.x, touchPoint.y)) {
				Asset.playClickSound();
				game.setScreen(new GameScreen(game));
				return;
			}
		}
		if (!ken.isDead() && Gdx.input.isTouched()) {
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			if (readyBounds.contains(touchPoint.x, touchPoint.y)) {
				Asset.playClickSound();
				isReady = true;
				return;
			}
		}
	}
}
