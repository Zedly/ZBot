/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.util;

/**
 * @author Dennis
 */
public class CartesianVector extends Vector {

    private double x, y, z;

    public CartesianVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public double getYaw() {
        return toCylindrical().getYaw();
    }

    @Override
    public double getPitch() {
        return toSpherical().getPitch();
    }

    @Override
    public double getRadius() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public Vector add(Vector v2) {
        if (v2 instanceof CartesianVector) {
            return new CartesianVector(x + v2.getX(), y + v2.getY(), z + v2.getZ());
        } else {
            return add(v2.toCartesian());
        }
    }

    @Override
    public Vector multiply(double d) {
        return new CartesianVector(x * d, y * d, z * d);
    }

    @Override
    public Vector normalize() {
        return normalize(1);
    }

    @Override
    public Vector normalize(double d) {
        return multiply(d / getRadius());
    }

    @Override
    public Vector subtract(Vector v2) {
        if (v2 instanceof CartesianVector) {
            return new CartesianVector(x - v2.getX(), y - v2.getY(), z - v2.getZ());
        } else {
            return subtract(v2.toCartesian());
        }
    }

    @Override
    public Vector cross(Vector v2) {
        return new CartesianVector(y * v2.getZ() - z * v2.getY(), z * v2.getX() - x * v2.getZ(), x * v2.getY() - y * v2.getX());
    }

    @Override
    public CartesianVector toCartesian() {
        return this;
    }

    @Override
    public CylindricalVector toCylindrical() {
        double yaw, hr;
        hr = Math.sqrt(x * x + z * z);
        if (x == 0) {
            if (z >= 0) {
                yaw = 0;
            } else {
                yaw = -Math.PI;
            }
        } else {
            if (x >= 0) {
                yaw = Math.atan(z / x) - 1.570796326794896619231321691639751442;
            } else {
                yaw = 1.570796326794896619231321691639751442 - Math.atan(-z / x);
            }
        }
        return new CylindricalVector(yaw, hr, y);
    }

    @Override
    public SphericalVector toSpherical() {
        double radius = getRadius();
        double pitch = 0;
        double yaw = 0;
        if (radius == 0 && y >= 0) {
            pitch = -1.570796326794896619231321691639751442;
        } else if (radius == 0 && y < 0) {
            pitch = 1.570796326794896619231321691639751442;
        } else {
            pitch = -Math.asin(y / radius);
        }
        if (x == 0) {
            if (z >= 0) {
                yaw = 0;
            } else {
                yaw = -Math.PI;
            }
        } else {
            if (x >= 0) {
                yaw = Math.atan(z / x) - 1.570796326794896619231321691639751442;
            } else {
                yaw = 1.570796326794896619231321691639751442 - Math.atan(-z / x);
            }
        }
        return new SphericalVector(yaw, pitch, radius);
    }

}
