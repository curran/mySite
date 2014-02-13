import java.awt.*;
public class universe3D
{
	window2D screen;
	object3D[] objects;
	double Zdist = 40; //the observer distance away from the origin
	double zoom = 25;
	boolean[] DisplaySwitches = {true,false,false,true};
	//DisplaySwitches[0] = zsort or not
	//DisplaySwitches[1] = true -> black outline
	//DisplaySwitches[2])// = true -> fill with objColor
	//DisplaySwitches[3])// = true -> fill with ShadedObjColor & update ShadedObjColor with movement
	
	public universe3D(window2D s)
	{
		screen = s;
		objects = new object3D[0];//perhaps not completely necessary
	}
	public void addObjects(object3D[] obj)
	{
		int objectsLength= objects.length;
		object3D[] tempObjects = new object3D[objectsLength + obj.length];
		
		for(int i = 0; i<objectsLength;i++)
		{
			tempObjects[i] = objects[i];
		}
		for(int i = objectsLength;i<tempObjects.length;i++)
		{
			tempObjects[i] = obj[i-objectsLength];
		}
		objects = new object3D[tempObjects.length];
		objects = tempObjects;
	}
	public void clearObjects()
	{
		objects = new object3D[0];
	}
	public void displayWireframe(Graphics2D g,rotationInstance ROTATION)
	{	
		//first gather all polygons from all objects into the 'polygons' array
		//count how many polygons:
		int totalPolygons = 0;
		for(int O=0; O<objects.length;O++)//for each object3D
		{
			totalPolygons+=objects[O].polygons.length;
		}
		polygon3D[] polygons = new polygon3D[totalPolygons];//contains all polygons
		//fill the polygon array:
		int currentPlace = 0;
		for(int O=0; O<objects.length;O++)//for each object3D
		{
			polygon3D[] temp = objects[O].getPolygons();
			for(int P=0; P<temp.length;P++)//for each polygon3D
			{
				polygons[currentPlace] = temp[P];
				currentPlace++;
			}
		}
		
		g.clearRect(0,0,screen.width,screen.height);
		point3D[] pnts;
		polygon3D[] rotatedpolygons = new polygon3D[polygons.length];
		for(int o = 0; o<polygons.length;o++)//for every polygon
		{
			pnts = polygons[o].getPoint3DArray();
			point3D[] temppnts = new point3D[pnts.length];
			for(int p = 0; p<pnts.length;p++)//for every point of every polygon
			{
				temppnts[p] = ROTATION.getRotatedPoint3D(pnts[p]);//rotate each point of each polygon
			}
			rotatedpolygons[o] = new polygon3D(temppnts,polygons[o].getColor());
		}
		
		//insert Z sort here
		
		if(DisplaySwitches[0])//ZSort
		{
			polygon3D temp;

			for(int a = 0; a<totalPolygons-1;a++)
			{
				for(int b = 0; b<totalPolygons-1;b++)
				{
					if(rotatedpolygons[b].getAvgZ()<=rotatedpolygons[b+1].getAvgZ())
					{
						temp = rotatedpolygons[b];
						rotatedpolygons[b] = rotatedpolygons[b+1];
						rotatedpolygons[b+1] = temp;
					}
				}
			}
		}//ZSort
		for(int i = 0; i<rotatedpolygons.length;i++)//for every rotated polygon
		{
			point3D[] ROTpnts = rotatedpolygons[i].getPoint3DArray();
			int[] Xs = new int[ROTpnts.length];
			int[] Ys = new int[ROTpnts.length];
			for(int p = 0; p<ROTpnts.length;p++)//for every point of every polygon
			{
				double denom = ROTpnts[p].getZ()+Zdist;
				Xs[p] = screen.getXpixel(zoom*ROTpnts[p].getX()/denom);
				Ys[p] = screen.getYpixel(zoom*ROTpnts[p].getY()/denom);
			}
			if(DisplaySwitches[2])// = true -> fill with objColor
			{
				g.setColor(rotatedpolygons[i].getColor());
				g.fillPolygon(Xs,Ys,Ys.length);
			}
			if(DisplaySwitches[3])// = true -> fill with ShadedObjColor & update ShadedObjColor with movement
			{
				rotatedpolygons[i].updateShadedObjColor();
				g.setColor(rotatedpolygons[i].getShadedObjColor());
				g.fillPolygon(Xs,Ys,Ys.length);
			}
			if(DisplaySwitches[1])// = true -> black outline
			{
				g.setColor(Color.black);
				g.drawPolygon(Xs,Ys,Ys.length);
			}
		}
	}//displayWireframe
	public void setSwitches(boolean[] s)
	{
		//DisplaySwitches = s;
	}
}