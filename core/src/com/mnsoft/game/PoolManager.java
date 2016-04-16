package com.mnsoft.game;

import com.badlogic.gdx.utils.Pool;
import com.mnsoft.objects.Bullet;
import com.mnsoft.objects.Enemy;

public class PoolManager {
	public static final Pool<Bullet> BULLET_POOL = new Pool<Bullet>() {
		@Override
		protected Bullet newObject() {
			return new Bullet();
		}
	};
	
	public static final Pool<Enemy> ENEMY_POOL = new Pool<Enemy>() {
		@Override
		protected Enemy newObject() {
			return new Enemy();
		}
	};
}
