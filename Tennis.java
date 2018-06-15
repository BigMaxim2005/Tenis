/**
 * Tennis , Java Application

 */

/**
 * @author MAXIM
 *
 */
//подключение необходимых библиотек 
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
//декларация основного класса и подключение клавиатуры 
public class Tennis extends JFrame implements KeyListener, /*MouseMotionListener, ActionListener,*/ Runnable
{
//декларируем переменные 
	int k=0;
//сложность игры
	int gameLevel;
//счёт сетов
	int[] gamePoints = new int[2];
//счёт баллов
	int[] setPoints = new int[2];
//координаты ракетки игрока 
	int playerX=1000;
//координаты ракетки игрока
	int playerY=450;
//скорость передвижения игрока
	int speedPlayerY=4;
	int speedPlayerX=1;
// горизонтальные координаты робота
	int robotX=200;
// вертикальные координаты робота
	int robotY=450;
//скорость передвижения робота
	int speedRobot=4;
// размеры ракетки игрока
	int widthRacket=10;
	int heightRacket=100;
//координаты ракеток компьютера
	int[] computerX = new int[10];
	int[][] computerY = new int[10][10];
//размеры окна
	int windowSizeX=1200;
	int windowSizeY=800;
//вертикальные границы корта
	int kortVerticalTop=200;
	int kortVerticalBottom=700;
//боковая граница корта
	int kortHorizontalLeft=100;
	int kortHorizontalRight=1100;
//размер мяча
	int ballSize=20;
//координаты мяча
	int ballX=(int)(kortHorizontalLeft+(kortHorizontalRight-kortHorizontalLeft)/2);
	int ballY=(int)(kortVerticalTop+(kortVerticalBottom-kortVerticalTop)/2);
//скорость мяча
	int speedX=5;
	int speedY=5;
//временные переменные скорости мяча
	int tempSpeedX=0;
	int tempSpeedY=0;
//время игры в секундах
	int time; 
//координаты отображения счёта
	int countX=100;
	int countY=100;
//задержка в мили секундах
	int delay=30;
//конец игры
	boolean endOfGame=false;
	boolean gamePause=false;
//временное содержимое экрана
	Graphics offscreen;
	Image offscreenImg;
//аудиоклип звуков удара
	public AudioClip BoomAudio;
	public AudioClip appAudio;
	public AudioClip oxxxAudio;

	private static final long serialVersionUID = 1L;
//декларация основного метода    
	public Tennis() 
    {
//декларируем переменную окна	
		JFrame f = this;
//установка способа раскладки 
		f.setLayout( new FlowLayout() );
//установка выхода при закрытии окна		
    	f.setDefaultCloseOperation(EXIT_ON_CLOSE);
//запуск начальной функции     
    	init();    	
//установка размеров окна    	
    	f.setSize(windowSizeX , windowSizeY);   	
//добавление обработчика клавиатуры    	
    	f.addKeyListener(this);
//установка фокуса        
    	f.setFocusable(true);    
//сделать окно видимым        
    	f.setVisible( true );   	
//декларация цепочки событий        
    	Thread t = new Thread( this );
//запуск цепочки    
    	t.start();
    }
 
//декларация начальной функции	
    public void init() {
//получить размер экрана    	
    	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        windowSizeX = gd.getDisplayMode().getWidth();
    	windowSizeY = gd.getDisplayMode().getHeight();
//получение пути к звуковому файлу удара
    	URL myUrl = ClassLoader.getSystemResource("boom.wav");
//чтение звукового файла        
    	BoomAudio=Applet.newAudioClip(myUrl);
//проигрывание звукового файла    	
//    	BoomAudio.play();
//получение пути к звуковому файлу удара
    	URL appUrl = ClassLoader.getSystemResource("4.wav");
//чтение звукового файла        
    	appAudio=Applet.newAudioClip(appUrl);
//проигрывание звукового файла    	
//      appAudio.play();
//получение пути к звуковому файлу удара
    	URL oxxxUrl = ClassLoader.getSystemResource("oxxx.wav");
//чтение звукового файла        
    	oxxxAudio=Applet.newAudioClip(oxxxUrl);
//проигрывание звукового файла    	
//    	oxxxAudio.play();
        endOfGame = true;
    }
    
//декларация функции начала игры 
    public void iniGame() {
//установка координаты X мяча в центр поля  
    	 ballX=(int)(kortHorizontalLeft+(kortHorizontalRight-kortHorizontalLeft)/2);
//установка координаты Y мяча в центр поля    	 
    	 ballY=(int)(kortVerticalTop+(kortVerticalBottom-kortVerticalTop)/2);
//установка горизонтальной скорости мяча в нуль    	 
    	 speedX=0;
//установка вертикальной скорости мяча в нуль         
    	 speedY=0;
//обнуление очков за игру у робота         
    	 setPoints[0]=0;
//обнуление очков за игру у игрока       	 
    	 setPoints[1]=0;
    }
    
//декларация функции начала нового сета
     public void iniSet() {
//установка координаты X мяча в ценр поля   	 
     ballX=(int)(kortHorizontalLeft+(kortHorizontalRight-kortHorizontalLeft)/2);
//установка координаты Y мяча в ценр поля   	 
     ballY=(int)(kortVerticalTop+(kortVerticalBottom-kortVerticalTop)/2);
//установка горизонтальной скорости мяча в нуль     	 
     speedX=((int)(8*(0.5-Math.random())));
//установка вертикальной скорости мяча в нуль   	 
     speedY=0;
//если скорость мяча нулевая, то задать скорость              	
 	 while (Math.abs(speedX)<2) {
 		speedX=((int)(8*(0.5-Math.random())));
        speedY=(int)(15*(0.5-Math.random()));
 	}
   }
     
//декларация функции старта игры
    public void startGame() {    	
//обнуление очков у робота    	 
    	 gamePoints[0]=0;
//обнуление очков у игрока      	 
    	 gamePoints[1]=0;
//вызов функции начала игры    	 
    	 iniGame();
//выставление флага окончания игры в ложь    	
    	 endOfGame=false;
    }
    
//декларация функции начала нового сета 
    public void newSet() {
//вызов функции старта сета   	 
    	iniSet();
//установка горизонтальной скорости мяча в нуль        
    	speedX=0;
//установка вертикальной скорости мяча в нуль        
    	speedY=0;
   }
  //пауза в игре
    public void pauseGame() {
        gamePause=true;
    }
  //продолжить игру
    public void runGame() {
        gamePause=false; 
    }
  //обработка нажатий клавиш  
        @Override
        public void keyPressed(KeyEvent evt) {
  //обработка нажатия клавишы по её коду	
            switch (evt.getKeyCode()) {
  //при нажатии на стрелку "вниз" двигать ракетку вниз
                case KeyEvent.VK_DOWN:
  //если скорость мяча нулевая, то задать скорость              	
                	while (Math.abs(speedX)<2) {
                		speedX=((int)(16*(0.5-Math.random())));
                        speedY=(int)(15*(0.5-Math.random()));
                	}
  //если ракетка игрока выше нижней границы, то двигать ракетку игрока вниз              	
                	if (playerY<kortVerticalBottom) {
                	    playerY += speedPlayerY;
                	}
                    break;
  //при нажатии на стрелку "вверх"  двигать ракетку вверх      
                case KeyEvent.VK_UP:
  //если скорость мяча нулевая, то задать скорость мяча              	
                	while (Math.abs(speedX)<2) {
                		speedX=((int)(16*(0.5-Math.random())));
                        speedY=(int)(15*(0.5-Math.random()));
                	}
  //если ракетка игрока ниже верхней границы, то двигать ракетку вверх              	
                	if (playerY>kortVerticalTop) {
                     playerY -= speedPlayerY;
                	}
                	break;
  //при нажатии на стрелку "влево"            	
                case KeyEvent.VK_LEFT:
  //если ракетка игрока не достигла середины поля, то двигать её влево              	
                	if (playerX>(kortHorizontalLeft+(int)((kortHorizontalRight-kortHorizontalLeft)/2))) {
                    playerX -= speedPlayerX;
                	}
                    break;
  //при нажатии на стрелку "вправо"                  
                case KeyEvent.VK_RIGHT:
  //если ракетка игрока не достигла правой границы, то двигать её вправо              	
                	if (playerX<kortHorizontalRight) {
                    playerX += speedPlayerX;
                	}
                    break;
            }
  //реакция на нажатие клавиши по букве          
            switch (evt.getKeyChar()) {
  //при нажатии на клавишу s начать новую игру                 
                case 's':
                	startGame();
                	break;
  //при нажатии на клавишу r продолжить игру              	
                case 'r': 
                	runGame();
                	break;
  //при нажатии на клавишу p-пауза в игре              	
                case 'p': 
                	pauseGame();
                    break;
           }
        }
 //зарезервированная функция, в программе не используется       
        public void keyTyped(KeyEvent ev){
        }
 //зарезервированная функция, в программе не используется       
        public void keyReleased(KeyEvent ev){
        }
    
 //проверить мяч на корте? Если да, вернуть истину
    public boolean movePossibleX() {
    	boolean result;
 //если после прибавки скорости координате мяча, он на корте, то вернуть истину
    	if ((ballX+speedX)>kortHorizontalLeft && (ballX+speedX)<kortHorizontalRight) {
    		result=true;
    	}
 //иначе вернуть фальш   	
    	else result=false;
    	return result;
    }
    
 //возможно ли вертикальное движение?
    public boolean movePossibleY() {
    	boolean result;
 //если мяч не достиг боковой границы корта, то вернуть истину  	
    	if ((ballY+speedY)>kortVerticalTop && (ballY+speedY)<kortVerticalBottom) {
    		result=true;
    	}
 //иначе вернуть ложь   	
    	else result=false;
    	return result;
    }
    
 //сдвинуть мяч по горизонтали
    public void moveX() {
       	ballX+=speedX;
    }
    
 //сдвинуть мяч по вертикали
    public void moveY() {
       	ballY+=speedY;
    }
    
 //поменять направление горизонтального движения мяча 
    public void changeSpeedX() {
 //поменять знак у горизонтальной скорости   	
       	speedX*=-1;
 //проиграть звук удара      	
       	BoomAudio.play();
 //сгенирировать новые скорости мяча
       	speedX=(speedX+(int)(2*(0.5-Math.random())));
        speedY=(speedY+(int)(6*(0.5-Math.random())));
    }
    
 //поменять направление вертикального движения мяча
    public void changeSpeedY() {
 //поменять знак у вертикальной скорости    	
       	speedY*=-1;
    }
    
 //если игрок или робот ударил мяч
    public boolean ballPushed() {
 //переменная, хранящая результат   	
    	boolean result;
 //будущие координаты мяча   	
    	int tempX, tempY;
    	tempX=ballX+speedX;
    	tempY=ballY+speedY;
 //если будущие координаты мяча пересекут координату X игрока, то 
    	if ((tempX>=playerX)&&(ballX<playerX)) {
 //если мяч попал в створ ракетки игрока, то вернуть истину    		
    	    if ((tempY>=(playerY-heightRacket/2))&&(tempY<(playerY+heightRacket/2))) {
    	    	result=true;
    	    }
 //если не попал, то вернуть ложь   	    
    	    else result=false;
    	  }
 //иначе, если будущие координаты мяча не пресекут координату X игрока, то   	
    	else {
 //если будущие координаты мяча пересекут координату X робота, то
    	if ((tempX<=robotX)&&(ballX>robotX)) {
 //если мяч попадёт в створ ракетки робота, то вернуть истину   		
    	    if ((tempY>=(robotY-heightRacket/2))&&(tempY<(robotY+heightRacket/2))) {
    	    	result=true;
    	    }
 //если не попадёт, то вернуть ложь   	    
    	    else result=false;
    	  }
 //иначе вернуть ложь   	
    	else result=false;
    	}
    	return result;
    }
    
 //функция, программирующая автоматическое движение робота
    public void moveRobot() {
 //если мяч отклонился от ракетки, то    	
    	if (ballY>=(robotY+5)) {
 //двигать робота вниз    		
    		robotY+=speedRobot;
    	}
 //если мяч отклонился от ракетки, то   	
    	if (ballY<=(robotY-5)) {
 //двигать робота вверх   		
    		robotY-=speedRobot;
    	}
    	
    }
 //начисление очков
    public void givePoints() {
 //если мяч достиг правого края корта, то   	
    	if (ballX>=(kortHorizontalRight-15)) {
 //присвоить очко роботу
    		setPoints[0]+=1;
 //огорчение от неудачи игрока   		
    		oxxxAudio.play();
 //если количество очков робота стало 15, то   		
    		if (setPoints[0]>14) {
 //прибавить очко в геймах роботу
    			gamePoints[0]+=1;
 //обнулить очки в сетах   			
    			setPoints[0]=0;   			
    			setPoints[1]=0;
 //если очки в геймах у робота стали больше двух, то   			
    			if (gamePoints[0]>2) {
 //конец игры   				
    				endOfGame=true;
    			}
    		}
 //начать новый сет   		
    		newSet();
    	}
 //если мяч достиг левого края корта, то   	
    	if (ballX<=(kortHorizontalLeft+15)) {
 //присвоить очко игроку   		
    		setPoints[1]+=1;
 //аплодисменты игроку   		
    		appAudio.play();
 //если количество очков игрока стало 15, то   		
    		if (setPoints[1]>14) {
 //добавить очко в геймах игроку   			
    			gamePoints[1]+=1;
 //обнулить очки сета   			
    			setPoints[0]=0;
    			setPoints[1]=0;
 //если счёт по геймам больше двух, то   			
    			if (gamePoints[1]>2) {
 //конец игры   				
    				endOfGame=true;
    			}
    		}
 //начать новый сет   		
    		newSet();
    	}
    }
 //движок игры
    public void playGame(){
 //если не конец игры и не пауза, то   	
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
 //если движение по вертикали возможно, то   	
    	  if (movePossibleY()) {
 //двигать мяч по вертикали    		  
    		moveY();
    	  }
 //иначе   	  
    	  else {
 //поменять направление мяча по вертикали   		  
    		changeSpeedY();
    	  }
    	}
 //двигать робота
    	moveRobot();
 //произвести начисление очков
    	givePoints();
    	}
    }
    @Override
    public void paint(Graphics screen) {
 //создания буфера против мерцания
    	offscreenImg = createImage(windowSizeX, windowSizeY);
 //получить контент графического устройства   	
    	offscreen=offscreenImg.getGraphics();
 //очистить содержимое
    	offscreen.clearRect(0,0,windowSizeX,windowSizeY);
 //декларируем используемые цвета
 //цвет заднего фона (белый)   	
        Color backColor = new Color(255, 255, 255);
 //цвет мяча (жёлтый)       
        Color ballColor = new Color(255, 255, 0);
 //цвет ракетки игрока (тёмно-синий)       
        Color playerColor = new Color(0, 0, 150);
 //цвет шрифта (синий)       
        Color fontColor = new Color(0, 0, 255);
 //цвет корта (светло-зелёный)       
        Color kortColor = new Color(100, 255, 100);
 //выбрать цвет заднего фона
    	offscreen.setColor(backColor);
 //заполнить им всё окно   	
        offscreen.fillRect(0, 0, windowSizeX, windowSizeY);
 //	---------------------------------------------------------------
 //выбрать размер и тип шрифта       
    	Font font = new Font ("Verdana", 1, 32); 
 //установить цвет шрифта
    	offscreen.setColor(fontColor); 
 //установить используемый шрифт
    	offscreen.setFont (font); 
 //отобразить счёт игры    
    	offscreen.drawString ("счёт игры "+gamePoints[0]+":"+gamePoints[1], countX, countY);
    	offscreen.drawString ("счёт сета "+setPoints[0]+":"+setPoints[1], countX, countY+50);
 // ---------------------------------------------------------------
 //выбирать новый тип и размер шрифта   	
        font = new Font ("Courier New", 1, 18); 
 //установить цвет шрифта
        offscreen.setColor(fontColor); 
 //установить используемый шрифт
        offscreen.setFont (font); 
 //отобразить правила игры    
        offscreen.drawString ("s - начать новую игру", countX+300, countY-20);
        offscreen.drawString ("p - пауза", countX+300, countY);
        offscreen.drawString ("r - продолжить ", countX+300, countY+20);
        offscreen.drawString ("управление ракеткой игрока", countX+300, countY+40);
        offscreen.drawString ("с помощью стрелок на клавиатуре", countX+300, countY+60);
 //выбрать цвет корта
        offscreen.setColor(kortColor);
 //заполнить выбранным цветом корт       
        offscreen.fillRect(kortHorizontalLeft, kortVerticalTop, kortHorizontalRight-kortHorizontalLeft, kortVerticalBottom-kortVerticalTop);
 //установить цвет ракетки игрока       
        offscreen.setColor(playerColor);
 //нарисовать линии на корте       
        offscreen.drawRect(kortHorizontalLeft, kortVerticalTop, kortHorizontalRight-kortHorizontalLeft, kortVerticalBottom-kortVerticalTop);
        offscreen.drawLine(kortHorizontalLeft+(int)((kortHorizontalRight-kortHorizontalLeft)/2), kortVerticalTop, kortHorizontalLeft+(int)((kortHorizontalRight-kortHorizontalLeft)/2), kortVerticalBottom);  	
 //рисовать, если не конец игры
        if (!endOfGame) {    
 //выбрать цвет мяча
        offscreen.setColor(ballColor);
 //заполнить цветом мяч       
        offscreen.fillOval(ballX-(int)(ballSize/2), ballY-(int)(ballSize/2), ballSize, ballSize);
 //установить цвет игрока       
        offscreen.setColor(playerColor);
 //сделать обводку мяча       
        offscreen.drawOval(ballX-(int)(ballSize/2), ballY-(int)(ballSize/2), ballSize, ballSize);
 //нарисовать ракетку игрока       
        offscreen.fillRect(playerX, playerY-(int)(heightRacket/2), widthRacket, heightRacket);
 //нарисовать ракетку робота
        offscreen.fillRect(robotX, robotY-(int)(heightRacket/2), widthRacket, heightRacket);
        }
 //если конец игры, то       
        else {
 //выбрать тип и размер шрифта        	
        	Font fontE = new Font ("Times New Roman", 1, 48); 
 //установить цвет шрифта
        	offscreen.setColor(fontColor); 
 //установит используемый шрифт
        	offscreen.setFont (fontE); 
 //отобразить счёт игры    
        	offscreen.drawString ("счёт игры "+gamePoints[0]+":"+gamePoints[1], countX+100, countY+400);
 //отобразиь пояснительную надпись       	
        	offscreen.drawString ("нажмите s для начала игры ", countX+100, countY+500);	
        }
 //выбрать новый шрифт       
        Font fontC = new Font ("Times New Roman", 1, 14); 
 //установить цвет шрифта
    	offscreen.setColor(fontColor); 
 //установит используемый шрифт
    	offscreen.setFont (fontC); 
 //отобразить copyright    
    	offscreen.drawString ("Tennis, ver. 2.01, Copyright (C) 2017 Maxim Mironov", 100, windowSizeY-50);
 //вывести содержимое буфера на экран
    	screen.drawImage(offscreenImg,0,0,this);
    }
 //функция обновления экрана   
    public void update(Graphics screen){
    	 paint(screen);
    	} 
    
    /*
    //обработка мыши -- не используется в игре
    public void mouseDragged(MouseEvent e) {
   // messageX = e.getX();
   // messageY = e.getY();
    repaint();
    }
    public void mouseMoved(MouseEvent e) { }
    public void actionPerformed( ActionEvent e) {
    repaint();
    }*/
 //функция движения игры   
    public void run() {
 //обработка исключительных ситуаций   
       try {   	
          while(true) {
 //играть пока всё нормально       	  
             playGame(); 
 //обновить содержимое экрана	
             repaint(); 
 //сделать задержку на delay милисекунд
             Thread.sleep(delay);
          }
       }
 //обработать исключительную ситуацию      
       catch (InterruptedException ie) { }
    }
 //декларация основной функции   
    public static void main(String[] args)
    {
 //запуск метода Tennis   	
       new Tennis();
    }
}