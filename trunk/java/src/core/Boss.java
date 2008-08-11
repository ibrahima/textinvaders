package core;



/**
 * @author Ibrahim
 *
 */
public class Boss extends Enemy {

	/**
	 * 
	 */
	public Boss() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param x
	 * @param y
	 * @param dmg
	 * @param shape
	 */
	public Boss(int x, int y, int dmg, String shape, int health) {
		super(x, y, dmg, shape, health);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see Collidable#collide(Collidable)
	 */
	@Override
	public void collide(Collidable other) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see Collidable#move()
	 */
	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

}
