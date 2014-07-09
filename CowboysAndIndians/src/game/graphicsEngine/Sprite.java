package game.graphicsEngine;

import java.awt.Image;
import java.awt.Point;

public class Sprite {
	protected Animation anim;
	
	private Point pos;

	
	public Sprite(Animation anim){
		this.anim = anim;
	}
	
	public void update(long elapsedTime){
		anim.update(elapsedTime);
	}
	
	
	public Image getImage() {
		return anim.getImage();
	}

	
	
	public int getWidth(){
		return anim.getImage().getWidth(null);
	}
	
	public int getHeight(){
		return anim.getImage().getHeight(null);
	}
	public Object clone(){
		return new Sprite(anim);
	}

	public int getX() {
		// TODO Auto-generated method stub
		return pos.x;
	}

	public int getY() {
		// TODO Auto-generated method stub
		return pos.y;
	}
	
}
