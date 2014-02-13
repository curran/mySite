public class rotationInstance
{
	double newX,newY,newZ,x,y,z,CenterX=0,CenterY=0,CenterZ=0;
	double[][] RMatrix;

	//public rotationInstance(double Xrot, double Yrot,double Zrot)
	public rotationInstance(double q, double f)//q = theta = Angle in the XY plane, f = phi = Angle from the Z axis, r = distance from origin
	{
		double r = 1;
		double sinq = Math.sin(q);
		double sinf = Math.sin(f);
		double cosq = Math.cos(q);
		double cosf = Math.cos(f);
		double[][] T = {{-sinq,   -(cosq)*(cosf),   -(cosq)*(sinf),0},
					   {cosq,    -(sinq)*(cosf),   -(sinq)*(sinf),0},
					   {0,       sinf,             -cosf,         0},
					   {0,       0,                r,             1}};
		RMatrix = T;
	}
	public void setCenterOfRotation(point3D C)
	{
		CenterX = C.getX();
		CenterY = C.getY();
		CenterZ = C.getZ();
	}
	public point3D getRotatedPoint3D(point3D p)
	{
		x = p.getX() -CenterX;
		y = p.getY() -CenterY;
		z = p.getZ() -CenterZ;
		newX = x * RMatrix[0][0] + y * RMatrix[1][0] + z * RMatrix[2][0] + CenterX;
		newY = x * RMatrix[0][1] + y * RMatrix[1][1] + z * RMatrix[2][1] + CenterY;
		newZ = x * RMatrix[0][2] + y * RMatrix[1][2] + z * RMatrix[2][2] + CenterZ;
		return new point3D(newX,newY,newZ);
	}
}