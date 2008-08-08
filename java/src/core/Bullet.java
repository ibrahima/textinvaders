package core;


import java.util.*;
import java.awt.*;
public class Bullet extends Collidable{
	final int speed=10;
	final int angle;
	int turnsLeft;
	final long bulletID;
	Collidable owner;//should know who owns the bullet so we can delete it if necessary
	public Bullet(int x, int y, int angle, int dmg, Collidable owner){
		super(x,y,dmg,"||",10);
		this.angle=angle;
		bulletID=new Date().getTime();//each bullet should have its own unique ID so that you can remove them easily.
		turnsLeft=60;
		this.owner=owner;
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
		if(other==owner)return;//don't collide with owner
		if(owner instanceof Enemy && other instanceof Enemy)return;//enemies can't shoot each other
		health=0;
		if(other instanceof Enemy && owner instanceof Ship&&other.health<=0){
			((Ship)owner).tempscore+=((Enemy)other).points;
		}
	}
    public void draw (Graphics2D g2){
    	super.draw(g2);
    }
}