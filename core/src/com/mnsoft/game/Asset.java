package com.mnsoft.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Asset {

	// TEXTURE
	public final static Texture BACKGROUND = new Texture(Gdx.files.internal("background.png"));
	public final static Texture MENU_IMAGE = new Texture(Gdx.files.internal("menuText.png"));
	public final static Texture DROP_IMAGE = new Texture(Gdx.files.internal("droplet.png"));
	public final static Texture ITEMS = new Texture(Gdx.files.internal("items.png"));
	public final static Texture RUNNING_MAN = new Texture(Gdx.files.internal("running-man.png"));
	public final static Texture EXPLODE = new Texture(Gdx.files.internal("explode.png"));
	public final static Texture LASER_BLUE1 = new Texture(Gdx.files.internal("laserBlue1.png"));
	public final static Texture LASER_BLUE2 = new Texture(Gdx.files.internal("laserBlue2.png"));
	public final static Texture LASER_BLUE3 = new Texture(Gdx.files.internal("laserBlue3.png"));
	// MAP
	public final static TiledMap TILED_MAP = new TmxMapLoader().load("archer-map.tmx");
	// MARTIAN
	private final static int MARTIAN_WIDTH = 27;
	private final static int MARTIAN_HEIGHT = 47;
	public final static Texture MARTIAN = new Texture(Gdx.files.internal("martian.png"));
	public final static TextureRegion MARTIAN_RUNNING1 = new TextureRegion(MARTIAN, 46, 101, MARTIAN_WIDTH, MARTIAN_HEIGHT);
	public final static TextureRegion MARTIAN_RUNNING2 = new TextureRegion(MARTIAN, 84, 101, MARTIAN_WIDTH, MARTIAN_HEIGHT);
	public final static TextureRegion MARTIAN_RUNNING3 = new TextureRegion(MARTIAN, 122, 101, MARTIAN_WIDTH, MARTIAN_HEIGHT);
	public final static TextureRegion MARTIAN_RUNNING4 = new TextureRegion(MARTIAN, 162, 101, MARTIAN_WIDTH, MARTIAN_HEIGHT);
	// KEN
	public final static Texture KEN_PIXELS = new Texture(Gdx.files.internal("ken-pixel.png"));
	public final static TextureRegion KEN_PIXEL1 = new TextureRegion(KEN_PIXELS, 0, 0, 14, 10);
	public final static TextureRegion KEN_PIXEL2 = new TextureRegion(KEN_PIXELS, 15, 0, 14, 10);
	public final static TextureRegion KEN_PIXEL3 = new TextureRegion(KEN_PIXELS, 30, 0, 14, 10);
	
	
	public final static Texture KEN = new Texture(Gdx.files.internal("ken.png"));
	public final static TextureRegion KEN_RIGHT_TEXTURE1 = new TextureRegion(ITEMS, 0, 128, 32, 32);
	public final static TextureRegion KEN_RIGHT_TEXTURE2 = new TextureRegion(ITEMS, 32, 128, 32, 32);	
	public final static TextureRegion KEN_IDLE_TEXTURE1 = new TextureRegion(ITEMS, 64, 128, 32, 32);	
	public final static TextureRegion KEN_IDLE_TEXTURE2 = new TextureRegion(ITEMS, 96, 128, 32, 32);	
	public final static TextureRegion KEN_DEAD_TEXTURE = new TextureRegion(ITEMS, 128, 128, 32, 32);
	public final static TextureRegion ENEMY_TEXTURE = new TextureRegion(ITEMS, 166, 128, 32, 32);
	// SOUND
	private final static Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("click.wav"));
	private final static Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
	private final static Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
	private final static Sound shotSound = Gdx.audio.newSound(Gdx.files.internal("shot.wav"));
	private final static Sound gunEmptySound = Gdx.audio.newSound(Gdx.files.internal("gun-empty.wav"));
	private final static Sound gunReloadSound = Gdx.audio.newSound(Gdx.files.internal("gun-reload.wav"));
	private final static Sound explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
	
	public Asset() {
	}
	
	public static void playExplosionSound() {
		playSound(explosionSound);
	}
	
	public static void playGunReloadSound() {
		playSound(gunReloadSound);
	}
	
	public static void playGunEmptySound() {
		playSound(gunEmptySound);
	}
	
	public static void playShotSound() {
		playSound(shotSound);
	}
	
	public static void playDropSound() {
		playSound(dropSound);
	}
	
	public static void playBackgroundMusic() {
		rainMusic.setLooping(true);
		if (GameSettings.isSoundEnabled) rainMusic.play();
	}
	
	public static void playClickSound() {
		playSound(clickSound);
	}
	
	private static void playSound(Sound sound) {
		if (GameSettings.isSoundEnabled) sound.play(1);
	}
	
	public static void destroy() {
		MENU_IMAGE.dispose();
		DROP_IMAGE.dispose();
		ITEMS.dispose();
		// SOUND 
		clickSound.dispose();
		dropSound.dispose();
		rainMusic.dispose();
	}
}
