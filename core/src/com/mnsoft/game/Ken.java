package com.mnsoft.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.TimeUtils;
import com.mnsoft.game.GameSettings.GameConst;

public class Ken implements Poolable {

	// PRIVATE
	// ANIM
	private Animation animIdle;
	private Animation animMoveRight;
	private Animation animMoveLeft;
	private TextureRegion[][] kenAssetMap;
	private long lastActionTime;
	private long lastMoveTime;
	private int bulletAmount;
	private Animation animExplode;
	private boolean shootRight;

	// PROTECTED
	protected float stateTime;
	protected HealthBar healthBar;
	// PUBLIC
	public State state;
	public Rectangle body;
	public ArrayList<Bullet> bulletList;
	public int kills;
	public int damage;

	// ENUM
	public enum State {
		IDLE, MOVE_RIGHT, MOVE_LEFT, DYING, DEAD, HIT, SHOOT_LEFT, SHOOT_RIGHT
	}

	public Ken() {
		body = new Rectangle();
		body.width = 14;
		body.height = 10;
		body.x = GameConst.camWidth / 2 - body.width / 2;
		body.y = 1;
		bulletList = new ArrayList<Bullet>();
		bulletAmount = 160000;
		stateTime = 0;
		state = State.IDLE;
		// IDLE ANIM
		animIdle = new Animation(0.3f, Asset.KEN_IDLE1,
				Asset.KEN_IDLE2, Asset.KEN_IDLE3, Asset.KEN_IDLE4);
		// MOVE RIGHT
		animMoveRight = new Animation(0.3f, Asset.KEN_IDLE1,
				Asset.KEN_IDLE2, Asset.KEN_IDLE3);
		// MOVE LEFT
		animMoveLeft = new Animation(0.3f, Asset.KEN_IDLE1,
				Asset.KEN_IDLE2, Asset.KEN_IDLE3);
		healthBar = new HealthBar(100, 10, 1);
		
		int explosionFrameCol = 6;
		int explosionFrameRow = 2;
		Texture explodeTexture = Asset.EXPLOSION_PIXEL;
		TextureRegion[][] tmp = TextureRegion.split(explodeTexture, explodeTexture.getWidth()/explosionFrameCol, explodeTexture.getHeight()/ explosionFrameRow);              // #10
		TextureRegion[] explodeFrames = new TextureRegion[explosionFrameCol * explosionFrameRow];
        int index = 0;
        for (int i = 0; i < explosionFrameRow; i++) {
            for (int j = 0; j < explosionFrameCol; j++) {
            	explodeFrames[index++] = tmp[i][j];
            }
        }
        animExplode = new Animation(0.025f, explodeFrames);
        shootRight = true;
        damage = 20;
	}
	
	public void setMaxHealth(int maxHealth) {
		healthBar.setMaxHealth(maxHealth);
	}

	public void hit(int damage) {
		if (state == State.DEAD) {
			return;
		}
		healthBar.decrease(damage);
		Asset.playDropSound();
		state = State.HIT;
		if (healthBar.isEmpty()) {
			die();
		}
	}

	public void shoot() {
		if (state == State.DEAD) {
			return;
		}
		if (TimeUtils.nanoTime() - lastActionTime < 100000000) {
			return;
		}
		if (bulletAmount < 1) {
			lastActionTime = TimeUtils.nanoTime();
			return;
		}
		Bullet bullet = PoolManager.BULLET_POOL.obtain();
		if (shootRight) {
			bullet.setPosition(body.x + ((body.width / 2) + 5), body.y + 5);
			state = State.SHOOT_RIGHT;
			shootRight = false;
		} else {
			bullet.setPosition(body.x + ((body.width / 2) - 6), body.y + 5);
			shootRight = true;
			state = State.SHOOT_LEFT;
		}
		if (kills > 110) {
			this.damage = 40;
		} else if (kills > 210) {
			this.damage = 100;
		}
		bulletList.add(bullet);
		Asset.playShotSound();
		bulletAmount--;
		lastActionTime = TimeUtils.nanoTime();
	}

	public void die() {
		if (state == State.DEAD) {
			return;
		}
		state = State.DYING;
		stateTime = 0;
		Asset.playExplosionSound();
	}

	public void reload() {
		if (state == State.DEAD) {
			return;
		}
		if (TimeUtils.nanoTime() - lastActionTime < 190000000) {
			return;
		}
		bulletAmount = 20;
		lastActionTime = TimeUtils.nanoTime();
	}
	
	public boolean isDead() {
		return state == State.DEAD;
	}
	
	public boolean isDying() {
		return state == State.DYING;
	}

	public void beIdle() {
		if (TimeUtils.nanoTime() - lastMoveTime < 10090000) {
			return;
		}
		state = State.IDLE;
	}

	public void moveLeft() {
		body.x = body.x - 20 * Gdx.graphics.getDeltaTime();
		state = State.MOVE_LEFT;
		lastMoveTime = TimeUtils.nanoTime();
	}

	public void moveRight() {
		body.x = body.x + 20 * Gdx.graphics.getDeltaTime();
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
		if (state != State.DEAD) {
			renderMe(batch);
			renderBullets(batch);
		}
	}

	public void renderMe(SpriteBatch batch) {
		TextureRegion stateFrame;
		switch (state) {
		case MOVE_LEFT:
			stateFrame = animMoveLeft.getKeyFrame(stateTime, true);
			break;
		case MOVE_RIGHT:
			stateFrame = animMoveRight.getKeyFrame(stateTime, true);
			break;
		case DYING:
			stateFrame = animExplode.getKeyFrame(stateTime, false);
			if (stateTime > 0.4f) {
				state = State.DEAD;	
			}
			break;
		case HIT:
			stateFrame = Asset.ENEMY_HIT1;
			if (stateTime > 0.4f) {
				state = State.IDLE;	
			}
			break;
		case SHOOT_LEFT:
			stateFrame = Asset.KEN_SHOOT_LEFT;
			if (stateTime > 0.9f) {
				state = State.IDLE;	
			}
			break;
		case SHOOT_RIGHT:
			stateFrame = Asset.KEN_SHOOT_RIGHT;
			if (stateTime > 0.9f) {
				state = State.IDLE;	
			}
			break;
		default:
			// IDLE
			stateFrame = animIdle.getKeyFrame(stateTime, true);
		}
		if (state != State.DEAD) {
			batch.draw(stateFrame, body.x, body.y);
			healthBar.render(batch, body.x + 2, body.y + body.height + 1);
		}
	}

	private void renderBullets(SpriteBatch batch) {
		for (Bullet bullet : bulletList) {
			bullet.render(batch);
		}
	}

	@Override
	public void reset() {
		body.x = GameConst.camWidth / 2 - body.width / 2;
		body.y = 1;
		bulletAmount = 16000;
		stateTime = 0;
		state = State.IDLE;
		healthBar.setCurrentHealth(100);
	}
}
