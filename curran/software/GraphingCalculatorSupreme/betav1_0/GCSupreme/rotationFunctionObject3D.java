package GraphingCalculatorSupreme;
import java.awt.*;//for Color
public class rotationFunctionObject3D extends object3D
{
	Function FofX;
	Color funcColor;
	boolean isRotationAboutYequals,isRotationAboutXequals;
	String Axis;
	double AxisEquals;
	double DomainMin;
	double DomainMax;
	public rotationFunctionObject3D(Function F,int res,Color Colr,String Ax,double AxisEq,double Dm, double dM)
	{
		super(new polygon3D[0]);
		FofX = F;
		funcColor = Colr;

		Axis = Ax.toUpperCase();
		AxisEquals = AxisEq;
		DomainMin = Dm;
		DomainMax = dM;

		if(Axis.equals("X"))
		{
			isRotationAboutXequals = true;
			isRotationAboutYequals = false;
		}
		else if(Axis.equals("Y"))
		{
			isRotationAboutXequals = false;
			isRotationAboutYequals = true;
		}
		
		setPolygons(res);
	}
	public void setPolygons(int res)
	{
		int cartesianResolution = res;
		point3D[][] cartesianCoords = new point3D[cartesianResolution+1][cartesianResolution+1];
		double Xvalue;
		double Yvalue;
		//set the points
		if(isRotationAboutYequals)
		{
			for(int x=0; x <=cartesianResolution;x++)
			{
				Xvalue = ((double)x/(double)cartesianResolution)*(DomainMax-DomainMin)+DomainMin;
				Yvalue = FofX.Evaluate(Xvalue);
				cartesianCoords[x][0] = new point3D(Xvalue,Yvalue,0);
				for(int rotation = 0;rotation <= cartesianResolution; rotation++)
				{
					point3D temp = new point3D(Xvalue,Yvalue,0);
					temp = toolbox3D.rotateAboutYequals(temp,AxisEquals,(((double)rotation+1)/cartesianResolution)*Math.PI*2.0);
					cartesianCoords[x][rotation] = temp;
				}
			}
		}
		
		if(isRotationAboutXequals)
		{
			for(int x=0; x <=cartesianResolution;x++)
			{
				Xvalue = ((double)x/(double)cartesianResolution)*(DomainMax-DomainMin)+DomainMin;
				Yvalue = FofX.Evaluate(Xvalue);
				cartesianCoords[x][0] = new point3D(Xvalue,Yvalue,0);
				for(int rotation = 0;rotation <= cartesianResolution; rotation++)
				{
					point3D temp = new point3D(Xvalue,Yvalue,0);
					temp = toolbox3D.rotateAboutXequals(temp,AxisEquals,(((double)rotation+1)/cartesianResolution)*Math.PI*2.0);
					cartesianCoords[x][rotation] = temp;
				}
			}
		}
		
		//set the polygons
		int numSquares = cartesianResolution*cartesianResolution;
		polygon3D[] squares = new polygon3D[numSquares];
		
		for(int x=0; x <cartesianResolution;x++)
		{
			for(int y = 0; y<cartesianResolution;y++)
			{
				point3D[] points = {cartesianCoords[x][y],cartesianCoords[x][y+1],cartesianCoords[x+1][y+1],cartesianCoords[x+1][y]};
				squares[x*cartesianResolution+y] = new polygon3D(points,funcColor);
			}
		}
		
		/*int numSquares = cartesianResolution*cartesianResolution*2;
		polygon3D[] squares = new polygon3D[numSquares];
		for(int x=0; x <cartesianResolution;x++)
		{
			for(int y = 0; y<cartesianResolution;y++)
			{
				point3D[] a = {cartesianCoords[x][y],cartesianCoords[x][y+1],cartesianCoords[x+1][y+1]};
				squares[2*(x*cartesianResolution+y)] = new polygon3D(a,funcColor);
				point3D[] b = {cartesianCoords[x][y],cartesianCoords[x+1][y+1],cartesianCoords[x+1][y]};
				squares[2*(x*cartesianResolution+y)+1] = new polygon3D(b,funcColor);

			}
		}*/
	
		polygons = squares;
	}//setPolygons
}