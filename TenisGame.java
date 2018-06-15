/**
 * TenisGame , JavaProgramm

 */

/**
 * @author MAXIM
 *
 */
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
//import javax.swing.*;
//import java.io.*;
import java.lang.*;
import java.util.*;
//import java.awt.event.KeyAdapter;
//import java.math.*;
//import java.applet.*;
import java.net.*;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
//import  sun.audio.*;    
//import the sun.audio package
import  java.io.*;
import java.io.File;
//import javax.swing.SwingUtilities;
public class TenisGame extends JFrame implements KeyListener, /*MouseMotionListener, ActionListener,*/ Runnable
{
	JFrame f= this;

	/**
	 * @param args
	 */
	//1. ���������� 
	int k=0;
	//��������� ����
	int gameLevel;
	//���� �����
	int[] gamePoints = new int[2];
	//���� ������
	int[] setPoints = new int[2];
	//���������� ������
	int playerX=1000;
	//���������� ������� ������
	int playerY=450;
	//�������� ������������ ������
	int speedPlayer=7;
	// �������������� ���������� ������
	int robotX=200;
	// ������������ ���������� ������
	int robotY=450;
	//�������� ������������ ������
	int speedRobot=7;
	// ������� ������� ������
	int widthRacket=10;
	int heightRacket=100;
	//���������� ������� ����������
	int[] computerX = new int[10];
	int[][] computerY = new int[10][10];
	//������� ����
	int windowSizeX=1200;
	int windowSizeY=800;
	//������� ������� �����
	int kortVerticalY1=200;
	int kortVerticalY2=700;
	//������� ������� �����
	int kortHorizontalX1=100;
	int kortHorizontalX2=1100;
	//������ ����
	int ballSize=20;
	//���������� ����
	int ballX=(int)(kortHorizontalX1+(kortHorizontalX2-kortHorizontalX1)/2);
	int ballY=(int)(kortVerticalY1+(kortVerticalY2-kortVerticalY1)/2);
	//�������� ����
	int speedX=10;
	int speedY=10;
	//��������� ���������� �������� ����
	int tempSpeedX=0;
	int tempSpeedY=0;
	//����� ���� � ��������
	int time; 
	//���������� ����������� �����
	int countX=100;
	int countY=100;
    //�������� � ���� ��������
	int delay=60;
	//����� ����
	boolean endOfGame=false;
	boolean gamePause=false;
	//��������� ���������� ������
	Graphics offscreen;
	Image offscreenImg;
	//��������� ������ �����
	public AudioClip BoomAudio;
	// File ff = null;
    //���� ����
	//Graphics2D g2d,g2d0;
	private static final long serialVersionUID = 1L;
    public TenisGame() 
    {
    	//super();
    	//f.addMouseMotionListener( this );
        //f.addKeyListener(this);
    	f.setLayout( new FlowLayout() );
    	f.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	f.setSize(windowSizeX , windowSizeY);
    	//f.addMouseMotionListener( this );
        f.addKeyListener(this);
        f.setFocusable(true);    
        f.setVisible( true ); 
        
    //theMessage = message;
    //theButton = new JButton("Change Color");
    
    //add( theButton );
    //k = 0;
    //theButton.addActionListener( this );
         init();
   
    Thread t = new Thread( this );
    t.start();
    }
 
    public void init() {
    	offscreenImg = createImage(windowSizeX, windowSizeY);
    	offscreen=offscreenImg.getGraphics();
    	//ff = new File("test.txt");
    	URL myUrl = ClassLoader.getSystemResource("boom.wav");
        BoomAudio=Applet.newAudioClip(myUrl);
    	BoomAudio.play();
    }
  //������������  ����
    public void iniGame() {
    	 ballX=(int)(kortHorizontalX1+(kortHorizontalX2-kortHorizontalX1)/2);
    	 ballY=(int)(kortVerticalY1+(kortVerticalY2-kortVerticalY1)/2);
    	 speedX=0;
         speedY=0;
         setPoints[0]=0;
       	 setPoints[1]=0;
    }
  //����� ������ ����
    public void iniSet() {
   	 ballX=(int)(kortHorizontalX1+(kortHorizontalX2-kortHorizontalX1)/2);
   	 ballY=(int)(kortVerticalY1+(kortVerticalY2-kortVerticalY1)/2);
   	 speedX=0;
   	 speedY=0;
   	 
   }
  //����� ����
    public void startGame() {
    	 gamePoints[0]=0;
      	 gamePoints[1]=0;
    	 iniGame();
    	 //System.in.read();
         //speedX=8+(int)(4*(Math.random()));
         //speedY=8+(int)(4*(Math.random()));
         endOfGame=false;
    }
  //����� ��� 
    public void newSet() {
   	 iniSet();
        speedX=0;
        speedY=0;
   }
  //����� ����
    public void pauseGame() {
        gamePause=true;
    }
  //���������� ����
    public void runGame() {
        gamePause=false; 
    }
  //����� ����
    /*public void pauseGame() {
         tempSpeedX=speedX;    	
    	 speedX=0;
    	 tempSpeedY=speedY;
         speedY=0;
    }*/
       //��������� ������� ������  
        @Override
        public void keyPressed(KeyEvent evt) {
            switch (evt.getKeyChar()) {
        //��� ������� �� ������� b ������� ������� ����
                case 'b':
                	if (Math.abs(speedX)<2) {
                		speedX=(8+(int)(8*(Math.random())));
                        speedY=(int)(30*(0.5-Math.random()));
                	}
                	if (playerY<kortVerticalY2) {
                    playerY += speedPlayer;
                	}
                    //repaint();
                    break;
       //��� ������� �� ������� m ������� ������� �����      
                case 'm':
                	if (Math.abs(speedX)<2) {
                		speedX=(8+(int)(8*(Math.random())));
                        speedY=(int)(30*(0.5-Math.random()));
                	}
                	if (playerY>kortVerticalY1) {
                     playerY -= speedPlayer;
                	}
                	break;
                case 's':
                	startGame();
                	break;
                case 'r': 
                	runGame();
                	break;
                case 'p': 
                	pauseGame();
                                   
                    //repaint();
                    break;
           }
        }
        public void keyTyped(KeyEvent ev){
        }
        public void keyReleased(KeyEvent ev){
        }

    
    //�������� �������� �� X?
    public boolean movePossibleX() {
    	boolean result;
    	if ((ballX+speedX)>kortHorizontalX1 && (ballX+speedX)<kortHorizontalX2) {
    		result=true;
    	}
    	
    	else result=false;
    	/*if ((((ballX+speedX)>(playerX-(int)(ballSize/2))) && (ballX+speedX)<playerX) &&
    			//���� ��������
    			((ballY>(playerY-(int)(heightRacket/2)) && (ballY<(playerY+(int)(heightRacket/2))) {
    		result=true;
    	}
    	else result=false;*/
    	return result;
    }
    
    //�������� �������� �� Y?
    public boolean movePossibleY() {
    	boolean result;
    	if ((ballY+speedY)>kortVerticalY1 && (ballY+speedY)<kortVerticalY2) {
    		result=true;
    	}
    	else result=false;
    	return result;
    }
    
    //�������� ��� �� X
    public void moveX() {
       	ballX+=speedX;
    }
    
    //�������� ��� �� Y
    public void moveY() {
       	ballY+=speedY;
    }
    
    //�������� �������� �� X
    public void changeSpeedX() {
       	speedX*=-1;
       	BoomAudio.play();
       	//-------------------------------------------
       	speedX=(speedX+(int)(4*(0.5-Math.random())));
        speedY=(speedY+(int)(12*(0.5-Math.random())));
    }
    
    //�������� �������� �� Y
    public void changeSpeedY() {
       	speedY*=-1;
    }
    
    //����� ��� ����� ������ ���?
    public boolean ballPushed() {
    	boolean result;
    	int tempX, tempY;
    	tempX=ballX+speedX;
    	tempY=ballY+speedY;
    	//��� �������� � ������� ������?
    	if ((tempX>=playerX)&&(ballX<playerX)) {
    	    if ((tempY>=(playerY-heightRacket/2))&&(tempY<(playerY+heightRacket/2))) {
    	    	result=true;
    	    }
    	    else result=false;
    	  }
    	else {
    	//��� �������� � ������� ������?
    	if ((tempX<=robotX)&&(ballX>robotX)) {
    	    if ((tempY>=(robotY-heightRacket/2))&&(tempY<(robotY+heightRacket/2))) {
    	    	result=true;
    	    }
    	    else result=false;
    	  }
    	else result=false;
    	}
    	return result;
    }
    
    //�������� ������
    public void moveRobot() {
    	if (ballY>=robotY) {
    		robotY+=speedRobot;
    	}
    	else robotY-=speedRobot;
    }
    //���������� �����
    public void givePoints() {
    	if (ballX>=(kortHorizontalX2-15)) {
    		setPoints[0]+=1;
    		if (setPoints[0]>14) {
    			gamePoints[0]+=1;
    			setPoints[0]=0;
    			setPoints[1]=0;
    			if (gamePoints[0]>2) {
    				endOfGame=true;
    			}
    		}
    		newSet();
    	}
    	if (ballX<=(kortHorizontalX1+15)) {
    		setPoints[1]+=1;
    		if (setPoints[1]>14) {
    			gamePoints[1]+=1;
    			setPoints[0]=0;
    			setPoints[1]=0;
    			if (gamePoints[1]>2) {
    				endOfGame=true;
    			}
    		}
    		newSet();
    	}
    }
    //������ ����
    public void playGame(){
    	if ((!endOfGame)&&(!gamePause)) {
    	//���� ����� ������ ���, �� �������� �������� ���� �� X
    	if (ballPushed()) {
    		changeSpeedX();
    	}
    	//����� ���������� �������� ����
    	else {
    	  if (movePossibleX()) {
    		moveX();
    	  }
    	  /*else {
    		changeSpeedX();
    	  }*/
    	
    	  if (movePossibleY()) {
    		moveY();
    	  }
    	  else {
    		changeSpeedY();
    	  }
    	}
    	//������� ������
    	moveRobot();
    	//���������� ���������� �����
    	givePoints();
    	//repaint();
    	}
    }
    @Override
    public void paint(Graphics screen) {
    //	�������� ������ ������ ��������
    	
    	//Graphics g;
    	offscreen.clearRect(0,0,windowSizeX,windowSizeY);
	//  �������� ���������� ������
        Color backColor = new Color(255, 255, 255);
        Color ballColor = new Color(255, 255, 0);
        Color playerColor = new Color(0, 0, 0);
        Color fontColor = new Color(0, 0, 255);
        Color kortColor = new Color(100, 255, 100);
        //Color ballColor = new Color(255, 0, 0);
    	offscreen.setColor(backColor);
        offscreen.fillRect(0, 0, windowSizeX, windowSizeY);
        offscreen.setColor(ballColor);
	//	---------------------------------------------------------------
    	Font font = new Font ("Times New Roman", 1, 32); //Initializes the font
    	offscreen.setColor(fontColor); //Sets the color of the font
    	offscreen.setFont (font); //Sets the font
    //  ���������� ���� ����    
    	offscreen.drawString ("���� ���� "+gamePoints[0]+":"+gamePoints[1], countX, countY);
    	offscreen.drawString ("���� ���� "+setPoints[0]+":"+setPoints[1], countX, countY+50);
    //    	---------------------------------------------------------------
        font = new Font ("Times New Roman", 1, 18); //Initializes the font
        offscreen.setColor(fontColor); //Sets the color of the font
        offscreen.setFont (font); //Sets the font
    //  ���������� ������� ����    
        offscreen.drawString ("s - ������ ����� ����", countX+300, countY-20);
        offscreen.drawString ("p - �����", countX+300, countY);
        offscreen.drawString ("r - ���������� ", countX+300, countY+20);
        offscreen.drawString ("b - ������� ����", countX+300, countY+40);
        offscreen.drawString ("m - ������� �����", countX+300, countY+60);
    //  ������ ���� ����
        offscreen.setColor(kortColor);
        offscreen.fillRect(kortHorizontalX1, kortVerticalY1, kortHorizontalX2-kortHorizontalX1, kortVerticalY2-kortVerticalY1);
        offscreen.setColor(playerColor);
        offscreen.drawRect(kortHorizontalX1, kortVerticalY1, kortHorizontalX2-kortHorizontalX1, kortVerticalY2-kortVerticalY1);
        offscreen.drawLine(kortHorizontalX1+(int)((kortHorizontalX2-kortHorizontalX1)/2), kortVerticalY1, kortHorizontalX1+(int)((kortHorizontalX2-kortHorizontalX1)/2), kortVerticalY2);  	
    //  ���� �� ����� ���� ������ �����
        if (!endOfGame) {    
    //  ������ ���
        offscreen.setColor(ballColor);
        offscreen.fillOval(ballX-(int)(ballSize/2), ballY-(int)(ballSize/2), ballSize, ballSize);
        offscreen.setColor(playerColor);
        offscreen.drawOval(ballX-(int)(ballSize/2), ballY-(int)(ballSize/2), ballSize, ballSize);
    //	������ ������� ������
        offscreen.setColor(playerColor);
        offscreen.fillRect(playerX, playerY-(int)(heightRacket/2), widthRacket, heightRacket);
    //  ������ ������� ������
        offscreen.fillRect(robotX, robotY-(int)(heightRacket/2), widthRacket, heightRacket);
        }
        else {
        	Font fontE = new Font ("Times New Roman", 1, 48); //Initializes the font
        	//offscreen.setColor(fontColor); //Sets the color of the font
        	offscreen.setFont (fontE); //Sets the font
        //  ���������� ���� ����    
        	offscreen.drawString ("���� ���� "+gamePoints[0]+":"+gamePoints[1], countX+100, countY+400);
        	offscreen.drawString ("����� ���� ", countX+100, countY+500);	
        }
        Font fontC = new Font ("Times New Roman", 1, 14); //Initializes the font
    	offscreen.setColor(fontColor); //Sets the color of the font
    	offscreen.setFont (fontC); //Sets the font
    //  ���������� copyright    
    	offscreen.drawString ("Copyright (C) 2017 Maxim Mironov", 100, windowSizeY-50);

    	screen.drawImage(offscreenImg,0,0,this);
    }
    
    public void update(Graphics screen){
    	 paint(screen);
    	} 
    
    /*
    //��������� ����
    public void mouseDragged(MouseEvent e) {
   // messageX = e.getX();
   // messageY = e.getY();
    repaint();
    }
    public void mouseMoved(MouseEvent e) { }
    public void actionPerformed( ActionEvent e) {
    repaint();
    }*/
    public void run() {
    try {
    	
    	
    	
    while(true) {
    playGame(); // ������	
    repaint(); // �������� ���������.
    Thread.sleep(delay);
    }
    } catch (InterruptedException ie) { }
    }
    public static void main(String[] args)
    {
    new TenisGame();
    	}
}