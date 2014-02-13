package GraphingCalculatorSupreme;
import java.awt.*;//for Color
public class FunctionObject3D extends object3D
{
	Function FofX;
	Color funcColor;
	double DomainMin;
	double DomainMax;
	public FunctionObject3D(Function F,int res,Color Colr,double Dm, double dM)
	{
		super(new polygon3D[0]);
		FofX = F;
		funcColor = Colr;

		DomainMin = Dm;
		DomainMax = dM;
		setPolygons(res);
	}
	public void setPolygons(int res)
	{
		point3D firstPoint,LastPoint;
		double firstY,lastY,firstX,lastX;
		polygon3D[] functionPolygons = new polygon3D[res];
		
		firstX = DomainMin;
		firstY = FofX.Evaluate(firstX);
		for(int i = 0; i<res;i++)
		{
			lastX = DomainMin+((double)(DomainMax-DomainMin)*((double)(i+1)/res));
			lastY = FofX.Evaluate(lastX);
			
			firstPoint = new point3D(firstX,firstY,0);
			LastPoint = new point3D(lastX,lastY,0);
			point3D[] points = {firstPoint,LastPoint};
			
			functionPolygons[i] = new polygon3D(points,funcColor);
			
			firstY = lastY;
			firstX = lastX;
		}
		
		polygons = functionPolygons;
	}//setPolygons
}