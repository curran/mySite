package GraphingCalculatorSupreme;
import java.awt.*;
import java.awt.event.*;//for MouseMotionListener
public class FunctionGraphPanel extends GenericGraphPanel implements MouseListener,MouseMotionListener
{
	Point ZoomPoint1;
	public FunctionGraphPanel(window2D funcSpace)
	{
		super(funcSpace);
		AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;
		addMouseListener (this);
		addMouseMotionListener (this);
	}
	public void setFunction(Function func)
	{
		bufGraphics.clearRect(0,0,functionSpace.getWidth(),functionSpace.getHeight());
		Toolbox.setFunction(func);
		Toolbox.drawFunction();
		Toolbox.drawAxes();
		repaint();
	}
	public void drawDerivative()
	{
		Toolbox.drawDerivative();
		repaint();
	}
	public void drawIntegral(double initX,double initY)
	{
		Toolbox.setSlopeField();
		Toolbox.drawSlopeField(false);
		Toolbox.drawParticularSolution(initX,initY);
		repaint();
	}
	public void doRiemannSum(double a, double b, int n)
	{
		
		String[][] results = new String[3][2];
		//do RRS;
		results[0] = Toolbox.doRiemannSum(a,b,n,GraphToolBox.RRS);
		//do LRS;
		results[1] = Toolbox.doRiemannSum(a,b,n,GraphToolBox.LRS);
		//do TRS;
		results[2] = Toolbox.doRiemannSum(a,b,n,GraphToolBox.TRS);
		
		for(int i= 0;i<3;i++)
		{
			bufGraphics.drawString(results[i][0],10,20+15*i);
			bufGraphics.drawString(results[i][1],130,20+15*i);
		}
		
		repaint();
	}
	
	public void mousePressed(MouseEvent event)
	{
		ZoomPoint1 = event.getPoint();
	}
	public void mouseDragged(MouseEvent event)
	{
		
		Point ZoomPoint2 = event.getPoint();
		ZoomBoxSideX = (int)(ZoomPoint2.getX()-ZoomPoint1.getX());
		ZoomBoxSideY = (int)(ZoomPoint2.getY()-ZoomPoint1.getY());
		Point T1 = new Point(0,0);
		
		if(ZoomBoxSideX >=0 && ZoomBoxSideY >=0)
		{
			T1 = new Point(ZoomPoint1.x,ZoomPoint1.y);
		}
		else if(ZoomBoxSideX <=0 && ZoomBoxSideY <=0)
		{
			T1 = new Point(ZoomPoint2.x,ZoomPoint2.y);
			ZoomBoxSideX = -ZoomBoxSideX;
			ZoomBoxSideY = -ZoomBoxSideY;
		}
		else if(ZoomBoxSideX <=0 && ZoomBoxSideY >=0)
		{
			T1 = new Point(ZoomPoint1.x+ZoomBoxSideX,ZoomPoint1.y);
			ZoomBoxSideX = -ZoomBoxSideX;
		}
		else if(ZoomBoxSideX >=0 && ZoomBoxSideY <=0)
		{
			T1 = new Point(ZoomPoint1.x,ZoomPoint1.y+ZoomBoxSideY);
			ZoomBoxSideY = -ZoomBoxSideY;
		}
		
		ZoomPoint = T1;
		
		repaint();//box gets drawn in GenericGraphPanel
	}
	
	
	public void mouseClicked(MouseEvent event){repaint();}
	public void mouseReleased(MouseEvent event)
	{
		if(ZoomPoint != null)
		{
			window2D w = Toolbox.functionSpace;
			double x1 = ZoomPoint.getX();
			double y1 = ZoomPoint.getY();
			double Xm = w.getXvalue(x1);
			double xM = w.getXvalue(x1+ZoomBoxSideX);
			double yM = w.getYvalue(y1);
			double Ym = w.getYvalue(y1+ZoomBoxSideY);
			bufGraphics.clearRect(0,0,functionSpace.getWidth(),functionSpace.getHeight());
			Toolbox.functionSpace.set(Xm,xM,Ym,yM);
			Toolbox.refresh();
			ZoomPoint = null; //so box does not get drawn in GenericGraphPanel
			repaint();
		}
	}
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event){}
	public void mouseMoved(MouseEvent event) {}
}