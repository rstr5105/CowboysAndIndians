package game.core;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Window;

import javax.swing.ImageIcon;

import game.graphicsEngine.WindowManager;

public abstract class GameCore {
	
	protected static final int FONT_SIZE = 24;
	
	private static final DisplayMode[] POSSIBLE_MODES={
		//new DisplayMode(800, 600, 32, 0),
		new DisplayMode(1366, 768, 32, 0)
	};

	private boolean isRunning;
	protected WindowManager wm;
	
	public void stop(){
		isRunning = false;
	}
	
	
	public void run(){
		try{
			init();
			gameLoop();
		}
		finally{
			wm.restoreScreen();
			exit();
		}
	}
	
	public void exit(){
		Thread thread = new Thread(){
			public void run(){
				try{
					Thread.sleep(2000);
				}
				catch(InterruptedException ex){
					System.exit(0);
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
	}
	
	public void init(){
		wm = new WindowManager();
		DisplayMode displayMode = wm.getFirstCompatibleMode(POSSIBLE_MODES);
		wm.setFullScreen(displayMode);
		Window window = wm.getFullScreenWindow();
		window.setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
		window.setBackground(Color.BLACK);
		window.setForeground(Color.WHITE);
		isRunning = true;
	}
	
	public Image loadImage(String filename){
		return new ImageIcon(filename).getImage();
	}
	
	public void gameLoop(){
		long startTime = System.currentTimeMillis();
		long currTime = startTime;
		
		while(isRunning){
			long elapsedTime = System.currentTimeMillis() - currTime;
			currTime += elapsedTime;
			
			update(elapsedTime);
			
			Graphics2D g = wm.getGraphics();
			draw(g);
			g.dispose();
			wm.update();
		}
	}
	
	public void update(long elapsedTime){
		//SKIP!
	}
	/**
	 * draws to the screen.  Sub-classes MUST OVERRIDE!
	 * @param g
	 */
	public abstract void draw(Graphics2D g);
}

