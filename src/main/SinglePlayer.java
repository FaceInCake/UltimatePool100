package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.ColoringAttributes;
import org.jogamp.java3d.PolygonAttributes;
import org.jogamp.java3d.QuadArray;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TriangleFanArray;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3d;

import behaviours.GroundedCamController;
import behaviours.PoolBallManager;
import jpanels.BasicView;
import lights.LightFactory;
import objects.AxisFrame;
import objects.PoolBall;
import objects.Scoreboard;
import objects.SimpleRoom;

public class SinglePlayer extends BasicView implements KeyListener {
	private static final long serialVersionUID = 1L;
    private GroundedCamController gcc;
    private PoolBallManager pbm;
    private Scoreboard sb;
	
	public SinglePlayer () {}

    private Shape3D createSquare () {
        int format = QuadArray.COORDINATES | QuadArray.NORMALS;
        QuadArray geom = new QuadArray(4, format);
        float wr = (float)PoolBallManager.width_2;
        float lr = (float)PoolBallManager.length_2;
        float h = (float)(PoolBall.height - PoolBall.radius);
        float coordinates[] = {
            -wr, h, -lr,    -wr, h, +lr,
            +wr, h, +lr,    +wr, h, -lr
        };
        float normals[] = {
            0, 1, 0,    0, 1, 0,
            0, 1, 0,    0, 1, 0
        };
        geom.setCoordinates(0, coordinates);
        geom.setNormals(0, normals);
        Appearance app = new Appearance();
        app.setColoringAttributes(new ColoringAttributes(
            new Color3f(0.1f, 0.5f, 0.1f), ColoringAttributes.NICEST
        ));
        return new Shape3D(geom, app);
    }

    private Shape3D createCircle (Point3d centre, float radius) {
        int fidelity = 64, strip[] = {fidelity} ;
        int format=TriangleFanArray.COORDINATES | TriangleFanArray.NORMALS; 
        float up [] = {0, 1, 0};
        TriangleFanArray geom = new TriangleFanArray(fidelity, format, strip);
        geom.setCoordinate(0, centre); geom.setNormal(0, up);
        double a=0, da = Math.PI*2*(1.0 / (fidelity-2));
        for (int i=1; i<fidelity; i++) {
            geom.setCoordinate(i, new Point3d(
                centre.getX() + Math.cos(a) * radius,
                centre.getY(),
                centre.getZ() + Math.sin(a) * radius
            ));
            geom.setNormal(i, up);
            a += da;
        }
        Appearance app = new Appearance();
        app.setColoringAttributes(new ColoringAttributes(
            new Color3f(0,0.2f,0), ColoringAttributes.NICEST
        ));
        app.setPolygonAttributes(new PolygonAttributes(
            PolygonAttributes.POLYGON_FILL,
            PolygonAttributes.CULL_NONE,
            0.0f
        ));
        return new Shape3D(geom, app);
    }
	
	@Override
	public BranchGroup createContent() {
		BranchGroup content = new BranchGroup();
		
        // Add the lights
		content.addChild(new AxisFrame());
		content.addChild(LightFactory.createAmbientLight());
		content.addChild(LightFactory.createPointLight(new Point3f(0.5f, 1, 0)));
		
        // Add the room
        content.addChild(new SimpleRoom(5.0f));
        
        // Add the scoreboard
        content.addChild(this.sb = new Scoreboard(new Vector3d(-3,1,-1)));

        // Add the free cam
		Transform3D t = new Transform3D();
		t.lookAt(new Point3d(2,1,2), new Point3d(0, -50, 0), new Vector3d(0,1,0));
		t.invert();     setViewTransform(t);
		content.addChild(this.gcc = new GroundedCamController(this));

        // Add the square 'pool table'
        content.addChild(createSquare());
        for (int i=0; i<6; i++) {
            content.addChild(createCircle(
                new Point3d(PoolBallManager.pockets[i].x, PoolBall.height, PoolBallManager.pockets[i].y),
                (float) PoolBallManager.pocketRadius
            ));
        }
		
        // Add the pool ball manager
		this.pbm = new PoolBallManager();
		content.addChild(pbm.getTG());
        content.addChild(pbm);
        this.getCanvas().addKeyListener(this);

		content.compile();
		return content;
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	new jframes.Window(new SinglePlayer(), "Ultimate Pool 100");
            }
        });
	}

    @Override
    public void keyPressed(KeyEvent arg0) {}

    @Override
    public void keyReleased(KeyEvent arg0) {
        int key = arg0.getKeyCode();
        if (key >= KeyEvent.VK_1 && key <= KeyEvent.VK_9) {
            float angle = (float)(this.gcc.getYaw()+Math.PI);
            float power = (key-KeyEvent.VK_0)*0.016f;
            this.pbm.strikeCueBall(angle, power);
        } else
        if (key == KeyEvent.VK_COMMA) {
            this.sb.scoreP1();
        } else
        if (key == KeyEvent.VK_PERIOD) {
            this.sb.scoreP2();
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {}

}
