package com.mnsoft.objects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.TimeUtils;
import com.mnsoft.game.Asset;
import com.mnsoft.game.GameSettings.GameConst;
import com.mnsoft.game.PoolManager;

public class Ken implements Poolable {

	// PRIVATE
	private Animation animMoveRight;
	private Animation animMoveLeft;
	private TextureRegion[][] kenAssetMap;
	private long lastActionTime;
	private long lastMoveTime;
	private int bulletAmount;
	// PROTECTED
	protected float stateTime;
	protected HealthBar healthBar;
	// PUBLIC
	public State state;
	public Rectangle body;
	public ArrayList<Bullet> bulletList;
	public int kills;

	// ENUM
	public enum State {
		IDLE, MOVE_RIGHT, MOVE_LEFT, DYING, DEAD
	}

	public Ken() {
		body = new Rectangle();
		body.width = 32;
		body.height = 32;
		body.x = GameConst.camWidth / 2 - body.width / 2;
		body.y = 20;
		bulletList = new ArrayList<Bullet>();
		bulletAmount = 8000;
		stateTime = 0;
		state = State.IDLE;
		int kenCol = 12;
		int kenRow = 8;
		kenAssetMap = TextureRegion.split(Asset.KEN, Asset.KEN.getWidth()
				/ kenCol, Asset.KEN.getHeight() / kenRow); // #10
		animMoveRight = new Animation(0.25f, kenAssetMap[2][0],
				kenAssetMap[2][1], kenAssetMap[2][2]);
		animMoveLeft = new Animation(0.25f, kenAssetMap[1][0],
				kenAssetMap[1][1], kenAssetMap[1][2]);

		healthBar = new HealthBar(100);
	}

	public void hit() {
		healthBar.decrease(20);
		Asset.playDropSound();
		if (healthBar.isEmpty()) {
			die();
		}
	}

	public void shoot() {
		if (TimeUtils.nanoTime() - lastActionTime < 100000000) {
			return;
		}
		if (bulletAmount < 1) {
			Asset.playGunEmptySound();
			lastActionTime = TimeUtils.nanoTime();
			return;
		}
		Bullet bullet = PoolManager.BULLET_POOL.obtain();
		bullet.setPosition(body.x + ((body.width / 2) - 4), body.y);
		bulletList.add(bullet);
		Asset.playShotSound();
		bulletAmount--;
		lastActionTime = TimeUtils.nanoTime();
		state = State.IDLE;
	}

	public void die() {
		state = State.DYING;
		stateTime = 0;
		Asset.playExplosionSound();
	}

	public void reload() {
		if (TimeUtils.nanoTime() - lastActionTime < 190000000) {
			return;
		}
		bulletAmount = 20;
		Asset.playGunReloadSound();
		lastActionTime = TimeUtils.nanoTime();
	}

	public void beIdle() {
		if (TimeUtils.nanoTime() - lastMoveTime < 10090000) {
			return;
		}
		state = State.IDLE;
	}

	public void moveLeft() {
		body.x = body.x - 200 * Gdx.graphics.getDeltaTime();
		state = State.MOVE_LEFT;
		lastMoveTime = TimeUtils.nanoTime();
	}

	public void moveRight() {
		body.x = body.x + 200 * Gdx.graphics.getDeltaTime();
		state = State.MOVE_RIGHT;
		lastMoveTime = TimeUtils.nanoTime();
	}

	public void stayWithinBounds() {
		if (body.x < 0) {
			body.x = 0;
		}
		if (body.x > GameConst.camWidth - body.width) {
			body.x = GameConst.camWidth - body.width;
		}
	}

	public void update(float delta) {
		stateTime += delta;
	}

	public void render(SpriteBatch batch) {
		renderMe(batch);
		renderBullets(batch);
	}

	public void renderMe(SpriteBatch batch) {
		TextureRegion stateFrame;
		switch (state) {
		case DEAD:
			stateFrame = Asset.KEN_DEAD_TEXTURE;
			break;
		case MOVE_LEFT:
			stateFrame = animMoveLeft.getKeyFrame(stateTime, true);
			break;
		case MOVE_RIGHT:
			stateFrame = animMoveRight.getKeyFrame(stateTime, true);
			break;
		default:
			// IDLE
			stateFrame = kenAssetMap[3][1];
		}
		batch.draw(stateFrame, body.x, body.y);
		healthBar.render(batch, body.x, body.y + body.height + 2);
	}

	private void renderBullets(SpriteBatch batch) {
		for (Bullet bullet : bulletList) {
			bullet.render(batch);
		}
	}

	@Override
	public void reset() {
		body.x = GameConst.camWidth / 2 - body.width / 2;
		body.y = 20;
		bulletAmount = 8000;
		stateTime = 0;
		state = State.IDLE;
		healthBar.setCurrentHealth(100);
	}
}
