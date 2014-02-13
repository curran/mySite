import java.awt.*;

public class Cell
{
	public final static int N=0,NE=1,E=2,SE=3,S=4,SW=5,W=6,NW=7;
	Polygon cellBox;
	Color cellColor;
	Cell[] surroundingCells;
	double state = 0;
	double futureState = 0;
	public Cell(int xPix,int yPix,int W,int H)
	{
		int[] xpoints = {xPix,xPix+W,xPix+W,xPix};
		int[] ypoints = {yPix,yPix,yPix+H,yPix+H};
		
		cellBox = new Polygon(xpoints,ypoints,4);
	}
	
	public void setState(double s)
	{
		double min=-1,max=1;
		state = s;
		double c = (s-min)/(max-min);
		//System.out.println("c = "+c);
		int a = (int)(c*255.0);
		cellColor = new Color(a,a,a);
	}
	public double getState()
	{
		return state;
	}
	
	public void setSurroundingCells (Cell[] surroundings)
	{
		surroundingCells = surroundings;
	}
	public Polygon getRectangle()
	{
		return cellBox;
	}
	public Color getColor()
	{
		//cellColor = new Color((int)((double)Math.random()*255),(int)((double)Math.random()*255),(int)((double)Math.random()*255));
		return cellColor;
	}
	
	public void applyRuleVirtually()
	{
		futureState = Parameters.applyRule(surroundingCells);
		
	}
	
	public void actuallyApplyFutureState()
	{
		setState(futureState);
	}
}