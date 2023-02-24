package Computergrafik.Engine.Planet.PlanetParts.Water;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

import Computergrafik.Engine.Core.Models.loadToGPU;
import Computergrafik.Engine.Core.Shaders.Core.BasicShader;
import Computergrafik.Engine.Planet.Core.Planet;
import Computergrafik.Engine.Planet.PlanetParts.PlanetPart;

public abstract class Water extends PlanetPart{

	protected static final int NUMBER_OF_MODELS =1;

	private static final String FILE = "PlanetShader/Water";
	
	protected float minWaveHeightInc = 0.00006f;
	protected float	maxWaveHeightInc = 0.00009f;
	protected float inc=1;
	
	protected float scale=planet.getPlanetModel().getScale()*1.25f;	
	
	protected float minWaveHeight=0.97f;
	protected float[] waveHeights;
	protected int waveHeightsVBOID;
	protected float[] waveHeightIncs;
	
	public Water(Planet planet) {
		super(NUMBER_OF_MODELS,planet,new BasicShader(FILE));
		models[0].setX(planet.getModel().getX());
		models[0].setY(planet.getModel().getY());
		models[0].setZ(planet.getModel().getZ());
		initWaves();	
		models[0].setScale(scale);
	}
	

	protected void initWaves() {
		float[] vert = models[0].getMesh().getBaseVertices();
		waveHeights=new float[models[0].getMesh().getVertices().length/3];
		
		waveHeights[0]=minWaveHeight+((float)Math.random()*(1-minWaveHeight));
		
		for (int i = 1; i < waveHeights.length; i++) {
			waveHeights[i]=minWaveHeight+((float)Math.random()*(1-minWaveHeight));
			for (int j = 0; j < vert.length; j+=3) 
				if (vert[j]==vert[i*3] && vert[j+1]==vert[i*3+1] && vert[j+2]==vert[i*3+2]) 
					waveHeights[j/3] = waveHeights[i];
		}
			
		
		GL4 gl=(GL4)GLContext.getCurrentGL();
		gl.glBindVertexArray(models[0].getMesh().getVaoID());
		loadToGPU.connectDataWithVAO(waveHeights, 3, 1);
			
		waveHeightsVBOID = loadToGPU.getLatestID();
		
		waveHeightIncs = new float[waveHeights.length];
		for (int i = 0; i < waveHeightIncs.length; i++) {
			waveHeightIncs[i]=minWaveHeightInc+((float)Math.random()*(maxWaveHeightInc-minWaveHeightInc));
			if (Math.random()>0.5f) 
				waveHeightIncs[i]*=-1;
			for (int j = 0; j < vert.length; j+=3) 
				if (vert[j]==vert[i*3] && vert[j+1]==vert[i*3+1] && vert[j+2]==vert[i*3+2]) 
					waveHeightIncs[j/3] = waveHeightIncs[i];
		}
	}

	public abstract void calculateColors();

	
	@Override
	public void update() {
			
		for (int i = 0; i < waveHeights.length; i++) {
			
			if (waveHeights[i]>=1 && waveHeightIncs[i]>0) 
				waveHeightIncs[i]*=-1;
			if (waveHeights[i]>=1 && waveHeightIncs[i]<0) 
				waveHeightIncs[i]*=1;
			
			if (waveHeights[i]<=minWaveHeight && waveHeightIncs[i]>0) 
				waveHeightIncs[i]*=1;
			if (waveHeights[i]<=minWaveHeight && waveHeightIncs[i]<0) 
				waveHeightIncs[i]*=-1;
			
			waveHeights[i]+=waveHeightIncs[i];
		}			
		loadToGPU.updateVBO(waveHeightsVBOID, waveHeights);
		
		models[0].setRotationX(planet.getPlanetModel().getRotationX());
		models[0].setRotationY(planet.getPlanetModel().getRotationY());
		models[0].setRotationZ(planet.getPlanetModel().getRotationZ());
	}
	
	public float[] getWaveHeights() {
		return waveHeights;
	}
	
	public void setScale() {
		for (int i = 0; i < models.length; i++) 
			models[i].setScale(planet.getPlanetModel().getScale()*1.25f);
	}
	
	@Override
	public void generateRandom() {
		calculateColors();
		setNormalColorValues(); 
	}
	
}
