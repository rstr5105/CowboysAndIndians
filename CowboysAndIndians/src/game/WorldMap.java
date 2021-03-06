/*
 * WorldMap.Java
 * @author Robert
 * Version 1
 */
package game;

import game.graphicsEngine.Sprite;
import game.world.World;

import java.awt.Image;
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;


public class WorldMap {
	
	private Image[][] tiles;
	private LinkedList<Sprite> sprites;
	private Point centerPoint;
	
	public WorldMap(World world, int height, int width){
		centerPoint = new Point();
		centerPoint.x = WorldMapRenderer.tilesToPixels(width) / 2;
		centerPoint.y = WorldMapRenderer.tilesToPixels(height) / 2;
		
		tiles = new Image[height][width];
		for(int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				setTile(y, x, world.getTile(y, x).getImageIcon());
			}
		}
		
	}
	
	public void setTile(int y, int x, ImageIcon icon){
		tiles[y][x] = icon.getImage();
	}
	
	public Image getTile(int y, int x){
		if (x < 0 || x >= getWidth()
			|| y < 0 || y>= getHeight()){
			return null;
		}
		else{
			return tiles[y][x];
		}
	}
	
	public int getWidth(){
		return tiles[0].length;
	}
	
	public int getHeight(){
		return tiles.length;
	}

	public void addSprite(Sprite sprite){
		sprites.add(sprite);
	}
	
	public void removeSprite(Sprite sprite){
		sprites.remove(sprite);
	}
	
	
	
	public Iterator<Sprite> getSprites() {
		// TODO Auto-generated method stub
		return sprites.iterator();
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
	}
	
	public int getCenterX(){
		return centerPoint.x;
	}
	
	public int getCenterY(){
		return centerPoint.y;
	}
	public void setCenterX(int delta){
		centerPoint.x +=  (delta);
	}
	public void setCenterY(int delta){
		centerPoint.y += (delta);
	}
	public void resetCenterX(int x){
		centerPoint.x = x;
	}
	public void resetCenterY(int y){
		centerPoint.y = y;
	}
	
}
//###End WorldMap.java