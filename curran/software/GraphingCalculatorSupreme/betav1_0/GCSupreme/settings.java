package GraphingCalculatorSupreme;
import java.awt.*;
public class settings
{
	static Color AxesColor = new Color(50,50,50);
	static Color GraphColor = Color.black;
	static Color DerivativeColor = new Color(0,200,0);
	static Color IntegralColor = new Color(200,0,0);
	static Color SlopeFieldColor = new Color(160,160,160);
	static Color ZoomBoundingBoxColor = Color.green;
	static double[] DefaultWindowDimArray = {-10,10,-10,10};
	static int currentRes = 20;
	static int tabSideWidth = 7;
	
	public static int getRes()
	{	return currentRes;	}
	public static double[] getDefaultWindowDimArray()
	{	return DefaultWindowDimArray;	}
	public static int getMediatedSize(int initial)//returns a new value for (individually) width and height that will let them fit into a tabbed pane
	{	return initial - tabSideWidth*2;	}
	
	
	public static Color getAxesColor()
	{	return AxesColor;	}
	public static Color getGraphColor()
	{	return GraphColor;	}
	public static Color getDerivativeColor()
	{	return DerivativeColor;	}
	public static Color getIntegralColor()
	{	return IntegralColor;	}
	public static Color getSlopeFieldColor()
	{	return SlopeFieldColor;	}
	public static Color getZoomBoundingBoxColor()
	{	return ZoomBoundingBoxColor;	}
}