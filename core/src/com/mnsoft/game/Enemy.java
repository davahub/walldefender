package com.mnsoft.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mnsoft.game.GameSettings.GameConst;

public class Enemy extends Ken {
	private Animation animRunning;
	private Animation animExplode;
	
	public Enemy() {
		super();
		body.width = 8;
		body.height = 6;
		body.y = GameConst.camHeight;
		int explosionFrameCol = 6;
		int explosionFrameRow = 2;
		Texture explodeTexture = Asset.EXPLOSION_PIXEL;
		TextureRegion[][] tmp = TextureRegion.split(explodeTexture, explodeTexture.getWidth()/explosionFrameCol, explodeTexture.getHeight()/explosionFrameRow);              // #10
		TextureRegion[] explodeFrames = new TextureRegion[explosionFrameCol * explosionFrameRow];
        int index = 0;
        for (int i = 0; i < explosionFrameRow; i++) {
            for (int j = 0; j < explosionFrameCol; j++) {
            	explodeFrames[index++] = tmp[i][j];
            }
        }
        animExplode = new Animation(0.025f, explodeFrames);
        animRunning = new Animation(0.25f, Asset.ENEMY_PIXEL1, Asset.ENEMY_PIXEL2, Asset.ENEMY_PIXEL3, Asset.ENEMY_PIXEL4);
		stateTime = 0;
		healthBar = new HealthBar(100, 6, 1);
		damage = 10;
	}
	
	public void hit() {
		if (state == State.DEAD) {
			return;
		}
		if (kills > 11) {
			healthBar.decrease(40);
		} else {
			healthBar.decrease(20);
		}
		Asset.playDropSound();
		state = State.HIT;
		if (healthBar.isEmpty()) {
			die();
		}
	}
	
	@Override
	public void update(float delta) {
		body.y -= 5 * delta;
		stateTime += delta;
	}
	
	@Override
	public void renderMe(SpriteBatch batch) {
		TextureRegion stateFrame;
		switch (state) {
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
		default:
			stateFrame = animRunning.getKeyFrame(stateTime, true);
		}
		if (state != State.DEAD) {
			batch.draw(stateFrame, body.x, body.y);
			healthBar.render(batch, body.x + 1, body.y + body.height + 1);
		}
	}
	
	@Override
	public void reset() {
		body.y = GameConst.camHeight;
		stateTime = 0;
		healthBar.setCurrentHealth(100);
		state = State.IDLE;
	}
}
