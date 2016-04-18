package com.mnsoft.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Bullet implements Poolable {
	public float stateTime;
	private Animation animBlueLaser;
	public Rectangle body;
	
	public Bullet() {
		body = new Rectangle();
		body.width = 1;
		body.height = 1;
//		animBlueLaser = new Animation(0.3f, new TextureRegion(Asset.LASER_BLUE2), new TextureRegion(Asset.LASER_BLUE3)
//		, new TextureRegion(Asset.LASER_BLUE1));
		stateTime = 0;
	}
	
	public void setPosition(float x, float y) {
		body.x = x;
		body.y = y;
	}
	
	public void update(float delta) {
		body.y += 100 * delta;
		stateTime += delta;
	}
	
	public void render(SpriteBatch batch) {
//		batch.draw(animBlueLaser.getKeyFrame(stateTime, false), body.x, body.y);
		batch.draw(Asset.BULLET_PIXEL1, body.x, body.y);
	}

	/**
     * Callback method when the object is freed. It is automatically called by Pool.free()
     * Must reset every meaningful field of this bullet.
     */
	@Override
	public void reset() {
		stateTime = 0;
	}
}
