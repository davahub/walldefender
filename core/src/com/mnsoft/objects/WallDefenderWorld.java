package com.mnsoft.objects;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mnsoft.game.GameSettings.GameConst;
import com.mnsoft.game.PoolManager;
import com.mnsoft.objects.Ken.State;

public class WallDefenderWorld {
	public Ken ken;
	private EnemyArmy army;

	public WallDefenderWorld() {
		ken = new Ken();
		army = new EnemyArmy();
	}
	
	public void render(float delta, SpriteBatch batch) {
		render(batch);
		update(delta); 
		processCollisions();
		army.spawnEnemy();
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
			if (enemy.state == State.DEAD || enemy.body.y < 0) {
				if (enemy.state == State.DEAD) {
					ken.kills++;
				}
				iter.remove();
				PoolManager.ENEMY_POOL.free(enemy);
			}
		}
	}
	
	
	// ----------------------------------------------------------------
	// UPDATE
	// ----------------------------------------------------------------
	private void update(float delta) {
		updateEnemy(delta);
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
	private void render(SpriteBatch batch) {
		ken.render(batch);
		army.render(batch);
	}
	
	
	// ----------------------------------------------------------------
	// INPUT
	// ----------------------------------------------------------------
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
			ken.shoot();
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			ken.shoot();
		}
		if (Gdx.input.isKeyPressed(Keys.R)) {
			ken.reload();
		}
		ken.beIdle();
	}
}
