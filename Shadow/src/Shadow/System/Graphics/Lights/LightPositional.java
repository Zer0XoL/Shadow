package Shadow.System.Graphics.Lights;

import Shadow.System.Graphics.Color;

public class LightPositional extends Light {
	
	public LightPositional(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public LightPositional(Color uniformColor, double x, double y, double z) {
		super(uniformColor);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public LightPositional(Color ambientColor, Color diffuseAndSpecular, double x, double y, double z) {
		super(ambientColor, diffuseAndSpecular);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public LightPositional(Color ambientColor, Color diffuseColor, Color specularColor, double x, double y, double z) {
		super(ambientColor, diffuseColor, specularColor);
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
