package zPhys;

import java.util.ArrayList;

/**
 * Created by es.zheludkov on 30.08.2017.
 */
public class zCell {
    private final Unit2D unit;

    private ArrayList<zCell> neibor;
    public zCell(int x,int y,double entropyFactor){
        unit = new Unit2D(x,y,entropyFactor);
    }

    public zCell AddForce(Vector2 vec){
        unit.AddForce(vec);
        return this;
    }

    public zCell Move(){
        unit.Move();
        return this;
    }


    public static zCell calcPriorityCell(int x,int y, zCell cell1, zCell cell2){
        if(cell1==null)return cell2;
        if(cell2==null)return cell1;
        Position2D currentPoint = new Position2D(x,y);
        double r1,r2;
        r1 = cell1.getSqrDistanceTo(currentPoint);
        r2 = cell2.getSqrDistanceTo(currentPoint);

        if(r1>r2)return cell2;
        return cell1;

    }

    public zCell Collider(zCell cell){


      return this;
    }

    public double getDistanceTo(Position2D point){
        return unit.position.getDistanceTo(point);
    }

    public double getSqrDistanceTo(Position2D point){
        return unit.position.getSqrDistanceTo(point);
    }
}
