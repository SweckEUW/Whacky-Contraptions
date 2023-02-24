package Computergrafik.Engine.Planet.PlanetVariations.EarthLike;

import Computergrafik.Engine.Planet.Core.Noise.Noise;
import Computergrafik.Engine.Planet.Core.Noise.SimplexNoise;

public class EarthNoise extends Noise {
	
	public float calculateNoiseValue(float x,float y,float z) {			
		float noiseValue=0;
		float frequency=noiseBaseRoughness;
		float amplitude=1;
		
		for (int i = 0; i <noiseLayers; i++) {
			float noiseX= x*frequency+noiseOffsetX;
			float noiseY= y*frequency+noiseOffsetY;
			float noiseZ= z*frequency+noiseOffsetZ;
			noiseValue+=(float)(SimplexNoise.noise(noiseX,noiseY,noiseZ)+1)*0.5f*amplitude;
			frequency*=noiseRoughness;
			amplitude*=noisePeristance;
		}
		noiseValue=Math.max(0, noiseValue-noiseMinValue);
		return noiseValue*noiseStrength;	
	}

	@Override
	public void randomizeNoiseValues() {
		noiseLayers=(int)(Math.random()*(7-5)+5);
		noisePeristance=(float)(Math.random()*(0.7f-0.4f)+0.4f);
		noiseBaseRoughness=(float)(Math.random()*(1f-0.8f)+0.8f);
		noiseStrength=(float)(Math.random()*(0.5f-0.3f)+0.3f);
		noiseRoughness=(float)(Math.random()*(2f-1.5f)+1.5f);
		noiseOffsetX=(float)Math.random()*9999;
		noiseOffsetY=(float)Math.random()*9999;
		noiseOffsetZ=(float)Math.random()*9999;
		noiseMinValue=(float)(Math.random()*(0.6f-0.2f)+0.2f);
	}
}
