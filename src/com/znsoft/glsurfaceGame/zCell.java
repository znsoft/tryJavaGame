package com.znsoft.glsurfaceGame;

import java.util.ArrayList;

/**
 * Created by es.zheludkov on 30.08.2017.
 */
public class zCell {
    private final Unit2D pos;

    private ArrayList<zCell> neibor;
    public zCell(int x,int y,double entropyFactor){
        pos = new Unit2D(x,y,entropyFactor);
    }

    public double getDistanceTo(Position2D point){
        return pos.getDistanceTo(point);
    }

    public double getSqrDistanceTo(Position2D point){
        return pos.getSqrDistanceTo(point);
    }
}
