
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import javax.sound.sampled.*;
import java.awt.Component;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
public class fnac extends JPanel implements KeyListener, MouseListener, Runnable{
	//misc 
	public static int gameSettings;
	//frame counter
	public static int frameCounter = 0;
	public static int hour = 0;
	public static int gameTime;
	// user saves (not entirely finished)
	public static int music = 1;
	public static int volume = 1;
	public static int difficulty = 1;
	public static int night = 3;
	public static int userState = 0;
	//music stuff
	public static Clip song;
	public static Clip sound;
	public static boolean ambientPlaying = false;
	
	//title screen
	public static int gameState = 0;
	public static int settings = 0;
	public static int about = 0;
	public static int instructions = 0;
	public static BufferedImage aboutPage;
	public static BufferedImage backStory;
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
	public static int score = 0;
	public static int winLoss = 0;
	public static int maxNight = 1;
	//office
	public static BufferedImage office;
	public static BufferedImage leftVent;
	public static BufferedImage rightVent;
	public static BufferedImage leftLight;
	public static BufferedImage rightLight;
	public static BufferedImage leftDoor;
	public static BufferedImage rightDoor;

	public static boolean lightLeft = false;
	public static boolean lightRight = false;
	public static boolean doorLeft = false;
	public static boolean doorRight = false;
	public static boolean ventLeft = false;
	public static boolean ventRight = false;
	public static int lightAccLeft = 0;
	public static int lightAccRight = 0;
	public static boolean chowLoss = true;
	public static boolean karelLoss = true;
	public static boolean batLoss = true;
	public static int powerUse = 0;
	public static int lightAccMax = 5;
	//change this variable to change the speed of the monsters moving at difficulty 1
	public static int d1Time = 675;
	//camera
	public static int cam = 0;
	public static int map = 0;
	public static int power = 100;
	public static int powerConstant = 1;
	public static BufferedImage nav;
	public static BufferedImage cameraBorder;
	public static BufferedImage leftLEChow;
	public static BufferedImage leftLKarel;
	public static BufferedImage leftLBat;
	public static BufferedImage rightLEChow;
	public static BufferedImage rightLKarel;
	public static BufferedImage rightLBat;
	public static BufferedImage[] cam1a = new BufferedImage[4];
	public static BufferedImage[] cam1b = new BufferedImage[4];
	public static BufferedImage[] cam2 = new BufferedImage[4];
	public static BufferedImage[] cam3 = new BufferedImage[4];
	public static BufferedImage[] cam4 = new BufferedImage[4];
	public static BufferedImage[] cam5 = new BufferedImage[4];
	public static BufferedImage[] cam6 = new BufferedImage[4];
	public static BufferedImage[] cam7a = new BufferedImage[4];
	public static BufferedImage[] cam7b = new BufferedImage[4];
	public static BufferedImage[] cam8a = new BufferedImage[4];
	public static BufferedImage[] cam8b = new BufferedImage[4];
	public static BufferedImage cam9;
	public static BufferedImage cams[][] = {cam1a,cam1b,cam2,cam3,cam4,cam5,cam6,cam7a,cam7b,cam8a,cam8b};	
	//characters
	public static BufferedImage evilChow;
	public static BufferedImage codeHS;
	public static BufferedImage karel;
	public static BufferedImage codingBat;
	public static BufferedImage chowScare;
	public static BufferedImage karelScare;
	public static BufferedImage batScare;
	public static int batPos = 1;
	public static int cHSPos = 11;
	public static int eChowPos = 1;
	public static int karelPos = 1;
	
	//game state 3 (unfinished)
	public static BufferedImage winMenu;
	public static BufferedImage loseMenu;
	public static boolean winOrLoss;
	
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
	
	//character move room 
	public static int moveRoom(int inRoom) {
		int temp = inRoom;
		Random generator = new Random();
		if (gameTime < d1Time*2) {
			inRoom = ThreadLocalRandom.current().nextInt(1, 6);
		}
		if (gameTime > d1Time) {
			if(inRoom == 1) {
				int[] rooms = {2,3,4,5};
				int randomIndex = generator.nextInt(rooms.length);
				inRoom = rooms[randomIndex];
			}
			else if(inRoom == 2) {
				int[] rooms = {3,4,5};
				int randomIndex = generator.nextInt(rooms.length);
				inRoom = rooms[randomIndex];
			}
			else if (inRoom == 3) {
				inRoom = 6;
			}
			else if (inRoom == 4) {
				inRoom = 7;
			}
			else if (inRoom == 5) {
				int[] rooms = {8,9,15};
				int randomIndex = generator.nextInt(rooms.length);
				inRoom = rooms[randomIndex];
				if(inRoom == 15)
				{
					playMusic("breathLeft.wav");
				}
			}
			else if (inRoom == 6) {
				int[] rooms = {9,10,5,16};
				int randomIndex = generator.nextInt(rooms.length);
				inRoom = rooms[randomIndex];
				if(inRoom == 16)
				{
					playMusic("breathRight.wav");
				}
			}
			else if (inRoom == 7) {
				int[] rooms = {8,9};
				int randomIndex = generator.nextInt(rooms.length);
				inRoom = rooms[randomIndex];
			}
			else if (inRoom == 8 || inRoom == 9) {
				inRoom = 13;
			}
			else if (inRoom == 10 || inRoom == 11) {
				inRoom = 14;
			}
			
			else if (inRoom == 13) {
				if (doorLeft) {
					if(lightAccLeft >= lightAccMax)
					{
						lightAccLeft = 0;
						inRoom = 1;
						score += 1000;
					}
				}
				else {
					if(inRoom == eChowPos)
					{
						chowLoss = true;
					}
					else if(inRoom == batPos)
					{
						batLoss = true;
					}
					else if(inRoom == karelPos)
					{
						karelLoss = true;
					}
					gameState = 3;
				}
			}
			else if (inRoom == 14) {
				if (doorRight) {
					if(lightAccRight >= lightAccMax)
					{
						lightAccRight = 0;
						inRoom = 1;
						score += 1000;
					}
				}
				else {
					gameState = 3;
				}
			}
			else if (inRoom == 15)
			{
				if (ventLeft) {
					inRoom = 1;
					score += 500;
				}
				else {
					if(inRoom == eChowPos)
					{
						chowLoss = true;
					}
					else if(inRoom == batPos)
					{
						batLoss = true;
					}
					else if(inRoom == karelPos)
					{
						karelLoss = true;
					}
					gameState = 3;
				}
			}
			else if (inRoom == 16)
			{
				if (ventRight) {
					inRoom = 1;
					score += 500;
				}
				else {
					if(inRoom == eChowPos)
					{
						chowLoss = true;
					}
					else if(inRoom == batPos)
					{
						batLoss = true;
					}
					else if(inRoom == karelPos)
					{
						karelLoss = true;
					}
					gameState = 3;
				}
			}
		}
		if (eChowPos == inRoom || cHSPos == inRoom || karelPos == inRoom || batPos == inRoom) {
			//monsters cannot move if someone is in the room they want to go to
			return temp;
		}
		return inRoom;
	}
	
	// music control method
	public static void playMusic(String musicLocation)
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
	public static void userSaves() throws FileNotFoundException, IOException { 
		if (gameState == 0) {
			Scanner fileInput = new Scanner(new File("saves.txt")); 
			userState = fileInput.nextInt();
			gameTime = fileInput.nextInt();
			score = fileInput.nextInt();
			batPos = fileInput.nextInt();
			eChowPos = fileInput.nextInt();
			cHSPos = fileInput.nextInt();
			karelPos = fileInput.nextInt();
		}
		if (gameState == 2) {
			PrintWriter fileOutput = new PrintWriter(new FileWriter("saves.txt"));
			fileOutput.printf("%d%f%d%f%d%f%d%f%d%f%d%f%d%f", userState, gameTime, score, batPos, eChowPos, cHSPos, karelPos);
			fileOutput.close();
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
			if (instructions == 1) {
				g.drawImage(aboutPage,0,0,null);
			}
			if (about == 1) {
				g.drawImage(backStory,0,0,null);
			}
		}
		if (gameState == 1){
			if (userState == 1) {
				gameState = 2;
			}
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
				if(map != 0)
				{
					powerUse += powerConstant * 2;
				}
				if(doorLeft)
				{
					powerUse += powerConstant * 3;
				}
				if(doorRight)
				{
					powerUse += powerConstant * 3;
				}
				if(lightLeft)
				{
					powerUse += powerConstant * 5;
				}
				if(lightRight)
				{
					powerUse += powerConstant * 5;
				}
				if(powerUse % 50 == 0) {
					score -= 25;
				}
				if(powerUse > 500)
				{
					power--;
					powerUse = 0;
					score -= 10;
				}
				if(power < 0)
				{
					winLoss = 0;
					chowLoss = true;
					gameState = 3;
				}
				if (gameTime > d1Time && gameTime % d1Time == 0) {
					score+= 500;
				}
				if (gameTime % 2700 == 0) {
					hour++;
				}
				if(hour == 7)
				{
					gameState = 3;
					winLoss = 1;
				}
				if (night == 1)
				{
					if (difficulty == 1 && gameTime % d1Time == 0) {
						eChowPos = moveRoom(eChowPos);
					}
					if (difficulty == 2 && gameTime % (d1Time/2) == 0) {
						eChowPos = moveRoom(eChowPos);
					}
				}
				if (night == 2)
				{
					if (difficulty == 1 && gameTime % d1Time == 0) {
						karelPos = moveRoom(karelPos);
						eChowPos = moveRoom(eChowPos);
					}
					if (difficulty == 2 && gameTime % (d1Time/2) == 0) {
						karelPos = moveRoom(karelPos);
						eChowPos = moveRoom(eChowPos);
					}
				}
				if (night == 3)
				{
					if (difficulty == 1 && gameTime % d1Time == 0) {
						karelPos = moveRoom(karelPos);
						eChowPos = moveRoom(eChowPos);
						batPos = moveRoom(batPos);
						System.out.printf("%4d%4d%4d%n", karelPos,eChowPos,batPos);
					}
					if (difficulty == 2 && gameTime % (d1Time/2) == 0) {
						karelPos = moveRoom(karelPos);
						eChowPos = moveRoom(eChowPos);
						batPos = moveRoom(batPos);
					}
				}
				if (night == 4)
				{
					if (difficulty == 1 && gameTime % (d1Time*0.75) == 0) {
						karelPos = moveRoom(karelPos);
						eChowPos = moveRoom(eChowPos);
						batPos = moveRoom(batPos);
					}
					if (difficulty == 2 && gameTime % (d1Time*0.75/2) == 0) {
						karelPos = moveRoom(karelPos);
						eChowPos = moveRoom(eChowPos);
						batPos = moveRoom(batPos);
					}
				}
				if (night == 5)
				{
					if (difficulty == 1 && gameTime % d1Time*0.5 == 0) {
						karelPos = moveRoom(karelPos);
						eChowPos = moveRoom(eChowPos);
						batPos = moveRoom(batPos);
					}
					if (difficulty == 2 && gameTime % (d1Time*0.5/2) == 0) {
						karelPos = moveRoom(karelPos);
						eChowPos = moveRoom(eChowPos);
						batPos = moveRoom(batPos);
					}
				}
			
			// draw office
			if (map == 0) {
				g.drawImage(office,0,0,null);
				if(doorLeft)
				{
					g.drawImage(leftDoor,0,0,null);
				}	
				if(doorRight) {
					g.drawImage(rightDoor,0,0,null);
				}
				
				// light
				if(lightLeft)
				{
					if(eChowPos == 13)
					{
						g.drawImage(leftLEChow,0,0,null);
					}
					else if(batPos == 13)
					{
						g.drawImage(leftLBat,0,0,null);
					}
					else if(karelPos == 13)
					{
						g.drawImage(leftLKarel,0,0,null);
					}
					else
					{
						g.drawImage(leftLight,0,0,null);		
					}

				}
				if(lightRight)
				{
					if(eChowPos == 13)
					{
						g.drawImage(rightLEChow,0,0,null);
					}
					else if(batPos == 13)
					{
						g.drawImage(rightLBat,0,0,null);
					}
					else if(karelPos == 13)
					{
						g.drawImage(rightLKarel,0,0,null);
					}
					else
					{
						g.drawImage(rightLight,0,0,null);		
					}
				}
				//vent
				if(ventLeft)
				{
					g.drawImage(leftVent,0,0,null);
				}
				if(ventRight)
				{
					g.drawImage(rightVent,0,0,null);
				}
				//draw map and cams
			}else{
				if (cam == 0) {
					g.drawImage(nav,0,0,null);
				}
				else if (cam == 12) {
					if(cHSPos == 12)
					{
						try
						{
							File musicPath2 = new File("ambientHS.wav");
							if(musicPath2.exists())
							{
								AudioInputStream audioInput2 = AudioSystem.getAudioInputStream(musicPath2);
								sound = AudioSystem.getClip();
								sound.open(audioInput2);
								sound.start();
								sound.loop(Clip.LOOP_CONTINUOUSLY);
								ambientPlaying = true;
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
					else
					{
						if(ambientPlaying) {
							ambientPlaying = false;
							sound.stop();
						}
					}
					g.drawImage(cam9,0,0,null);	
				}
				else
				{
					for(int i = 0; i < cams.length; i++)
					{
						if(cam == i+1)
						{
							if(eChowPos == i+1)
							{
								g.drawImage(cams[i][1],0,0,null);
							}
							else if(karelPos == i+1)
							{
								g.drawImage(cams[i][2],0,0,null);
							}
							else if(batPos == i+1)
							{
								g.drawImage(cams[i][3],0,0,null);
							}
							else
							{
								g.drawImage(cams[i][0],0,0,null);
							}
						}
					}
				}
			}
		}
		if (gameState == 3) {
			if(winLoss == 0)
			{
				if (chowLoss)
				{
					playMusic("scare.wav");
					chowLoss = false;
					g.drawImage(chowScare,0,0,null);
				}
				if (batLoss)
				{
					playMusic("scare.wav");
					batLoss = false;
					g.drawImage(batScare,0,0,null);
				}
				if (karelLoss)
				{
					playMusic("scare.wav");
					karelLoss = false;
					g.drawImage(karelScare,0,0,null);
				}
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else
			{
				if (night + 1 > maxNight)
				{
					maxNight = night+1;	
				}
			}
			g.drawString("game state 3: win/loss screen", 300, 250);
			g.drawString("press 'e' to go to back to menu/ game state 1", 300, 300);
		}
	}
	
	public static void main(String [] args) throws FileNotFoundException{ 
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
			leftVent = ImageIO.read(new File("leftVent.png"));
			rightVent = ImageIO.read(new File("rightVent.png"));
			leftLight = ImageIO.read(new File("leftLight.png"));
			rightLight = ImageIO.read(new File("rightLight.png"));
			rightDoor = ImageIO.read(new File("doorRight.png"));
			leftDoor = ImageIO.read(new File("doorLeft.png"));
			backStory = ImageIO.read(new File("backStory.png"));
			aboutPage = ImageIO.read(new File("howToPlay.png"));
			
			cam1a[0] = ImageIO.read(new File("cam1a.png"));
			cam1a[1] = ImageIO.read(new File("echow1a.png"));
			cam1a[2] = ImageIO.read(new File("karel1a.png"));
			cam1a[3] = ImageIO.read(new File("bat1a.png"));
 
			cam1b[0] = ImageIO.read(new File("cam1b.png"));
			cam1b[1] = ImageIO.read(new File("echow1b.png"));
			cam1b[2] = ImageIO.read(new File("karel1b.png"));
			cam1b[3] = ImageIO.read(new File("bat1b.png"));


			cam2[0] = ImageIO.read(new File("cam2.png"));
			cam2[1] = ImageIO.read(new File("echow2.png"));
			cam2[2] = ImageIO.read(new File("karel2.png"));
			cam2[3] = ImageIO.read(new File("bat2.png"));

			cam3[0] = ImageIO.read(new File("cam3.png"));
			cam3[1] = ImageIO.read(new File("echow3.png"));
			cam3[2] = ImageIO.read(new File("karel3.png"));
			cam3[3] = ImageIO.read(new File("bat3.png"));

			cam4[0] = ImageIO.read(new File("cam4.png"));
			cam4[1] = ImageIO.read(new File("echow4.png"));
			cam4[2] = ImageIO.read(new File("karel4.png"));
			cam4[3] = ImageIO.read(new File("bat4.png"));

			cam5[0] = ImageIO.read(new File("cam5.png"));
			cam5[1] = ImageIO.read(new File("echow5.png"));
			cam5[2] = ImageIO.read(new File("karel5.png"));
			cam5[3] = ImageIO.read(new File("bat5.png"));

			cam6[0] = ImageIO.read(new File("cam6.png"));
			cam6[1] = ImageIO.read(new File("echow6.png"));
			cam6[2] = ImageIO.read(new File("karel6.png"));
			cam6[3] = ImageIO.read(new File("bat6.png"));

			cam7a[0] = ImageIO.read(new File("cam7a.png"));
			cam7b[0] = ImageIO.read(new File("cam7b.png"));
			cam7b[1] = ImageIO.read(new File("echow7b.png"));
			cam7a[1] = ImageIO.read(new File("echow7a.png"));
			cam7a[2] = ImageIO.read(new File("karel7a.png"));
			cam7b[2] = ImageIO.read(new File("karel7b.png"));
			cam7a[3] = ImageIO.read(new File("bat7a.png"));
			cam7b[3] = ImageIO.read(new File("bat7b.png"));
			
			cam8a[0] = ImageIO.read(new File("cam8a.png"));
			cam8b[0] = ImageIO.read(new File("cam8b.png"));
			cam8b[1] = ImageIO.read(new File("echow8b.png"));
			cam8a[1] = ImageIO.read(new File("echow8a.png"));
			cam8a[2] = ImageIO.read(new File("karel8a.png"));
			cam8b[2] = ImageIO.read(new File("karel8b.png"));
			cam8a[3] = ImageIO.read(new File("bat8a.png"));
			cam8b[3] = ImageIO.read(new File("bat8b.png"));

			cam9 = ImageIO.read(new File("audioRoom.png"));

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
			userState = 1;
			// resets position
			if (e.getKeyCode() == 27) {
				gameSettings = 1;
			}
			purpleX = 1218;
			purpleY = 460;
			if (map == 0 && cam == 0) {
				if (e.getKeyChar() == ' ') {
					map = 1;
				}
			}
			else {
				if (cam == 0) {
					map = 0;
				}
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
	public void mousePressed(MouseEvent e)  { 
		mousePosX = e.getX();
		mousePosY = e.getY();
		// for convenience, wherever you click on the screen, it prints out the mouse position
		System.out.printf("%d, %d%n", mousePosX, mousePosY);
		
		if (gameState == 0){
			if (settings == 0 && about == 0 && instructions == 0){
				// enter game 
				if (mousePosX > 126 && mousePosY > 436 && mousePosX < 299 && mousePosY < 495){
					gameState = 1;
					song.stop();
				}
				//about page
				if (mousePosX > 133 && mousePosY > 616 && mousePosX < 345 && mousePosY < 669){
					about = 1;
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
					lightAccMax = 5;
				}
				
				//difficulty hard
				if (mousePosX > 1049 && mousePosY > 513 && mousePosX < 1139 && mousePosY < 543){
					difficulty = 2;
					lightAccMax = 10;
				}
			}
			else if (about == 1) {
				if (mousePosX > 691 && mousePosY > 770 && mousePosX < 1002 && mousePosY < 835){
					about = 0;

				}
				if (mousePosX > 1407 && mousePosY > 444 && mousePosX < 1448 && mousePosY < 582){
					instructions = 1;
					about = 0;
				}
			}
			else if (instructions == 1) {
				if (mousePosX > 240 && mousePosY > 451 && mousePosX < 289 && mousePosY < 514){
					about = 1;
					instructions = 0;
				}
			}
		}
		
		// main game
		if (gameState == 2) {
			if (gameSettings == 1) {
				if (mousePosX > 44 && mousePosY > 49 && mousePosX < 107 && mousePosY < 113) {
					gameSettings = 0;
				}
			}
			if (cam == 0 && map == 1) {
				if (mousePosX > 44 && mousePosY > 49 && mousePosX < 107 && mousePosY < 113) {
					map = 0;
				}
			}
			//exit cams
			if (cam > 0) {
				if (mousePosX > 44 && mousePosY > 49 && mousePosX < 107 && mousePosY < 113) {
					//stops audio room music
					if(cam == 12)
					{
						if(ambientPlaying)
						{
							sound.stop();
						}
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
				if (mousePosX > 507 && mousePosY > 106 && mousePosX < 532 && mousePosY < 125) {
					cam = 1;
				}
				if (mousePosX > 884 && mousePosY > 418 && mousePosX < 913 && mousePosY < 436) {
					cam = 2;
				}
				if (mousePosX > 1186 && mousePosY > 328 && mousePosX < 1209 && mousePosY < 360) {
					cam = 3;
				}
				if (mousePosX > 487 && mousePosY > 582 && mousePosX < 506 && mousePosY < 615) {
					cam = 4;
				}
				if (mousePosX > 711 && mousePosY > 568 && mousePosX < 744 && mousePosY < 584) {
					cam = 5;
				}
				if (mousePosX > 975 && mousePosY > 444 && mousePosX < 1005 && mousePosY < 462) {
					cam = 6;
				}
				if (mousePosX > 542 && mousePosY > 818 && mousePosX < 578 && mousePosY < 840) {
					cam = 7;
				}
				if (mousePosX > 769 && mousePosY > 627 && mousePosX < 782 && mousePosY < 642) {
					cam = 8;
				}
				if (mousePosX > 742 && mousePosY > 802 && mousePosX < 752 && mousePosY < 819) {
					cam = 9;
				}
				if (mousePosX > 924 && mousePosY > 617 && mousePosX < 935 && mousePosY < 635) {
					cam = 10;
				}
				if (mousePosX > 960 && mousePosY > 789 && mousePosX < 974 && mousePosY < 808) {
					cam = 11;
				}
				if (mousePosX > 1005 && mousePosY > 604 && mousePosX < 1113 && mousePosY < 756) {
					cam = 12;
				}
			}
			else
			{
				//door
				if (mousePosX > 1500 && mousePosY > 55 && mousePosX < 1691 && mousePosY < 960) {
					if(!doorRight)
					{
						doorRight = true;
					}
					else
					{
						doorRight = false;
					}
				}
				if (mousePosX > 0 && mousePosY > 80 && mousePosX < 175 && mousePosY < 952) {
					if(!doorLeft)
					{
						doorLeft = true;
					}
					else
					{
						doorLeft = false;
					}
				}

				// light
				if (mousePosX > 267 && mousePosY > 200 && mousePosX < 388 && mousePosY < 639) {
					if(!lightLeft)
					{
						lightLeft = true;
						if(eChowPos == 13 || batPos == 13 || karelPos == 13)
						{
							lightAccLeft += 1;	
							System.out.print(lightAccLeft);
						}
					}
					else
					{
						lightLeft = false;
					}
				}
				if (mousePosX > 1277 && mousePosY > 219 && mousePosX < 1398 && mousePosY < 683) {
					if(!lightRight)
					{
						lightRight = true;
						if(eChowPos == 14 || batPos == 14 || karelPos == 14)
						{
							lightAccRight += 1;
							System.out.print(lightAccRight);
						}
					}
					else
					{
						lightRight = false;
					}
				}

				// vent
				if (mousePosX > 1294 && mousePosY > 717 && mousePosX < 1430 && mousePosY < 941) {
					if(!ventRight)
					{
						ventRight = true;
					}
					else
					{
						ventRight = false;
					}
				}

				if (mousePosX > 192 && mousePosY > 757 && mousePosX < 335 && mousePosY < 908) {
					if(!ventLeft)
					{
						ventLeft = true;
					}
					else
					{
						ventLeft = false;
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
