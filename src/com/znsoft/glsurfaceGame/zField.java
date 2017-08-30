package com.znsoft.glsurfaceGame;

/**
 * Created by es.zheludkov on 30.08.2017.
 */
public class zField {
    public zCell[][] field;
    private int x,y;
    private double entropyFactor;
    public zField(int x,int y, double entripyFactor) {
        field = new zCell[x][y];
        this.entropyFactor = entripyFactor;
    }

    public zCell get(Position2D point){
        return get(point.getXi(),point.getYi());
    }

    public zCell get(int x,int y){
        if (x < 0 || x >= this.x || y < 0 || y >= this.y) return null;
        return field[x][y];
    }

    private zCell put(int x,int y, zCell cell){
        if (x < 0 || x >= this.x || y < 0 || y >= this.y) return null;
        field[x][y] = cell;
        return cell;
    }

    public zCell calcPriorityCell(int x,int y, zCell cell1, zCell cell2){
        if(cell1==null)return cell2;
        if(cell2==null)return cell1;
        Position2D currentPoint = new Position2D(x,y);
        double r1,r2;
        r1 = cell1.getSqrDistanceTo(currentPoint);
        r2 = cell2.getSqrDistanceTo(currentPoint);

        if(r1>r2)return cell2;
        return cell1;

    }


    public zCell AddCell(int x,int y, zCell cell){
        zCell c;
        c = get(x,y);
        if(c!=null)return put(x,y, calcPriorityCell(x,y,c,cell));
        if(cell == null)c = new zCell(x,y,entropyFactor); else c = cell;
        //force addiction

        put(x,y,c);
        return c;

    }

}
