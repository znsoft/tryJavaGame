package com.znsoft.glsurfaceGame;

/**
 * Created by es.zheludkov on 30.08.2017.
 */
public class zField {
    public zCell[][] field;
    private int x,y;
    public zField(int x,int y) {
        field = new zCell[x][y];
    }

    public zCell get(Position2D point){
        return get(point.getXi(),point.getYi());
    }

    public zCell get(int x,int y){
        if (x < 0 || x >= this.x || y < 0 || y >= this.y) return null;
        return field[x][y];
    }

    private void put(int x,int y, zCell cell){
        if (x < 0 || x >= this.x || y < 0 || y >= this.y) return;
        field[x][y] = cell;
    }


    public zCell AddCell(int x,int y){
        zCell c;
        c = get(x,y);
        if(c!=null)return c;
        c = new zCell(x,y,2.0f);



        put(x,y,c);
        return c;

    }

}
