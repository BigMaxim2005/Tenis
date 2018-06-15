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
	//1. переменные 
	int k=0;
	//сложность игры
	int gameLevel;
	//счёт сетов
	int[] gamePoints = new int[2];
	//счёт баллов
	int[] setPoints = new int[2];
	//координаты игрока
	int playerX=1000;
	//координаты ракетки игрока
	int playerY=450;
	//скорость передвижения игрока
	int speedPlayer=7;
	// горизонтальные координаты робота
	int robotX=200;
	// вертикальные координаты робота
	int robotY=450;
	//скорость передвижения робота
	int speedRobot=7;
	// размеры ракетки игрока
	int widthRacket=10;
	int heightRacket=100;
	//координаты ракеток компьютера
	int[] computerX = new int[10];
	int[][] computerY = new int[10][10];
	//размеры окна
	int windowSizeX=1200;
	int windowSizeY=800;
	//верхняя граница корта
	int kortVerticalY1=200;
	int kortVerticalY2=700;
	//боковая граница корта
	int kortHorizontalX1=100;
	int kortHorizontalX2=1100;
	//размер мяча
	int ballSize=20;
	//координаты мяча
	int ballX=(int)(kortHorizontalX1+(kortHorizontalX2-kortHorizontalX1)/2);
	int ballY=(int)(kortVerticalY1+(kortVerticalY2-kortVerticalY1)/2);
	//скорость мяча
	int speedX=10;
	int speedY=10;
	//временные переменные скорости мяча
	int tempSpeedX=0;
	int tempSpeedY=0;
	//время игры в секундах
	int time; 
	//координаты отображения счёта
	int countX=100;
	int countY=100;
    //задержка в мили секундах
	int delay=60;
	//конец игры
	boolean endOfGame=false;
	boolean gamePause=false;
	//временное содержимое экрана
	Graphics offscreen;
	Image offscreenImg;
	//аудиоклип звуков удара
	public AudioClip BoomAudio;
	// File ff = null;
    //поле игры
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
  //инициировать  игру
    public void iniGame() {
    	 ballX=(int)(kortHorizontalX1+(kortHorizontalX2-kortHorizontalX1)/2);
    	 ballY=(int)(kortVerticalY1+(kortVerticalY2-kortVerticalY1)/2);
    	 speedX=0;
         speedY=0;
         setPoints[0]=0;
       	 setPoints[1]=0;
    }
  //старт нового сета
    public void iniSet() {
   	 ballX=(int)(kortHorizontalX1+(kortHorizontalX2-kortHorizontalX1)/2);
   	 ballY=(int)(kortVerticalY1+(kortVerticalY2-kortVerticalY1)/2);
   	 speedX=0;
   	 speedY=0;
   	 
   }
  //старт игры
    public void startGame() {
    	 gamePoints[0]=0;
      	 gamePoints[1]=0;
    	 iniGame();
    	 //System.in.read();
         //speedX=8+(int)(4*(Math.random()));
         //speedY=8+(int)(4*(Math.random()));
         endOfGame=false;
    }
  //новый сет 
    public void newSet() {
   	 iniSet();
        speedX=0;
        speedY=0;
   }
  //пауза игры
    public void pauseGame() {
        gamePause=true;
    }
  //продолжить игры
    public void runGame() {
        gamePause=false; 
    }
  //пауза игры
    /*public void pauseGame() {
         tempSpeedX=speedX;    	
    	 speedX=0;
    	 tempSpeedY=speedY;
         speedY=0;
    }*/
       //обработка нажатий клавиш  
        @Override
        public void keyPressed(KeyEvent evt) {
            switch (evt.getKeyChar()) {
        //при нажатии на клавишу b двигать ракетку вниз
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
       //при нажатии на клавишу m двигать ракетку вверх      
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

    
    //возможно движение по X?
    public boolean movePossibleX() {
    	boolean result;
    	if ((ballX+speedX)>kortHorizontalX1 && (ballX+speedX)<kortHorizontalX2) {
    		result=true;
    	}
    	
    	else result=false;
    	/*if ((((ballX+speedX)>(playerX-(int)(ballSize/2))) && (ballX+speedX)<playerX) &&
    			//надо доделать
    			((ballY>(playerY-(int)(heightRacket/2)) && (ballY<(playerY+(int)(heightRacket/2))) {
    		result=true;
    	}
    	else result=false;*/
    	return result;
    }
    
    //возможно движение по Y?
    public boolean movePossibleY() {
    	boolean result;
    	if ((ballY+speedY)>kortVerticalY1 && (ballY+speedY)<kortVerticalY2) {
    		result=true;
    	}
    	else result=false;
    	return result;
    }
    
    //сдвинуть мяч по X
    public void moveX() {
       	ballX+=speedX;
    }
    
    //сдвинуть мяч по Y
    public void moveY() {
       	ballY+=speedY;
    }
    
    //поменять скорость по X
    public void changeSpeedX() {
       	speedX*=-1;
       	BoomAudio.play();
       	//-------------------------------------------
       	speedX=(speedX+(int)(4*(0.5-Math.random())));
        speedY=(speedY+(int)(12*(0.5-Math.random())));
    }
    
    //поменять скорость по Y
    public void changeSpeedY() {
       	speedY*=-1;
    }
    
    //игрок или робот ударил мяч?
    public boolean ballPushed() {
    	boolean result;
    	int tempX, tempY;
    	tempX=ballX+speedX;
    	tempY=ballY+speedY;
    	//мяч ударился о ракетку игрока?
    	if ((tempX>=playerX)&&(ballX<playerX)) {
    	    if ((tempY>=(playerY-heightRacket/2))&&(tempY<(playerY+heightRacket/2))) {
    	    	result=true;
    	    }
    	    else result=false;
    	  }
    	else {
    	//мяч ударился о ракетку робота?
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
    
    //движение робота
    public void moveRobot() {
    	if (ballY>=robotY) {
    		robotY+=speedRobot;
    	}
    	else robotY-=speedRobot;
    }
    //присвоение очков
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
    //движок игры
    public void playGame(){
    	if ((!endOfGame)&&(!gamePause)) {
    	//если игрок ударил мяч, то изменить скорость мяча по X
    	if (ballPushed()) {
    		changeSpeedX();
    	}
    	//иначе отработать движение мяча
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
    	//двигать робота
    	moveRobot();
    	//произвести начисление очков
    	givePoints();
    	//repaint();
    	}
    }
    @Override
    public void paint(Graphics screen) {
    //	создания буфера против мерцания
    	
    	//Graphics g;
    	offscreen.clearRect(0,0,windowSizeX,windowSizeY);
	//  обнуляем содержимое экрана
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
    //  отображаем счёт игры    
    	offscreen.drawString ("счёт игры "+gamePoints[0]+":"+gamePoints[1], countX, countY);
    	offscreen.drawString ("счёт сета "+setPoints[0]+":"+setPoints[1], countX, countY+50);
    //    	---------------------------------------------------------------
        font = new Font ("Times New Roman", 1, 18); //Initializes the font
        offscreen.setColor(fontColor); //Sets the color of the font
        offscreen.setFont (font); //Sets the font
    //  отображаем правила игры    
        offscreen.drawString ("s - начать новую игру", countX+300, countY-20);
        offscreen.drawString ("p - пауза", countX+300, countY);
        offscreen.drawString ("r - продолжить ", countX+300, countY+20);
        offscreen.drawString ("b - ракетку вниз", countX+300, countY+40);
        offscreen.drawString ("m - ракетку вверх", countX+300, countY+60);
    //  рисуем поле игры
        offscreen.setColor(kortColor);
        offscreen.fillRect(kortHorizontalX1, kortVerticalY1, kortHorizontalX2-kortHorizontalX1, kortVerticalY2-kortVerticalY1);
        offscreen.setColor(playerColor);
        offscreen.drawRect(kortHorizontalX1, kortVerticalY1, kortHorizontalX2-kortHorizontalX1, kortVerticalY2-kortVerticalY1);
        offscreen.drawLine(kortHorizontalX1+(int)((kortHorizontalX2-kortHorizontalX1)/2), kortVerticalY1, kortHorizontalX1+(int)((kortHorizontalX2-kortHorizontalX1)/2), kortVerticalY2);  	
    //  если не конец игры рисуем мячик
        if (!endOfGame) {    
    //  рисуем мяч
        offscreen.setColor(ballColor);
        offscreen.fillOval(ballX-(int)(ballSize/2), ballY-(int)(ballSize/2), ballSize, ballSize);
        offscreen.setColor(playerColor);
        offscreen.drawOval(ballX-(int)(ballSize/2), ballY-(int)(ballSize/2), ballSize, ballSize);
    //	рисуем ракетку игрока
        offscreen.setColor(playerColor);
        offscreen.fillRect(playerX, playerY-(int)(heightRacket/2), widthRacket, heightRacket);
    //  рисуем ракетку робота
        offscreen.fillRect(robotX, robotY-(int)(heightRacket/2), widthRacket, heightRacket);
        }
        else {
        	Font fontE = new Font ("Times New Roman", 1, 48); //Initializes the font
        	//offscreen.setColor(fontColor); //Sets the color of the font
        	offscreen.setFont (fontE); //Sets the font
        //  отображаем счёт игры    
        	offscreen.drawString ("счёт игры "+gamePoints[0]+":"+gamePoints[1], countX+100, countY+400);
        	offscreen.drawString ("Конец игры ", countX+100, countY+500);	
        }
        Font fontC = new Font ("Times New Roman", 1, 14); //Initializes the font
    	offscreen.setColor(fontColor); //Sets the color of the font
    	offscreen.setFont (fontC); //Sets the font
    //  отображаем copyright    
    	offscreen.drawString ("Copyright (C) 2017 Maxim Mironov", 100, windowSizeY-50);

    	screen.drawImage(offscreenImg,0,0,this);
    }
    
    public void update(Graphics screen){
    	 paint(screen);
    	} 
    
    /*
    //обработка мыши
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
    playGame(); // играть	
    repaint(); // показать изменения.
    Thread.sleep(delay);
    }
    } catch (InterruptedException ie) { }
    }
    public static void main(String[] args)
    {
    new TenisGame();
    	}
}