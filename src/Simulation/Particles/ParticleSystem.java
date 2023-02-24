package Simulation.Particles;

import Simulation.RenderEngine.Core.Math.Vector2f;
import Simulation.RenderEngine.Core.Shaders.Core.BasicShader;
import Simulation.RenderEngine.Core.Shaders.Core.Material;
import Simulation.RenderEngine.Primitives.Cube;

//TODO Keine Nullen zulassen

public class ParticleSystem {
    public Particles particles;
    private Cube cube = new Cube (10);
    private float [] yNow;
    private BasicShader basicShader = new BasicShader("Instanced");
    private Vector2f position = new Vector2f(0,0);

    private Vector2f [] coordinates;

    public ParticleSystem(Material basicMaterial, float x, float y) {
        particles = new Particles(cube, 100, basicMaterial, new float[] {1,0,0});

        yNow = new float [particles.getInstances()];

        for (int i = 0; i < yNow.length; i++) {
            yNow[i] = -5;
        }

        createVectorsX(particles.getVx());

        coordinates = new Vector2f[particles.getInstances()];
        coordinates = normalizingVector(particles.getVx(), yNow);

        position.x = x;
        position.y = y;

        //createVectorsY(particles.getVy());
    }

    //Die Art der Vektorerstellung sorgt für die letztendliche Bewegung der Partikel, weswegen sie vom System abhängen sollte.
    public float [] createVectorsX (float [] vx) {
        for (int i = 0; i < particles.getInstances(); i++) {
            vx[i] = (-12) + (int)(Math.random() * ((12 - (-12)) + 1));
            while (vx[i] == 0){
                vx[i] = (-12) + (int)(Math.random() * ((12 - (-12)) + 1));
            }
        }
        return vx;
    }

    public Vector2f [] normalizingVector(float [] x, float [] y) {
        float length;
        Vector2f [] newVectors = new Vector2f[particles.getInstances()];

        for (int i = 0; i < particles.getInstances(); i++) {
            length = (float) Math.sqrt(x[i]*x[i] + y[i]*y[i]);
            newVectors[i] = new Vector2f(x[i]/length, y[i]/length);
        }
        return newVectors;
    }


    //Anpassen, dass die Position der Partikel in den Föhn gelegt werden
    public void update() {
        particles.setXconstant(0,position.x);
        particles.setYconstant(0,position.y);

        for (int i = 1; i < particles.getInstances(); i++) {
            if (particles.getY(i - 1) < -20 || i-1 == 0) {
                particles.setXconstant(i, coordinates[i].x );
                particles.setYconstant(i, coordinates[i].y * 5);
            }

            //Rücksetzen auf den Kern des Systems
            if (particles.getY(i) < -700) {
                particles.setX(i, position.x);
                particles.setY(i, position.y);
            }
        }
    }

    public BasicShader getBasicShader() {
        return basicShader;
    }

    public void setBasicShader(BasicShader basicShader) {
        this.basicShader = basicShader;
    }
}
