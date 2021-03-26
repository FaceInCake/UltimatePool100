package objects.poolballs;

import org.jogamp.vecmath.Color3f;

/**
 * Yellow pool ball.
 * Subclass of Poolball.
 * @author Matthew Eppel
 */
public class YellowBall extends PoolBall {

	/**
	 * Default constructor, requring only a position
	 * @param x x-right-axis position of this ball to spawn and rest at
	 * @param z z-forward-axis position of this ball to spawn and rest at 
	 */
	public YellowBall (double x, double z) {
		super(
			new Color3f(0.75f, 0.75f, 0.125f),
			2,
			x, z
		);
	}

}
