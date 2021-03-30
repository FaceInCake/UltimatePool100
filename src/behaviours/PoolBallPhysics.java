package behaviours;

import java.util.Iterator;

import org.jogamp.java3d.Behavior;
import org.jogamp.java3d.WakeupCriterion;
import objects.poolballs.PoolBall;

/**
 * Class for moving and colliding pool balls
 * @author Matthew Eppel
 *
 */
public class PoolBallPhysics extends Behavior {
	private static PoolBall [] poolballs = new PoolBall [21];
	private static int poolballCnt = 0;

	/**
	 * 
	 */
	public PoolBallPhysics() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void processStimulus(Iterator<WakeupCriterion> arg0) {
		// TODO Auto-generated method stub

	}

}
