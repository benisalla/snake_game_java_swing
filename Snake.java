


package Snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake extends JFrame
{
	
	int margeX = 100,margeY = 100;
	int nbCaseX = 40,nbCaseY=20;
	int LargeCellule = 20;
	int Vitesse=90;
	int Score = 0;
	int direction = 1;   					        				 //1->right , 2->down , 3->left , 4->up
	int Stage = 0;
	ArrayList<Cellule> Corps;
	ArrayList<Cellule> Obstacle;     
	Color CouleurTete = Color.red;
	Color CouleurCorps = Color.green;
	Color CouleurBG= Color.gray;
	Color CouleurRepas = Color.yellow;
	Color CouleurObstacle = Color.blue;
	Cellule Repas = new Cellule(5,7,CouleurRepas);
	//--------------------stage variables----------------------
	boolean TestObstacle_stage_1 = false;
	boolean TestObstacle = false;
	//---------------------------------------------------------
	JPanel MyPanel;
	Timer time;
	//---------------------------------------------------------
//	Jpanel
	
	public Snake()
	{
		this.setTitle("jeu de snake");
		this.setSize(margeX*2+LargeCellule*nbCaseX + 14,margeY*2 + LargeCellule*nbCaseY+30);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		Initialisation();
		InitialisationStage_2();
		
		MyPanel = new JPanel() 
		{
			@Override
			public void paint(Graphics g) 
			{
				super.paint(g);
				g.setColor(CouleurBG);
				dessigner(g);
				dessigner_snake(g);
				dessignerRepas(g);
				PassToNextLevel(g);
				
				//---------------------display a text--------------------
				g.setFont(new Font("serif",Font.PLAIN,30));
				g.setColor(new Color(133,204,239));
				g.fillRect(0, 0, margeX+4*LargeCellule, margeY);
				g.setColor(Color.BLUE);
				g.drawString("Score : "+Score+" pts",5, 30);
				g.drawString("Stage : "+Stage,5, 80);
			}
		};
		//--------------------------------keylistener-----------------------------------
		this.addKeyListener(new KeyAdapter() 
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
		time = new Timer(350, new ActionListener() {
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
					if(Corps.get(0).x == nbCaseX)
					{
						Corps.get(0).x = 0;
						TestObstacle_stage_1 = true;
					}
				}
				if(direction == 2) 
				{
					Corps.get(0).y++;
					if(Corps.get(0).y == nbCaseY)
					{
						TestObstacle_stage_1 = true;
						Corps.get(0).y = 0;
					}	
				}
				if(direction == 3) 
				{
					if(Corps.get(0).x == 0)
					{
						Corps.get(0).x = nbCaseX;
						TestObstacle_stage_1 = true;
					}
					Corps.get(0).x--;
				}
				if(direction == 4) 
				{
					if(Corps.get(0).y == 0)
					{
						Corps.get(0).y = nbCaseY;
						TestObstacle_stage_1 = true;
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
		time.start();
		
		
		this.setContentPane(MyPanel);
		this.setVisible(true);
	}
	//----------------methodeStage0------------------------
	void Initialisation()
	{
		Corps = new ArrayList<Cellule>();
		Corps.add(new Cellule(15,10,CouleurTete));
		Corps.add(new Cellule(14,10,CouleurCorps));
		Corps.add(new Cellule(13,10,CouleurCorps));
		Corps.add(new Cellule(12,10,CouleurCorps));
		Corps.add(new Cellule(11,10,CouleurCorps));
		Corps.add(new Cellule(10,10,CouleurCorps));

//		Corps.add(new Cellule(14,10,CouleurCorps));
//		Corps.add(new Cellule(13,10,CouleurCorps));
//		Corps.add(new Cellule(12,10,CouleurCorps));
//		Corps.add(new Cellule(11,10,CouleurCorps));
//		Corps.add(new Cellule(10,10,CouleurCorps));
//		Corps.add(new Cellule(14,10,CouleurCorps));
//		Corps.add(new Cellule(13,10,CouleurCorps));
//		Corps.add(new Cellule(12,10,CouleurCorps));
//		Corps.add(new Cellule(11,10,CouleurCorps));
//		Corps.add(new Cellule(10,10,CouleurCorps));
//		Corps.add(new Cellule(14,10,CouleurCorps));
//		Corps.add(new Cellule(13,10,CouleurCorps));
//		Corps.add(new Cellule(12,10,CouleurCorps));
//		Corps.add(new Cellule(11,10,CouleurCorps));
//		Corps.add(new Cellule(10,10,CouleurCorps));
//		Corps.add(new Cellule(14,10,CouleurCorps));
//		Corps.add(new Cellule(13,10,CouleurCorps));
//		Corps.add(new Cellule(12,10,CouleurCorps));
//		Corps.add(new Cellule(11,10,CouleurCorps));
//		Corps.add(new Cellule(10,10,CouleurCorps));
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
			Repas.x =  rand.nextInt(nbCaseX);
			Repas.y =  rand.nextInt(nbCaseY);
			//-----------------don't display in snake's body-----------------
			for(int i=1 ; i<Corps.size() ; i++)
			{
				if(Corps.get(i).x == Repas.x && Corps.get(i).y == Repas.y)
				{
					Repas.x =  rand.nextInt(nbCaseX);
					Repas.y =  rand.nextInt(nbCaseY);
				}
			}
			//-----------------don't display inside the obstacle-----------------
			for(int i=1 ; i<Obstacle.size() ; i++)
			{
				if(Obstacle.get(i).x == Repas.x && Obstacle.get(i).y == Repas.y)
				{
					Repas.x =  rand.nextInt(nbCaseX);
					Repas.y =  rand.nextInt(nbCaseY);
				}
			}
			//-------------------------------------------------------------
			Score++;
			int s = Corps.size();
			Corps.add(new Cellule(Corps.get(s-1).x,Corps.get(s-1).y,CouleurRepas));
			//----------------sound of eating le repas--------------
			playsound(1);
		}
		dessignerCellule(g, Repas,'O');
	}
	
	//----------------------methode dessigne----------------------
	void dessigner(Graphics g)  //Graphics g
	{
        //Graphics g = MyPanel.getGraphics();
		g.fillRect(margeX, margeY, nbCaseX*LargeCellule, nbCaseY*LargeCellule);
		g.setColor(Color.black);
		
		for(int i= margeX ; i<= nbCaseX*LargeCellule+margeX; i+=LargeCellule)
		{
			g.drawLine(i, margeY,i, margeY + nbCaseY*LargeCellule);
		}
		for(int j= margeY ; j<= nbCaseY*LargeCellule+margeY; j+=LargeCellule)
		{
			g.drawLine(margeX, j,margeX + nbCaseX*LargeCellule, j);
		}
	}
	//--------------methode dessignee cellule-----------------
	void dessignerCellule(Graphics g,Cellule cel,char shape)
	{
		g.setColor(cel.Couleur);
		if(shape == 'O')
			g.fillOval(margeX+LargeCellule*cel.x,margeY+LargeCellule*cel.y, LargeCellule, LargeCellule);
		else
			g.fillRect(margeX+LargeCellule*cel.x,margeY+LargeCellule*cel.y, LargeCellule, LargeCellule);
	}
	//----------------methode dessigner snake--------------------
	void dessigner_snake(Graphics g)
	{
		for(Cellule cel : Corps) {
			if(cel == Corps.get(0))
				dessignerCellule(g, cel,'O');
			else
				dessignerCellule(g, cel,'r');
		}
	}
	//-----------------test to pass to the next level----------------
	void PassToNextLevel(Graphics g)
	{
		if(Score == 4) {Stage++;Score=0;Initialisation();}
		if(Stage == 1) {Stage_1(g);}
		if(Stage == 2){Stage_2(g);}
		if(Stage == 3){
			TestObstacle = true;
			g.setFont(new Font("serif",Font.PLAIN,40));
			g.setColor(Color.cyan);
			g.fillRect(margeX+4*LargeCellule, 0,LargeCellule*(nbCaseX-8),margeY);
			g.setColor(Color.blue);
			g.drawString("Congratulation you have won the game", margeX+4*LargeCellule,50);
		}
	}
	//----------------methode dessigner snake--------------------
		void dessigner_Obstacle(Graphics g)
		{
			for(Cellule cel : Obstacle)
				dessignerCellule(g, cel,'r');
		}
	//--------------------------stage 1 ---------------------------
	void Stage_1(Graphics g)
	{
		g.setColor(Color.blue);
		g.fillRect(margeX-LargeCellule, margeY-LargeCellule,LargeCellule*(nbCaseX+2) , LargeCellule);
		g.fillRect(margeX-LargeCellule, margeY-LargeCellule,LargeCellule , LargeCellule*(nbCaseY+2));
		g.fillRect(margeX-LargeCellule, margeY+nbCaseY*LargeCellule,LargeCellule*(nbCaseX+2),LargeCellule);
		g.fillRect(margeX+LargeCellule*nbCaseX, margeY-LargeCellule ,LargeCellule , LargeCellule*(nbCaseY+2));
		if(TestObstacle_stage_1)
		{
			TestObstacle = true;
			GameIsOver(g);
			time.stop();
		}
	}
	//----------------methodeStage0------------------------
		void InitialisationStage_2()
		{
			Obstacle = new ArrayList<Cellule>();
			for(int i=3 ; i<9 ; i++)
			{
				Obstacle.add(new Cellule(3,i,CouleurObstacle));
				Obstacle.add(new Cellule(i,3,CouleurObstacle));
				Obstacle.add(new Cellule(nbCaseX-4,i,CouleurObstacle));
				Obstacle.add(new Cellule(nbCaseX-i-1,3,CouleurObstacle));
				Obstacle.add(new Cellule(i,nbCaseY-4,CouleurObstacle));
				Obstacle.add(new Cellule(3,nbCaseY-i-1,CouleurObstacle));
				Obstacle.add(new Cellule(nbCaseX-4,nbCaseY-i-1,CouleurObstacle));
				Obstacle.add(new Cellule(nbCaseX-i-1,nbCaseY-4,CouleurObstacle));
			}
				
		}
	//----------------------------stage 2-------------------------------
	void Stage_2(Graphics g)
	{
		dessigner_Obstacle(g);
		for(Cellule X : Obstacle)
		{
			if(Corps.get(0).x == X.x && Corps.get(0).y == X.y)
			{
				GameIsOver(g);
				TestObstacle = true;
			}	
		}
	}
	//-------------------------game is over------------------------------
	void GameIsOver(Graphics g)
	{
		g.setFont(new Font("serif",Font.PLAIN,100));
		g.setColor(Color.red);
		g.drawString("Game over !!!", 10*LargeCellule,17*LargeCellule);
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
			path = "D:\\eclipse\\MyProjects\\src\\Snake\\comic_applause.wav";
//			int i = rand.nextInt(4);
//			switch(i) {
//			case 0:
//				path = "D:\\eclipse\\MyProjects\\src\\Snake\\yes_yes.wav";
//				break;
//			case 1:
//				path = "D:\\eclipse\\MyProjects\\src\\Snake\\woo_woo.wav";
//				break;
//			case 2:
//				path = "D:\\eclipse\\MyProjects\\src\\Snake\\worm_applause.wav";
//				break;
//			case 3:
//				path = "D:\\eclipse\\MyProjects\\src\\Snake\\comic_applause.wav";
//				break;
//			default:break;
//			}
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
		new Snake();
	}
	
}


//---------------------------------------class cellule-------------------------------------------
class Cellule
{
	int x,y;
	Color Couleur;
	public Cellule(int x, int y, Color couleur) {
		super();
		this.x = x;
		this.y = y;
		Couleur = couleur;
	}
	
}