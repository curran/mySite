package GraphingCalculatorSupreme;
import java.awt.*;
import java.awt.event.*;//for MouseMotionListener
public class ParametricGraphPanel extends GenericGraphPanel implements MouseListener,MouseMotionListener
{
	Point ZoomPoint1;
	public ParametricGraphPanel(window2D funcSpace)
	{
		super(funcSpace);
		AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;
		addMouseListener (this);
		addMouseMotionListener (this);
	}
	public void setEquation(Function funcX,Function funcY)
	{
		bufGraphics.clearRect(0,0,functionSpace.getWidth(),functionSpace.getHeight());
		Toolbox.setParametricEquation(funcX,funcY);
		Toolbox.drawParametricEquation();
		Toolbox.drawAxes();
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