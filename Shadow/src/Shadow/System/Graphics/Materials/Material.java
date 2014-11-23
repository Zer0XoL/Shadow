package Shadow.System.Graphics.Materials;

public class Material {
	public double ambientCoeffR, ambientCoeffG, ambientCoeffB;
	public double diffuseCoeffR, diffuseCoeffG, diffuseCoeffB; 
	public double specularCoeffR, specularCoeffG, specularCoeffB;
	public double specularPow;
	
	public Material(double ambientCoeffR, double ambientCoeffG, double ambientCoeffB,
					double diffuseCoeffR, double diffuseCoeffG, double diffuseCoeffB,
					double specularCoeffR, double specularCoeffG, double specularCoeffB,
					double specularPow) {
		
		this.ambientCoeffR = ambientCoeffR;
		this.ambientCoeffG = ambientCoeffG;
		this.ambientCoeffB = ambientCoeffB;
		
		this.diffuseCoeffR = diffuseCoeffR;
		this.diffuseCoeffG = diffuseCoeffG;
		this.diffuseCoeffB = diffuseCoeffB;
		
		this.specularCoeffR = specularCoeffR;
		this.specularCoeffG = specularCoeffG;
		this.specularCoeffB = specularCoeffB;
		
		this.specularPow = specularPow;
	}
}
