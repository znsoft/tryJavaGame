package com.znsoft.glsurfaceGame;

/**
 * Created by es.zheludkov on 30.08.2017.
 */
public class Unit2D extends Position2D {
    private Vector2 force;
    private double entropyFactor,massFactor,adgesion;

    public boolean isGravityOn;
    public boolean isAlive;
    public boolean isAfrozen;

    public Unit2D(int x, int y, double entropyFactor) {
        super(x, y);
        this.entropyFactor = entropyFactor;
    }

    public Unit2D(double x, double y) {
        super(x, y);
        this.force = new Vector2(0.0f,0.0f);
    }

    public Unit2D(int x, int y) {
        super(x, y);
        force = new Vector2(0.0f,0.0f);

    }
}
