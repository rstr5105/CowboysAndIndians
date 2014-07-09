/**######
 * Game.Java
 *@author Robert
 *@version 0.0.1 (Super-Pre-Alpha Edition)
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

/*
 * Game Class.  This class is responsible for all elements of the game.  
 * Most of this class is implemented in game.core.GameCore and inheirited here.
 * Welcome to the suck.   
 */


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
	private WorldMap map;
	private WorldMapRenderer renderer;
	private InputManager inputManager;	
	
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
		//create and start a new Resource Manager.  The resource Manager is responsible for loading game elements.
		ResourceManager rM = new ResourceManager(wm.getFullScreenWindow().getGraphicsConfiguration());
		world = generateGameWorld(SIZE_H, SIZE_W);
		//load our resources!
		renderer = new WorldMapRenderer();
		map = new WorldMap(world, SIZE_H, SIZE_W);
		//initialize the renderer at the center of the map(Or of course, whereever the hell we want to really)
		renderer.setRenderCenterX(wm.getWidth() / 2 - map.getCenterX());
		renderer.setRenderCenterY(wm.getHeight() / 2 - map.getCenterY());
		
		
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
		String string = WorldMapRenderer.pixelsToTiles(map.getCenterX()) + " | " + WorldMapRenderer.pixelsToTiles(map.getCenterY()) 
			+ "\n" + renderer.getRenderCenterX() + "|" + renderer.getRenderCenterY();
		
		renderer.draw(g, map, wm.getWidth(), wm.getHeight());
		g.drawString(string, 0, FONT_SIZE);
		
	}
	
	public void initInput(){
		
		//Create Some Game Actions.  These will help when we're checking our input here in just a second.  
		exit = new GameAction("exit");
		scrollMapLeft = new GameAction("Scroll Map Left", GameAction.DETECT_INITAL_PRESS_ONLY);
		scrollMapRight = new GameAction("Scroll Map Right", GameAction.DETECT_INITAL_PRESS_ONLY);
		scrollMapUp = new GameAction("Scroll Map UP", GameAction.DETECT_INITAL_PRESS_ONLY);
		scrollMapDown = new GameAction("Scroll Map Down", GameAction.DETECT_INITAL_PRESS_ONLY);
		
		//create a new InputManager to Handle KeyMapping.
		inputManager = new InputManager(wm.getFullScreenWindow());
		inputManager.mapToKey(scrollMapLeft, KeyEvent.VK_LEFT);
		inputManager.mapToKey(scrollMapRight, KeyEvent.VK_RIGHT);
		inputManager.mapToKey(scrollMapUp, KeyEvent.VK_UP);
		inputManager.mapToKey(scrollMapDown, KeyEvent.VK_DOWN);
		
		//Finally...No more alt-tabbing + Terminating the program.  This just shuts us down...like now.
		inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
		
	}
	
	public void checkInput(long elapsedTime){
		if (exit.isPressed()){
			stop();
		}
		
		//Mouse Scrolling Implemented Here.
		if (inputManager.getMouseX() == 0){
			renderer.setRenderCenterX(renderer.getRenderCenterX() + 5);		
			}
		
		//More Mouse Scrolling
		if (inputManager.getMouseX() == wm.getWidth() - 1){
			renderer.setRenderCenterX(renderer.getRenderCenterX() - 5);
		}
		
		//And Some More.
		if (inputManager.getMouseY() == 0){
			renderer.setRenderCenterY(renderer.getRenderCenterY() + 5);;
		}
		
		//And Finally, The Last Mouse Scroll.
		if (inputManager.getMouseY() == wm.getHeight() - 1){
			renderer.setRenderCenterY(renderer.getRenderCenterY() - 5);;
		}
		
		//Keyboard Scrolling.
		if(scrollMapLeft.isPressed()){
			renderer.setRenderCenterX(renderer.getRenderCenterX() + WorldMapRenderer.tilesToPixels(3));
		}
		
		if(scrollMapRight.isPressed()){
			renderer.setRenderCenterX(renderer.getRenderCenterX() - WorldMapRenderer.tilesToPixels(3));
		}
		if(scrollMapUp.isPressed()){
			renderer.setRenderCenterY(renderer.getRenderCenterY() + WorldMapRenderer.tilesToPixels(3));
		}
		
		if(scrollMapDown.isPressed()){
			renderer.setRenderCenterY(renderer.getRenderCenterY() - WorldMapRenderer.tilesToPixels(3));
		}
		//End of Keyboard Scrolling.
		//More Actions to Go Here.
	}
	
	public void update(long elapsedTime){
		//Updates the game status.  Right now, the only thing to do is check for input.
		checkInput(elapsedTime);
	}
	
	
}

//###END GAME.JAVA
