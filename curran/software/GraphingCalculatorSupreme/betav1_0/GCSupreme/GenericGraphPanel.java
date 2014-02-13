package GraphingCalculatorSupreme;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;//for MouseListener etc

public class GenericGraphPanel extends JPanel 
{
	Color bkgColor = Color.white;
	window2D functionSpace;
	Function function;
	Point ZoomPoint;
	int ZoomBoxSideX,ZoomBoxSideY;
	public Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_OFF;
    public Object Rendering = RenderingHints.VALUE_RENDER_SPEED;
    Graphics2D bufGraphics;
    Image bufImg = null;
    GraphToolBox Toolbox;//does all operations and stores the window information
    
	public GenericGraphPanel(window2D funcSpace)
	{
		setSpace(funcSpace);
		setBackground(bkgColor);
		repaint();
	}
	public void setSpace(window2D funcSpace)
	{
		functionSpace = funcSpace;
		setPreferredSize(new Dimension(functionSpace.getWidth(),functionSpace.getHeight()));
	}
	public void turnOnAntiAlias()
	{
		AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;
		try
		{
			bufGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);
		}
		catch(Exception e){}
	}
	public void turnOffAntiAlias()
	{
		AntiAlias = RenderingHints.VALUE_ANTIALIAS_OFF;
	    bufGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);
	}
	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
		if(bufImg==null)
		{
			bufImg = createImage(functionSpace.getWidth(),functionSpace.getHeight());
			bufGraphics = (Graphics2D) bufImg.getGraphics();
	        bufGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);
	        bufGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, Rendering);
	        bufGraphics.setBackground(bkgColor);
	        
	        Toolbox = new GraphToolBox(functionSpace,bufGraphics);
	    }
	    else
	    {
			page.drawImage(bufImg,0,0,this);
			page.setColor(settings.getZoomBoundingBoxColor());
			if(ZoomPoint != null)//draws zooming box
			{
				page.drawRect((int)ZoomPoint.getX(),(int)ZoomPoint.getY(),ZoomBoxSideX,ZoomBoxSideY);
			}
		}
	}
	public void acceptVisitor(Visitor V)
    {
    	V.visitHost(this);
    }
//	public void mousePressed(MouseEvent event){}
//	public void mouseDragged(MouseEvent event){}
//	public void mouseClicked(MouseEvent event){}
//	public void mouseReleased(MouseEvent event) {}
//	public void mouseEntered(MouseEvent event) {}
//	public void mouseExited(MouseEvent event){}
//	public void mouseMoved(MouseEvent event) {}

}
