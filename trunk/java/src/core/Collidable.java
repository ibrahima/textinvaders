package core;

import java.awt.*;
import java.awt.font.FontRenderContext;

public abstract class Collidable{
	protected int x, y, width, height;
	protected int speedx,speedy;
	protected int angle=90;
	protected int dmg;
	protected Rectangle bounds;
	protected String shape;
	protected static Font smallFont, bigFont;
	FontRenderContext frc;
	public Collidable(){
		x=0;
		y=50;
		shape="/\\";
	}
	public Collidable(String shape){
		x=0;
		y=50;
		this.shape=shape;
	}
	public Collidable(int x, int y, int dmg, String shape){
		this.x=x;
		this.y=y;
		this.dmg=dmg;
		this.shape=shape;
	}
	public int getDmg(){
		return dmg;
	}
	public Rectangle getRect(){
		return bounds;
	}
	public boolean checkCollision(Collidable other){
		return this.getRect().intersects(other.getRect());
	}
	public abstract void move();
	public abstract void collide(Collidable other);
	public int getX(){return x;}
	public int getY(){return y;}
	public void draw(Graphics2D g2){
		if(smallFont==null){
			smallFont=g2.getFont();
			bigFont=smallFont.deriveFont((float)18);
		}
		if(frc==null)frc=g2.getFontRenderContext();
		g2.setFont(bigFont);
		if(width==0||height==0||bounds==null){
			width=(int)bigFont.getStringBounds(shape, frc).getWidth();
			height=(int)bigFont.getStringBounds(shape, frc).getWidth();
			bounds = new Rectangle(x,y,width,height);
		}
		bounds.setLocation(x, y) ;
		g2.setPaint(Color.WHITE);
		g2.drawString(shape,x,y);
		g2.setFont(smallFont);
	}
}