package com.mnsoft.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public class MapManager {

	private OrthogonalTiledMapRenderer renderer;
	private ArrayList<Rectangle> collisionItems;
	private int[] backgroundLayers = { 0, 1, 2, 3 };
	private int[] foregroundLayers = { 4, 5 };
	
	public MapManager() {
		renderer = new OrthogonalTiledMapRenderer(Asset.TILED_MAP);
		generateCollisionItems();
	}
	
	public void setView(OrthographicCamera camera) {
		renderer.setView(camera);
	}
	
	public void renderBackground() {
		renderer.render(backgroundLayers);
	}
	
	public void renderForeground() {
		renderer.render(foregroundLayers);
	}
	
	private void generateCollisionItems() {
		TiledMapTileLayer tiledLayer = (TiledMapTileLayer)Asset.TILED_MAP.getLayers().get("collision");
		int columnXs = tiledLayer.getWidth();
		int rowYs = tiledLayer.getHeight();
		tiledLayer.setOpacity(0);
		collisionItems = new ArrayList<Rectangle>();
		for (int y = 0; y <= rowYs; y++) {
			for (int x = 0; x < columnXs; x++) {
				Cell cell = tiledLayer.getCell(x, y);
				if (cell != null) {
					Rectangle rect = new Rectangle();
					rect.set(x * 32, y * 32, 32, 32);
					collisionItems.add(rect);
				}
			}
		}
	}
}
