package Snake;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.print.attribute.standard.Sides;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;


public class Snake_v2 extends JFrame
{
	private static final String Interger = null;
	int top=40,bottom=100,right=10,left=100;
	int nbrX = 40,nbrY=20;
	int step = 20;
	int Vitesse=100;
	int Score = 0;     //save
	int direction = 1;   	//save				        				 //1->right , 2->down , 3->left , 4->up
	int Stage = 0;		//save
	
	
	ArrayList<Cell> Corps;		//save
	ArrayList<Cell> Obstacle;     //save
	Color ColorTete = Color.red;		
	Color ColorCorps = Color.green;
	Color ColorBG= Color.gray;
	Color ColorRepas = Color.yellow;
	Color ColorObstacle = Color.blue;
	Cell Repas = new Cell(7,7,ColorRepas);		//save
	//--------------------stage variables----------------------
	boolean TestObstacle = false;
	//---------------------------------------------------------
	JPanel MyPanel;
	Timer time;
	//---------------------------------------------------------
	JPanel Header,Footer,LeftSide,RightSide;
	JPanel Body;
	JButton Start,Stop,Save,Continue,Restart,Plus,Minus;
	JLabel GameName;
	
	public Snake_v2()
	{
		this.setTitle("Game of snake");
		this.setSize(right+left+step*nbrX + 14,top+bottom+step*nbrY+30);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		InitialisationBody();
		
		GameName = new JLabel("Snake Game by Mr ismail Ben Alla",JLabel.CENTER);
		GameName.setForeground(Color.white);
		GameName.setFont(new Font("Serif", Font.PLAIN, 20));
		
		Start = new JButton("Start");
		Start.setForeground(Color.white);
		Start.setBackground(new Color(120,60,180));
		
		Start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				time.start();
			}
		});
		 
		Restart = new JButton("Restart");
		Restart.setForeground(Color.white);
		Restart.setBackground(new Color(120,60,180));
		
		Restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		  
		Stop = new JButton("Stop");
		Stop.setForeground(Color.white);
		Stop.setBackground(new Color(120,60,180));
		
		Stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				time.stop();
			}
		});
		
		Save = new JButton("Save");
		Save.setForeground(Color.white);
		Save.setBackground(new Color(120,60,180));
		
		Save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				time.stop();
				try {
					saveInfoInFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		Continue = new JButton("Continue");
		Continue.setForeground(Color.white);
		Continue.setBackground(new Color(120,60,180));
		
		Continue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ContinueFromWhereYouAre();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		Plus = new JButton("+V");
		Plus.setForeground(Color.white);
		Plus.setBackground(new Color(120,60,180));
		
		Plus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vitesse-=10;
				if(Vitesse <= 0)
					Vitesse = 0;
				time.setDelay(Vitesse);
			}
		});
		
		Minus = new JButton("-V");
		Minus.setForeground(Color.white);
		Minus.setBackground(new Color(120,60,180));
		
		Minus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vitesse+=10;
				time.setDelay(Vitesse);
			}
		});
		
		//--------------------------------header-------------------------------
		Header = new JPanel();
		Header.setBackground(Color.DARK_GRAY);
		Header.setPreferredSize(new Dimension(nbrX*step, top));
		Header.add(GameName);
		
		//--------------------------------Footer-------------------------------
		Footer = new JPanel();
		Footer.setBackground(Color.DARK_GRAY);
		Footer.setPreferredSize(new Dimension(nbrX*step, bottom-7));
		
		//--------------------------------leftside-------------------------------
		LeftSide = new JPanel(new FlowLayout(1,10,20));
		LeftSide.setBackground(Color.DARK_GRAY);
		LeftSide.setPreferredSize(new Dimension(left,nbrY*step));
		LeftSide.add(Start);
		LeftSide.add(Stop);
		LeftSide.add(Save);
		LeftSide.add(Continue);
		LeftSide.add(Restart);
		LeftSide.add(Plus);
		LeftSide.add(Minus);
		
		//--------------------------------rightside-------------------------------
		RightSide = new JPanel();
		RightSide.setBackground(Color.DARK_GRAY);
		RightSide.setPreferredSize(new Dimension(right,nbrY*step));
		
		//--------------------------------body-------------------------------
		Body = new JPanel()
		{
			@Override
			public void paint(Graphics g) 
			{
				super.paint(g);
				g.setColor(ColorBG);
				GridOn(g);
				dessigner_Snake_v2(g);
				dessignerRepas(g);
				PassToNextLevel(g);
			}
		};
		//--------------------------------keylistener-----------------------------------
		Body.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if(e.getKeyCode() == KeyEvent.VK_RIGHT && direction!=3) direction = 1;
				if(e.getKeyCode() == KeyEvent.VK_DOWN && direction!=4)  direction = 2; 
				if(e.getKeyCode() == KeyEvent.VK_LEFT && direction!=1) direction = 3;
				if(e.getKeyCode() == KeyEvent.VK_UP && direction!=2)  direction = 4;
				time.setDelay(Vitesse);
			}
		});
		//----------------------------------timer-----------------------------------------
		time = new Timer(Vitesse, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//----------------move the body--------------------
				for(int i=Corps.size()-1 ; i>=1 ; i--)
				{
					Corps.get(i).x = Corps.get(i-1).x;
					Corps.get(i).y = Corps.get(i-1).y;
				}
				//----------------then move head------------------
				if(direction == 1) 
				{
					Corps.get(0).x++;
					if(Corps.get(0).x == nbrX)
					{
						Corps.get(0).x = 0;
					}
				}
				if(direction == 2) 
				{
					Corps.get(0).y++;
					if(Corps.get(0).y == nbrY)
					{
						Corps.get(0).y = 0;
					}	
				}
				if(direction == 3) 
				{
					if(Corps.get(0).x == 0)
					{
						Corps.get(0).x = nbrX;
					}
					Corps.get(0).x--;
				}
				if(direction == 4) 
				{
					if(Corps.get(0).y == 0)
					{
						Corps.get(0).y = nbrY;
					}
					Corps.get(0).y--;
				}
				
				TestIfEatYourself();
				//----------------test if the end-----------------
				if(TestObstacle)
					time.stop();
				
				repaint();
			}
		});
//		time.start();
		
		MyPanel = new JPanel(new BorderLayout());
		MyPanel.add(BorderLayout.NORTH,Header);
		MyPanel.add(BorderLayout.SOUTH,Footer);
		MyPanel.add(BorderLayout.WEST,RightSide);
		MyPanel.add(BorderLayout.EAST,LeftSide);
		MyPanel.add(BorderLayout.CENTER,Body);
		
		Body.setFocusable(true);
		Start.setFocusable(false);
		Stop.setFocusable(false);
		Save.setFocusable(false);
		Restart.setFocusable(false);
		Plus.setFocusable(false);
		Minus.setFocusable(false);
		Continue.setFocusable(false);
		this.setContentPane(MyPanel);
		this.setVisible(true);
	}
	//######################################################################################
	//----------------methodeStage0------------------------
	void InitialisationBody()
	{
		Corps = new ArrayList<Cell>();
		Corps.add(new Cell(15,10,ColorTete));
		Corps.add(new Cell(14,10,ColorCorps));
		Corps.add(new Cell(13,10,ColorCorps));
		Corps.add(new Cell(12,10,ColorCorps));
		Corps.add(new Cell(11,10,ColorCorps));
		Corps.add(new Cell(10,10,ColorCorps));
//		
		Corps.add(new Cell(14,10,ColorCorps));
		Corps.add(new Cell(13,10,ColorCorps));
		Corps.add(new Cell(12,10,ColorCorps));
		Corps.add(new Cell(11,10,ColorCorps));
		Corps.add(new Cell(10,10,ColorCorps));
		Corps.add(new Cell(14,10,ColorCorps));
		Corps.add(new Cell(13,10,ColorCorps));
		Corps.add(new Cell(12,10,ColorCorps));
		Corps.add(new Cell(11,10,ColorCorps));
		Corps.add(new Cell(10,10,ColorCorps));
		Corps.add(new Cell(14,10,ColorCorps));
		Corps.add(new Cell(13,10,ColorCorps));
		Corps.add(new Cell(12,10,ColorCorps));
		Corps.add(new Cell(11,10,ColorCorps));
		Corps.add(new Cell(10,10,ColorCorps));
		Corps.add(new Cell(14,10,ColorCorps));
		Corps.add(new Cell(13,10,ColorCorps));
		Corps.add(new Cell(12,10,ColorCorps));
		Corps.add(new Cell(11,10,ColorCorps));
		Corps.add(new Cell(10,10,ColorCorps));
	}
	//-----------------eat your self------------------------
	void TestIfEatYourself()
	{
		for(int i=1 ; i<Corps.size() ; i++)
		{
			if(Corps.get(0).x == Corps.get(i).x && Corps.get(0).y == Corps.get(i).y)
			{
				for(int j = Corps.size()-1 ; j>=i ; j--)
				{
					Corps.remove(j);
				}
				//-----------------add a voice here------------------
				playsound(2);
			}
		}
	}
	//--------------methode dessigner le repas---------------
	void dessignerRepas(Graphics g)
	{
		Random rand = new Random();
		if(Corps.get(0).x == Repas.x && Corps.get(0).y == Repas.y)
		{
			Repas.x =  rand.nextInt(nbrX);
			Repas.y =  rand.nextInt(nbrY);
			//-----------------don't display in Snake_v2's body-----------------
			for(int i=1 ; i<Corps.size() ; i++)
			{
				if(Corps.get(i).x == Repas.x && Corps.get(i).y == Repas.y)
				{
					Repas.x =  rand.nextInt(nbrX);
					Repas.y =  rand.nextInt(nbrY);
				}
			}
			//-----------------don't display inside the obstacle-----------------
			if(Stage >= 1) {
				for(int i=0 ; i<Obstacle.size() ; i++)
				{
					if(Obstacle.get(i).x == Repas.x && Obstacle.get(i).y == Repas.y)
					{
						Repas.x =  rand.nextInt(nbrX);
						Repas.y =  rand.nextInt(nbrY);
					}
				}
			}
			//-------------------------------------------------------------
			Score++;
			int s = Corps.size();
			Corps.add(new Cell(Corps.get(s-1).x,Corps.get(s-1).y,new Color(rand.nextInt(200),rand.nextInt(200),rand.nextInt(200))));
			//----------------sound of eating le repas--------------
			playsound(1);
		}
		dessignerCell(g, Repas,'O');
	}
	
	//----------------------methode dessigne----------------------
	void GridOn(Graphics g)  //Graphics g
	{
        //Graphics g = MyPanel.getGraphics();
		g.fillRect(0, 0, nbrX*step, nbrY*step);
		g.setColor(Color.black);
		
		for(int i= 0 ; i<= nbrX*step+0; i+=step)
		{
			g.drawLine(i, 0,i, 0 + nbrY*step);
		}
		for(int j= 0 ; j<= nbrY*step+0; j+=step)
		{
			g.drawLine(0, j,0 + nbrX*step, j);
		}
	}
	//--------------methode dessignee Cell-----------------
	void dessignerCell(Graphics g,Cell cel,char shape)
	{
		g.setColor(cel.Color);
		if(shape == 'O')
			g.fillOval(0+step*cel.x,0+step*cel.y, step, step);
		else
			g.fillRect(0+step*cel.x,0+step*cel.y, step, step);
	}
	//----------------methode dessigner Snake_v2--------------------
	void dessigner_Snake_v2(Graphics g)
	{
		for(Cell cel : Corps) {
			if(cel == Corps.get(0))
				dessignerCell(g, cel,'O');
			else
				dessignerCell(g, cel,'r');
		}
	}
	//-----------------test to pass to the next level----------------
	void PassToNextLevel(Graphics g)
	{
		if(Score == 2) {Stage++;Score=0;InitialisationBody();repaint();time.stop();}
		if(Stage == 1) {InitialisationStage_1();Stage(g);}
		if(Stage == 2){InitialisationStage_2();Stage(g);}
		if(Stage == 3){
			TestObstacle = true;
			g.setFont(new Font("serif",Font.PLAIN,40));
			g.setColor(Color.cyan);
			g.fillRect(0+4*step, 0,step*(nbrX-8),0);
			g.setColor(Color.blue);
			g.drawString("Congratulation you have won the game", 0+4*step,50);
		}
	}
	//----------------methode dessigner Snake_v2--------------------
		void dessigner_Obstacle(Graphics g)
		{
			for(Cell cel : Obstacle)
				dessignerCell(g, cel,'r');
		}
	//----------------methodeStage0------------------------
		void InitialisationStage_1()
		{
			Obstacle = new ArrayList<Cell>();
			for(int i=0 ; i<nbrX ; i++)
			{
				Obstacle.add(new Cell(i, 0,ColorObstacle));
				Obstacle.add(new Cell(i, nbrY-1,ColorObstacle));
			}
				
			for(int i=0 ; i<nbrY ; i++)
			{
				Obstacle.add(new Cell(0, i,ColorObstacle));
				Obstacle.add(new Cell(nbrX-1,i,ColorObstacle));
			}
		}
	//----------------methodeStage0------------------------
		void InitialisationStage_2()
		{
			Obstacle = new ArrayList<Cell>();
			for(int i=3 ; i<9 ; i++)
			{
				Obstacle.add(new Cell(3,i,ColorObstacle));
				Obstacle.add(new Cell(i,3,ColorObstacle));
				Obstacle.add(new Cell(nbrX-4,i,ColorObstacle));
				Obstacle.add(new Cell(nbrX-i-1,3,ColorObstacle));
				Obstacle.add(new Cell(i,nbrY-4,ColorObstacle));
				Obstacle.add(new Cell(3,nbrY-i-1,ColorObstacle));
				Obstacle.add(new Cell(nbrX-4,nbrY-i-1,ColorObstacle));
				Obstacle.add(new Cell(nbrX-i-1,nbrY-4,ColorObstacle));
			}
				
		}
	//----------------------------stage 2-------------------------------
	void Stage(Graphics g)
	{
		dessigner_Obstacle(g);
		for(Cell X : Obstacle)
		{
			if(Corps.get(0).x == X.x && Corps.get(0).y == X.y)
			{
				GameIsOver(g);
				TestObstacle = true;
			}	
		}
	}
	//---------------------------save function--------------------------
	void saveInfoInFile() throws IOException
	{
		FileWriter File = new FileWriter("snakedata.txt");
		PrintWriter Pen = new PrintWriter(File);
		
		String str = "";
		str = str + ((Object)Score).toString()+"#"+((Object)direction).toString()+"#"+((Object)Stage).toString();
		Pen.write(str+"\n");
		// save snake's body
		for(Cell Bit : Corps)
		{
			str = "";
			str += ((Object)Bit.x).toString()+"#"+((Object)Bit.y).toString()+"#"+((Object)Bit.Color.getRed()).toString()+"#";
			str += ((Object)Bit.Color.getGreen()).toString()+"|"+((Object)Bit.Color.getBlue()).toString()+"\n";
			Pen.write(str);
		}
		if(Obstacle != null) {
			for(Cell Bit : Obstacle)
			{
				str = "";
				str += ((Object)Bit.x).toString()+"#"+((Object)Bit.y).toString()+"#"+((Object)Bit.Color.getRed()).toString()+"#";
				str += ((Object)Bit.Color.getGreen()).toString()+"#"+((Object)Bit.Color.getBlue()).toString()+"\n";
				Pen.write(str);
			}
		}
		Pen.close();
		File.close();
	}
	//--------------------------continue from where you are----------------------------
	void ContinueFromWhereYouAre() throws IOException
	{
		FileReader File = new FileReader("snakedata.txt");
		BufferedReader Reader = new BufferedReader(File);
		
		String line = Reader.readLine();
		String []str = line.split("#");
		this.Score = Integer.parseInt(str[0]);
//		this.direction = Integer.parseInt(str[1]);
		this.Stage = Integer.parseInt(str[2]);
		this.TestObstacle = false;
	}
	//-------------------------game is over------------------------------
	void GameIsOver(Graphics g)
	{
		g.setFont(new Font("serif",Font.PLAIN,100));
		g.setColor(Color.red);
		g.drawString("Game over !!!", (nbrX/5)*step,(nbrX/4)*step);
		playsound(3);
	}
	//-----------------------play the sound------------------------------
	void playsound(int choice)
	{
		Random rand = new Random();
		String path=null;
		switch(choice)
		{
		case 1:
			path = "D:\\eclipse\\MyProjects\\src\\Snake\\snakeEating.wav";
			break;
		case 2:
			path = "D:\\eclipse\\MyProjects\\src\\Snake\\eatinghimself.wav";
			break;
		case 3:
			path = "D:\\eclipse\\MyProjects\\src\\Snake\\youlost.wav";
			break;
		case 4:{
//			path = "D:\\eclipse\\MyProjects\\src\\Snake\\comic_applause.wav";
			int i = rand.nextInt(4);
			switch(i) {
			case 0:
				path = "D:\\eclipse\\MyProjects\\src\\Snake_v2\\yes_yes.wav";
				break;
			case 1:
				path = "D:\\eclipse\\MyProjects\\src\\Snake_v2\\woo_woo.wav";
				break;
			case 2:
				path = "D:\\eclipse\\MyProjects\\src\\Snake_v2\\worm_applause.wav";
				break;
			case 3:
				path = "D:\\eclipse\\MyProjects\\src\\Snake_v2\\comic_applause.wav";
				break;
			default:break;
			}
		}
			break;
		default:
			break;
		}
		SnakeEating sound = new SnakeEating(path);
		sound.start();
	}
	//===========================================================
	public static void main(String[] args) {
		new Snake_v2();
	}
	
}


//---------------------------------------class Cell-------------------------------------------
class Cell
{
	int x,y;
	Color Color;
	public Cell(int x, int y, Color color) {
		super();
		this.x = x;
		this.y = y;
		Color = color;
	}
	
}
