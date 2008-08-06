package core;




import java.util.*;

public class TextInvaders extends Thread{
	public Ship ship;
	GUI gui;
	public KeyBoardState kb;

    public TextInvaders(){
    	gui = new GUI();
    	kb=new KeyBoardState(gui);
    	ship = new Ship();
    	gui.addCollidable(ship);
    	gui.addCollidable(new Enemy());
    	this.start();
    }
    public void run(){
    	while(true){
    		//check keyboard and then move ship accordingly
    		if(kb!=null) {
    			ArrayList<String> keys=kb.keysDown();
    			Iterator<String> kiter=keys.iterator();			
    			while(kiter.hasNext()){
    				String k=kiter.next();
    				gui.addMsg(k);
    				if(k!=null&&k.equals("Up")) ship.up();
    				else if(k!=null&&k.equals("Down")) ship.down();
    				else if(k!=null&&k.equals("Left")) ship.left();
    				else if(k!=null&&k.equals("Right")) ship.right();
    				else if(k!=null&&k.equals("Space")) ship.shoot();
    			}
    		}
    		gui.update();
    	}
    }
}