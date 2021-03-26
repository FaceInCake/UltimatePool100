package objects;

import org.jogamp.java3d.AmbientLight;
import org.jogamp.java3d.BoundingSphere;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.PointLight;
import org.jogamp.java3d.Transform3D;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3d;

import jpanels.BasicView;
import objects.poolballs.*;

public class PoolBallViewer extends BasicView {
	private static final long serialVersionUID = 1L;

	public PoolBallViewer() {
		Transform3D t = new Transform3D();
		t.lookAt(new Point3d(1,2,1), new Point3d(), new Vector3d(0,1,0));
		t.invert();
		setViewTransform(t);
	}

	@Override
	public BranchGroup createContent() {
		BranchGroup bg = new BranchGroup();
		
		AmbientLight al = new AmbientLight(true, new Color3f(0.2f,0.2f,0.2f));
		al.setInfluencingBounds(new BoundingSphere(new Point3d(), 100));
		bg.addChild(al);
		
		PointLight pl = new PointLight(new Color3f(1,1,1), new Point3f(0,3,0), new Point3f(1,0,0));
		pl.setInfluencingBounds(new BoundingSphere(new Point3d(), 100));
		bg.addChild(pl);
		
		bg.addChild(new RedBall(0, 0).getTG());
		bg.addChild(new YellowBall(0.5, 0).getTG());
		bg.addChild(new GreenBall(0, 0.5).getTG());
		bg.addChild(new BrownBall(0.5, 0.5).getTG());
		bg.addChild(new BlueBall(-0.5, 0).getTG());
		bg.addChild(new PinkBall(0, -0.5).getTG());
		bg.addChild(new BlackBall(-0.5, -0.5).getTG());
		bg.addChild(new CueBall(-0.5, 0.5).getTG());

		bg.compile();
		return bg;
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	new jframes.Window(new PoolBallViewer(), "Pool Ball Tester");
            }
        });
	}

}
