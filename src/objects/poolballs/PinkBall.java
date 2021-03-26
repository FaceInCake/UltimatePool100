package objects.poolballs;

import org.jogamp.vecmath.Color3f;

/**
 * Pink pool ball.
 * Subclass of Poolball.
 * @author Matthew Eppel
 */
public class PinkBall extends PoolBall {

	/**
	 * Default constructor, requring only a position
	 * @param x x-right-axis position of this ball to spawn and rest at
	 * @param z z-forward-axis position of this ball to spawn and rest at 
	 */
	public PinkBall (double x, double z) {
		super(
			new Color3f(0.875f, 0.375f, 0.625f),
			6,
			x, z
		);
	}

}