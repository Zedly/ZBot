package zedly.zbot.util;

public abstract class Vector {

    public abstract double getX();

    public abstract double getY();

    public abstract double getZ();

    public abstract double getYaw();

    public abstract double getPitch();

    public abstract double getRadius();

    public abstract Vector add(Vector v2);

    public abstract Vector multiply(double d);

    public abstract Vector normalize();

    public abstract Vector normalize(double d);

    public abstract Vector subtract(Vector v2);

    public abstract Vector cross(Vector v2);

    public abstract CartesianVector toCartesian();

    public abstract CylindricalVector toCylindrical();

    public abstract SphericalVector toSpherical();
}
