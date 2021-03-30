package objects;

import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.Material;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.utils.geometry.Sphere;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Vector3d;

/**
 * Abstract class for a pool ball.
 * Subclass this, calling the super constructor and
 * filling in the appropriate values (colour and point value)
 */
public class PoolBall {
	/** The Y-up value that all balls spawn and rest at */
	private static double height = 0.5;
	/** The transformgroup that carries the ball's sphere object */
	private TransformGroup tg;
	/** The transform that translates this ball to it's position */
	private Transform3D t;
	/** Number of points this ball is worth */ 
	private int pointValue;
	/** Colour of the sphere for this ball */
	private Color3f clr;
	/** Worldspace position of this ball */
	private Vector3d pos;
	
	/**
	 * Enum for every different type of Snooker ball
	 * <p>
	 * Used in the PoolBall constructor.<br>
	 * Simply pass one of the static fields.
	 * @see PoolBall#PoolBall(Type, double, double)
	 */
	public static class Type {
		/** Number of points this type of pool ball is worth */
		private int pointValue;
		/** Colour of this type of pool ball */
		private Color3f colour;
		
		/** Private constructor for initializing the enum */
		private Type (int p, Color3f c) {
			this.pointValue = p;
			this.colour = c;
		}
		
		public static Type CUE    = new Type(0, new Color3f(0.875f, 0.875f,  0.75f  ));
		public static Type RED    = new Type(1, new Color3f(0.875f, 0.0625f, 0.0625f));
		public static Type YELLOW = new Type(2, new Color3f(0.75f,  0.75f,   0.125f ));
		public static Type GREEN  = new Type(3, new Color3f(0.125f, 0.5f,    0.125f ));
		public static Type BROWN  = new Type(4, new Color3f(0.375f, 0.1875f, 0.0625f));
		public static Type BLUE   = new Type(5, new Color3f(0.125f, 0.125f,  0.875f ));
		public static Type PINK   = new Type(6, new Color3f(0.875f, 0.375f,  0.625f ));
		public static Type BLACK  = new Type(7, new Color3f(0.125f, 0.125f,  0.125f ));
	}

	/**
	 * Default and only constructor for a pool ball
	 * @param type The type of Snooker pool ball. Use the enum {@link Type}
	 * @param x x-right position of this ball to spawn and rest at
	 * @param z z-forward position of this ball to spawn and rest at
	 */
	public PoolBall (Type type, double x, double z) {
		this.pointValue = type.pointValue;
		this.clr = type.colour;
		this.t = new Transform3D();
		this.pos = new Vector3d(x, height, z);
		this.t.setTranslation(this.pos);
		this.tg = new TransformGroup(this.t);
		this.tg.addChild(new Sphere(
			0.1f, // Radius of the ball
			Sphere.GENERATE_NORMALS, // Capability flags
			128, // Fidelity of sphere, number of polygons
			createBallAppearance(clr)
		));
	}
	
	/**
	 * private static method that creates an Appearance for this ball of the given colour.
	 * Only used in the constructor.
	 * @param clr The colour to set the new appearance to
	 * @return The new Appearance object, to make our sphere with
	 */
	private static Appearance createBallAppearance (Color3f clr) {
		Appearance app = new Appearance();
		app.setMaterial(new Material(
			clr, // Ambient colour
			new Color3f(0.0f, 0.0f, 0.0f), // Emissive colour
			clr, // Diffuse colour
			new Color3f(0.9f, 0.9f, 0.9f), // Specular colour
			128 // Shininess
		));
		return app;
	}
	
	/**
	 * Returns the tg for this ball
	 * @return TransformGroup that this ball is in
	 */
	public TransformGroup getTG () {
		return this.tg;
	}
		
	/**
	 * Returns the number of points this ball is worth
	 * @return Integer value of this ball
	 */
	public int getPointValue () {
		return this.pointValue;
	}
	
	/**
	 * Returns the colour of this ball
	 * @return Color3f of this ball
	 */
	public Color3f getColour () {
		return this.clr;
	}
	
	/**
	 * Static method that returns the y-up height that all
	 * pool balls spawn, move, and rest at.
	 * @return Height position on the y-up axis
	 */
	public static double getHeight () {
		return PoolBall.height;
	}
	
	/**
	 * Gets the worldspace position of this ball
	 * @return The position of this ball as a Vector3d
	 */
	public Vector3d getPos () {
		return (Vector3d) this.pos.clone();
	}
		
	/**
	 * Sets the value of the given Vector3d reference to this ball's position
	 * @param posRef Reference to a Vector3d to edit
	 */
	public void getPos (Vector3d posRef) {
		posRef.set(this.pos);
	}
	
	/**
	 * Sets the position of this ball and
	 * updates the TransformGroup of this ball.
	 * @param pos The new position to set to.
	 * The y value is ignored and replace with the static height.
	 */
	public void setPos (Vector3d pos) {
		this.setPos(pos.x, pos.z);
	}
	
	/**
	 * Sets the position of this ball and
	 * updates the TransformGroup of this ball.
	 * @param x The new x-right position to set to
	 * @param z The new z-forward position to set to
	 */
	public void setPos (double x, double z) {
		this.pos.set(x, height, z);
		this.t.setTranslation(this.pos);
		this.tg.setTransform(this.t);
	}
	
}
