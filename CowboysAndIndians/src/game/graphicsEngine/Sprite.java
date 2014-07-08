package game.graphicsEngine;

import java.awt.Image;

public class Sprite {
	protected Animation anim;

	
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
	
}
