package Computergrafik.Engine.Core.Shaders.Core;
import Computergrafik.Engine.Core.Math.Vector3f;

/**
 * Material of a model defining:
 * -rgb emission color (self lightning)
 * -rgb amtient color 
 * -rgb diffuse color
 * -rgb specular color
 * -shininess  (0 no shininess 400 really shiny) shininess controlls the cone size of the specular highlight
 * -transparency (alpha from 0=completely transparent to 1=no transparency)
 * @author Simon Weck
 *
 */
public class Material {
	
    private Vector3f emissionColor;
    private Vector3f ambientColor;
    private Vector3f diffuseColor;
    private Vector3f specularColor;
    private float shininess;
    private float alpha; //alpha in range [0,1]

    /**
     * creates default material
     * emissionColor = 0,0,0 -> no emission
     * ambientColor = 0.2f,0.2f,0.2f
     * diffuseColor = 0.5f,0.5f,0.5f
     * specularColor = 0.7f,0.7f,0.7f
     * shininess = 10
     * alpha = 1
     */
    public Material() {
    	emissionColor=new Vector3f(0,0,0);
    	ambientColor=new Vector3f(0.2f,0.2f,0.2f);
    	diffuseColor=new Vector3f(0.5f,0.5f,0.5f);
    	specularColor=new Vector3f(0.7f,0.7f,0.7f);
    	shininess=10;
    	alpha=1;
    }

    /**
     * creates material with alpha = 1 and the given attributes
     */
    public Material(Vector3f emissionColor, Vector3f ambientColor, Vector3f diffuseColor, Vector3f specularColor, float shininess) {
        this.emissionColor = emissionColor;
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.shininess = shininess;     
        alpha=1;
    }

    /**
     * creates material with alpha = 1, emissionColor = 0,0,0 and the given attributes
     */
    public Material(Vector3f ambientColor, Vector3f diffuseColor, Vector3f specularColor, float shininess) {
        this.emissionColor = new Vector3f();
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.shininess = shininess;    
        alpha=1;
    }

    /**
     * creates material with emissionColor = 0,0,0 and the given attributes
     */
    public Material(Vector3f ambientColor, Vector3f diffuseColor, Vector3f specularColor, float shininess,float alpha) {
        this.emissionColor = new Vector3f();
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.shininess = shininess;    
        this.alpha=alpha;     		
    }
    
	public Vector3f getEmissionColor() {
        return emissionColor;
    }

    public void setEmissionColor(Vector3f emissionColor) {
        this.emissionColor = emissionColor;
    }

    public Vector3f getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(Vector3f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public Vector3f getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Vector3f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Vector3f getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Vector3f specularColor) {
        this.specularColor = specularColor;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }
    
    public float getAlpha() {
        return alpha;
    }
    
    public void setAlpha(float alpha) {
    	this.alpha=alpha;
    }
    
}
