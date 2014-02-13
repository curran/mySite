class point3D//stores and retrieves X,Y,Z coordinates
{
	double X,Y,Z;
	public point3D(double x,double y,double z)//constructor
	{
		X=x;
		Y=y;
		Z=z;
	}
	public void setX(double x)
	{	X=x;	}
	public void setY(double y)
	{	Y=y;	}
	public void setZ(double z)
	{	Z=z;	}
	public double getX()
	{	return X;	}
	public double getY()
	{	return Y;	}
	public double getZ()
	{	return Z;	}
}//point3D class