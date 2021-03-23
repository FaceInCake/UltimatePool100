package main;

import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.utils.geometry.ColorCube;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

import jpanels.BasicView;

public class BasicTest extends BasicView {
	private static final long serialVersionUID = 1L;
	
	public BasicTest () {
		
		Transform3D t = new Transform3D();
		t.lookAt(new Point3d(1,1,1), new Point3d(), new Vector3d(0,1,0));
		t.invert();
		setViewTransform(t);
	}
	
	@Override
	public BranchGroup createContent() {
		BranchGroup content = new BranchGroup();
		content.addChild(new ColorCube(0.35));
		content.compile();
		return content;
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	new jframes.Window(new BasicTest(), "Test title display");
            }
        });
	}


}
