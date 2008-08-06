package core;

import java.awt.event.*;
import java.util.*;
import java.awt.*;
public class KeyBoardState implements KeyListener{
	LinkedList<String> keys=new LinkedList<String>();
	
	public KeyBoardState(Frame inputFrame){
		inputFrame.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e){
		if(!keys.contains(KeyEvent.getKeyText(e.getKeyCode()))) keys.add(KeyEvent.getKeyText(e.getKeyCode()));
 	}
 	public void keyReleased(KeyEvent e){
 		keys.remove(KeyEvent.getKeyText(e.getKeyCode()));
 	}
 	public void keyTyped(KeyEvent e){
 	}
 	public ArrayList<String> keysDown(){
 		return new ArrayList<String>(keys);
 	}
}