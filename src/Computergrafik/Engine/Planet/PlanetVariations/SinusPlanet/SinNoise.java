package Computergrafik.Engine.Planet.PlanetVariations.SinusPlanet;

import Computergrafik.Engine.Planet.Core.Noise.Noise;
import Computergrafik.Engine.Planet.Core.Noise.SimplexNoise;

public class SinNoise extends Noise {
		
	public float calculateNoiseValue(float x,float y,float z) {			
		float noiseValue=0;
		float frequency=noiseBaseRoughness;
		 
		for (int i = 0; i <noiseLayers; i++) {
			float noiseX= x*frequency+noiseOffsetX;
			float noiseY= y*frequency+noiseOffsetY;
			float noiseZ= z*frequency+noiseOffsetZ;
			noiseValue+=(float)(SimplexNoise.noise(noiseX,noiseY,noiseZ));
			frequency*=noiseRoughness;
		}
			noiseValue=(float)Math.cos(noiseValue);
			noiseValue*=noiseValue;
		return (float)noiseValue*noiseStrength;	
	}

	@Override
	public void randomizeNoiseValues() {
		noiseLayers=2; 
		noisePeristance=(float)(Math.random()*(0.7f-0.4f)+0.4f);
		noiseBaseRoughness=(float)(Math.random()*(1f-0.8f)+0.8f);
		noiseStrength=(float)(Math.random()*(0.8f-0.3f)+0.3f);
		noiseRoughness=(float)(Math.random()*(2f-1.5f)+1.5f);
		noiseOffsetX=(float)Math.random()*9999;
		noiseOffsetY=(float)Math.random()*9999;
		noiseOffsetZ=(float)Math.random()*9999;
	}
}
