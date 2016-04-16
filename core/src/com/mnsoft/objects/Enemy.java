package com.mnsoft.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mnsoft.game.Asset;
import com.mnsoft.game.GameSettings.GameConst;

public class Enemy extends Ken {
	private Animation animRunning;
	private Animation animExplode;
	
	public Enemy() {
		super();
		body.y = GameConst.camHeight - 42;
		int explosionFrameCol = 4;
		int explosionFrameRow = 4;
		Texture explodeTexture = Asset.EXPLODE;
		TextureRegion[][] tmp = TextureRegion.split(explodeTexture, explodeTexture.getWidth()/explosionFrameCol, explodeTexture.getHeight()/explosionFrameRow);              // #10
		TextureRegion[] explodeFrames = new TextureRegion[explosionFrameCol * explosionFrameRow];
        int index = 0;
        for (int i = 0; i < explosionFrameRow; i++) {
            for (int j = 0; j < explosionFrameCol; j++) {
            	explodeFrames[index++] = tmp[i][j];
            }
        }
        animExplode = new Animation(0.025f, explodeFrames);
        animRunning = new Animation(0.25f, Asset.MARTIAN_RUNNING1, Asset.MARTIAN_RUNNING2, Asset.MARTIAN_RUNNING3, Asset.MARTIAN_RUNNING4);
		stateTime = 0;
		healthBar = new HealthBar(100);
	}
	
	@Override
	public void update(float delta) {
		body.y -= 10 * delta;
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
		default:
			stateFrame = animRunning.getKeyFrame(stateTime, true);
		}
		if (state != State.DEAD) {
			batch.draw(stateFrame, body.x, body.y);
			healthBar.render(batch, body.x, body.y + body.height + 15);
		}
	}
	
	@Override
	public void reset() {
		body.y = GameConst.camHeight - 42;
		stateTime = 0;
		healthBar.setCurrentHealth(100);
		state = State.IDLE;
	}
}
