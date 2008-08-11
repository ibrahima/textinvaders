package core;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;


public class KeyBoardState implements KeyListener{
	HashMap<String,Boolean> keys=new HashMap<String, Boolean>(5);
	
	public KeyBoardState(Frame inputFrame){
		inputFrame.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e){
		if(!keys.containsKey(KeyEvent.getKeyText(e.getKeyCode()))) keys.put(KeyEvent.getKeyText(e.getKeyCode()),false);
 	}
 	public void keyReleased(KeyEvent e){
 		keys.remove(KeyEvent.getKeyText(e.getKeyCode()));
 	}
 	public void keyTyped(KeyEvent e){
 	}
 	public HashMap<String, Boolean> keysDown(){
 		return keys;
 	}
}