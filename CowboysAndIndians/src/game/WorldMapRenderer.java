package game;

import java.awt.Graphics2D;
import java.awt.Image;

public class WorldMapRenderer {
	private static final int TILE_SIZE = 64;
	/**
	 * The Size in Bits of Tile.
	 * Math.pow(2, TILE_SIZE_BITS) == TILE_SIZE;
	 */
	private static final int TILE_SIZE_BITS = 6;
	
	public static int pixelsToTiles(float pixels){
		return pixelsToTiles(Math.round(pixels));
	}
	
	public static int pixelsToTiles(int pixels){
		return pixels >> TILE_SIZE_BITS;
	}
	
	public static int tilesToPixels(int numOfTiles){
		
		return numOfTiles << TILE_SIZE_BITS;
		
	}
	
	public void draw(Graphics2D g, WorldMap map, int screenWidth, int screenHeight){
		
		int mapWidth = tilesToPixels(map.getWidth());
		int mapHeight = tilesToPixels(map.getHeight());
		
		//Not quite sure how to implement this yet.
		int offsetX = ((screenWidth / 2) - (mapWidth / 2));
		//offsetX = Math.min(offsetX, 0);
		//offsetX = Math.max(offsetX, screenWidth - mapWidth);
		
		
		
		//get offsetY for drawing Sprites.
		int offsetY = ((screenHeight / 2) - (mapHeight / 2));
		//offsetY = Math.min(offsetY, 0);
		//offsetY = Math.max(offsetY, screenHeight - mapHeight);
			
		
		//draw the visible map.
		int firstTileX = pixelsToTiles(-offsetX);
		int firstTileY = pixelsToTiles(-offsetY);
		if (firstTileX < 0){
			firstTileX = 0;
		}
		if (firstTileY < 0){
			firstTileY = 0;
		}
		
		int lastTileX = firstTileX + pixelsToTiles(screenWidth) + 1;
		int lastTileY = firstTileY + pixelsToTiles(screenHeight) + 1;
		if (lastTileX >= map.getWidth()){
			lastTileX = map.getWidth() - 1;
		}
		if (lastTileY >= map.getHeight()){
			lastTileY = map.getHeight() - 1;
		}
		for (int y = firstTileY; y <= lastTileY; y++){
			for(int x = firstTileX; x <= lastTileX; x++){
				Image image = map.getTile(y, x);
				if (image != null){
					g.drawImage(image, 
								tilesToPixels(x) + offsetX,
								tilesToPixels(y) + offsetY,
								null);
				}
			}
		}
		
		/*
		//draw Sprites --(really do nothing for now, uncomment when you have unit animations and shit.  
		 * right now we're just trying to draw the map.)
		Iterator i = map.getSprites();
		while(i.hasNext()){
			Sprite sprite = (Sprite)i.next();
			int x = Math.round(sprite.getX() + offsetX);
			int y = Math.round(sprite.getY() + offsetY);
			g.draw(sprite.getImage(), x, y, null);
		
			//wake up the unit if its on screen.
			if (sprite instanceof Unit && x >= 0 && x < screenWidth && y >= 0 && y < screenHeight){
				((Unit)sprite).wakeup();
			}
		}
		*/
	}
}
