package Shadow.System.Graphics.Lights;

import Shadow.System.Graphics.Color;
import Shadow.System.Graphics.Screen.Viewport;

public class Light {
	public Color ambientColor, diffuseColor, specularColor;
	public double x, y, z;
	
	/*
	 * Default light, all colors are full intensity except ambient
	 */
	public Light() {
		ambientColor = new Color(0D, 0D, 0D, 0D);
		diffuseColor = new Color(1D, 1D, 1D, 1D);
		specularColor = new Color(1D, 1D, 1D, 1D);
	}
	
	public Light(Color uniformColor) {
		this.ambientColor = uniformColor;
		this.diffuseColor = uniformColor;
		this.specularColor = uniformColor;
	}
	
	public Light(Color ambientColor, Color diffuseAndSpecular) {
		this.ambientColor = ambientColor;
		this.diffuseColor = diffuseAndSpecular;
		this.specularColor = diffuseAndSpecular;
	}
	
	public Light(Color ambientColor, Color diffuseColor, Color specularColor) {
		this.ambientColor = ambientColor;
		this.diffuseColor = diffuseColor;
		this.specularColor = specularColor;
	}
	
	public String toString() {
		return "Light Base";
	}
}
