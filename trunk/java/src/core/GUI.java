package core;


import java.awt.*;
import javax.swing.*;


import java.awt.image.BufferedImage;
import java.util.*;
public class GUI extends JFrame{
	BufferedImage backbuffer=new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB );
	ArrayList<String> msgs=new ArrayList<String>(5);
	int fps=0,tfps=0;
	long lastTime, now, lastFrame;
	Ship ship;
	ArrayList<Enemy> enemies;
	public GUI(){
		super("Text Invaders");
		setBounds(0,0,800,600);
		setVisible(true);
		enemies=new ArrayList<Enemy>(20);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void paint(Graphics g){
		//double buffering ftw
		Graphics2D bg=(Graphics2D)backbuffer.getGraphics();
		Graphics2D g2 = (Graphics2D)g;
		//fill background w/ cyan
		bg.setColor(Color.DARK_GRAY);
		bg.fillRect(0, 0, 800, 600);
		if(ship!=null){
			ship.move();
			ship.draw(bg);
		}
		Iterator<Enemy> enemIter = enemies.iterator();
		while(enemIter.hasNext()){
			Enemy cur=enemIter.next();
			cur.move();
			cur.draw(bg);
		}
		//calculate fps
		tfps++;
		now=new Date().getTime();
		if((now-lastTime)>1000)//A second has passed
		{
			lastTime=now;
			fps=tfps;
			tfps=0;
		}
		if(now-lastFrame<1000/60){
			try{Thread.sleep(1000/60);} catch(InterruptedException e){}
		}
		lastFrame=now;
		bg.setColor(Color.GREEN);
		bg.drawString(fps+"FPS",750,40);		
		//output messages
		Iterator<String> msgIt=msgs.iterator();
		int my=580;
		while(msgIt.hasNext()){
			bg.drawString(msgIt.next(),5,my);
			my-=15;
		}
		//paint backbuffer to window
		g2.drawImage(backbuffer,null,0,0);
	}
	public void update(){
		paint(this.getGraphics());
	}

	public synchronized void addMsg(String msg){
		msgs.add(msg);
		while(msgs.size()>=6){
			msgs.remove(0);
		}
	}
	void addCollidable(Collidable c){
		if(c instanceof Ship)ship=(Ship)c;
		else if(c instanceof Enemy) enemies.add((Enemy)c);
	}

}
