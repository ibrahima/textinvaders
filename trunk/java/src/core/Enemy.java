package core;



/**
 * @author Ibrahim
 *
 */
public class Enemy extends Collidable {
	/**
	 * 
	 */
	int points;
	public Enemy() {
		// TODO Auto-generated constructor stub
		super("(-<+>-)");

	}
	public Enemy(int x, int y){
		super(x, y, 10, "<-(+)->", 20);
		speedx=2;
		speedy=1;
		points=10;
	}
	public Enemy(int x, int y, int health){
		super(x, y, 10, "<-(+)->", health);
		speedx=2;
		speedy=1;
		points=health;
	}	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param dmg
	 * @param shape
	 */
	public Enemy(int x, int y, int health, String shape) {
		super(x, y, health, shape, health);
		speedx=2;
		speedy=1;
		points=health;
		
	}
	
	public Enemy(int x, int y, int health, String shape, int points) {
		super(x, y, health, shape, health);
		speedx=2;
		speedy=1;
		this.points=points;
		
	}	
	/* (non-Javadoc)
	 * @see Collidable#collide(Collidable)
	 */
	@Override
	public void collide(Collidable other) {
		// TODO Auto-generated method stub
		health-=other.dmg;
	}

	/* (non-Javadoc)
	 * @see Collidable#move()
	 */
	@Override
	public void move() {
		x+=speedx;
		if(x>760||x<0){
			speedx*=-1;
		}
		y+=speedy;
		if(y>300||y<40){
			speedy*=-1;
		}
		// TODO Auto-generated method stub

	}

}
