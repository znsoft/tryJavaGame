package com.znsoft.glsurfaceGame;

/**
 * Created by es.zheludkov on 30.08.2017.
 */
public class Position2D {
    private final double x;
    private final double y;
    private final int xi,yi;
    public Position2D(double x, double y) {
        this.x = x;
        this.y = y;
        xi = (int)x;
        yi = (int)y;
    }

    public Position2D(int x, int y) {
        this.xi = x;
        this.yi = y;
        this.x = (double)x;
        this.y = (double)y;
    }

    public int getXi() {
        return xi;
    }

    public int getYi() {
        return yi;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngleTo(double x, double y) {
        double absoluteAngleTo = StrictMath.atan2(y - this.y, x - this.x);
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
        return StrictMath.hypot(this.x - x, this.y - y);
    }

    public double getDistanceTo(Position2D point) {
        return getDistanceTo(point.x, point.y);
    }

    public double getSqrDistanceTo(double x, double y){
        double xx = this.x - x;
        double yy = this.y - y;
        return xx*xx+yy*yy;
    }

    public double getSqrDistanceTo(Position2D point){
        return getSqrDistanceTo(point.x, point.y);
    }

    }
