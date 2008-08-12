package core;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;

public class GUI extends JFrame{
	//BufferedImage backbuffer=new BufferedImage(800,600,BufferedImage.TYPE_INT_ARGB );
	ArrayList<String> msgs=new ArrayList<String>(5);
	int fps=0,tfps=0;
	long lastTime, now, lastFrame;
	protected Ship ship;
	protected ArrayList<Enemy> enemies;
	TextInvaders logic;
	BufferStrategy strategy;
	public GUI(TextInvaders txt){
		super("Text Invaders");
		setBounds(0,0,800,600);
		setVisible(true);
		enemies=new ArrayList<Enemy>(20);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	logic=txt;
    	this.createBufferStrategy(2);
    	strategy=this.getBufferStrategy();
	}
	public void paint(Graphics g){
		//double buffering ftw
		Graphics2D graphics=(Graphics2D) strategy.getDrawGraphics();
		//fill background w/ dark gray
		graphics.setColor(Color.DARK_GRAY);
		graphics.fillRect(0, 0, 800, 600);
		if(ship!=null){
			ship.move();
			ship.draw(graphics);
		}
		Iterator<Enemy> enemIter = enemies.iterator();
		while(enemIter.hasNext()){
			Enemy cur=enemIter.next();
			cur.draw(graphics);
		}
		drawHUD(graphics);
		graphics.dispose();
		//paint backbuffer to window
		strategy.show();
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
	
	public void drawStarfield(Graphics2D g){
		
	}
	
	public void drawHUD(Graphics2D g){
		//draw health
		g.setColor(Color.GREEN);
        g.drawString("Health:", 9, 38);
        g.setPaint(Color.WHITE);
        g.fill3DRect(50,30,100,8,true);
		if(ship.health<=20) g.setPaint(Color.RED);
		else if(ship.health<=40) g.setPaint(Color.ORANGE);
		else if(ship.health<=60) g.setPaint(Color.YELLOW);
		else if(ship.health<=80) g.setPaint(Color.CYAN);
		else if(ship.health>80) g.setPaint(Color.GREEN);
		g.fill3DRect(50,30,ship.health,8,true);
		g.setColor(Color.GREEN);
		//draw score
        g.drawString("Score:"+TextInvaders.score, 500, 38);
		//draw money
        g.drawString("Money:"+TextInvaders.money, 580, 38);
		//calculate fps
		tfps++;
		now=System.currentTimeMillis();
		if((now-lastTime)>1000)//A second has passed
		{
			lastTime=now;
			fps=tfps;
			tfps=0;
		}
		/*if(now-lastFrame<1000/60){
			try{Thread.sleep(1000/60);} catch(InterruptedException e){}
		}*/
		lastFrame=now;
		g.setColor(Color.GREEN);
		g.drawString(fps+"FPS",750,40);		
		//output messages
		Iterator<String> msgIt=msgs.iterator();
		int my=580;
		while(msgIt.hasNext()){
			g.drawString(msgIt.next(),5,my);
			my-=15;
		}
		if(logic.state==GameState.PAUSED){
			g.drawString("PAUSED", 380, 290);
		}
	}
}
