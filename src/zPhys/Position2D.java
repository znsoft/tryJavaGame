package zPhys;

/**
 * Created by es.zheludkov on 30.08.2017.
 */
public class Position2D {
    private Vector2 pos;
    private int x,y;

    public Position2D(Vector2 vec) {
        pos = new Vector2(vec);
        this.x = (int)vec.x;
        this.y = (int)vec.y;
    }

    public Position2D(double x, double y) {
        pos = new Vector2(x,y);
        this.x = (int)x;
        this.y = (int)y;
    }

    public Position2D(int x, int y) {
        this.x = x;
        this.y = y;
        pos = new Vector2((double)x,(double)y);
    }

    public Position2D SetXY(int x, int y) {
        this.x = x;
        this.y = y;
        pos.x = (double)x;
        pos.y = (double)y;
        return this;
    }

    public Position2D SetXY(double x,double y){

        pos.x = x;
        pos.y = y;
        this.x = (int)x;
        this.y = (int)y;
        return this;
    }

    public Position2D Add(Vector2 vec){
        pos.Add(vec);
        this.x = (int)pos.x;
        this.y = (int)pos.y;
        return this;
    }


    public int getXi() {
        return x;
    }

    public int getYi() {
        return y;
    }

    public double getX() {
        return pos.x;
    }

    public double getY() {
        return pos.y;
    }

    public double getAngleTo(double x, double y) {
        double absoluteAngleTo = StrictMath.atan2(y - pos.y, x - pos.x);
        double relativeAngleTo = absoluteAngleTo;

        while (relativeAngleTo > StrictMath.PI) {
            relativeAngleTo -= 2.0D * StrictMath.PI;
        }

        while (relativeAngleTo < -StrictMath.PI) {
            relativeAngleTo += 2.0D * StrictMath.PI;
        }

        return relativeAngleTo;
    }

    public double getDistanceTo(double x, double y) {
        return StrictMath.hypot(pos.x - x, pos.y - y);
    }

    public double getDistanceTo(Position2D point) {
        return getDistanceTo(point.x, point.y);
    }

    public double getSqrDistanceTo(double x, double y){
        double xx = pos.x - x;
        double yy = pos.y - y;
        return xx*xx+yy*yy;
    }

    public double getSqrDistanceTo(Position2D point){
        return getSqrDistanceTo(point.x, point.y);
    }

    }
