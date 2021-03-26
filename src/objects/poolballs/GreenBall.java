package objects.poolballs;

import org.jogamp.vecmath.Color3f;

/**
 * Green pool ball.
 * Subclass of Poolball.
 * @author Matthew Eppel
 */
public class GreenBall extends PoolBall {

	/**
	 * Default constructor, requring only a position
	 * @param x x-axis position of this ball to spawn and rest at
	 * @param z z-forward-axis position of this ball to spawn and rest at 
	 */
	public GreenBall (double x, double z) {
		super(
			new Color3f(0.125f, 0.5f, 0.125f),
			3,
			x, z
		);
	}

}
