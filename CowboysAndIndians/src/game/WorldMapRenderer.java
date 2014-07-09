/**
 * WorldMapRenderer.java
 *@author Robert
 * renders a worldMap to a displayable graphic
 */
package game;

//local imports
import game.graphicsEngine.Sprite;
import game.sprites.UnitSprite;
import game.units.Unit;


//java imports
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.Iterator;

public class WorldMapRenderer {
	
	private Point renderCenter = new Point();
	
	private int offsetX;
	private int offsetY;
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
		return (int)Math.floor((float)pixels / TILE_SIZE);
	}
	
	public static int tilesToPixels(int numOfTiles){
		
		return numOfTiles << TILE_SIZE_BITS;
		
	}
	
	public void draw(Graphics2D g, WorldMap map, int screenWidth, int screenHeight){
		
		int mapWidth = tilesToPixels(map.getWidth());
		int mapHeight = tilesToPixels(map.getHeight());
		

		
		int tilesX = pixelsToTiles(screenWidth); 
		int tilesY = pixelsToTiles(screenHeight);
		
		System.out.println(tilesX);
		System.out.println(tilesY);
		
		
		//Not quite sure how to implement this yet.
		setRenderCenterX((Math.min(getRenderCenterX(), 0)));
		setRenderCenterX((Math.max(getRenderCenterX(), screenWidth - mapWidth)));
		
		//get offsetY for drawing Sprites.
		
		setRenderCenterY((Math.min(getRenderCenterY(), 0)));
		setRenderCenterY((Math.max(getRenderCenterY(), screenHeight - mapHeight)));
				
		//draw the visible map.
		int firstTileX = pixelsToTiles(-getRenderCenterX());
		int firstTileY = pixelsToTiles(-getRenderCenterY());
		//Check to make sure the first tiles are within range...IE., BOUNDSCHECK.
		if (firstTileX < 0){
			firstTileX = 0;
			
		}
		
		if (firstTileY < 0){
			firstTileY = 0;
		}
		
		//set the last tile, and bounds check(ish) once again.
		int lastTileX = firstTileX + pixelsToTiles(screenWidth) + 1;
		int lastTileY = firstTileY + pixelsToTiles(screenHeight) + 1;
		if (lastTileX >= map.getWidth()){
			lastTileX = map.getWidth() - 1;
		}
		if (lastTileY >= map.getHeight()){
			lastTileY = map.getHeight() - 1;
		}
		//finally, here we are, drawing the map.
		for (int y = firstTileY; y <= lastTileY; y++){
			for(int x = firstTileX; x <= lastTileX; x++){
				Image image = map.getTile(y, x);
				if (image != null){
					g.drawImage(image, 
								tilesToPixels(x) + getRenderCenterX(),
								tilesToPixels(y) + getRenderCenterY(),
								null);
				}
			}
		}

		
		/*draw Sprites --(really do nothing for now, uncomment when you have unit animations and shit.  
		// right now we're just trying to draw the map.)
		Iterator i = map.getSprites();
		while(i.hasNext()){
			Sprite sprite = (Sprite)i.next();
			int x = Math.round(sprite.getX() + offsetX);
			int y = Math.round(sprite.getY() + offsetY);
			g.drawImage(sprite.getImage(), x, y, null);
		}
		*/
		
	}

	public static int getTileSize() {
		return TILE_SIZE;
	}
	public void setRenderCenterX(int x){
		renderCenter.x = x;
	}
	public void setRenderCenterY(int y){
		renderCenter.y = y;
	}
	
	public int getRenderCenterX(){
		return renderCenter.x;
	}
	
	public int getRenderCenterY(){
		return renderCenter.y;
	}
}

//###End WorldMap.java###