package core;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TextInvaders extends Thread{
	public Ship ship;
	GUI gui;
	public KeyBoardState kb;
	ArrayList<Enemy> enemies;
	static protected int money, score;
	protected GameState state=GameState.GAME;
	Menu mainmenu, shopmenu, pausemenu;
    public TextInvaders(){
    	gui = new GUI(this);
    	kb=new KeyBoardState(gui);
    	ship = new Ship();
    	gui.ship=this.ship;
    	enemies= new ArrayList<Enemy>(20);
    	for(int i=0;i<20;i++){
    		enemies.add(new Enemy(i*120%600,i/5*60+50));
    	}
    	gui.enemies=this.enemies;
    	this.start();
    	mainmenu = new Menu("Start Game,High Scores,Exit", 50, 50, new Font("SansSerif", Font.BOLD, 24));
    	pausemenu = new Menu("Resume,Exit", 350, 280, new Font("SansSerif", Font.BOLD, 24));
    }
    @Override
	public void run(){
    	while(true){
    		switch(state){
				case MAINMENU:
		        	//menu code
					break;
				case GAME:
		    		//check keyboard and then move ship accordingly
		    		gui.update();
		    		if(kb!=null) {
		    			HashMap<String, Boolean> keys=kb.keysDown();
		    			Iterator<String> kiter=keys.keySet().iterator();			
		    			while(kiter.hasNext()){
		    				String k=kiter.next();
		    				if(k!=null&&k.equals("Up")) ship.up();
		    				else if(k!=null&&k.equals("Down")) ship.down();
		    				else if(k!=null&&k.equals("Left")) ship.left();
		    				else if(k!=null&&k.equals("Right")) ship.right();
		    				else if(k!=null&&k.equals("Space")) ship.shoot();
		    				else if(k!=null&&k.equals("Escape")){
		    					if(!keys.get(k)){
			    					state=GameState.PAUSED;
			    					keys.put(k, true);
		    					}
		    				}
		    			}
		    		}
		    		if(ship!=null){
		    			ship.move();
		    		}
		    		//check for collisions
		        	Iterator<Enemy> enemIter=enemies.iterator();
		        	while(enemIter.hasNext()){
		        		//check for collisions with ship, then ship's bullets, then enemy's bullets with ship
		        		Enemy e=enemIter.next();
		        		e.move();
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
		        		if(e.health<=0)enemIter.remove();
		        	}
		        	score+=ship.tempscore;
		        	money+=ship.tempscore;
		        	ship.tempscore=0;
					break;
				case PAUSED:
		    		if(kb!=null) {
		    			HashMap<String, Boolean> keys=kb.keysDown();
		    			Iterator<String> kiter=keys.keySet().iterator();			
		    			while(kiter.hasNext()){
		    				String k=kiter.next();
		    				if(k!=null&&k.equals("Up")) {
		    					pausemenu.up();
		    					keys.remove(k);
		    				}
		    				
		    				else if(k!=null&&k.equals("Down")) {
		    					pausemenu.down();
		    					keys.remove(k);
		    				}
		    				else if(k!=null&&k.equals("Enter")){
		    					if(pausemenu.getPosition().equals("Resume")){
			    					state=GameState.GAME;
			    					keys.remove(k);
		    					}
		    					else if(pausemenu.getPosition().equals("Exit")){
		    						System.exit(1);
		    					}
		    				}
		    				else if(k!=null&&k.equals("Escape")){
		    					if(!keys.get(k)){
			    					state=GameState.GAME;
			    					keys.put(k, true);
		    					}
		    				}
		    			}
		    		}
		    		gui.update();
					break;
				default:
					gui.addMsg("Oops, unknown game state reached. Bug the developer.");
    		}
    	}
    }
}