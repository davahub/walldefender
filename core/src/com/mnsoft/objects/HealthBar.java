package com.mnsoft.objects;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HealthBar {

	private HashMap<Integer, Texture> healthMap;
	private Texture healthBackground;
	private int maxHealth;
	private int currentHealth;
	
	public HealthBar(int maxHealth) {
		// BACKGROUND
		Pixmap pixmapHealth = new Pixmap(30, 5, Format.Alpha);
		pixmapHealth.setColor(Color.BLUE);
		pixmapHealth.drawRectangle(0, 0, 30, 5);
		healthBackground = new Texture(30, 5, Format.Alpha);
		healthBackground.draw(pixmapHealth, 0, 0);
		// MAP OF HEALTH
		healthMap = new HashMap<Integer, Texture>();
		for (int i = 1; i <= 10; i++) {
			int healthRise = i * 3;
			pixmapHealth = new Pixmap(healthRise, 5, Format.RGB565);
			pixmapHealth.setColor(Color.BLUE);
			pixmapHealth.drawRectangle(0, 0, healthRise, 5);
			Texture healthBar = new Texture(healthRise, 5, Format.RGB565);
			healthBar.draw(pixmapHealth, 0, 0);
			healthMap.put(i, healthBar);
		}
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
	}
	
	public void render(SpriteBatch batch, float x, float y) {
		if (currentHealth > 0) {
			batch.draw(healthBackground, x, y);
			batch.draw(getHealthTexture(), x, y);
		}
	}
	
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
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
		int tenPercentHealth = maxHealth/ 10;
		for (int i=10; i >= 1; i--) {
			if ((i * tenPercentHealth) <= currentHealth 
					&& currentHealth <= maxHealth) {
				return i;
			}
		}
		return 1;
	}
}
