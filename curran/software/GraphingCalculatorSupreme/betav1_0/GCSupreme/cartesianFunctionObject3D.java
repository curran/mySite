package GraphingCalculatorSupreme;
import java.awt.*;//for Color
public class cartesianFunctionObject3D extends object3D
{
	window3D graphSpace;
	Function FofX_Y;
	Color funcColor;
	public cartesianFunctionObject3D(Function F,int res,window3D wind,Color Colr)
	{
		super(new polygon3D[0]);
		FofX_Y = F;
		graphSpace = wind;
		funcColor = Colr;
		setPolygons(res);
	}
	public void setPolygons(int res)
	{
		int cartesianResolution = res;
		int cartesianResolutionP = cartesianResolution;//for square and /|/| shapes
		//int cartesianResolutionP = cartesianResolution*2;//for /|\ shapes
		
		point3D[][] cartesianCoords = new point3D[cartesianResolutionP+1][cartesianResolutionP+1];
		double Xvalue;
		double Yvalue;
		//set the points
		for(int x=0; x <=cartesianResolutionP;x++)
		{
			for(int y = 0; y<=cartesianResolutionP;y++)
			{
				Xvalue = ((double)x/(double)cartesianResolutionP)*(graphSpace.xMaxW3D-graphSpace.xMinW3D)+graphSpace.xMinW3D;
				Yvalue = ((double)y/(double)cartesianResolutionP)*(graphSpace.yMaxW3D-graphSpace.yMinW3D)+graphSpace.yMinW3D;
				cartesianCoords[x][y] = new point3D(Xvalue,Yvalue,FofX_Y.Evaluate(Xvalue,Yvalue));
			}
		}
		
		//set the polygons
		int numSquares = cartesianResolution*cartesianResolution;
		polygon3D[] squares = new polygon3D[numSquares];
		
		for(int x=0; x <cartesianResolution;x++)//makes "squares"
		{
			for(int y = 0; y<cartesianResolution;y++)
			{
				point3D[] points = {cartesianCoords[x][y],cartesianCoords[x][y+1],cartesianCoords[x+1][y+1],cartesianCoords[x+1][y]};
				squares[x*cartesianResolution+y] = new polygon3D(points,funcColor);

			}
		}//*/
		
		/*int numSquares = cartesianResolution*cartesianResolution*2;
		polygon3D[] squares = new polygon3D[numSquares];
		for(int x=0; x <cartesianResolution;x++)//makes /|/|/| triangles
		{
			for(int y = 0; y<cartesianResolution;y++)
			{
				point3D[] a = {cartesianCoords[x][y],cartesianCoords[x][y+1],cartesianCoords[x+1][y+1]};
				
				point3D[] b = {cartesianCoords[x][y],cartesianCoords[x+1][y+1],cartesianCoords[x+1][y]};
				
				//do not make polygons containing points that are undefined
				if(Double.isNaN(cartesianCoords[x][y].getZ()) || Double.isNaN(cartesianCoords[x+1][y].getZ()) ||Double.isNaN(cartesianCoords[x][y+1].getZ()) ||Double.isNaN(cartesianCoords[x+1][y+1].getZ()))
				{
					point3D[] stupiduselessspacefillingpoints = {new point3D(0,0,0)};
					squares[2*(x*cartesianResolution+y)] = new polygon3D(stupiduselessspacefillingpoints,Color.black);
					squares[2*(x*cartesianResolution+y)+1] = new polygon3D(stupiduselessspacefillingpoints,Color.black);
				}
				else
				{
					squares[2*(x*cartesianResolution+y)] = new polygon3D(a,funcColor);
					squares[2*(x*cartesianResolution+y)+1] = new polygon3D(b,funcColor);
				}
			}
		}*/
		
		/*int numSquares = cartesianResolutionP*cartesianResolutionP;
		int currentIndex = 0;
		polygon3D[] squares = new polygon3D[numSquares];
		for(int x=0; x <cartesianResolutionP;x+=2)//makes /|\ triangles
		{
			for(int y = 0; y<cartesianResolutionP;y+=2)
			{
				point3D[] Q = {cartesianCoords[x][y],cartesianCoords[x][y+2],cartesianCoords[x+2][y+2],cartesianCoords[x+2][y],cartesianCoords[x+1][y+1]};
				
				
				point3D[] a = {Q[0],Q[4],Q[1]};
				point3D[] b = {Q[2],Q[4],Q[1]};
				point3D[] c = {Q[2],Q[4],Q[3]};
				point3D[] d = {Q[0],Q[4],Q[3]};

				//do not make polygons containing points that are _undefined_
				if(Double.isNaN(cartesianCoords[x][y].getZ()) || Double.isNaN(cartesianCoords[x+1][y].getZ()) ||Double.isNaN(cartesianCoords[x][y+1].getZ()) ||Double.isNaN(cartesianCoords[x+1][y+1].getZ()))
				{
					point3D[] FILL = {new point3D(0,0,0)};
					a = b = FILL;
				}
				
				squares[currentIndex] = new polygon3D(a,funcColor);
				currentIndex++;
				squares[currentIndex] = new polygon3D(b,funcColor);
				currentIndex++;
				squares[currentIndex] = new polygon3D(c,funcColor);
				currentIndex++;
				squares[currentIndex] = new polygon3D(d,funcColor);
				currentIndex++;
			}
		}
		*/

		polygons = squares;
	}//setPolygons
}