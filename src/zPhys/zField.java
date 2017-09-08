package zPhys;

import java.util.ArrayList;
import java.util.HashSet;


/**
 * Created by es.zheludkov on 30.08.2017.
 */
public class zField implements Runnable {
    public zCell[][] field;
    public static Unit2D taked;
    Vector2 bound;
    public HashSet<zCell> touchedCells;
    public int x, y;

    public zField(int x, int y) {
        taked = null;
        field = new zCell[x][y];
        this.x = x;
        this.y = y;
        bound = new Vector2((double) x, (double) y);
        touchedCells = new HashSet<>();
        for (y = 0; y < this.y; y++)
            for (x = 0; x < this.x; x++)
                put(x, y);


        int s = 1;
        for (y = 0; y < this.y; y += s)
            for (x = 0; x < this.x; x += s) {
                zCell cell = put(x, y);
                if (cell == null) continue;
                Unit2D unit = new Unit2D(x, y);
                //if(y == 0 && (x+3)%6==0 ) unit.isPinned = true;
                // if(y == this.y-1) unit.isPinned = true;
                //if(x != 0) unit.attach(getUnitsInCell(x - s, y));
                if (y == this.y / 2 + 2 || y == this.y / 2 + 1) continue;

                unit.attach(getUnitsInCell(x - s, y), false);
                unit.attach(getUnitsInCell(x, y - s), false);
                unit.attach(getUnitsInCell(x - s, y - s), true);
                unit.attach(getUnitsInCell(x + s, y - s), true);
                s = 2;
                unit.attach(getUnitsInCell(x - s, y), true);
                unit.attach(getUnitsInCell(x, y - s), true);
                unit.attach(getUnitsInCell(x - s, y - s), true);
                unit.attach(getUnitsInCell(x + s, y - s), true);
                s = 1;

                //}
                cell.addUnit(unit);
                touchedCells.add(cell);
            }
        /*
        Unit2D luCorner = getUnitsInCell(0, 0);
        Unit2D ldCorner = getUnitsInCell(0, this.y-1);
        Unit2D ruCorner = getUnitsInCell(this.x-1, 0);
        Unit2D rdCorner = getUnitsInCell(this.x-1, this.y-1);
        rdCorner.attach(luCorner,false);
        rdCorner.attach(ruCorner,false);
        rdCorner.attach(ldCorner,false);

        ldCorner.attach(luCorner,false);
        ldCorner.attach(ruCorner,false);

        luCorner.attach(ruCorner,false);
*/

    }


    public Unit2D getUnitsInCell(int x, int y) {
        zCell cell = get(x, y);
        
        if (cell == null) return null;
        for (Unit2D u:cell.inner             ) 
            return u;
        
        return null;
    }

    public zCell get(Position2D point) {
        return get(point.getXi(), point.getYi());
    }

    public zCell get(int x, int y) {
        if (x < 0 || x >= this.x || y < 0 || y >= this.y) return null;
        return field[x][y];
    }

    private zCell put(int x, int y, zCell cell) {
        if (x < 0 || x >= this.x || y < 0 || y >= this.y) return null;
        field[x][y] = cell;
        return cell;
    }

    private zCell put(int x, int y) {
        if (x < 0 || x >= this.x || y < 0 || y >= this.y) return null;
        zCell cell = new zCell(x, y);
        field[x][y] = cell;
        return cell;
    }

    public zField update() {
        ResolveConstraints();
        ResolveCells();


        return this;
    }

    public void ResolveCells() {
        ArrayList<zCell> toRemove = new ArrayList<>();
        ArrayList<Unit2D> needTouch = new ArrayList<>();
        for (zCell cell : touchedCells) {
            int oldCount = cell.inner.size();
            if (oldCount == 0) {
                toRemove.add(cell);
                continue;
            }

            ArrayList<Unit2D> units = cell.update(bound);
            if (units.size() == oldCount) toRemove.add(cell);
            cell.inner.removeAll(units);
            needTouch.addAll(units);
        }
        touchedCells.removeAll(toRemove);
        for (Unit2D unit : needTouch) {
            zCell nCell = get(unit.position.getXi(), unit.position.getYi());
            if (nCell == null) continue;
            nCell.addUnit(unit);
            touchedCells.add(nCell);
        }
    }

    public void ResolveConstraints() {
        ArrayList<Constraint> toRemove = new ArrayList<>();
        for (Constraint link : Constraint.AllConstraints) {
            Constraint l = link.resolve();
            if (l != null) toRemove.add(l);
        }
        Constraint.AllConstraints.removeAll(toRemove);
    }


    @Override
    public void run() {
        while (true) this.update();
    }

    public void Click(double xd, double yd, boolean b) {

        Position2D pos = new Position2D(xd, yd);
        zCell cell = get(pos);


        if (cell == null) return;

        if (cell.inner.size() == 0 && zPhys.Controll.isDrawMode) {
            if (Controll.isDrawBox) DrawBox(pos, cell);
            else drawcircle(pos);
        }

        if (cell.inner.size() == 0) return;
        if (!zPhys.Controll.isDrawMode) cell.MouseClick(pos);
    }


    public void drawcircle(Position2D pos) {
        int i;
        double oldspacing = Constraint.SPACING;
        Constraint.SPACING = 0.9;
        double oldspringstop = Constraint.SPRINGSTOP;
        Constraint.SPRINGSTOP = 0.6;
        Unit2D unit = new Unit2D(pos.getX(), pos.getY());
        ArrayList<Unit2D> o = new ArrayList<>();
        o.add(unit);
        int sections = 4;
        for (i = 0; i < sections; i++) {

            double s = StrictMath.sin(6.28 / sections * (double) i);
            double c = StrictMath.cos(6.28 / sections * (double) i);

            Unit2D u = new Unit2D(pos.getX() + s, pos.getY() + c);
            o.add(u);
        }
        for (Unit2D u : o) {
            for (Unit2D v : o) {
                u.attach(v, false);
            }
            zCell cell = put(u.position.getXi(), u.position.getYi());
            if (cell == null) continue;
            cell.addUnit(u);
            touchedCells.add(cell);


        }

        Constraint.SPRINGSTOP = oldspringstop;
        Constraint.SPACING = oldspacing;
    }


    public void drawcells(Position2D pos) {
        int x, y;
        int x1 = pos.getXi() - 1;
        int s = 1;
        int y1 = pos.getYi() - 1;

        for (y = y1; y < y1 + 4; y += s)
            for (x = x1; x < x1 + 4; x += s) {
                zCell cell = put(x, y);
                if (cell == null) continue;
                Unit2D unit = new Unit2D(x, y);
                unit.isPinned = Controll.isPinMode;

                unit.attach(getUnitsInCell(x - s, y), false);
                unit.attach(getUnitsInCell(x, y - s), false);
                unit.attach(getUnitsInCell(x - s, y - s), true);
                unit.attach(getUnitsInCell(x + s, y - s), true);
                s = 2;
                unit.attach(getUnitsInCell(x - s, y), false);
                unit.attach(getUnitsInCell(x, y - s), false);
                unit.attach(getUnitsInCell(x - s, y - s), true);
                unit.attach(getUnitsInCell(x + s, y - s), true);
                s = 1;

                cell.addUnit(unit);
                touchedCells.add(cell);
            }


    }

    public void DrawBox(Position2D pos, zCell cell) {
        int s = 1;
        int x = pos.getXi();
        int y = pos.getYi();
        double xd = (double) x;
        double yd = (double) y;

        Unit2D unit1 = new Unit2D(x, y);
        unit1.isPinned = Controll.isPinMode;
        Unit2D unit2 = new Unit2D(xd + 0.5, yd);
        unit2.isPinned = Controll.isPinMode;
        Unit2D unit3 = new Unit2D(xd + 0.5, yd + 0.5);
        unit3.isPinned = Controll.isPinMode;
        Unit2D unit4 = new Unit2D(xd, y + 0.5);
        unit4.isPinned = Controll.isPinMode;
        unit1.attach(unit2, false);
        unit1.attach(unit3, false);
        unit1.attach(unit4, false);
        unit2.attach(unit3, false);
        unit2.attach(unit4, false);
        unit3.attach(unit4, false);

        unit1.attach(getUnitsInCell(x - s, y), false);
        unit2.attach(getUnitsInCell(x, y - s), false);
        unit1.attach(getUnitsInCell(x, y - s), false);
        unit1.attach(getUnitsInCell(x - s, y - s), false);
        unit2.attach(getUnitsInCell(x + s, y - s), false);

        unit3.attach(getUnitsInCell(x, y + s), false);
        unit3.attach(getUnitsInCell(x + s, y), false);
        unit3.attach(getUnitsInCell(x + s, y + s), false);
        unit4.attach(getUnitsInCell(x + s, y + s), false);
        unit4.attach(getUnitsInCell(x, y + s), false);

        cell.addUnit(unit1);
        cell.addUnit(unit2);
        cell.addUnit(unit3);
        cell.addUnit(unit4);
        touchedCells.add(cell);
    }
}
