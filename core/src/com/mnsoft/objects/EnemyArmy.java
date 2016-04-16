package com.mnsoft.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mnsoft.game.GameSettings.GameConst;
import com.mnsoft.game.PoolManager;

public class EnemyArmy {

	public ArrayList<Enemy> enemies;
	private long lastSpawnTime ;
	
	public EnemyArmy() {
		enemies = new ArrayList<Enemy>();
	}
	
	public void render(SpriteBatch batch) {
		for (Enemy enemy: enemies) {
			enemy.render(batch);
		}
	}
	
	public void spawnEnemy() {
		if(TimeUtils.nanoTime() - lastSpawnTime > 1000000000) {
			Enemy enemy = PoolManager.ENEMY_POOL.obtain();
			enemy.body.x = MathUtils.random(0, GameConst.camWidth - enemy.body.width);
			enemies.add(enemy);
			lastSpawnTime = TimeUtils.nanoTime();
		}
	}
}
