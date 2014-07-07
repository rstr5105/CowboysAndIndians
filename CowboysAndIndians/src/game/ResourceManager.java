package game;

import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import game.graphicsEngine.Sprite;
import game.world.*;


public class ResourceManager {

	private ArrayList<Image> tiles;
	private GraphicsConfiguration gc;
	
	public ResourceManager(GraphicsConfiguration gc){
		this.gc = gc;
		loadTileImage();
		LoadUnitSprites();
	}
	
	private void LoadUnitSprites() {
		// TODO Auto-generated method stub
		
	}

	public void loadTileImage(){
		tiles = new ArrayList<Image>();
		for(TileType type : TileType.values()){
			System.out.println("GOT HERE!" + type.toString());
			File file = new File(type.tileImage);
			if(!file.exists()){
				System.out.println("ERROR!  FILE DOES NOT EXIST: " + file.toString());
			
				break;
			}
			
			tiles.add(loadImage(type.tileImage));
		}
	}

	private Image loadImage(String name) {
		// TODO Auto-generated method stub
		return new ImageIcon(name).getImage();
	}
	
}
