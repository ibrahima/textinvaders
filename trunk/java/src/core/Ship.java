package core;

import java.awt.Graphics2D;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Vector;



public class Ship extends Collidable{
	protected int invTimer=0;
	protected int bTimer;
	private Vector<Bullet> bullets=new Vector<Bullet>();
	int tempscore=0;
	public Ship(){
		super(400,500,10,"_/|^|\\_",100);
	}

	public void respawn(){
		if(health<=0) {
			java.util.Random r = new java.util.Random();
			x=r.nextInt(700);
			y=r.nextInt(500);
			health=100;
		}
	}
	public Ship(int x, int y, int health,int angle, int invTimer,int bTimer){
		super(x,y,health/4,"_/|^|\\_",health);
		this.angle=angle;
		this.invTimer=invTimer;
		this.bTimer=bTimer;
	}
	public Ship(int x, int y, int health){
		super(x,y,health/4,"_/|^|\\_",health);
	}	
	@Override
	public void collide(Collidable other){
		if(invTimer>0)return;
		health-=Math.abs(other.getDmg());
		invTimer=2;
	}
	public void up(){
		y-=5;
	}
	public void down(){
		y+=5;;
	}
	@Override
	public String toString(){
		return "Ship at ("+x+","+y+") "+health+ "hp "+angle+"degrees";
	}
	@Override
	public synchronized void move(){
		invTimer--;
		bTimer--;
		if(y<30) y=30;
		if(y>586) y=586;
		if(x<9) x=9;
		if(x>740) x=740;
		if(speedx<-20) speedx=-20;
		if(speedx>20) speedx=20;
		Iterator<Bullet> iter=bullets.iterator();
		Bullet b;
		while(iter.hasNext()){
			b=iter.next();
			b.move();
			if(b.getY()<=0||b.health<=0)iter.remove();
		}
	}
	public synchronized Iterator<Bullet> getBullets(){
		Iterator<Bullet> iter=bullets.iterator();
		return iter;
	}/**/
	public void left(){
		x-=5;
	}
	public void right(){
		x+=5;
	}
	public synchronized void shoot(){
		if(bTimer>0)return;
		bullets.add(new Bullet(x+width/2-3, y+2, angle, 5, this));
		bullets.lastElement().move();
		bTimer=20;
	}

	public int getAngle(){return angle;}
	public int getHealth(){return health;}
	public void setHealth(int health){this.health=health;}
	public void setX(int x){this.x=x;}
	public void setY(int y){this.y=y;}
	@Override
	public void draw(Graphics2D g2){
		super.draw(g2);
		try{
			Iterator<Bullet> bs=getBullets();
			while(bs.hasNext()){
				bs.next().draw(g2);
			}
		}
		catch(ConcurrentModificationException e){
		}		
	}
}