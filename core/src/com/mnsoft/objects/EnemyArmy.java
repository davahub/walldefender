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
	public int level1EnemyCount;
	public int level2EnemyCount;
	public int level3EnemyCount;
	
	public EnemyArmy() {
		enemies = new ArrayList<Enemy>();
		level1EnemyCount = 100;
		level2EnemyCount = 100;
		level3EnemyCount = 100;
	}
	
	public void render(SpriteBatch batch) {
		for (Enemy enemy: enemies) {
			enemy.render(batch);
		}
	}
	
	public void spawnLevel1Enemy() {
		if(TimeUtils.nanoTime() - lastSpawnTime > 1000000000) {
			Enemy enemy = PoolManager.ENEMY_POOL.obtain();
			enemy.body.x = MathUtils.random(0, GameConst.camWidth - enemy.body.width);
			enemies.add(enemy);
			level1EnemyCount = level1EnemyCount -1;
			lastSpawnTime = TimeUtils.nanoTime();
		}
	}
	public void spawnLevel2Enemy() {
		if(TimeUtils.nanoTime() - lastSpawnTime > 1000000000) {
			Enemy enemy = PoolManager.ENEMY_POOL.obtain();
			enemy.body.x = MathUtils.random(0, GameConst.camWidth - enemy.body.width);
			enemy.setMaxHealth(200);
			enemies.add(enemy);
			level2EnemyCount = level2EnemyCount -1;
			lastSpawnTime = TimeUtils.nanoTime();
		}
	}
	public void spawnLevel3Enemy() {
		if(TimeUtils.nanoTime() - lastSpawnTime > 1000000000) {
			Enemy enemy = PoolManager.ENEMY_POOL.obtain();
			enemy.body.x = MathUtils.random(0, GameConst.camWidth - enemy.body.width);
			enemy.setMaxHealth(300);
			enemies.add(enemy);
			level3EnemyCount = level3EnemyCount -1;
			lastSpawnTime = TimeUtils.nanoTime();
		}
	}
}
