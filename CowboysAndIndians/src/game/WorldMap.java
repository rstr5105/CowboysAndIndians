package game;

import game.graphicsEngine.Sprite;
import game.world.World;

import java.awt.Image;
import java.util.LinkedList;
import java.util.Iterator;

import javax.swing.ImageIcon;


public class WorldMap {
	
	private Image[][] tiles;
	private LinkedList<Sprite> sprites;
	
	public WorldMap(World world, int height, int width){
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
	
	
}
