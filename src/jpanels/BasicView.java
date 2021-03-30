/**
 * Default GUI that goes inside a window
 * Contains a SimpleUniverse and Canvas3D.
 * Extend this class and override the getContent()
 */
package jpanels;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.swing.JPanel;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.Canvas3D;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.utils.universe.SimpleUniverse;

/**
 * Abstract class that extends the JPanel
 * Basically the screen of window.
 * Creates a Canvas3D and SimpleUniverse.
 * Pass this into a JFrame to use it. Look through the jpanels package.
 */
public abstract class BasicView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Contains stuff for rendering the
	 * environment to the JPanel.
	 */
	private Canvas3D screen;
	
	/**
	 * Contains stuff for viewing the
	 * the environment from a POV.
	 */
	private SimpleUniverse su;
	
	/** The width in pixels of the screen, not the window */
	private int width;
	/** The height in pixels of the screen, not the window */
	private int height;

	/**
	 * Default constructor
	 * Just make sure to override the getContent
	 */
	public BasicView() {
		// All the settings the Canvas3D needs to make a rendering context
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		// Whenever we draw to the screen, this is what it draws to
		// Basically this contains a BUNCH of fancy rendering info and stuff
		this.screen = new Canvas3D(config);
		// Creates our ViewingPlatform and Viewer object
		// AKA creates our camera that we can look through
		// Believe it or not, it's simple. Really crazy programs might not use this.
		// We're not writing a crazy program, one camera for every user is all we want
		this.su = new SimpleUniverse(this.screen);
		// Add the BranchGroup content
		this.su.addBranchGraph(this.createContent());
		// Makes sure the screen takes up the entire window
		super.setLayout(new BorderLayout());
		super.add("Center", this.screen);		
	}
	
	/**
	 * Returns the width of the screen,
	 * in pixels.
	 */
	public int getScreenWidth () {
		return this.getCanvas().getWidth();
	}
	
	/**
	 * Returns the height of the screen,
	 * in pixels.
	 */
	public int getScreenHeight () {
		return this.getCanvas().getHeight();
	}
	
	/**
	 * Sets the values of t to be of the Transform3D of the viewer
	 * @param t The non-null Transform3D to fill-in
	 */
	public void getViewTransform (Transform3D t) {
		this.su.getViewingPlatform().getViewPlatformTransform().getTransform(t);
	}
	
	/**
	 * Creates a Transform3D and returns it with the values
	 * of the current view transform
	 * @return Transform3D of the viewer
	 */
	public Transform3D getViewTransform () {
		Transform3D t = new Transform3D();
		this.getViewTransform(t);
		return t;
	}
	
	/**
	 * Shortcut function for getting the transformgroup of this viewing platform
	 * @return The TransformGroup of the camera
	 */
	public TransformGroup getViewTransformGroup () {
		return this.su.getViewingPlatform().getViewPlatformTransform();
	}
	
	/**
	 * Sets the view transform of the SimpleUniverse to be t
	 * @param t The Transform3D to set to
	 */
	public void setViewTransform (Transform3D t) {
		this.su.getViewingPlatform().getViewPlatformTransform().setTransform(t);
	}
	
	/**
	 * Returns this view's canvas object
	 * @return The Canvas3D object this view draws to
	 */
	public Canvas3D getCanvas () {
		return this.screen;
	}
	
	/**
	 * Override this function with the function that creates the main content
	 * BranchGroup and returns it
	 * @return BranchGroup The main content
	 */
	public abstract BranchGroup createContent ();

}
