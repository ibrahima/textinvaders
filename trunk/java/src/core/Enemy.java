package core;



/**
 * @author Ibrahim
 *
 */
public class Enemy extends Collidable {
	/**
	 * 
	 */
	public Enemy() {
		// TODO Auto-generated constructor stub
		super("<-(+)->");
		speedx=4;
		speedy=1;
	}
	public Enemy(int x, int y){
		super(x, y, 10, "<-(+)->", 20);
		speedx=4;
		speedy=1;
	}
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param dmg
	 * @param shape
	 */
	public Enemy(int x, int y, int dmg, String shape) {
		super(x, y, dmg, shape, 20);
		speedx=4;
		speedy=1;
		// TODO Auto-generated constructor stub (-<+>-)
		
	}

	/* (non-Javadoc)
	 * @see Collidable#collide(Collidable)
	 */
	@Override
	public void collide(Collidable other) {
		// TODO Auto-generated method stub
		health-=other.dmg;
		System.out.println(this+" has "+health+"HP");

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
