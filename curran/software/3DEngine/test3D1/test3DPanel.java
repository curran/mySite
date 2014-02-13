import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JPanel;

class test3DPanel extends generic3DPanel
{
	public test3DPanel(universe3D U,int w,int h)
	{
		super(U,w,h);
	}
	public void initObjects()//automatically called from generic3DPanel
	{
		object3D[] objects = new object3D[25];
		
		double x1=0,y1=0,z1=0;
		double theta=0,phi=0;
		double Rw= 20,Rh =10.0/objects.length,Rd = 2;
		for(int i = 0;i<objects.length;i++)
		{
			double c = 7;
			double t =10*(double)i/objects.length;
			x1 = 0;
			y1 = 0;
			z1 = -10+2*t;//2*((double)i/objects.length-.5)*10;
			theta = Math.PI+t/2.0;
			//									       center point  ,width,height,depth,theta,phi,color       ,
			objects[i] = toolbox3D.makeRectangularPrism(new point3D(x1,y1,z1),Rw,Rh,Rd,theta,phi,Color.getHSBColor((float)((double)i/objects.length),1,1));
		}
		currentUniverse.addObjects(objects);
		
		objects = new object3D[25];

		Rw= 10.0/objects.length;
		Rh =20.0/objects.length;
		Rd = 10;
		for(int i = 0;i<objects.length;i++)
		{
			double c = 10;
			double t =10*(double)i/objects.length;
			x1 = c*Math.sin(t/2);
			y1 = c*Math.cos(t/2);
			z1 = -10+2*t;//2*((double)i/objects.length-.5)*10;
			theta = Math.PI+t/2.0;
			//									       center point  ,width,height,depth,theta,phi,color       ,
			objects[i] = toolbox3D.makeRectangularPrism(new point3D(x1,y1,z1),Rw,Rh,Rd,theta,phi,Color.getHSBColor((float)((double)i/objects.length),1,1));
		}
		currentUniverse.addObjects(objects);
		
		objects = new object3D[25];


		for(int i = 0;i<objects.length;i++)
		{
			double c = 10;
			double t =10*(double)i/objects.length;
			x1 = -c*Math.sin(t/2);
			y1 = -c*Math.cos(t/2);
			z1 = -10+2*t;//2*((double)i/objects.length-.5)*10;
			theta = Math.PI+t/2.0;
			//									       center point  ,width,height,depth,theta,phi,color       ,
			objects[i] = toolbox3D.makeRectangularPrism(new point3D(x1,y1,z1),Rw,Rh,Rd,theta,phi,Color.getHSBColor((float)((double)i/objects.length),1,1));
		}
		currentUniverse.addObjects(objects);
	}
}
	