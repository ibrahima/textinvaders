package core;


import java.util.*;
import java.awt.*;
public class Bullet extends Collidable{
	final int speed=25;
	final int angle;
	int turnsLeft;
	final long bulletID;
	Collidable owner;//should know who owns the bullet so we can delete it if necessary
	public Bullet(int x, int y, int angle, int dmg){
		super(x,y,dmg,"||",10);
		this.angle=angle;
		bulletID=new Date().getTime();//each bullet should have its own unique ID so that you can remove them easily.
		turnsLeft=35;
		//health=10;
	}
	public long getID(){
		return bulletID;
	}
	public int getTL(){
		return turnsLeft;
	}
	public void move(){
		y-=speed*Math.sin(Math.toRadians(angle));
		x+=speed*Math.cos(Math.toRadians(angle));
		turnsLeft--;
	}
	public void collide(Collidable other){
		System.out.println("A bullet hit"+other);
		health=0;
//		this=null;
	}
    public void draw (Graphics2D g2){
    	super.draw(g2);
    }
}