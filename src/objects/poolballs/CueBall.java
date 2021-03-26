package objects.poolballs;

import org.jogamp.vecmath.Color3f;

/**
 * Cue ball.
 * Subclass of PoolBall.
 * @author Matthew Eppel
 */
public class CueBall extends PoolBall {

	/**
	 * Default constructor, requring only a position
	 * @param x x-axis position of this ball to spawn and rest at
	 * @param z z-forward-axis position of this ball to spawn and rest at 
	 */
	public CueBall(double x, double z) {
		super(
			new Color3f(0.875f, 0.875f, 0.75f),
			0,
			x, z
		);
	}

}
