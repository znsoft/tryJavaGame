package zPhys;

/**
 * Created by es.zheludkov on 30.08.2017.
 */
public class Unit2D {
    public Vector2 force;
    public Position2D position;
    private double entropyFactor,massFactor,adgesion;

    public boolean isGravityOn;
    public boolean isAlive;
    public boolean isAfrozen;

    public Unit2D(int x, int y, double entropyFactor) {
        position = new Position2D(x, y);
        this.entropyFactor = entropyFactor;
        this.force = new Vector2(0.0f,0.0f);
    }


    public Unit2D(double x, double y) {
        position = new Position2D(x, y);
        this.force = new Vector2(0.0f,0.0f);
    }

    public Unit2D(int x, int y) {
        position = new Position2D(x, y);
        force = new Vector2(0.0f,0.0f);

    }

    public Unit2D AddForce(Vector2 vec){
        force.Add(vec);
        return this;
    }

    public Unit2D Move(){
        position.Add(force);
        return this;
    }


}
