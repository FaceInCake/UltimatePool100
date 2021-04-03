package objects;

import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.QuadArray;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3f;

import appearances.TexturedAppearance;

public class SimpleRoom extends TransformGroup {

	public static Shape3D createRectangle(Point3f pnt1, Point3f pnt2, Point3f pnt3, Point3f pnt4, float scale, Vector3f normal, String filename) {
		int flags = QuadArray.NORMALS | QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2;
		QuadArray square = new QuadArray(4, flags); //quadArray to define one side
		Point3f[] pt1 = {pnt1, pnt2, pnt3, pnt4};
		float uvs[][] = {{0f, 0f}, {1f, 0f}, {1f, 1f}, {0f, 1f}};
		for(int i = 0; i<4; i++) {
			square.setCoordinate(i, pt1[i]);//set coordinates
			square.setNormal(i, normal);	//set surface normal
			square.setTextureCoordinate(0, i, uvs[i]);
		}
		Appearance app = new TexturedAppearance(filename, 0.0001f, 0.5f);
		return new Shape3D(square, app);
	}
	
	public SimpleRoom () {
		this(1.0f);
	}
	
	public SimpleRoom (float factor) {
		super();
		
		Vector3f up = new Vector3f(0, 1, 0);
		Vector3f down = new Vector3f(0, -1, 0);
		Vector3f right = new Vector3f(1, 0, 0);
		Vector3f left = new Vector3f(-1, 0, 0);
		Vector3f forward = new Vector3f(0, 0, 1);
		Vector3f back = new Vector3f(0, 0, -1);
		
		Point3f[] pnt = new Point3f[8];
		pnt[0] = new Point3f(new Point3f(-1f, 0f, -1f)); // Back -Bot-Left
		pnt[1] = new Point3f(new Point3f(1f, 0f, -1f));  // Back -Bot-Right
		pnt[2] = new Point3f(new Point3f(1f, 1f, -1f));  // Back -Top-Right
		pnt[3] = new Point3f(new Point3f(-1f, 1f, -1f)); // Back -Top-Left
		pnt[4] = new Point3f(new Point3f(-1f, 1f, 1f));  // Front-Top-Left
		pnt[5] = new Point3f(new Point3f(-1f, 0f, 1f));  // Front-Bot-Left
		pnt[6] = new Point3f(new Point3f(1f, 0f, 1f));   // Front-Bot-Right
		pnt[7] = new Point3f(new Point3f(1f, 1f, 1f));   // Front-Top-Right
		for(int i=0; i<8; i++) {
			pnt[i].x = pnt[i].x * factor;
			pnt[i].y = pnt[i].y * factor;
			pnt[i].z = pnt[i].z * factor;
		}

		float invScale = 1.0f / factor;
		super.addChild(createRectangle(pnt[4], pnt[3], pnt[2], pnt[7], invScale, down, "roof.png"));	//top
		super.addChild(createRectangle(pnt[5], pnt[0], pnt[3], pnt[4], invScale, right, "sky.png"));	//left
		super.addChild(createRectangle(pnt[0], pnt[1], pnt[2], pnt[3], invScale, forward, "sky.png"));	//back
		super.addChild(createRectangle(pnt[1], pnt[6], pnt[7], pnt[2], invScale, left, "sky.png"));	//right
		super.addChild(createRectangle(pnt[6], pnt[5], pnt[4], pnt[7], invScale, back, "sky.png"));	//front
		super.addChild(createRectangle(pnt[5], pnt[6], pnt[1], pnt[0], invScale, up, "floor.png"));	//bottom
		
	}

}