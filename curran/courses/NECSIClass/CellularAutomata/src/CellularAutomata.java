/**
 * @(#)CellularAutomata.java
 * @author Curran Kelleher
 * @version 1.00 06/01/09
 */
 
import java.awt.*;
import java.applet.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.text.*;

public class CellularAutomata extends JPanel implements Runnable {
	
	static int ApW = 500;//width and height of the applet
	static int ApH = 500;
	
	int PWidth = 500;//width and height of the cell grid
	int PHeight = 500;
	int margin = 0;//distance from the side of the screen
	
	static int runNumber = 0;
	static boolean running = false;
	
	Cell[][] cells = new Cell[Parameters.sideCells][Parameters.sideCells];
	
	BufferedImage bufferImg;
	Graphics buffer;
	
	public static void main(String[] args)
	{
		startCellularAutomata();
	}
	
	public static void startCellularAutomata()
	{
		JFrame f = new JFrame("Cellular Automata");
		CellularAutomata c = new CellularAutomata();
		f.getContentPane().add(c);
		f.setSize(ApW,ApH);
		f.setVisible(true);
		
		if(Parameters.mode == Parameters.MODE_OUTPUTIMAGES)
		{
			for(int i=0;i<5;i++)
			{
				c.startRunThread();
				running = true;
				while (running)
				{
					try{Thread.currentThread().sleep(2000);}catch(Exception e){}
					System.out.println("waiting on run #"+runNumber);
				}
				runNumber++;
			}
		}
		else if(Parameters.mode == Parameters.MODE_RENDERONSCREEN)
		{
			c.startRunThread();
		}
	}
	
	public CellularAutomata()
	{
		super();
		initCells();
		setVisible(true);
		bufferImg = new BufferedImage(ApW, ApH, BufferedImage.TYPE_INT_RGB);
		buffer = bufferImg.createGraphics();
	}
	
	public void initCells()
	{
		//create the cells
		for(int i=0;i<Parameters.sideCells;i++)
			for(int j=0;j<Parameters.sideCells;j++)
			{
				int W = (int)((double)(i+1)/Parameters.sideCells*PWidth+margin);
				W -= (int)((double)i/Parameters.sideCells*PWidth+margin);
				int H = (int)((double)(j+1)/Parameters.sideCells*PWidth+margin);
				H -= (int)((double)j/Parameters.sideCells*PWidth+margin);
				
				cells[i][j] = new Cell( (int)((double)i/Parameters.sideCells*PWidth+margin),
										(int)((double)j/Parameters.sideCells*PHeight+margin),W,H);

				cells[i][j].setState(Parameters.generateInitialCellState());
			}
		
		//interconnect the cells
		int I,J;
		for(int i=0;i<Parameters.sideCells;i++)
			for(int j=0;j<Parameters.sideCells;j++)
			{
				Cell N,NE,E,SE,S,SW,W,NW;
				
				//N
				J = j-1;
				if(J<0)
					J+=Parameters.sideCells;
				N = cells[i][J];
				
				//NW
				I = i-1;
				if(I<0)
					I+=Parameters.sideCells;
				NW = cells[I][J];
				
				//NE
				I = i+1;
				if(I>=Parameters.sideCells)
					I-=Parameters.sideCells;
				NE = cells[I][J];
				
				//E
				E = cells[I][j];
				
				//W
				I = i-1;
				if(I<0)
					I+=Parameters.sideCells;
				W = cells[I][j];
				
				//SW
				J = j+1;
				if(J>=Parameters.sideCells)
					J-=Parameters.sideCells;
				SW = cells[I][J];
				
				//S
				S = cells[i][J];
				
				//SE
				I = i+1;
				if(I>=Parameters.sideCells)
					I-=Parameters.sideCells;
				SE = cells[I][J];
				
				Cell[] cellss = {N,NE,E,SE,S,SW,W,NW};
				cells[i][j].setSurroundingCells(cellss);
			}
	}
	public void initCellStates()
	{
		for(int i=0;i<Parameters.sideCells;i++)
			for(int j=0;j<Parameters.sideCells;j++)
				cells[i][j].setState(Parameters.generateInitialCellState());
	}
	public void startRunThread()
	{
		Thread t = new Thread(this);
		t.start();
	}
	public void run()
	{
		running = true;
		DecimalFormat f = new DecimalFormat("000");
		initCellStates();//init the cells with randomized initial conditions
		for(int t=0;t<Parameters.numIterations;t++)
		{
			if(Parameters.mode == Parameters.MODE_RENDERONSCREEN)
				try{Thread.currentThread().sleep(100);}catch(Exception e){}
			else
				System.out.println("    calculating iteration #"+t);
			buffer.setColor(Color.black);
			buffer.fillRect(0,0,600,600);
			//draw
			for(int i=0;i<Parameters.sideCells;i++)
				for(int j=0;j<Parameters.sideCells;j++)
				{
					buffer.setColor(cells[i][j].getColor());
					buffer.fillPolygon(cells[i][j].getRectangle());
				}
			
			if(Parameters.mode == Parameters.MODE_OUTPUTIMAGES)
				saveImage(runNumber+"CA"+t);
			
			if(Parameters.mode == Parameters.MODE_RENDERONSCREEN)
				repaint();
			
			for(int i=0;i<Parameters.sideCells;i++)
				for(int j=0;j<Parameters.sideCells;j++)
					cells[i][j].applyRuleVirtually();
					
			for(int i=0;i<Parameters.sideCells;i++)
				for(int j=0;j<Parameters.sideCells;j++)
					cells[i][j].actuallyApplyFutureState();
		}
		running = false;
	}
	
	public void saveImage(String fileName)
	{
		File imageOutputFile = new File("C:\\Java\\CellularAutomata\\Images\\"+fileName+".png");
		try
	    {
	        ImageIO.write(bufferImg, "png", imageOutputFile);
	    } catch (IOException e){}
	}

	public void paintComponent(Graphics g)
	{
		g.drawImage(bufferImg, 0, 0, this);
	}
}
