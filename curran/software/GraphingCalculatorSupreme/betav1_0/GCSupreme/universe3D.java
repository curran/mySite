package GraphingCalculatorSupreme;
import java.awt.*;
public class universe3D
{
	window3D screen;
	object3D[] objects;
	double Zdist = 40; //the observer distance away from the origin
	double zoom = 25;
	public static final boolean[] SWITCHES_WIREFRAME = {false,false,false,false,false,false,false,false,true , false};
	public static final boolean[] SWITCHES_SHADE =     {true, false,false,true, false,false,false,true, false, false};
	public static final boolean[] SWITCHES_RENDER =    {true, false,false,true, false,true, true, true, false, false};
	public static final boolean[] SWITCHES_RAYTRACE =  {false,false,false,false,false,false,false,false,false, true};
	
	//                                                    0    1     2     3     4     5     6     7     8      9
	boolean[] DisplaySwitches = SWITCHES_SHADE;
	//DisplaySwitches[0] = zsort or not
	//DisplaySwitches[1] = true -> black outline
	//DisplaySwitches[2])// = true -> fill with objColor
	//DisplaySwitches[3])// = true -> fill with ShadedObjColor
	//DisplaySwitches[4])// = true -> outline with ShadedObjColor
	//DisplaySwitches[5])// = true -> anti-alias
	//DisplaySwitches[6])// = true -> ShadedObjColor stroked outline (for rendering in anti-alias mode to fill in the cracks)
	//DisplaySwitches[7])// = true -> updateShadedObjColor
	//DisplaySwitches[8])// = true -> ObjColor outline
	//DisplaySwitches[9])// = true -> use Raytrace rendering
	
	
	polygon3D[] polygons = new polygon3D[0];
	int totalPolygons;
	toolbox3D universalToolbox3D = new toolbox3D();
	public universe3D(window3D s)
	{
		screen = s;
		objects = new object3D[0];
	}
	public void addObject(object3D obj)
	{
		object3D[] objs = new object3D[1];
		objs[0] = obj;
		addObjects(objs);
	}
	public void addObjects(object3D[] obj)
	{
		int objectsLength= objects.length;
		object3D[] tempObjects = new object3D[objectsLength + obj.length];
		
		//fill an array with the existing objects, then add the new objects
		for(int i = 0; i<objectsLength;i++)//existing objects
		{
			tempObjects[i] = objects[i];
		}
		for(int i = objectsLength;i<tempObjects.length;i++)//add the new objects
		{
			tempObjects[i] = obj[i-objectsLength];
		}
		objects = new object3D[tempObjects.length];//redefine the whole object list
		for(int i = 0; i<tempObjects.length;i++)//for each object
		{
			polygon3D[] poly = tempObjects[i].getPolygons();
			polygon3D[] polyT = new polygon3D[poly.length];
			for(int p = 0; p<poly.length;p++)//for every polygon
			{
//				point3D[] pnts = poly[p].getPoint3DArray();
//				point3D[] pntsT = new point3D[pnts.length];
//				for(int n=0;n<pnts.length;n++)
//				{
//					pntsT[n] = new point3D(pnts[n].getX(),pnts[n].getY(),pnts[n].getZ());
//				}
				polyT[p] = new polygon3D(poly[p]);
			}
			objects[i] = new object3D(polyT);
		}
		
		//first gather all polygons from all objects into the 'polygons' array
		//count how many polygons:
		totalPolygons = 0;
		for(int O=0; O<objects.length;O++)//for each object3D
		{
			totalPolygons+=objects[O].polygons.length;
		}
		polygons = new polygon3D[totalPolygons];//contains all polygons
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
	}
	public void clearObjects()
	{
		objects = new object3D[0];
	}
	public void display(Graphics2D g,rotationInstance ROTATION)
	{	
		if(!DisplaySwitches[9])
		{
			polygon3D[] rotatedpolygons = new polygon3D[polygons.length];
			for(int o = 0; o<polygons.length;o++)//for every polygon
			{
				/*pnts = polygons[o].getPoint3DArray();
				point3D[] temppnts = new point3D[pnts.length];
				
				for(int p = 0; p<pnts.length;p++)//for every point of every polygon
				{
					temppnts[p] = ROTATION.getRotatedPoint3D(pnts[p]);//rotate each point of each polygon
				}*/
				
				//rotatedpolygons[o] = new polygon3D(temppnts,polygons[o].getColor());
				rotatedpolygons[o] = universalToolbox3D.getRotatedPolygon(ROTATION,polygons[o]);
			}
			if(DisplaySwitches[0])//ZSort
			{
				polygon3D temp;
	
				for(int a = 0; a<polygons.length-1;a++)
				{
					for(int b = 0; b<polygons.length-1;b++)
					{
						if(rotatedpolygons[b].getAvgZ()<rotatedpolygons[b+1].getAvgZ())
						{
							temp = rotatedpolygons[b];
							rotatedpolygons[b] = rotatedpolygons[b+1];
							rotatedpolygons[b+1] = temp;
						}
					}
				}
			}//ZSort
			if(DisplaySwitches[5])// = true -> anti-alias
			{
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			}
			else
			{
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			}
			g.clearRect(0,0,screen.width,screen.height);
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
				
				if(ROTpnts.length > 2) //if it is a polygon
				{
					if(DisplaySwitches[7])// = true -> updateShadedObjColor
					{
						rotatedpolygons[i].updateShadedObjColor();
					}
					if(DisplaySwitches[6])// = true -> ShadedObjColor stroked outline (for rendering in anti-alias mode to fill in the cracks)
					{
						g.setColor(rotatedpolygons[i].getShadedObjColor());
						Shape strokedShape = (new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL)).createStrokedShape(new Polygon(Xs,Ys,Ys.length));  
						g.fill(strokedShape);
					}
					//filling options
					if(DisplaySwitches[3])// = true -> fill with ShadedObjColor
					{
						g.setColor(rotatedpolygons[i].getShadedObjColor());
						g.fillPolygon(Xs,Ys,Ys.length);
					}
					else if(DisplaySwitches[2])// = true -> fill with objColor
					{
						g.setColor(rotatedpolygons[i].getColor());
						g.fillPolygon(Xs,Ys,Ys.length);
					}
					
					//outline options
					if(DisplaySwitches[8])// = true -> ObjColor outline
					{
						g.setColor(rotatedpolygons[i].getColor());
						g.drawPolygon(Xs,Ys,Ys.length);
					}
					else if(DisplaySwitches[4])// = true -> ShadedObjColor outline
					{
						g.setColor(rotatedpolygons[i].getShadedObjColor());
						g.drawPolygon(Xs,Ys,Ys.length);
					}
					else if(DisplaySwitches[1])// = true -> black outline
					{
						g.setColor(Color.black);
						g.drawPolygon(Xs,Ys,Ys.length);
					}
				}
				else if(ROTpnts.length == 2)//if it is a line
				{
					g.setColor(rotatedpolygons[i].getColor());
					g.drawPolygon(Xs,Ys,Ys.length);
				}
				else if(ROTpnts.length == 1)//if it is text
				{
					g.setColor(rotatedpolygons[i].getColor());
					g.drawString(rotatedpolygons[i].getDisplayText(),Xs[0]-3,Ys[0]+3);
				}
			}
		}//if not raytracing
		else //if raytracing
		{
			point3D Viewpoint = new point3D(0,0,-30);
			g.setColor(Color.white);
			for(int x=0;x<screen.getWidth();x++)
			{
				for(int y=0;y<screen.getHeight();y++)
				{
					point3D currentRayStartingPoint3D = new point3D(screen.getXvalue(x),screen.getYvalue(y),Viewpoint.getZ());
					vector3D currentRayDirectionVector= new vector3D(0,0,1);
					
					Ray currentPixelRay = new Ray(currentRayStartingPoint3D,currentRayDirectionVector);
					
					if(objects[0].intersectsRay(currentPixelRay))
						g.drawLine(x,y,x,y);
				}
				System.out.println((double)x/screen.getWidth()*100.0+"% done");
			}
		}
	}//display
	public void setSwitches(boolean[] s)
	{
		DisplaySwitches = s;
	}
	
	public universe3D getClone()
	{
		return new universe3D(screen);
	}
}