package com.mnsoft.game;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HealthBar {

	private HashMap<Integer, Texture> healthMap;
	private Texture healthBackground;
	private int maxHealth;
	private int currentHealth;
	private int width;
	private int height;
	
	
	public HealthBar(int maxHealth, int width, int height) {
		// BACKGROUND
//		Pixmap pixmapHealth = new Pixmap(width, height, Format.Alpha);
//		pixmapHealth.setColor(Color.BLUE);
//		pixmapHealth.drawRectangle(0, 0, width, height);
//		healthBackground = new Texture(width, height, Format.Alpha);
//		healthBackground.draw(pixmapHealth, 0, 0);
		// MAP OF HEALTH
		this.width = width;
		this.height = height;
		healthMap = new HashMap<Integer, Texture>();
		for (int i = 1; i <= width; i++) {
			int healthRise = i;
			Pixmap pixmapHealth = new Pixmap(healthRise, height, Format.RGB565);
			pixmapHealth.setColor(Color.BLUE);
			pixmapHealth.drawRectangle(0, 0, healthRise, height);
			Texture healthBar = new Texture(healthRise, height, Format.RGB565);
			healthBar.draw(pixmapHealth, 0, 0);
			healthMap.put(i, healthBar);
		}
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
	}
	
	public void render(SpriteBatch batch, float x, float y) {
		if (currentHealth > 0) {
//			batch.draw(healthBackground, x, y);
			batch.draw(getHealthTexture(), x, y);
		}
	}
	
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public boolean isEmpty() {
		return (currentHealth <= 0);
	}
	
	public void increase(int amount) {
		currentHealth = currentHealth + amount;
	}
	
	public void decrease(int amount) {
		currentHealth = currentHealth - amount;
	}
	
	private Texture getHealthTexture() {
		return healthMap.get(getHealthMapKey());
	}
	
	private int getHealthMapKey() {
		int tenPercentHealth = maxHealth/ width;
		for (int i=width; i >= 1; i--) {
			if ((i * tenPercentHealth) <= currentHealth 
					&& currentHealth <= maxHealth) {
				return i;
			}
		}
		return 1;
	}
	
	
}
