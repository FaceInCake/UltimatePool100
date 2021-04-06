package appearances;

import org.jogamp.java3d.Material;
import org.jogamp.vecmath.Color3f;

public class MaterialFactory extends Material {

	private MaterialFactory() {}
	
	/**
	 * Creates a plain material, semi-shiny, like acrylic.
	 * @param clr The colour of the material
	 * @return The newly created material
	 */
	public static Material createMaterial(Color3f clr) {
		return new Material(
			clr, // Ambient colour
			new Color3f(0.0f, 0.0f, 0.0f), // Emissive colour
			clr, // Diffuse colour
			new Color3f(0.9f, 0.9f, 0.9f), // Specular colour
			128 // Shininess
		);
	}
}
