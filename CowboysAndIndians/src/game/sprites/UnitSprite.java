package game.sprites;

import game.graphicsEngine.Animation;
import game.graphicsEngine.Sprite;

import java.awt.Point;
import java.lang.reflect.Constructor;

/*Unit.java
 * @author Robert
 * Empty, placeholder class, that will be used later.
 */
public abstract class UnitSprite extends Sprite {

	
	private Animation anim;
	
	public UnitSprite(Animation anim){
		super(anim);
		this.anim =anim;
	}
	
	public Object clone(){
		Constructor<?> cTor = getClass().getConstructors()[0];
		try{
			return cTor.newInstance(new Object[]{
			(Animation)anim.clone(),
			});
		}
		catch(Exception ex){
			//should not happen.  Do Nothing.
			ex.printStackTrace();
			return null;
		}
	}

	public void update(long elapsedTime){
		Animation newAnim = anim;
		
	}
}

//###END UNIT###