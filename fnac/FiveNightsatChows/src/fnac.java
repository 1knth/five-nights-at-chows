
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import javax.sound.sampled.*;
import java.awt.Component;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class fnac extends JPanel implements KeyListener, MouseListener, Runnable{
	//frame counter
	public static int frameCounter = 0;
	public static int minutes;
	public static int gameTime;
	// user saves (not entirely finished)
	public static int music = 1;
	public static int volume = 1;
	public static int difficulty = 1;
	
	//music stuff
	public static Clip song;
	public static Clip sound;
	
	//title screen
	public static int gameState = 0;
	public static int settings = 0;
	public static BufferedImage titleScreen;
	public static BufferedImage titleScreen2;
	public static BufferedImage settingsM1D1;
	public static BufferedImage settingsM1D2;
	public static BufferedImage settingsM0D1;
	public static BufferedImage settingsM0D2;
	
	//game state 1
	public static BufferedImage[] purpleSprite = new BufferedImage[9];
	public static BufferedImage bg;
	public static BufferedImage savesMenu;
	public static int purpleState = 4;
	public static int purpleX = 1218;
	public static int purpleY = 460;
	public static int cutscene = 0;
	public static boolean left = false;
	public static boolean right = false;
	public static boolean north = false;
	public static boolean south = false;
	
	//game state 2 (unfinished)
	public static BufferedImage office;
	//test
	public static BufferedImage testdoor;
	public static BufferedImage testlight;
	// moveRoom() method
	public static int inRoom = 1;
	public static Boolean doorClosed;
	//office
	public static boolean lightLeft = false;
	public static boolean lightRight = false;
	public static boolean doorLeft = false;
	public static boolean doorRight = false;
	public static boolean ventLeft = false;
	public static boolean ventRight = false;
	public static boolean lightVLeft = false;
	public static boolean lightVRight = false;
	public static int powerUse = 0;
	
	//camera
	public static int cam = 0;
	public static int map = 0;
	public static int power = 1000;
	public static BufferedImage nav;
	public static BufferedImage cameraBorder;
	public static BufferedImage cam1a;
	public static BufferedImage cam1b;
	public static BufferedImage cam2;
	public static BufferedImage cam3;
	public static BufferedImage cam4;
	public static BufferedImage cam5;
	public static BufferedImage cam6;
	public static BufferedImage cam7a;
	public static BufferedImage cam7b;
	public static BufferedImage cam8a;
	public static BufferedImage cam8b;
	public static BufferedImage cam9;
	
	//characters
	public static BufferedImage evilChow;
	public static BufferedImage codeHS;
	public static BufferedImage karel;
	public static BufferedImage codingBat;
	public static int batPos = 2;
	public static int cHSPos = 12;
	public static int eChowPos = 1;
	public static int karelPos = 1;
	
	//game state 3 (unfinished)
	public static BufferedImage winMenu;
	public static BufferedImage loseMenu;
	
	// just here so we can determine where to set our buttons
	public static int mousePosX = 0; 
	public static int mousePosY = 0;
	
	public fnac() {
		setName("Five Night's at Chow's");
		setPreferredSize(new Dimension(1696,954));
		addMouseListener(this);
		this.setFocusable(true);
		addKeyListener(this);
		Thread thread = new Thread(this);
		thread.start();
	}
	
	//character move room (unfinished)
	public static int moveRoom() {
		Random generator = new Random();
		if (gameTime % 2700 == 0 && gameTime < 5400) {
			inRoom = ThreadLocalRandom.current().nextInt(1, 6);
		}
		if (gameTime > 2700 && gameTime % 2700 == 0) {
			if (inRoom == 3) {
				inRoom = 6;
			}
			else if (inRoom == 4) {
				inRoom = 7;
			}
			else if (inRoom == 5) {
				int[] rooms = {5,8,9};
				int randomIndex = generator.nextInt(rooms.length);
				inRoom = rooms[randomIndex];
			}
			else if (inRoom == 6) {
				int[] rooms = {9,10,5};
				int randomIndex = generator.nextInt(rooms.length);
				inRoom = rooms[randomIndex];
			}
			else if (inRoom == 7) {
				int[] rooms = {8,9};
				int randomIndex = generator.nextInt(rooms.length);
				inRoom = rooms[randomIndex];
			}
			else if (inRoom == 8 || inRoom == 9 || inRoom == 10 || inRoom == 11) {
				inRoom = 13;
			}
			else if (inRoom == 13) {
				if (doorClosed) {
					inRoom = 1;
				}
				else {
					gameState = 3;
				}
			}
		}
		if (eChowPos == inRoom || cHSPos == inRoom || karelPos == inRoom || batPos == inRoom) {
			moveRoom();
		}
		return inRoom;
	}
	
	// music control method
	public void playMusic(String musicLocation)
	{
		
		try
		{
			File musicPath = new File(musicLocation);
			if(musicPath.exists())
			{
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
			}
			else
			{
				System.out.print("no file");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	//user saves method (unfinished)
	public static void userSaves() { 
		try {
			PrintWriter userOut = new PrintWriter(new FileWriter("save.txt"));
			
			userOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// method to update page depending on which button is active
	public static BufferedImage displaySettings(){
		if (music == 1){
			if (difficulty == 1){
				return settingsM1D1;
			}else{
				return settingsM1D2;
			}
		}else{
			if (difficulty == 1){
				return settingsM0D1;
			}else{
				return settingsM0D2;
			}
		}
	}
	
	// write a method to update the character facing position
	public static void updatePurple()
	{
		if(north)
		{
			purpleY -= 5;
			if(!right && !left)
			{
				if (purpleState>1)
				{
					purpleState = 0;
					frameCounter = 0;
				}
				if(frameCounter > 6)
				{
					if(purpleState == 0)
					{
						purpleState = 1;
					}
					else
					{
						purpleState = 0;
					}
					frameCounter = 0;
				}
			}
		}
		if(south)
		{
			purpleY += 5;
			if(!right&&!left)
			{
				if (purpleState>3||purpleState<2)
				{
					if(!right&&!left)
					{
						purpleState = 2;
						frameCounter = 0;
					}
				}
				if(frameCounter > 6)
				{
					if(purpleState == 2)
					{
						purpleState = 3;
					}
					else
					{
						purpleState = 2;
					}
					frameCounter = 0;
				}
			}
		}
		if(left)
		{
			purpleX -= 5;
			if(!right)
			{
				if (purpleState>5||purpleState<4)
				{
					purpleState = 4;
					frameCounter = 0;
				}
				if(frameCounter > 6)
				{
					if(purpleState == 4)
					{
						purpleState = 5;
					}
					else
					{
						purpleState = 4;
					}
					frameCounter = 0;
				}
			}
		}
		if(right)
		{
			purpleX += 5;
			if (purpleState<6)
			{
				purpleState = 6;
				frameCounter = 0;
			}
			if(frameCounter > 6)
			{
				if(purpleState == 6)
				{
					purpleState = 7;
				}
				else
				{
					purpleState = 6;
				}
				frameCounter = 0;
			}
		}
		if(north||south||left||right)
		{
			frameCounter++;
		}
	}
	
	public void paintComponent(Graphics g) {
		
		if(map != 0)
		{
			powerUse++;	
		}
		if(doorLeft)
		{
			powerUse++;
		}
		if(lightLeft)
		{
			powerUse += 2;
		}
		if(powerUse > 500)
		{
			power--;
			powerUse = 0;
			System.out.println(power);
		}
		// paint component
		super.paintComponent(g);
		if (gameState == 0) {
			if (settings == 1) {
				g.drawImage(displaySettings(),0,0,null);
			}
			if (settings == 0){
				g.drawImage(titleScreen,0,0,null);
				if (gameTime >= 450 && gameTime <= 490) {
					g.drawImage(titleScreen2,0,0,null);
				}
			}
		}
		if (gameState == 1){
			updatePurple();
			g.drawImage(bg,0,0,null);
			g.drawImage(purpleSprite[purpleState], purpleX, purpleY, null);
			if (cutscene == 1) {
				g.setColor(new Color(255,0,0));
				g.drawString("press 'e' to go to the next game state",300,300);
			}
		}
		if (gameState == 2){
			//frame
				gameTime++;
				if (gameTime > 2700 && gameTime % 2700 == 0) {
					minutes++;
				}
				if (difficulty == 1 && gameTime % 2700 == 0) {
					moveRoom();
				}
				if (difficulty == 2 && gameTime % 1350 == 0) {
					moveRoom();
				}
			
			// draw office
			if (map == 0) {
				g.drawImage(office,0,0,null);
				//draw map and cams
				if(doorLeft)
				{
					g.drawImage(testdoor,3,0,null);
				}	
				if(lightLeft)
				{
					g.drawImage(testlight,300,0,null);
				}
			}else{
				if (cam == 0) {
					g.drawImage(nav,0,0,null);
				}
				if (cam == 1) {
					g.drawImage(cam1a,0,0,null);
				}
				if (cam == 2) {
					g.drawImage(cam1b,0,0,null);
				}
				if (cam == 3) {
					g.drawImage(cam2,0,0,null);
				}
				if (cam == 4) {
					g.drawImage(cam3,0,0,null);
				}
				if (cam == 5) {
					g.drawImage(cam4,0,0,null);
				}
				if (cam == 6) {
					g.drawImage(cam5,0,0,null);
				}
				if (cam == 7) {
					g.drawImage(cam6,0,0,null);
				}
				if (cam == 8) {
					g.drawImage(cam7a,0,0,null);
				}
				if (cam == 9) {
					g.drawImage(cam7b,0,0,null);
				}
				if (cam == 10) {
					g.drawImage(cam8a,0,0,null);
				}
				if (cam == 11) {
					g.drawImage(cam8b,0,0,null);
				}
				if (cam == 12) {
					g.drawImage(cam9,0,0,null);
				}
			}
			
		}
		if (gameState == 3) {
			g.drawString("game state 3: win/loss screen", 300, 250);
			g.drawString("press 'e' to go to back to menu/ game state 1", 300, 300);
		}
	}
	
	public static void main(String [] args){
		try {
			titleScreen = ImageIO.read(new File("titleScreen.png"));
			titleScreen2 = ImageIO.read(new File("titleScreen2.png"));
			settingsM1D1 = ImageIO.read(new File("settingsM1D1.png"));
			settingsM1D2 = ImageIO.read(new File("settingsM1D2.png"));
			settingsM0D1 = ImageIO.read(new File("settingsM0D1.png"));
			settingsM0D2 = ImageIO.read(new File("settingsM0D2.png"));
			purpleSprite[0] = ImageIO.read(new File("walkNorth1.png"));
			purpleSprite[1] = ImageIO.read(new File("walkNorth2.png"));
			purpleSprite[2] = ImageIO.read(new File("walkSouth1.png"));
			purpleSprite[3] = ImageIO.read(new File("walkSouth2.png"));
			purpleSprite[8] = ImageIO.read(new File("walkLeft0.png"));
			purpleSprite[4] = ImageIO.read(new File("walkLeft1.png"));
			purpleSprite[5] = ImageIO.read(new File("walkLeft2.png"));
			purpleSprite[6] = ImageIO.read(new File("walkRight1.png"));
			purpleSprite[7] = ImageIO.read(new File("walkRight2.png"));
			bg = ImageIO.read(new File("gs1bg.png"));
			office = ImageIO.read(new File("officeEmpty.png"));
			nav = ImageIO.read(new File("map.png"));
			cameraBorder = ImageIO.read(new File("cameraBorder.png"));
			cam7a = ImageIO.read(new File("7a.png"));
			cam7b = ImageIO.read(new File("7b.png"));
			testdoor = ImageIO.read(new File("download.jpg"));
			testlight = ImageIO.read(new File("lighttest.jpg"));
		}
		catch (Exception e) {
			System.out.println("image import error");
		}
		JFrame myFrame = new JFrame();
		fnac myPanel = new fnac();
		myFrame.add(myPanel);
		myFrame.setVisible(true);
		myFrame.pack();
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setResizable(false);
		
		//play the start menu music, which stops when gamestate becomes 1
		try
		{
			File musicPath = new File("menu.wav");
			if(musicPath.exists())
			{
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				song = AudioSystem.getClip();
				song.open(audioInput);
				if (music == 1) {
					song.start();
				}
				else {
					song.stop();
				}
				song.start();
				song.loop(Clip.LOOP_CONTINUOUSLY);
			}
			else
			{
				System.out.print("no file");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void run(){
		while (true){
			try {
				
				Thread.sleep(17);
				repaint();
 			}
			catch (Exception e) {}
			
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if (gameState == 1) {
			if (purpleX < 445) {
				left = false;
				right = false;
				purpleState = 8;
				cutscene = 1;
				if (e.getKeyChar() == 'e') {
					gameState = 2;
				}
			}else{
				if (e.getKeyChar() == 'a')
				{
					left = true;
				}

				if (e.getKeyChar() == 'd')
				{
					right = true;
				}
				if (e.getKeyCode() == 37) {
					left = true;
				}
				if (e.getKeyCode() == 39) {
					right = true;
				}
			}
		}
		if (gameState == 2) {
			// resets position
			purpleX = 1218;
			purpleY = 460;
			if (e.getKeyChar() == ' ') {
				gameState = 3;
			}
		}
		if (gameState == 3) {
			if (e.getKeyChar() == 'e') {
				gameState = 0;
			}
		}
	}
	public void keyReleased(KeyEvent e)
	{
		if (gameState == 1) {
			if (e.getKeyChar() == 'a')
			{
				left = false;
			}				
			if (e.getKeyChar() == 'd')
			{
				right = false;
			}
			if (e.getKeyCode() == 37) {
				left = false;
			}
			if (e.getKeyCode() == 39) {
				right = false;
			}
		}
	}
	public void mousePressed(MouseEvent e) {
		mousePosX = e.getX();
		mousePosY = e.getY();
		// for convenience, wherever you click on the screen, it prints out the mouse position
		System.out.printf("%d, %d%n", mousePosX, mousePosY);
		
		if (gameState == 0){
			if (settings == 0){
				// enter game 
				if (mousePosX > 126 && mousePosY > 436 && mousePosX < 299 && mousePosY < 495){
					gameState = 1;
					playMusic("scare.wav");
				}
				
				// enter settings menu
				if (mousePosX > 132 && mousePosX < 460 && mousePosY > 533 && mousePosY < 574){
					settings = 1;
				}
				//quit game
				if (mousePosX > 132 && mousePosY > 779 && mousePosX < 307 && mousePosY < 831){
					System.exit(0);
				}
			}else if (settings == 1){
				// exit the settings menu
				if (mousePosX > 516 && mousePosY > 275 && mousePosX < 633 && mousePosY < 684){
					settings = 0;
				}
				
				//settings buttons
				// music on
				if (mousePosX > 1054 && mousePosY > 399 && mousePosX < 1131 && mousePosY < 447){
					music = 1;
					song.start();
				}
				
				//music off
				if (mousePosX > 972 && mousePosY > 408 && mousePosX < 1043 && mousePosY < 442){
					music = 0;
					song.stop();
				}
				
				//difficulty normal
				if (mousePosX > 935 && mousePosY > 505 && mousePosX < 1049 && mousePosY < 550){
					difficulty = 1;
				}
				
				//difficulty hard
				if (mousePosX > 1049 && mousePosY > 513 && mousePosX < 1139 && mousePosY < 543){
					difficulty = 2;
				}
			}
		}
		
		// main game
		if (gameState == 2) {
			if (cam == 0 && map == 1) {
				if (mousePosX > 44 && mousePosY > 49 && mousePosX < 107 && mousePosY < 113) {
					map = 0;
				}
			}
			//exit cams
			if (cam > 0) {
				if (mousePosX > 44 && mousePosY > 49 && mousePosX < 107 && mousePosY < 113) {
					if(cam == 12)
					{
						sound.stop();
					}
					cam = 0;
				}
			}
			//go into map
			if (mousePosX > 405 && mousePosY > 851 && mousePosX < 1292 && mousePosY < 921) {
				map = 1;
			}
			if (map == 1) {
				//go into cams
				if (mousePosX > 111 && mousePosY > 111 && mousePosX < 111 && mousePosY < 111) {
					cam = 1;
				}
				if (mousePosX > 111 && mousePosY > 111 && mousePosX < 111 && mousePosY < 111) {
					cam = 2;
				}
				if (mousePosX > 111 && mousePosY > 111 && mousePosX < 111 && mousePosY < 111) {
					cam = 3;
				}
				if (mousePosX > 111 && mousePosY > 111 && mousePosX < 111 && mousePosY < 111) {
					cam = 4;
				}
				if (mousePosX > 111 && mousePosY > 111 && mousePosX < 111 && mousePosY < 111) {
					cam = 5;
				}
				if (mousePosX > 111 && mousePosY > 111 && mousePosX < 111 && mousePosY < 111) {
					cam = 6;
				}
				if (mousePosX > 111 && mousePosY > 111 && mousePosX < 111 && mousePosY < 111) {
					cam = 7;
				}
				if (mousePosX > 769 && mousePosY > 627 && mousePosX < 782 && mousePosY < 642) {
					cam = 8;
				}
				if (mousePosX > 742 && mousePosY > 802 && mousePosX < 752 && mousePosY < 819) {
					cam = 9;
				}
				if (mousePosX > 111 && mousePosY > 111 && mousePosX < 111 && mousePosY < 111) {
					cam = 10;
				}
				if (mousePosX > 111 && mousePosY > 111 && mousePosX < 111 && mousePosY < 111) {
					cam = 11;
				}
				if (mousePosX > 1005 && mousePosY > 604 && mousePosX < 1113 && mousePosY < 756) {
					cam = 12;
					if(cHSPos == 12)
					{
						try
						{
							File musicPath = new File("HSAmbient.wav");
							if(musicPath.exists())
							{
								AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
								sound = AudioSystem.getClip();
								sound.open(audioInput);
								sound.start();
								sound.loop(Clip.LOOP_CONTINUOUSLY);
							}
							else
							{
								System.out.print("no file");
							}
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
					}
				}
			}
			else
			{
				if (mousePosX > 68 && mousePosY > 216 && mousePosX < 205 && mousePosY < 708) {
					if(!doorLeft)
					{
						doorLeft = true;
						System.out.print("door left");
						
					}
					else
					{
						doorLeft = false;
					}
				}
				if (mousePosX > 111 && mousePosY > 111 && mousePosX < 111 && mousePosY < 111) {
					if(!doorRight)
					{
						doorRight = true;
						
					}
					else
					{
						doorRight = false;
					}
				}
				if (mousePosX > 267 && mousePosY > 200 && mousePosX < 388 && mousePosY < 639) {
					if(!lightLeft)
					{
						lightLeft = true;
						System.out.print("light left");
					}
					else
					{
						lightLeft = false;
					}
				}
				if (mousePosX > 111 && mousePosY > 111 && mousePosX < 111 && mousePosY < 111) {
					if(!lightRight)
					{
						lightRight = true;
					}
					else
					{
						lightRight = false;
					}
				}
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) {
	
		 
	}


	public void mouseReleased(MouseEvent e) {

	}


	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

}