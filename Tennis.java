/**
 * Tennis , Java Application

 */

/**
 * @author MAXIM
 *
 */
//����������� ����������� ��������� 
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
//���������� ��������� ������ � ����������� ���������� 
public class Tennis extends JFrame implements KeyListener, /*MouseMotionListener, ActionListener,*/ Runnable
{
//����������� ���������� 
	int k=0;
//��������� ����
	int gameLevel;
//���� �����
	int[] gamePoints = new int[2];
//���� ������
	int[] setPoints = new int[2];
//���������� ������� ������ 
	int playerX=1000;
//���������� ������� ������
	int playerY=450;
//�������� ������������ ������
	int speedPlayerY=4;
	int speedPlayerX=1;
// �������������� ���������� ������
	int robotX=200;
// ������������ ���������� ������
	int robotY=450;
//�������� ������������ ������
	int speedRobot=4;
// ������� ������� ������
	int widthRacket=10;
	int heightRacket=100;
//���������� ������� ����������
	int[] computerX = new int[10];
	int[][] computerY = new int[10][10];
//������� ����
	int windowSizeX=1200;
	int windowSizeY=800;
//������������ ������� �����
	int kortVerticalTop=200;
	int kortVerticalBottom=700;
//������� ������� �����
	int kortHorizontalLeft=100;
	int kortHorizontalRight=1100;
//������ ����
	int ballSize=20;
//���������� ����
	int ballX=(int)(kortHorizontalLeft+(kortHorizontalRight-kortHorizontalLeft)/2);
	int ballY=(int)(kortVerticalTop+(kortVerticalBottom-kortVerticalTop)/2);
//�������� ����
	int speedX=5;
	int speedY=5;
//��������� ���������� �������� ����
	int tempSpeedX=0;
	int tempSpeedY=0;
//����� ���� � ��������
	int time; 
//���������� ����������� �����
	int countX=100;
	int countY=100;
//�������� � ���� ��������
	int delay=30;
//����� ����
	boolean endOfGame=false;
	boolean gamePause=false;
//��������� ���������� ������
	Graphics offscreen;
	Image offscreenImg;
//��������� ������ �����
	public AudioClip BoomAudio;
	public AudioClip appAudio;
	public AudioClip oxxxAudio;

	private static final long serialVersionUID = 1L;
//���������� ��������� ������    
	public Tennis() 
    {
//����������� ���������� ����	
		JFrame f = this;
//��������� ������� ��������� 
		f.setLayout( new FlowLayout() );
//��������� ������ ��� �������� ����		
    	f.setDefaultCloseOperation(EXIT_ON_CLOSE);
//������ ��������� �������     
    	init();    	
//��������� �������� ����    	
    	f.setSize(windowSizeX , windowSizeY);   	
//���������� ����������� ����������    	
    	f.addKeyListener(this);
//��������� ������        
    	f.setFocusable(true);    
//������� ���� �������        
    	f.setVisible( true );   	
//���������� ������� �������        
    	Thread t = new Thread( this );
//������ �������    
    	t.start();
    }
 
//���������� ��������� �������	
    public void init() {
//�������� ������ ������    	
    	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        windowSizeX = gd.getDisplayMode().getWidth();
    	windowSizeY = gd.getDisplayMode().getHeight();
//��������� ���� � ��������� ����� �����
    	URL myUrl = ClassLoader.getSystemResource("boom.wav");
//������ ��������� �����        
    	BoomAudio=Applet.newAudioClip(myUrl);
//������������ ��������� �����    	
//    	BoomAudio.play();
//��������� ���� � ��������� ����� �����
    	URL appUrl = ClassLoader.getSystemResource("4.wav");
//������ ��������� �����        
    	appAudio=Applet.newAudioClip(appUrl);
//������������ ��������� �����    	
//      appAudio.play();
//��������� ���� � ��������� ����� �����
    	URL oxxxUrl = ClassLoader.getSystemResource("oxxx.wav");
//������ ��������� �����        
    	oxxxAudio=Applet.newAudioClip(oxxxUrl);
//������������ ��������� �����    	
//    	oxxxAudio.play();
        endOfGame = true;
    }
    
//���������� ������� ������ ���� 
    public void iniGame() {
//��������� ���������� X ���� � ����� ����  
    	 ballX=(int)(kortHorizontalLeft+(kortHorizontalRight-kortHorizontalLeft)/2);
//��������� ���������� Y ���� � ����� ����    	 
    	 ballY=(int)(kortVerticalTop+(kortVerticalBottom-kortVerticalTop)/2);
//��������� �������������� �������� ���� � ����    	 
    	 speedX=0;
//��������� ������������ �������� ���� � ����         
    	 speedY=0;
//��������� ����� �� ���� � ������         
    	 setPoints[0]=0;
//��������� ����� �� ���� � ������       	 
    	 setPoints[1]=0;
    }
    
//���������� ������� ������ ������ ����
     public void iniSet() {
//��������� ���������� X ���� � ���� ����   	 
     ballX=(int)(kortHorizontalLeft+(kortHorizontalRight-kortHorizontalLeft)/2);
//��������� ���������� Y ���� � ���� ����   	 
     ballY=(int)(kortVerticalTop+(kortVerticalBottom-kortVerticalTop)/2);
//��������� �������������� �������� ���� � ����     	 
     speedX=((int)(8*(0.5-Math.random())));
//��������� ������������ �������� ���� � ����   	 
     speedY=0;
//���� �������� ���� �������, �� ������ ��������              	
 	 while (Math.abs(speedX)<2) {
 		speedX=((int)(8*(0.5-Math.random())));
        speedY=(int)(15*(0.5-Math.random()));
 	}
   }
     
//���������� ������� ������ ����
    public void startGame() {    	
//��������� ����� � ������    	 
    	 gamePoints[0]=0;
//��������� ����� � ������      	 
    	 gamePoints[1]=0;
//����� ������� ������ ����    	 
    	 iniGame();
//����������� ����� ��������� ���� � ����    	
    	 endOfGame=false;
    }
    
//���������� ������� ������ ������ ���� 
    public void newSet() {
//����� ������� ������ ����   	 
    	iniSet();
//��������� �������������� �������� ���� � ����        
    	speedX=0;
//��������� ������������ �������� ���� � ����        
    	speedY=0;
   }
  //����� � ����
    public void pauseGame() {
        gamePause=true;
    }
  //���������� ����
    public void runGame() {
        gamePause=false; 
    }
  //��������� ������� ������  
        @Override
        public void keyPressed(KeyEvent evt) {
  //��������� ������� ������� �� � ����	
            switch (evt.getKeyCode()) {
  //��� ������� �� ������� "����" ������� ������� ����
                case KeyEvent.VK_DOWN:
  //���� �������� ���� �������, �� ������ ��������              	
                	while (Math.abs(speedX)<2) {
                		speedX=((int)(16*(0.5-Math.random())));
                        speedY=(int)(15*(0.5-Math.random()));
                	}
  //���� ������� ������ ���� ������ �������, �� ������� ������� ������ ����              	
                	if (playerY<kortVerticalBottom) {
                	    playerY += speedPlayerY;
                	}
                    break;
  //��� ������� �� ������� "�����"  ������� ������� �����      
                case KeyEvent.VK_UP:
  //���� �������� ���� �������, �� ������ �������� ����              	
                	while (Math.abs(speedX)<2) {
                		speedX=((int)(16*(0.5-Math.random())));
                        speedY=(int)(15*(0.5-Math.random()));
                	}
  //���� ������� ������ ���� ������� �������, �� ������� ������� �����              	
                	if (playerY>kortVerticalTop) {
                     playerY -= speedPlayerY;
                	}
                	break;
  //��� ������� �� ������� "�����"            	
                case KeyEvent.VK_LEFT:
  //���� ������� ������ �� �������� �������� ����, �� ������� � �����              	
                	if (playerX>(kortHorizontalLeft+(int)((kortHorizontalRight-kortHorizontalLeft)/2))) {
                    playerX -= speedPlayerX;
                	}
                    break;
  //��� ������� �� ������� "������"                  
                case KeyEvent.VK_RIGHT:
  //���� ������� ������ �� �������� ������ �������, �� ������� � ������              	
                	if (playerX<kortHorizontalRight) {
                    playerX += speedPlayerX;
                	}
                    break;
            }
  //������� �� ������� ������� �� �����          
            switch (evt.getKeyChar()) {
  //��� ������� �� ������� s ������ ����� ����                 
                case 's':
                	startGame();
                	break;
  //��� ������� �� ������� r ���������� ����              	
                case 'r': 
                	runGame();
                	break;
  //��� ������� �� ������� p-����� � ����              	
                case 'p': 
                	pauseGame();
                    break;
           }
        }
 //����������������� �������, � ��������� �� ������������       
        public void keyTyped(KeyEvent ev){
        }
 //����������������� �������, � ��������� �� ������������       
        public void keyReleased(KeyEvent ev){
        }
    
 //��������� ��� �� �����? ���� ��, ������� ������
    public boolean movePossibleX() {
    	boolean result;
 //���� ����� �������� �������� ���������� ����, �� �� �����, �� ������� ������
    	if ((ballX+speedX)>kortHorizontalLeft && (ballX+speedX)<kortHorizontalRight) {
    		result=true;
    	}
 //����� ������� �����   	
    	else result=false;
    	return result;
    }
    
 //�������� �� ������������ ��������?
    public boolean movePossibleY() {
    	boolean result;
 //���� ��� �� ������ ������� ������� �����, �� ������� ������  	
    	if ((ballY+speedY)>kortVerticalTop && (ballY+speedY)<kortVerticalBottom) {
    		result=true;
    	}
 //����� ������� ����   	
    	else result=false;
    	return result;
    }
    
 //�������� ��� �� �����������
    public void moveX() {
       	ballX+=speedX;
    }
    
 //�������� ��� �� ���������
    public void moveY() {
       	ballY+=speedY;
    }
    
 //�������� ����������� ��������������� �������� ���� 
    public void changeSpeedX() {
 //�������� ���� � �������������� ��������   	
       	speedX*=-1;
 //��������� ���� �����      	
       	BoomAudio.play();
 //������������� ����� �������� ����
       	speedX=(speedX+(int)(2*(0.5-Math.random())));
        speedY=(speedY+(int)(6*(0.5-Math.random())));
    }
    
 //�������� ����������� ������������� �������� ����
    public void changeSpeedY() {
 //�������� ���� � ������������ ��������    	
       	speedY*=-1;
    }
    
 //���� ����� ��� ����� ������ ���
    public boolean ballPushed() {
 //����������, �������� ���������   	
    	boolean result;
 //������� ���������� ����   	
    	int tempX, tempY;
    	tempX=ballX+speedX;
    	tempY=ballY+speedY;
 //���� ������� ���������� ���� ��������� ���������� X ������, �� 
    	if ((tempX>=playerX)&&(ballX<playerX)) {
 //���� ��� ����� � ����� ������� ������, �� ������� ������    		
    	    if ((tempY>=(playerY-heightRacket/2))&&(tempY<(playerY+heightRacket/2))) {
    	    	result=true;
    	    }
 //���� �� �����, �� ������� ����   	    
    	    else result=false;
    	  }
 //�����, ���� ������� ���������� ���� �� �������� ���������� X ������, ��   	
    	else {
 //���� ������� ���������� ���� ��������� ���������� X ������, ��
    	if ((tempX<=robotX)&&(ballX>robotX)) {
 //���� ��� ������ � ����� ������� ������, �� ������� ������   		
    	    if ((tempY>=(robotY-heightRacket/2))&&(tempY<(robotY+heightRacket/2))) {
    	    	result=true;
    	    }
 //���� �� ������, �� ������� ����   	    
    	    else result=false;
    	  }
 //����� ������� ����   	
    	else result=false;
    	}
    	return result;
    }
    
 //�������, ��������������� �������������� �������� ������
    public void moveRobot() {
 //���� ��� ���������� �� �������, ��    	
    	if (ballY>=(robotY+5)) {
 //������� ������ ����    		
    		robotY+=speedRobot;
    	}
 //���� ��� ���������� �� �������, ��   	
    	if (ballY<=(robotY-5)) {
 //������� ������ �����   		
    		robotY-=speedRobot;
    	}
    	
    }
 //���������� �����
    public void givePoints() {
 //���� ��� ������ ������� ���� �����, ��   	
    	if (ballX>=(kortHorizontalRight-15)) {
 //��������� ���� ������
    		setPoints[0]+=1;
 //��������� �� ������� ������   		
    		oxxxAudio.play();
 //���� ���������� ����� ������ ����� 15, ��   		
    		if (setPoints[0]>14) {
 //��������� ���� � ������ ������
    			gamePoints[0]+=1;
 //�������� ���� � �����   			
    			setPoints[0]=0;   			
    			setPoints[1]=0;
 //���� ���� � ������ � ������ ����� ������ ����, ��   			
    			if (gamePoints[0]>2) {
 //����� ����   				
    				endOfGame=true;
    			}
    		}
 //������ ����� ���   		
    		newSet();
    	}
 //���� ��� ������ ������ ���� �����, ��   	
    	if (ballX<=(kortHorizontalLeft+15)) {
 //��������� ���� ������   		
    		setPoints[1]+=1;
 //������������ ������   		
    		appAudio.play();
 //���� ���������� ����� ������ ����� 15, ��   		
    		if (setPoints[1]>14) {
 //�������� ���� � ������ ������   			
    			gamePoints[1]+=1;
 //�������� ���� ����   			
    			setPoints[0]=0;
    			setPoints[1]=0;
 //���� ���� �� ������ ������ ����, ��   			
    			if (gamePoints[1]>2) {
 //����� ����   				
    				endOfGame=true;
    			}
    		}
 //������ ����� ���   		
    		newSet();
    	}
    }
 //������ ����
    public void playGame(){
 //���� �� ����� ���� � �� �����, ��   	
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
 //���� �������� �� ��������� ��������, ��   	
    	  if (movePossibleY()) {
 //������� ��� �� ���������    		  
    		moveY();
    	  }
 //�����   	  
    	  else {
 //�������� ����������� ���� �� ���������   		  
    		changeSpeedY();
    	  }
    	}
 //������� ������
    	moveRobot();
 //���������� ���������� �����
    	givePoints();
    	}
    }
    @Override
    public void paint(Graphics screen) {
 //�������� ������ ������ ��������
    	offscreenImg = createImage(windowSizeX, windowSizeY);
 //�������� ������� ������������ ����������   	
    	offscreen=offscreenImg.getGraphics();
 //�������� ����������
    	offscreen.clearRect(0,0,windowSizeX,windowSizeY);
 //����������� ������������ �����
 //���� ������� ���� (�����)   	
        Color backColor = new Color(255, 255, 255);
 //���� ���� (�����)       
        Color ballColor = new Color(255, 255, 0);
 //���� ������� ������ (����-�����)       
        Color playerColor = new Color(0, 0, 150);
 //���� ������ (�����)       
        Color fontColor = new Color(0, 0, 255);
 //���� ����� (������-������)       
        Color kortColor = new Color(100, 255, 100);
 //������� ���� ������� ����
    	offscreen.setColor(backColor);
 //��������� �� �� ����   	
        offscreen.fillRect(0, 0, windowSizeX, windowSizeY);
 //	---------------------------------------------------------------
 //������� ������ � ��� ������       
    	Font font = new Font ("Verdana", 1, 32); 
 //���������� ���� ������
    	offscreen.setColor(fontColor); 
 //���������� ������������ �����
    	offscreen.setFont (font); 
 //���������� ���� ����    
    	offscreen.drawString ("���� ���� "+gamePoints[0]+":"+gamePoints[1], countX, countY);
    	offscreen.drawString ("���� ���� "+setPoints[0]+":"+setPoints[1], countX, countY+50);
 // ---------------------------------------------------------------
 //�������� ����� ��� � ������ ������   	
        font = new Font ("Courier New", 1, 18); 
 //���������� ���� ������
        offscreen.setColor(fontColor); 
 //���������� ������������ �����
        offscreen.setFont (font); 
 //���������� ������� ����    
        offscreen.drawString ("s - ������ ����� ����", countX+300, countY-20);
        offscreen.drawString ("p - �����", countX+300, countY);
        offscreen.drawString ("r - ���������� ", countX+300, countY+20);
        offscreen.drawString ("���������� �������� ������", countX+300, countY+40);
        offscreen.drawString ("� ������� ������� �� ����������", countX+300, countY+60);
 //������� ���� �����
        offscreen.setColor(kortColor);
 //��������� ��������� ������ ����       
        offscreen.fillRect(kortHorizontalLeft, kortVerticalTop, kortHorizontalRight-kortHorizontalLeft, kortVerticalBottom-kortVerticalTop);
 //���������� ���� ������� ������       
        offscreen.setColor(playerColor);
 //���������� ����� �� �����       
        offscreen.drawRect(kortHorizontalLeft, kortVerticalTop, kortHorizontalRight-kortHorizontalLeft, kortVerticalBottom-kortVerticalTop);
        offscreen.drawLine(kortHorizontalLeft+(int)((kortHorizontalRight-kortHorizontalLeft)/2), kortVerticalTop, kortHorizontalLeft+(int)((kortHorizontalRight-kortHorizontalLeft)/2), kortVerticalBottom);  	
 //��������, ���� �� ����� ����
        if (!endOfGame) {    
 //������� ���� ����
        offscreen.setColor(ballColor);
 //��������� ������ ���       
        offscreen.fillOval(ballX-(int)(ballSize/2), ballY-(int)(ballSize/2), ballSize, ballSize);
 //���������� ���� ������       
        offscreen.setColor(playerColor);
 //������� ������� ����       
        offscreen.drawOval(ballX-(int)(ballSize/2), ballY-(int)(ballSize/2), ballSize, ballSize);
 //���������� ������� ������       
        offscreen.fillRect(playerX, playerY-(int)(heightRacket/2), widthRacket, heightRacket);
 //���������� ������� ������
        offscreen.fillRect(robotX, robotY-(int)(heightRacket/2), widthRacket, heightRacket);
        }
 //���� ����� ����, ��       
        else {
 //������� ��� � ������ ������        	
        	Font fontE = new Font ("Times New Roman", 1, 48); 
 //���������� ���� ������
        	offscreen.setColor(fontColor); 
 //��������� ������������ �����
        	offscreen.setFont (fontE); 
 //���������� ���� ����    
        	offscreen.drawString ("���� ���� "+gamePoints[0]+":"+gamePoints[1], countX+100, countY+400);
 //��������� ������������� �������       	
        	offscreen.drawString ("������� s ��� ������ ���� ", countX+100, countY+500);	
        }
 //������� ����� �����       
        Font fontC = new Font ("Times New Roman", 1, 14); 
 //���������� ���� ������
    	offscreen.setColor(fontColor); 
 //��������� ������������ �����
    	offscreen.setFont (fontC); 
 //���������� copyright    
    	offscreen.drawString ("Tennis, ver. 2.01, Copyright (C) 2017 Maxim Mironov", 100, windowSizeY-50);
 //������� ���������� ������ �� �����
    	screen.drawImage(offscreenImg,0,0,this);
    }
 //������� ���������� ������   
    public void update(Graphics screen){
    	 paint(screen);
    	} 
    
    /*
    //��������� ���� -- �� ������������ � ����
    public void mouseDragged(MouseEvent e) {
   // messageX = e.getX();
   // messageY = e.getY();
    repaint();
    }
    public void mouseMoved(MouseEvent e) { }
    public void actionPerformed( ActionEvent e) {
    repaint();
    }*/
 //������� �������� ����   
    public void run() {
 //��������� �������������� ��������   
       try {   	
          while(true) {
 //������ ���� �� ���������       	  
             playGame(); 
 //�������� ���������� ������	
             repaint(); 
 //������� �������� �� delay ����������
             Thread.sleep(delay);
          }
       }
 //���������� �������������� ��������      
       catch (InterruptedException ie) { }
    }
 //���������� �������� �������   
    public static void main(String[] args)
    {
 //������ ������ Tennis   	
       new Tennis();
    }
}