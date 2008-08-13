package core;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;

public class GUI extends JFrame{
	private static final long serialVersionUID = 1L;
	//BufferedImage backbuffer=new BufferedImage(800,600,BufferedImage.TYPE_INT_ARGB );
	ArrayList<String> msgs=new ArrayList<String>(5);
	int fps=0,tfps=0;
	long lastTime, now, lastFrame;
	TextInvaders logic;
	BufferStrategy strategy;
	public GUI(TextInvaders txt){
		super("Text Invaders");
		setBounds(0,0,800,600);
    	//this.setUndecorated(true);//removes window borders, probably useful for a final version
		setVisible(true);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setIgnoreRepaint(true);
    	logic=txt;
    	this.createBufferStrategy(2);
    	strategy=this.getBufferStrategy();
	}
	@Override
	public void paint(Graphics g){
		//double buffering ftw
		Graphics2D graphics;
		try {
			graphics = (Graphics2D) strategy.getDrawGraphics();
		} catch (Exception e) {
			return;
		}

		//fill background w/ dark gray
		graphics.setColor(Color.DARK_GRAY);
		graphics.fillRect(0, 0, 800, 600);
		switch(logic.state){
		case MAINMENU:
			logic.mainmenu.draw(graphics);
			break;
		case PAUSED:
		case GAME:
			if(logic.ship!=null){
				logic.ship.draw(graphics);
			}
			Iterator<Enemy> enemIter = logic.enemies.iterator();
			while(enemIter.hasNext()){
				Enemy cur=enemIter.next();
				cur.draw(graphics);
			}
			drawHUD(graphics);
			break;
		case SHOP:
			logic.shopmenu.draw(graphics);
			drawHUD(graphics);
			break;
		}
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
		if(logic.ship.health<=20) g.setPaint(Color.RED);
		else if(logic.ship.health<=40) g.setPaint(Color.ORANGE);
		else if(logic.ship.health<=60) g.setPaint(Color.YELLOW);
		else if(logic.ship.health<=80) g.setPaint(Color.CYAN);
		else if(logic.ship.health>80) g.setPaint(Color.GREEN);
		g.fill3DRect(50,30,logic.ship.health,8,true);
		g.setColor(Color.GREEN);
        //draw wave #
		g.drawString("Wave: "+logic.wave, 440, 38);
		//draw score
        g.drawString("Score: "+logic.score, 500, 38);
		//draw money
        g.drawString("Money: "+logic.money, 570, 38);
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
			logic.pausemenu.draw(g);
		}
	}
}
