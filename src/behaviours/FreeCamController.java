package behaviours;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Iterator;
import org.jogamp.java3d.Behavior;
import org.jogamp.java3d.BoundingSphere;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.WakeupCriterion;
import org.jogamp.java3d.WakeupOnElapsedFrames;
import org.jogamp.vecmath.Matrix3d;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Quat4d;
import org.jogamp.vecmath.Vector3d;
import jpanels.BasicView;

/**
 * Allows the moving and rotating of a ViewTransform, AKA a camera.
 * > Use W & S to move forward and back
 * > Use A & D to move left and right
 * > Use Space & Shift to move up and down
 * > Drag the mouse to rotate the camera
 * @implNote Create this object, passing in the BasicView object.
 * Then add this to the content branch.
 * It will add itself to the KeyListener and MouseListener list.
 */
public class FreeCamController extends Behavior
implements
java.awt.event.KeyListener,
java.awt.event.MouseMotionListener,
java.awt.event.MouseListener {
	private static double movSpeed = 2.0; // Units per seconds
	private static double angSpeed = 2.0; // Radians per second
	private static WakeupCriterion wakecon = new WakeupOnElapsedFrames(0);
	private static double PI_2 = Math.PI/2.0;
	private TransformGroup targetTG;
	private Transform3D targetT;
	private HashSet<Integer> keys;
	private Vector3d forward, right, up;
	private double viewYaw;
	private double viewPitch;
	private Vector3d viewPos;
	private int lastMX; // Last mouse x pos, used when mouse dragging
	private int lastMY; // Last mouse y pos, used when mouse dragging


	public FreeCamController (BasicView view) {
		super();
		this.keys = new HashSet<>(4);
		this.targetTG = view.getViewTransformGroup();
		view.getCanvas().addKeyListener(this);
		view.getCanvas().addMouseMotionListener(this);
		view.getCanvas().addMouseListener(this);
		this.targetT = new Transform3D();
		this.targetTG.getTransform(this.targetT);
		Quat4d q = new Quat4d();
		this.viewPos = new Vector3d();
		this.targetT.get(q, this.viewPos);
		this.viewYaw = Math.atan2(2*q.y*q.w-2*q.x*q.z , 1 - 2*q.y*q.y - 2*q.z*q.z);
		this.viewPitch = -Math.asin(2.0 * (q.w*q.y + q.x*q.z));
		this.forward = new Vector3d();
		this.right = new Vector3d();
		this.up = new Vector3d();
		this.lastMX = 0;
		this.lastMY = 0;
		updateDirs();
		updateTargetTG();
		BoundingSphere bounds = new BoundingSphere(new Point3d(), 100.0);
		super.setSchedulingBounds(bounds);
		super.setEnable(true);

	}
	
	public Vector3d getPos () {return this.viewPos;}
	public double getYaw () {return this.viewYaw;}
	public double getPitch () {return this.viewPitch;}
	public Vector3d getForward () {return this.forward;}
	public Vector3d getUp () {return this.up;}
	public Vector3d getRight () {return this.right;}

	@Override
	public void initialize() {
		super.wakeupOn(wakecon);
	}
	
	private void updateTargetTG () {
		Matrix3d m1=new Matrix3d(), m2=new Matrix3d();
		m1.rotY(viewYaw); m2.rotX(viewPitch); m1.mul(m2);
		targetT.setRotation(m1);
		targetT.setTranslation(viewPos);
		targetTG.setTransform(targetT);
	}
	
	private void updateDirs () {
		forward.set(-Math.sin(viewYaw)*Math.cos(viewPitch), -Math.sin(-viewPitch), -Math.cos(viewYaw)*Math.cos(viewPitch));
		right.set(Math.cos(-viewYaw), 0, Math.sin(-viewYaw));
		up.cross(right, forward);
	}

	/**
	 * Checks key presses and updates the ViewTransform
	 */
	@Override
	public void processStimulus(Iterator<WakeupCriterion> arg0) {
		// Time difference in seconds
		double dt = 0.016;
		// Check movement
		if (keys.contains(KeyEvent.VK_W)) viewPos.scaleAdd(+movSpeed*dt, forward, viewPos);
		if (keys.contains(KeyEvent.VK_A)) viewPos.scaleAdd(-movSpeed*dt, right, viewPos);
		if (keys.contains(KeyEvent.VK_D)) viewPos.scaleAdd(+movSpeed*dt, right, viewPos);
		if (keys.contains(KeyEvent.VK_S)) viewPos.scaleAdd(-movSpeed*dt, forward, viewPos);
		if (keys.contains(KeyEvent.VK_SPACE)) viewPos.scaleAdd(+movSpeed*dt, up, viewPos);
		if (keys.contains(KeyEvent.VK_SHIFT)) viewPos.scaleAdd(-movSpeed*dt, up, viewPos);
		updateTargetTG(); // Apply changes
		super.wakeupOn(wakecon);
	}

	@Override
	public void keyPressed(java.awt.event.KeyEvent arg0) {
		this.keys.add(arg0.getKeyCode());
	}

	@Override
	public void keyReleased(java.awt.event.KeyEvent arg0) {
		this.keys.remove(arg0.getKeyCode());
	}

	@Override
	public void keyTyped(java.awt.event.KeyEvent arg0) {}

	@Override
	public void mouseDragged(java.awt.event.MouseEvent arg0) {
		int difX = this.lastMX - arg0.getX();
		int difY = this.lastMY - arg0.getY();
		this.lastMX = arg0.getX();
		this.lastMY = arg0.getY();
		// Moving across half the screen rotates 45deg or pi/4 radians
		viewYaw += PI_2 * difX / arg0.getComponent().getWidth();
		viewPitch += PI_2 * difY / arg0.getComponent().getHeight();
		updateDirs();
	}

	@Override
	public void mouseMoved(java.awt.event.MouseEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {
		this.lastMX = arg0.getX();
		this.lastMY = arg0.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
