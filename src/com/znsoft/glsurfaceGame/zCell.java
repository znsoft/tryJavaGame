package com.znsoft.glsurfaceGame;

import java.util.ArrayList;

/**
 * Created by es.zheludkov on 30.08.2017.
 */
public class zCell {
    private Position2D pos;
    private float forceVecX,forceVecY;
    private float entropyFactor;

    public boolean isAlive;
    public boolean isAfrozen;

    private ArrayList<zCell> neibor;
    public zCell(int x,int y,float entropyFactor){
        pos = new Position2D(x,y);
        this.entropyFactor = entropyFactor;
        forceVecX = 0.0f;
        forceVecY = 0.0f;
    }



}
