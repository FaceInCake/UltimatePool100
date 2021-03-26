package objects.poolballs;

import org.jogamp.vecmath.Color3f;

/**
 * Brown pool ball.
 * Subclass of Poolball.
 * @author Matthew Eppel
 */
public class BrownBall extends PoolBall {

	/**
	 * Default constructor, requring only a position
	 * @param x x-axis position of this ball to spawn and rest at
	 * @param z z-forward-axis position of this ball to spawn and rest at 
	 */
	public BrownBall (double x, double z) {
		super(
			new Color3f(0.375f, 0.1875f, 0.0625f),
			4,
			x, z
		);
	}

}
