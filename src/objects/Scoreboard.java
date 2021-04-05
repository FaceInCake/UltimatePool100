package objects;

import java.awt.Font;
import org.jogamp.java3d.Alpha;
import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.BoundingSphere;
import org.jogamp.java3d.ColoringAttributes;
import org.jogamp.java3d.Font3D;
import org.jogamp.java3d.FontExtrusion;
import org.jogamp.java3d.Material;
import org.jogamp.java3d.QuadArray;
import org.jogamp.java3d.RotationInterpolator;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.Text3D;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Matrix4d;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3f;
import appearances.TexturedAppearance;

public class Scoreboard {
    private static final float z = (float) (1 / Math.tan(Math.PI/10) / 5) ;
    private TransformGroup tg;
    private TransformGroup[] digits;
    private int [] counts;

    public Scoreboard (Point3d p) {
        this.counts = new int[6];
        this.digits = new TransformGroup[6];
        for(int i=0;i<6;i++) this.counts[i]=0;
        this.tg = createBoard();
    }

    public TransformGroup getTG () {
        return this.tg;
    }

    public void updateScore(int score, int player) {
        if (player == 1) {
            for(int t=0; t<score; t++) {
                rotateScore(2);
                if(counts[2] == 0) {
                    rotateScore(1);
                    if(counts[1] == 0) {
                        rotateScore(0);
                    }
                }
            }
        }
        if(player == 2) {
            for(int t=0; t<score; t++) {
                rotateScore(5);
                if(counts[5] == 0) {
                    rotateScore(4);
                    if(counts[4] == 0) {
                        rotateScore(3);
                    }
                }
            }			
        }
    }
    
    private void rotateScore(int position) {
        this.counts[position] = (this.counts[position] + 1) % 10;
        System.out.println(position + " " + this.counts[position]);
        Transform3D temp = new Transform3D();
        Matrix4d mat = new Matrix4d();
        digits[position].getTransform(temp);
        temp.get(mat);
        Matrix4d m4d = new Matrix4d();
        m4d.rotX(-Math.PI/5);
        mat.mul(m4d);
        temp.set(mat);
        digits[position].setTransform(temp);
    }
    
    private TransformGroup createBoard() {
        TransformGroup boardTG = new TransformGroup();
        Transform3D back3D = new Transform3D();
        back3D.setTranslation(new Vector3f(-0.09f, -0.41f, 0.6f));
        TransformGroup backTG = new TransformGroup(back3D);
        boardTG.addChild(backTG);
        backTG.addChild(board(4));
        TransformGroup edgeTG = new TransformGroup();
        backTG.addChild(edgeTG);
        edgeTG.addChild(edge(1));
        edgeTG.addChild(text3D("Player1     Player2", new Color3f(1.0f, 1.0f, 1.0f), new Point3f(-0.2f, 4.6f, 0f)));

        TransformGroup numberTG = new TransformGroup();
        boardTG.addChild(numberTG);
        Transform3D[] transform3ds = new Transform3D[6];
        TransformGroup[] sceneTG = new TransformGroup[6];	
        
        for(int i=0; i<6; i++ ) {
            float x = (float) (-1+i*0.3);
            if(i>2)
                x = x + 0.2f;
            transform3ds[i] = new Transform3D();
            transform3ds[i].setTranslation(new Vector3f(x, 0f, 0f));
            sceneTG[i] = new TransformGroup(transform3ds[i]);
            if(i<3)
                sceneTG[i].addChild(createNumber(new Color3f(1.0f, 0.0f, 0.0f)));
            else 
                sceneTG[i].addChild(createNumber(new Color3f(0.0f, 0.0f, 1.0f)));				
            digits[i] = new TransformGroup();
            digits[i].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            digits[i].addChild(sceneTG[i]);
            numberTG.addChild(digits[i]);
        }
        return boardTG;
    }
    
    private static TransformGroup createNumber(Color3f clr) {
        TransformGroup ts = new TransformGroup();
        Transform3D[] transform3ds = new Transform3D[10];
        TransformGroup[] sceneTG = new TransformGroup[10];
        
//		Transform3D[] number3ds = new Transform3D[10];
//		TransformGroup[] numberTG = new TransformGroup[10];		
        for(int i=0; i<10; i++) {
            transform3ds[i] = new Transform3D();
            transform3ds[i].rotX(i * Math.PI / 5);
            sceneTG[i] = new TransformGroup(transform3ds[i]);
            sceneTG[i].addChild(square(clr));
            
//			number3ds[i] = new Transform3D();
//			number3ds[i].setTranslation(new Vector3f(0f, -0.5f, 0f));
//			numberTG[i] = new TransformGroup(number3ds[i]);
            int temp = i;
            sceneTG[i].addChild(text3D(String.valueOf(temp), new Color3f(1.0f, 1.0f, 1.0f), new Point3f(0f, -0.3f, 5 * z)));
            
//			sceneTG[i].addChild(numberTG[i]);
            ts.addChild(sceneTG[i]);
        }
        return ts;
    }

    private static TransformGroup text3D(String text, Color3f clr, Point3f pnt) {
        Font my2DFont = new Font(text, Font.PLAIN, 1); //create 3d font
        FontExtrusion myExtru = new FontExtrusion();
        Font3D my3DFont = new Font3D(my2DFont, myExtru);// create 3d font
        Appearance app = new Appearance();				//create new appearance
        app.setColoringAttributes(new ColoringAttributes(clr, 1));		//set the color of the text according to the argument clr

        Text3D text1 = new Text3D(my3DFont, text, pnt, 0, 1); // make 3d text

        Transform3D scaler = new Transform3D();
        scaler.setScale(0.2);							//scale the 3D text based on the factor 
                
        TransformGroup ts = new TransformGroup(scaler);
        ts.addChild(new Shape3D(text1, app));
        return ts;		
    }

    private static Shape3D square(Color3f clr) {

        QuadArray square = new QuadArray(4, QuadArray.NORMALS | QuadArray.COLOR_3 | QuadArray.COORDINATES); //quadArray to define one side
        Point3f[] pt1 = {new Point3f(0.1f, 0.2f, z), new Point3f(-0.1f, 0.2f, z), new Point3f(-0.1f, -0.2f, z), new Point3f(0.1f, -0.2f, z)};
        float[] normal = {0, 0, 1};
        for(int i = 0; i<4; i++) {
            square.setCoordinate(i, pt1[i]);//set coordinates
            square.setColor(i, clr);	//set colors
            square.setNormal(i, normal);	//set surface normal
        }
        Appearance app = new Appearance();	
        app.setMaterial(setMaterial(new Color3f(0.0f, 1.0f, 1.0f)));	//set appearance
        
        return new Shape3D(square, app);
    }

    private static Material setMaterial(Color3f clr) {
        int SH = 128;
        Color3f newColor = new Color3f(0.7f*clr.x, 0.7f*clr.y, 0.7f*clr.z);
        Material ma = new Material();
        ma.setAmbientColor(newColor);
        ma.setEmissiveColor(new Color3f(0.0f, 0.0f, 0.0f));
        ma.setDiffuseColor(newColor);
        ma.setSpecularColor(newColor);
        ma.setShininess(SH);
        ma.setLightingEnable(true);
        return ma;

    }

    private static TransformGroup board(float factor) {
        QuadArray square = new QuadArray(4, QuadArray.NORMALS | QuadArray.COLOR_3 | QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2); //quadArray to define one side
        Point3f[] pt1 = {new Point3f(0.29f, 0f, 0.0f), new Point3f(0.29f, 0.21f, 0.0f), new Point3f(-0.29f, 0.21f, 0.0f), new Point3f(-0.29f, 0f, 0.0f)};
        float[] normal = {0, 0, 1};
        
        for(int i=0; i<4; i++) {
            pt1[i].x = pt1[i].x * factor;
            pt1[i].y = pt1[i].y * factor;
            pt1[i].z = pt1[i].z * factor;
        }
        
        float uv0[] = {0f, 0f};
        float uv1[] = {1f, 0f};
        float uv2[] = {1f, 1f};
        float uv3[] = {0f, 1f};
        
        for(int i = 0; i<4; i++) {
            square.setCoordinate(i, pt1[i]);//set coordinates
//			square.setColor(i, clr);	//set colors
            square.setNormal(i, normal);	//set surface normal
        }
        square.setTextureCoordinate(0, 0, uv0);
        square.setTextureCoordinate(0, 1, uv1);
        square.setTextureCoordinate(0, 2, uv2);
        square.setTextureCoordinate(0, 3, uv3);		
        
        Appearance app = new TexturedAppearance("ledscreen.png");
        TransformGroup board = new TransformGroup();
        board.addChild(new Shape3D(square, app));
        
//		board.addChild(score(factor));
        return board;
    }

    private static RotationInterpolator rotateBehavior(int r_num, TransformGroup my_TG) {

        my_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D yAxis = new Transform3D();
        yAxis.rotZ(Math.PI/2);
        Alpha rotationAlpha = new Alpha(1, r_num);
        RotationInterpolator rot_beh = new RotationInterpolator(rotationAlpha, my_TG, yAxis, 0f, (float) Math.PI /5f);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        rot_beh.setSchedulingBounds(bounds);
        return rot_beh;
    }

    private static TransformGroup edge(float factor) {
        QuadArray square = new QuadArray(4, QuadArray.NORMALS | QuadArray.COLOR_3 | QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2); //quadArray to define one side
        
        Point3f[] pts = new Point3f[4];
        pts[0] = new Point3f(1.4f, 1.18f, -0.1f);
        pts[1] = new Point3f(-1.4f, 1.18f, -0.1f);
        pts[2] = new Point3f(-1.4f, -0.2f, -0.1f);
        pts[3] = new Point3f(1.4f, -0.2f, -0.1f);
        float[] normal = {0, 0, 1};
        
        for(int i=0; i<4; i++) {
            pts[i].x = pts[i].x * factor;
            pts[i].y = pts[i].y * factor;
            pts[i].z = pts[i].z * factor;
        }
        
        float uv0[] = {0f, 0f};
        float uv1[] = {1f, 0f};
        float uv2[] = {1f, 1f};
        float uv3[] = {0f, 1f};
        
        for(int i = 0; i<4; i++) {
            square.setCoordinate(i, pts[i]);//set coordinates
//			square.setColor(i, clr);	//set colors
            square.setNormal(i, normal);	//set surface normal
        }
        square.setTextureCoordinate(0, 0, uv0);
        square.setTextureCoordinate(0, 1, uv1);
        square.setTextureCoordinate(0, 2, uv2);
        square.setTextureCoordinate(0, 3, uv3);		
        
        Appearance app = new TexturedAppearance("edge.png");
        TransformGroup board = new TransformGroup();
        board.addChild(new Shape3D(square, app));
        
//		board.addChild(score(factor));
        return board;
    }
}