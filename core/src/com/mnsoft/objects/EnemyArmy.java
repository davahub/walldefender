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
	int level1EnemyCount = 5;
	int level2EnemyCount = 5;
	int level3EnemyCount = 5;
	
	public EnemyArmy() {
		enemies = new ArrayList<Enemy>();
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
			enemies.add(enemy);
			level2EnemyCount = level2EnemyCount -1;
			lastSpawnTime = TimeUtils.nanoTime();
		}
	}
	public void spawnLevel3Enemy() {
		if(TimeUtils.nanoTime() - lastSpawnTime > 1000000000) {
			Enemy enemy = PoolManager.ENEMY_POOL.obtain();
			enemy.body.x = MathUtils.random(0, GameConst.camWidth - enemy.body.width);
			enemies.add(enemy);
			level3EnemyCount = level3EnemyCount -1;
			lastSpawnTime = TimeUtils.nanoTime();
		}
	}
}
