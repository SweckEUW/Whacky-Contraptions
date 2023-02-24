package Computergrafik.Engine.Planet.PlanetVariations.Sun;

import Computergrafik.Engine.Planet.Core.Noise.Noise;
import Computergrafik.Engine.Planet.Core.Noise.SimplexNoise;

public class SunNoise extends Noise {
	
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
		noiseLayers=8;
		noisePeristance=0.5f;
		noiseBaseRoughness=2.3f;
		noiseStrength=1.3f;
		noiseRoughness=2.2f;
		noiseOffsetX=(float)Math.random()*9999;
		noiseOffsetY=(float)Math.random()*9999;
		noiseOffsetZ=(float)Math.random()*9999;
		noiseMinValue=0.4f;
	}
}
