package core;

import java.awt.geom.*;
import java.awt.*;
import java.util.*;

public class Ship extends Collidable{
	protected int invTimer=0;
	protected int bTimer;
	Point tip=new Point();
	private Vector<Bullet> bullets=new Vector<Bullet>();
	public Ship(){
		super(400,500,10,"_/|^|\\_",100);
	}
	public synchronized boolean removeBullet(long id){
		Iterator<Bullet> iter=getBullets();
		while(iter.hasNext()){
			if(iter.next().getID()==id){
				iter.remove();
				//System.out.println("Ship: Removed bullet "+id);
				return true;
			}
		}
		return false;
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
	public String toString(){
		return "Ship at ("+x+","+y+") "+health+ "hp "+angle+"degrees";
	}
	public synchronized void move(){
		invTimer--;
		bTimer--;

		if(y<30) y=30;
		if(y>561) y=561;
		if(x<7) x=7;
		if(x>760) x=760;
		if(speedx<-20) speedx=-20;
		if(speedx>20) speedx=20;

		Iterator<Bullet> iter=bullets.iterator();
		Bullet b;
		while(iter.hasNext()){
			b=iter.next();
			b.move();
			if(b.getY()<=0||b.getTL()<=0||b.health<=0)iter.remove();
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
		getPoly();
		bullets.add(new Bullet((int)tip.getX(),(int)tip.getY(),angle,5));
		bullets.lastElement().move();
		bTimer=10;
	}
	public Polygon getPoly(){
		Polygon p=new Polygon();
		p.addPoint(x+16,y);
		p.addPoint(x+32,y+32);
		p.addPoint(x,y+32);
        Point2D.Double c = getPolygonCenter(p);
        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(-(angle-90))/2, c.x, c.y);
        Shape l = at.createTransformedShape(p);
		PathIterator iter=l.getPathIterator(at);
		float[] pts= new float[6];
		p.reset();
		int i=0;
		while(!iter.isDone()){
			
	      	int type = iter.currentSegment(pts);
	      	switch(type){
	        case PathIterator.SEG_MOVETO :
	          //System.out.println("SEG_MOVETO");
	          p.addPoint((int)pts[0],(int)pts[1]);
	          if(i==0){tip.setLocation((int)pts[0],(int)pts[1]);}
	          break;
	        case PathIterator.SEG_LINETO :
	          //System.out.println("SEG_LINETO");
	          p.addPoint((int)pts[0],(int)pts[1]);
				if(i==0){tip.setLocation((int)pts[0],(int)pts[1]);}
	          break;
	      	}
	      	i++;
	      	iter.next();
    	}
    	return p;
    	
	}
    private Point2D.Double getPolygonCenter(Polygon poly)
    {
        // R + r = height
        Rectangle2D r2 = poly.getBounds2D();
        double cx = r2.getX() + r2.getWidth()/2;
        double cy = r2.getY() + r2.getHeight()/2;
        int sides = poly.xpoints.length;
        double side = Point2D.distance(poly.xpoints[0], poly.ypoints[0],
                                       poly.xpoints[1], poly.ypoints[1]);
        double R = side / (2 * Math.sin(Math.PI/sides));
        double r = R * Math.cos(Math.PI/sides);
        double dy = (R - r)/2;
        return new Point2D.Double(cx, cy + dy);
    }
	public int getAngle(){return angle;}
	public int getHealth(){return health;}
	public void setHealth(int health){this.health=health;}
	public void setX(int x){this.x=x;}
	public void setY(int y){this.y=y;}
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