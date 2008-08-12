package core;


import java.awt.Graphics2D;

public class Bullet extends Collidable{
	final int angle;
	Collidable owner;//should know who owns the bullet so we can delete it if necessary
	public Bullet(int x, int y, int angle, int dmg, Collidable owner){
		super(x,y,dmg,"||",10);
		this.angle=angle;
		this.owner=owner;
		this.speedy=5;
		this.speedx=0;
		//health=10;
	}
	@Override
	public void move(){
		y-=speedy;
		x+=speedx;
	}
	@Override
	public void collide(Collidable other){
		if(other==owner)return;//don't collide with owner
		if(owner instanceof Enemy && other instanceof Enemy)return;//enemies can't shoot each other
		health=0;
		if(other instanceof Enemy && owner instanceof Ship&&other.health<=0){
			((Ship)owner).tempscore+=((Enemy)other).points;
		}
	}
    @Override
	public void draw (Graphics2D g2){
    	super.draw(g2);
    }
}