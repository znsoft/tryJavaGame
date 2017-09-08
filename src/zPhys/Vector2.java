package zPhys;

/**
 * Created by es.zheludkov on 30.08.2017.
 */
public class Vector2 {
    public double x;
    public double y;


    public Vector2(Vector2 vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 Add(Vector2 vec) {
        this.x = this.x + vec.x;
        this.y = this.y + vec.y;
        return this;
    }

    public Vector2 Sub(Vector2 vec) {
        this.x = this.x - vec.x;
        this.y = this.y - vec.y;
        return this;
    }

    public Vector2 normalize() {
        double max = StrictMath.max(x, y);
        if (max == 0) return this;
        this.x = x / max;
        this.y = y / max;
        return this;
    }

    public Vector2 Mul(double m) {
        this.x *= m;
        this.y *= m;
        return this;
    }


    public double length() {
        return StrictMath.sqrt(x * x + y * y);
        //return StrictMath.hypot(x,y);

    }
}
