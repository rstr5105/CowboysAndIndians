package game.sprites;

import java.lang.reflect.Constructor;

import game.graphicsEngine.Animation;
import game.graphicsEngine.Sprite;

/*Unit.java
 * @author Robert
 * Empty, placeholder class, that will be used later.
 */
public abstract class Unit extends Sprite {

	private static final int DIE_TIME = 1000; //Time it takes for a creature to die.
	
	private static final int STATE_ALIVE = 0;
	private static final int STATE_DYING = 1;
	private static final int STATE_DEAD = 2;
	
	private static final int NUM_OF_STATES = 3;
	
	private Animation left;
	private Animation right;
	private Animation up;
	private Animation down;
	private Animation dyingLeft;
	private Animation dyingRight;
	private Animation dyingUp;
	private Animation dyingDown;
	
	private int state;
	private int stateTime;
	
	public Unit(Animation left, Animation right, Animation up, Animation down, Animation dyingLeft, Animation dyingRight,
				Animation dyingUp, Animation dyingDown){
		super(right);
		this.left = left;
		this.right = right;
		this.down = down;
		this.up = up;
		state = STATE_ALIVE;
	}
	
	public Object clone(){
		Constructor<?> cTor = getClass().getConstructors()[0];
		try{
			return cTor.newInstance(new Object[]{
			(Animation)left.clone(),
			(Animation)right.clone(),
			(Animation)up.clone(),
			(Animation)down.clone(),
			(Animation)dyingLeft.clone(),
			(Animation)dyingRight.clone(),
			(Animation)dyingUp.clone(),
			(Animation)dyingDown.clone()
			});
		}
		catch(Exception ex){
			//should not happen.  Do Nothing.
			ex.printStackTrace();
			return null;
		}
	}
	
	public float getMaxSpeed(){
		return 0;
	}
	public void wakeup() {
		if (getState() == STATE_ALIVE && destination != null){
			moveTowardsDestination(destination);
		}
	}
	
	public int getState(){
		return state;
	}
	
	public void setState(int state){
		if(this.state != state){
			this.state = state;
			stateTime = 0;
			if (state == STATE_DYING){
				setVelocityX(0);
				setVelocityY(0);
			}
		}
	}
	
	public boolean isAlive(){
		return (state == STATE_ALIVE);
	}
	
	public void collideHorizontal(){
		setvelocityX(0);
	}
	
	public void collideVertical(){
		setVelocityY(0);
	}
	
	public void update(long elapsedTime){
		Animation newAnim = anim;
		if(getVelocityX < 0){
			anim = left;
		}
		
		else if (getVelocityY < 0){
			anim = up;
		}
		
		else if (getVelocityX > 0 ){
			anim = right;
		}
		else if (getVelocityY > 0){
			anim = down;
		}
		else if(getState() == STATE_DYING && newAnim == left){
			anim = dyingLeft;
		}
		else if(getState() == STATE_DYING && newAnim == right){
			anim = dyingRight;
		}
		else if(getState() == STATE_DYING && newAnim == up){
			anim = dyingUp;
		}
		else if(getState() == STATE_DYING && newAnim == down){
			anim = dyingDown;
		}
	}
}

//###END UNIT###