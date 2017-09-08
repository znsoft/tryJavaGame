package zPhys;

import java.util.HashSet;

/**
 * Created by es.zheludkov on 01.09.2017.
 */
public class Constraint {
    public double ELASTICITY = 0.010;
    public double FREEZE = 0.060;
    public static final double XDROP = 0.5;
    public static final double CONSTRAINTFRICTION = 0.5;
    public static HashSet<Constraint> AllConstraints = new HashSet<>();
    public Unit2D unit1;
    public Unit2D unit2;

    public double minlength;
    public double length;
    public double rad;
    public double tearDist;// длина обрыва связи

    public static double SPACING = 0.61; // длина спокойной (не сжатой и не растянутой) связи
    public static double SPRINGSTOP = 0.5; // длина сжатой связи
    public static final double TEARDIST = 4.5;// длина обрыва связи
    public static final double GLUEDIST = 30.873;// приклеивание
    public boolean isHide;

    public Constraint(Unit2D unit1, Unit2D unit2, boolean ishide) {
        this.unit1 = unit1;
        this.unit2 = unit2;
        length = Constraint.SPACING * unit1.getDistanceTo(unit2);
        AllConstraints.add(this);
        tearDist = length * TEARDIST;
        isHide = ishide;
        minlength = length * SPRINGSTOP;
        unit1.linked.put(unit2, this);
        unit2.linked.put(unit1, this);
    }


    public Constraint resolve() {
        double dx = unit1.position.getX() - unit2.position.getX();
        double dy = unit1.position.getY() - unit2.position.getY();
        double dist = StrictMath.sqrt(dx * dx + dy * dy);
        rad = dist;
        if (dist == this.length) return null;


        double diff = (this.length - dist) / dist;
        double mul = diff * CONSTRAINTFRICTION * (1 - this.length / dist);

        Vector2 delta = new Vector2(dx * mul * XDROP, dy * mul);

        if (dist > tearDist) {
            return this.tear();
        }

        if (dist <= this.length) {
            delta.Mul(FREEZE);
            unit1.force.Sub(delta);
            unit2.force.Add(delta);
            if (dist <= minlength) {
                unit1.position = new Position2D(unit1.position.prevpos);
                unit2.position = new Position2D(unit2.position.prevpos);
            }
            return null;
        }
        delta.Mul(ELASTICITY);
        unit1.force.Add(delta);
        unit2.force.Sub(delta);
        return null;
    }

    public Constraint tear() {
        unit1.free(unit2);
        unit2.free(unit1);
        return this;
    }

}
