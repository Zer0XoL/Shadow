package Shadow.System.Graphics.Materials;

public class Material {
	public double ambientCoeff, diffuseCoeff, specularCoeff, specularPow;
	
	public Material(double ambientCoeff, double diffuseCoeff, double specularCoeff, double specularPow) {
		this.ambientCoeff = ambientCoeff;
		this.diffuseCoeff = diffuseCoeff;
		this.specularCoeff = specularCoeff;
		this.specularPow = specularPow;
	}
}
