package zedly.zbot.util;

public class SphericalVector extends Vector
{
	private double yaw, pitch, radius;

	public SphericalVector(double yaw, double pitch, double radius)
	{
		this.yaw = yaw;
		this.pitch = pitch;
		this.radius = radius;
	}

	@Override
	public double getX()
	{
		return toCartesian().getX();
	}

	@Override
	public double getY()
	{
		return toCartesian().getY();
	}

	@Override
	public double getZ()
	{
		return toCartesian().getZ();
	}

	@Override
	public double getYaw()
	{
		return yaw;
	}

	@Override
	public double getPitch()
	{
		return pitch;
	}

	@Override
	public double getRadius()
	{
		return radius;
	}

	@Override
	public Vector add(Vector v2)
	{
		return toCartesian().add(v2.toCartesian()).toSpherical();
	}

	@Override
	public Vector multiply(double d)
	{
		return new SphericalVector(yaw, pitch, radius * d);
	}

	@Override
	public Vector normalize()
	{
		return normalize(1);
	}

	@Override
	public Vector normalize(double d)
	{
		return multiply(d / getRadius());
	}

	@Override
	public Vector subtract(Vector v2)
	{
		return toCartesian().subtract(v2.toCartesian()).toSpherical();
	}

	@Override
	public Vector cross(Vector v2)
	{
		return toCartesian().cross(v2.toCartesian()).toSpherical();
	}

	@Override
	public CartesianVector toCartesian()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public CylindricalVector toCylindrical()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public SphericalVector toSpherical()
	{
		return this;
	}
}
