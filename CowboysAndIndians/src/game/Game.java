/**######
 * Game.Java
 *@author Robert
 *@version 0.0.2
 *last edited: 7/06/2014
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
	
	private static final int SMALL = 64;
	private static final int MEDIUM = 128;
	private static final int LARGE = 256;
	private static final int HUGE = 512;
	private static final int OH_MY_GOD = 1024;
	
	private Point cache = new Point();
	private final static int SIZE_H = MEDIUM;
	private final static int SIZE_W = MEDIUM;
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
		String string = WorldMapRenderer.pixelsToTiles(map.getCenterX()) + " | " + WorldMapRenderer.pixelsToTiles(map.getCenterY());
		renderer.draw(g, map, wm.getWidth(), wm.getHeight());
		g.drawString(string, wm.getWidth() / 2 - string.length() / 2, wm.getHeight() / 2);
		
	}
	
	public void initInput(){
		
		exit = new GameAction("exit");
		scrollMapLeft = new GameAction("Scroll Map Left", GameAction.DETECT_INITAL_PRESS_ONLY);
		scrollMapRight = new GameAction("Scroll Map Right", GameAction.DETECT_INITAL_PRESS_ONLY);
		scrollMapUp = new GameAction("Scroll Map UP", GameAction.DETECT_INITAL_PRESS_ONLY);
		scrollMapDown = new GameAction("Scroll Map Down", GameAction.DETECT_INITAL_PRESS_ONLY);
		
		inputManager = new InputManager(wm.getFullScreenWindow());
		inputManager.mapToKey(scrollMapLeft, KeyEvent.VK_LEFT);
		inputManager.mapToKey(scrollMapRight, KeyEvent.VK_RIGHT);
		inputManager.mapToKey(scrollMapUp, KeyEvent.VK_UP);
		inputManager.mapToKey(scrollMapDown, KeyEvent.VK_DOWN);
		
		inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
		
	}
	
	public void checkInput(long elapsedTime){
		if (exit.isPressed()){
			stop();
		}
		
		if (inputManager.getMouseX() == 0){

			map.setCenterX(-5);
		}
		
		if (inputManager.getMouseX() == wm.getWidth() - 1){
			map.setCenterX(5);
		}
		
		if (inputManager.getMouseY() == 0){
			map.setCenterY(-5);
		}
		
		if (inputManager.getMouseY() == wm.getHeight() - 1){
			map.setCenterY(5);
		}
		
		if(scrollMapLeft.isPressed()){
			map.setCenterX(WorldMapRenderer.tilesToPixels(-3));
		}
		
		if(scrollMapRight.isPressed()){
			map.setCenterX(WorldMapRenderer.tilesToPixels(3));
		}
		if(scrollMapUp.isPressed()){
			map.setCenterY(WorldMapRenderer.tilesToPixels(-3));
		}
		
		if(scrollMapDown.isPressed()){
			map.setCenterY(WorldMapRenderer.tilesToPixels(3));
		}
	}
	
	public void update(long elapsedTime){
		checkInput(elapsedTime);
	}
	
	
}

//###END GAME.JAVA
