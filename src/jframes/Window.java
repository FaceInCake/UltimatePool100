/**
 * Default window
 * Contains a title and whatever JPanel you put inside it.
 * Your main function should create a Runnable which just creates
 * a new one of these. Then in this classes constructor would you pass the JPanel,
 * and optionally a title, or dimensions.
 */
package jframes;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with defaulting title and dimensions
	 * Title defaults to "Ultimate Pool 100"
	 * Dimensions default to 600x600
	 * @param source The JPanel is basically the screen inside this window. Look through the jpanels package.
	 */
	public Window (JPanel source) {
		this(source, "Ultimate Pool 100", 600, 600);
	}
	
	/**
	 * Constructor with defaulting dimensions.
	 * Dimensions default to 600x600
	 * @param source The JPanel is basically the screen inside this window. Look through the jpanels package.
	 * @param titleTitle of the window
	 */
	public Window (JPanel source, String title) {
		this(source, title, 600, 600);
	}
	
	/**
	 * Full constructor for a JFrame object.
	 * The JFrame is basically the window itself.
	 * @param source The JPanel is basically the screen inside this window. Look through the jpanels package.
	 * @param title Title of the window
	 * @param width Width of the window
	 * @param height Height of the window
	 */
	public Window (JPanel source, String title, int width, int height) {
		super(title);
		super.getContentPane().add(source);
		super.pack();
		super.setSize(width, height);
		super.setVisible(true);
	}

}
