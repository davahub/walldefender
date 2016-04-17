package com.mnsoft.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Asset {

	// FONT
	public final static BitmapFont FONT = new BitmapFont(Gdx.files.internal("fonts/font-small.fnt"), Gdx.files.internal("fonts/font-small.png"), false);
	public final static GlyphLayout GLYPHLAYOUT = new GlyphLayout();
	public final static BitmapFont FONT_BIG = new BitmapFont(Gdx.files.internal("fonts/font-big.fnt"), Gdx.files.internal("fonts/font-big.png"), false);
	public final static GlyphLayout GLYPHLAYOUT_BIG = new GlyphLayout();
	// TEXTURE
	public final static Texture BACKGROUND = new Texture(Gdx.files.internal("background.png"));
	public final static Texture EXPLOSION_PIXEL = new Texture(Gdx.files.internal("explosion.png"));
	public final static Texture THE_END = new Texture(Gdx.files.internal("theEnd.png"));
	// KEN
	public final static Texture MAIN_TEXTURE = new Texture(Gdx.files.internal("main-texture.png"));
	// idle
	public final static TextureRegion KEN_IDLE1 = new TextureRegion(MAIN_TEXTURE, 0, 24, 14, 10);
	public final static TextureRegion KEN_IDLE2 = new TextureRegion(MAIN_TEXTURE, 15, 24, 14, 10);
	public final static TextureRegion KEN_IDLE3 = new TextureRegion(MAIN_TEXTURE, 30, 24, 14, 10);
	public final static TextureRegion KEN_IDLE4 = new TextureRegion(MAIN_TEXTURE, 45, 24, 14, 10);
    // shoot
	public final static TextureRegion KEN_SHOOT_LEFT = new TextureRegion(MAIN_TEXTURE, 0, 34, 14, 10);
	public final static TextureRegion KEN_SHOOT_RIGHT = new TextureRegion(MAIN_TEXTURE, 15, 34, 14, 10);
	// WALL
	public final static TextureRegion WALL = new TextureRegion(MAIN_TEXTURE, 0, 47, 64, 1);
	public final static TextureRegion WALL_HIT = new TextureRegion(MAIN_TEXTURE, 0, 48, 64, 1);
	
	// ENEMY
	public final static TextureRegion ENEMY_PIXEL1 = new TextureRegion(MAIN_TEXTURE, 0, 58, 8, 6);
	public final static TextureRegion ENEMY_PIXEL2 = new TextureRegion(MAIN_TEXTURE, 9, 58, 8, 6);
	public final static TextureRegion ENEMY_PIXEL3 = new TextureRegion(MAIN_TEXTURE, 18, 58, 8, 6);
	public final static TextureRegion ENEMY_PIXEL4 = new TextureRegion(MAIN_TEXTURE, 27, 58, 8, 6);
	// hit
	public final static TextureRegion ENEMY_HIT1 = new TextureRegion(MAIN_TEXTURE, 0, 51, 8, 6);
	// BULLET
	public final static TextureRegion BULLET_PIXEL1 = new TextureRegion(MAIN_TEXTURE, 63, 0, 1, 1);
	
	// SOUND
	private final static Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("click.wav"));
	private final static Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("droid-glitch.wav"));
	private final static Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
	private final static Sound shotSound = Gdx.audio.newSound(Gdx.files.internal("shot.wav"));
	private final static Sound explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
	
	public Asset() {
	}
	
	public static void playExplosionSound() {
		playSound(explosionSound);
	}
	
	public static void playShotSound() {
		playSound(shotSound);
	}
	
	public static void playDropSound() {
		playSound(dropSound);
	}
	
	public static void playBackgroundMusic() {
		backgroundMusic.setLooping(true);
		if (GameSettings.isSoundEnabled) backgroundMusic.play();
	}
	
	public static void playClickSound() {
		playSound(clickSound);
	}
	
	private static void playSound(Sound sound) {
		if (GameSettings.isSoundEnabled) sound.play(1);
	}
	
	public static void destroy() {
		// SOUND 
		clickSound.dispose();
		dropSound.dispose();
		backgroundMusic.dispose();
		// TEXTURE
		BACKGROUND.dispose();
		EXPLOSION_PIXEL.dispose();
		THE_END.dispose();
		MAIN_TEXTURE.dispose();
	}
}
