package zPhys;

/**
 * Created by es.zheludkov on 30.08.2017.
 */
public class Vector2 {
    public double x;
    public double y;
    public static Vector2 ZERO = new Vector2(0.0d,0.0d);

    public Vector2(Vector2 vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 Add(Vector2 vec){
        x+=vec.x;
        y+=vec.y;
        return this;
    }
}
