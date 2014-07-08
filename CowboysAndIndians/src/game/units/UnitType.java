package game.units;
import game.graphicsEngine.*;

public enum UnitType {
	UNIT1(left, right, up, down, dyingLeft, dyingRight, dyingUp, dyingDown);
	Animation left; 
	Animation right; 
	Animation  up;
	Animation  down; 
	Animation  dyingLeft; 
	Animation  dyingRight;
	Animation  dyingUp;
	Animation  dyingDown;
	
	private UnitType(Animation left, 
						Animation right, 
						Animation  up, 
						Animation  down, 
						Animation  dyingLeft, 
						Animation  dyingRight, 
						Animation  dyingUp, 
						Animation  dyingDown){
		this.left = left;
		this.right = right;
		this.up = up;
		this.down = down;
		this.dyingLeft = dyingLeft;
		this.dyingRight = dyingRight;
		this.dyingUp = dyingUp;
		this.dyingDown = dyingDown;
	}
	
	
}
