package appearances;

import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.ImageComponent2D;
import org.jogamp.java3d.Texture;
import org.jogamp.java3d.Texture2D;
import org.jogamp.java3d.TextureAttributes;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.utils.image.ImageException;
import org.jogamp.java3d.utils.image.TextureLoader;

public class TexturedAppearance extends Appearance {
	/** Texture image to use as a backup */
	private static String backupImage = "NoTexture.png";
	
	private String texName;
	private float scale;
	private float rotation;
	
	public TexturedAppearance () { this("NoTexture.png"); }
	public TexturedAppearance (String textureName) { this(textureName, 1.0f, 0.0f); }
	public TexturedAppearance (String textureName, float scale) { this(textureName, scale, 0.0f); }
	public TexturedAppearance (String textureName, float scale, float rotation) {
		super();
		this.texName = textureName;
		this.scale = scale;
		this.rotation = rotation;
		this.setTexture(loadTexture(textureName));
		this.setTextureAttributes(newTextureAttributes(scale, rotation));
	}
	
	public String getName() {return this.texName;}
	public float getScale() {return this.scale;}
	public float getRotation() {return this.rotation;}
	
	/**
	 * Creates a texture attributes object for modifying texture objects
	 * @param scale Multiplier to scale your texture by
	 * @param rotation Radians to rotaate your texture by
	 * @return The newly created TextureAttributes object
	 */
	public static TextureAttributes newTextureAttributes (float scale, float rotation) {
		TextureAttributes ta = new TextureAttributes();
    	Transform3D trans = new Transform3D();
    	ta.setTextureMode(TextureAttributes.REPLACE);
    	trans.setScale(scale);
    	trans.rotY(rotation);
    	ta.setTextureTransform(trans);
    	return ta;
	}
	
	/**
	 * Creates a 2D texture object from the given image file name
	 * @param fileName The name, with extension, of the image file to use
	 * @return The newly created Texture2D object
	 */
	public static Texture2D loadTexture (String fileName) {
		String filePath = "assets/images/" + fileName;
		TextureLoader loader ;
		try {
			loader = new TextureLoader(filePath, null);
		} catch (ImageException e) {
			if (fileName.equals(backupImage)) {
				e.printStackTrace(System.err);
				return null;
			} else {
				System.err.println("Failed to open texture image: "+fileName);
				return loadTexture(backupImage);
			}
		}
		ImageComponent2D image = loader.getImage();
		Texture2D texture = new Texture2D(
			Texture.BASE_LEVEL, Texture.RGBA,
			image.getWidth(), image.getHeight()
		);
		texture.setImage(0, image);
		return texture;
	}

}