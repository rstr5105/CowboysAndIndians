package game.input;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	public static final int MOUSE_MOVE_LEFT = 0;
	public static final int MOUSE_MOVE_RIGHT = 1;
	public static final int MOUSE_MOVE_UP = 2;
	public static final int MOUSE_MOVE_DOWN = 3;
	public static final int MOUSE_WHEEL_UP = 4;
	public static final int MOUSE_WHEEL_DOWN = 5;
	public static final int MOUSE_BUTTON_1 = 6;
	public static final int MOUSE_BUTTON_2 = 7;
	public static final int MOUSE_BUTTON_3 = 8;
	
	public static final int MOUSE_CODES = 9;
	
	public static final int NUM_OF_KEY_CODES = 600;
	
	private GameAction[] keyActions = new GameAction[NUM_OF_KEY_CODES];
	private GameAction[] mouseActions = new GameAction[MOUSE_CODES];
	
	private Point mousePosition;
	private Point centerLocation;
	private Component comp;
	private Robot robot;
	private boolean isRecentering;
	
	public InputManager(Component comp){
		this.comp = comp;
		mousePosition = new Point();
		centerLocation = new Point();
		
		//register Listeners
		comp.addKeyListener(this);
		comp.addMouseListener(this);
		comp.addMouseMotionListener(this);
		comp.addMouseWheelListener(this);
	
		//tell focus travesal to take a hike!
		comp.setFocusTraversalKeysEnabled(false);	

	}
	
	public void setCursor(Cursor cursor){
		comp.setCursor(cursor);
	}
	
	public void setIsRelativeMouseMode(boolean mode){
		if(mode == isRelativeMouseMode()){
			return;
		}
		if (mode){
			try{
				robot = new Robot();
				recenterMouse();
			}
			catch(AWTException ex){
				System.out.println("Couldn't Create Robot!");
				robot = null;
			}
		}
		else{
			robot = null;
		}
	}
	
	private synchronized void recenterMouse() {
		if (robot != null && comp.isShowing()){
			centerLocation.x = comp.getWidth() / 2;
			centerLocation.y = comp.getHeight() / 2;
		}
	}

	public boolean isRelativeMouseMode(){
		return (robot != null);
	}
	
	public void mapToKey(GameAction action, int keyCode){
		keyActions[keyCode] = action;
	}
	
	public void mapToMouse(GameAction action, int mouseCode){
		mouseActions[mouseCode] = action;
	}
	
	public void clearActions(GameAction action){
		for (int i = 0; i < keyActions.length; i++ ){
			if (keyActions[i] == action){
				keyActions[i] = null;
			}
		}
		
		for (int i = 0; i < mouseActions.length; i ++){
			if (mouseActions[i] == action){
				mouseActions[i] = null;
			}
		}
		action.reset();
	}
	
	public List<String> getMaps(GameAction action){
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < keyActions.length; i++){
			if (keyActions[i] == action){
				list.add(getKeyName(i));
			}
		}
		for(int i = 0; i < mouseActions.length; i ++){
			if (mouseActions[i] == action){
				list.add(getMouseName(i));
			}
		}
		return list;
	}
	
	public void resetAllActions(){
		for(int i = 0; i < keyActions.length; i++){
			if(keyActions[i] != null){
				keyActions[i].reset();
			}
		}
		
		for (int i = 0; i < mouseActions.length; i++){
			if(mouseActions[i] != null){
				mouseActions[i].reset();
			}
		}
	}
	
	public static String getKeyName(int keyCode){
		return KeyEvent.getKeyText(keyCode);
	}
	
	public static String getMouseName(int mouseCode){
		switch(mouseCode){
		case MOUSE_MOVE_LEFT: return "Mouse Left";
		case MOUSE_MOVE_RIGHT: return "Mouse Right";
		case MOUSE_MOVE_DOWN: return "Mouse Down";
		case MOUSE_MOVE_UP: return "Mouse Up";
		case MOUSE_WHEEL_UP: return "Mouse Wheel Up";
		case MOUSE_WHEEL_DOWN: return "Mouse Wheel Down";
		case MOUSE_BUTTON_1: return "Button 1";
		case MOUSE_BUTTON_2: return "Button 2";
		case MOUSE_BUTTON_3: return "Button 3";
		default: return "Unknown Mouse Code" + mouseCode;
		}
	}
	
	public int getMouseX(){
		return mousePosition.x;
	}
	
	public int getMouseY(){
		return mousePosition.y;
	}
	
	private GameAction getKeyAction(KeyEvent e){
		int keyCode = e.getKeyCode();
		if (keyCode < keyActions.length){
			return keyActions[keyCode];
		}
		else{
			return null;
		}
	}
	
	public static int getMouseButtonCode(MouseEvent e){
		switch(e.getButton()){
			case MouseEvent.BUTTON1:
				return MOUSE_BUTTON_1;
			case MouseEvent.BUTTON2:
				return MOUSE_BUTTON_2;
			case MouseEvent.BUTTON3:
				return MOUSE_BUTTON_3;
			default:
					return -1;
		}
	}
	
	private GameAction getMouseButtonAction(MouseEvent e){
		int mouseButtonCode = getMouseButtonCode(e);
		if (mouseButtonCode != -1){
			return mouseActions[mouseButtonCode];
		}
		else{
			return null;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		GameAction action = getKeyAction(e);
		if(action != null){
			action.press();
		}
		e.consume();
	}
	
	@Override
	public void keyReleased(KeyEvent e){
		GameAction action = getKeyAction(e);
		if(action != null){
			action.release();
		}
		e.consume();
	}
	
	@Override
	public void keyTyped(KeyEvent e){
		e.consume();
	}
	
	public void mousePressed(MouseEvent e){
		GameAction action =getMouseButtonAction(e);
		if(action != null){
			action.press();
		}
	}
	
	public void mouseReleased(MouseEvent e){
		GameAction action = getMouseButtonAction(e);
		if(action != null){
			action.release();
		}
	}
	
	public void mouseClicked(MouseEvent e){
		//skip!!
	}
	
	public void mouseEntered(MouseEvent e){
		mouseMoved(e);
	}
	
	public void mouseExited(MouseEvent e){
		mouseMoved(e);
	}
	public void mouseDragged(MouseEvent e){
		mouseMoved(e);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(isRecentering &&
			centerLocation.x == e.getX() &&
			centerLocation.y == e.getY()){
			isRecentering = false;
		}
		else{
			int dx = e.getX() - centerLocation.x;
			int dy = e.getY() - centerLocation.y;
			
			mouseHelper(MOUSE_MOVE_LEFT, MOUSE_MOVE_RIGHT, dx);
			mouseHelper(MOUSE_MOVE_UP, MOUSE_MOVE_DOWN, dy);
			
			if(isRelativeMouseMode()){
				recenterMouse();
			}
		}
		mousePosition.x = e.getX();
		mousePosition.y = e.getY();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseHelper(MOUSE_MOVE_UP, MOUSE_MOVE_DOWN, e.getWheelRotation());
	}

	private void mouseHelper(int negative, int positive, int delta) {
		GameAction action;
		if(delta < 0){
			action = mouseActions[negative];
		}
		else{
			action = mouseActions[positive];
		}
		if(action != null){
			action.press(Math.abs(delta));
			action.release();
		}
	}
}
