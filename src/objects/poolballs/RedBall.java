package objects.poolballs;

import org.jogamp.vecmath.Color3f;

/**
 * Red pool ball.
 * Subclass of Poolball.
 * @author Matthew Eppel
 */
public class RedBall extends PoolBall {

	/**
	 * Default constructor, requring only a position
	 * @param x x-right-axis position of this ball to spawn and rest at
	 * @param z z-forward-axis position of this ball to spawn and rest at 
	 */
	public RedBall (double x, double z) {
		super(
			new Color3f(0.875f, 0.0625f, 0.0625f),
			1,
			x, z
		);
	}

}
