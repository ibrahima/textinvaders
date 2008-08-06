package core;




import java.util.*;

public class TextInvaders extends Thread{
	public Ship ship;
	GUI gui;
	public KeyBoardState kb;
	ArrayList<Enemy> enemies;
    public TextInvaders(){
    	gui = new GUI();
    	kb=new KeyBoardState(gui);
    	ship = new Ship();
    	gui.ship=this.ship;
    	enemies= new ArrayList<Enemy>(20);
    	for(int i=0;i<20;i++){
    		enemies.add(new Enemy(i*120%600,i/5*60+50));
    	}
    	gui.enemies=this.enemies;
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
    		//check for collisions
        	Iterator<Enemy> enemIter=enemies.iterator();
        	while(enemIter.hasNext()){
        		//check for collisions with ship, then ship's bullets, then enemy's bullets with ship
        		Enemy e=enemIter.next();
        		if(ship.checkCollision(e)){
        			e.collide(ship);
        			ship.collide(e);
        		}
        		//ship's bullets
        		Iterator<Bullet> bs=ship.getBullets();
        		while(bs.hasNext()){
            		Bullet b=bs.next();
            		if(e.checkCollision(b)){
            			e.collide(b);
            			b.collide(e);
            		}        			
        		}
        		//TODO: enemy's bullets with ship
        	}
    	}
    	
    }
}