/**######
 * Game.Java
 *@author Robert
 *@version 0.0.1
 *Main Game class, handles the entire game.
 */

package game;
//Local Imports
import game.core.GameCore;
import game.input.GameAction;
import game.input.InputManager;
import game.world.World;

//Java Imports
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Point;


public class Game extends GameCore {
	
	private Point cache = new Point();
	private final static int SIZE_H = 64;
	private final static int SIZE_W = 256;
	private World world;
	private WorldMapRenderer renderer;
	private InputManager inputManager;
	
	
	private WorldMap map;
	
	private GameAction exit;
	private GameAction scrollMapLeft;
	private GameAction scrollMapRight;
	private GameAction scrollMapUp;
	private GameAction scrollMapDown;

	public static void main(String args[]){
		new Game().run();
		
	}
	public void init(){
		super.init();
		initInput();
		//create and start a new Resource Manager....Why?  Who Cares?!
		ResourceManager rM = new ResourceManager(wm.getFullScreenWindow().getGraphicsConfiguration());
		world = generateGameWorld(SIZE_H, SIZE_W);
		//load our resources!
		renderer = new WorldMapRenderer();
		map = new WorldMap(world, SIZE_H, SIZE_W);
		
		
	}
	public void stop(){
		super.stop();
	}
	private World generateGameWorld(int x, int y){
		World gameWorld = new World(x, y);
		return gameWorld;
	}
	@Override
	public void draw(Graphics2D g) {
		renderer.draw(g, map, wm.getWidth(), wm.getHeight());
		
	}
	
	public void initInput(){
		
		exit = new GameAction("exit");
		
		inputManager = new InputManager(wm.getFullScreenWindow());
		inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
		
	}
	
	public void checkInput(long elapsedTime){
		if (exit.isPressed()){
			stop();
		}
	}
	
	public void update(long elapsedTime){
		checkInput(elapsedTime);
	}
	
	
}

//###END GAME.JAVA
