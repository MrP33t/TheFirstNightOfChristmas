import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// SCREEN SIZE
	public static final int WINDOW_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int WINDOW_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	// PROGRAM DRAWING TILE DIMENSION
	public static int TILE_WIDTH = WINDOW_WIDTH / 16;
	public static int TILE_HEIGHT = WINDOW_HEIGHT / 9;
	
	// MAIN PROGRAM STATES
	public static int PROGRAM_STATE = 0;
	public static final int MENU_STATE = 0;
	public static final int GAME_STATE = 1;
	// STATES HANDLERS
	private MenuState menuState;
	private GameState gameState;
	
	// FPS AND PROGRAM MAIN THREAD
	private Thread programThread;
	public final static int FPS = 60;
	public final static double drawInterval = 1000000000 / FPS;
	
	// INPUT HANDLERS
	private KeyboardHandler keyH = new KeyboardHandler();
	
	public static Audio audio = new Audio();
	
	
	// GROUND LEVEL 
	public static final int GROUND_LEVEL = TILE_HEIGHT * 7;
	
	// Player
	private Player player;
	
	// Map
	private Map map;
	
	// Images 
	public static BufferedImage snowBallImg, snowManImg, roofImg, chimneyImg, santaImg, santa2Img, hearthImg, snowFlakeImg, titleImg;
	
	public static Color c1 = new Color(1, 11, 28);
	
	// Path for saving HighScore
	private final static String dataPath = "hs";
	
	public static HighScore highScore;
	
	// Constructor
	public Game() {
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setDoubleBuffered(true);
		this.setBackground(c1);
		this.setFocusable(true);
		
		player = new Player();
		map = new Map();
		
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		
		try {
			snowBallImg = ImageIO.read(getClass().getResourceAsStream("/res/SnowBall.png"));
			snowManImg = ImageIO.read(getClass().getResourceAsStream("/res/SnowMan.png"));
			roofImg = ImageIO.read(getClass().getResourceAsStream("/res/Roof.png"));
			chimneyImg = ImageIO.read(getClass().getResourceAsStream("/res/Chimney.png"));
			santaImg = ImageIO.read(getClass().getResourceAsStream("/res/Santa.png"));
			hearthImg = ImageIO.read(getClass().getResourceAsStream("/res/Hearth.png"));
			santa2Img = ImageIO.read(getClass().getResourceAsStream("/res/Santa2.png"));
			snowFlakeImg = ImageIO.read(getClass().getResourceAsStream("/res/SnowFlake.png"));
			titleImg = ImageIO.read(getClass().getResourceAsStream("/res/Title.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loadData();
		
		this.setCursor(blankCursor);
		this.addKeyListener(keyH);
		
		// Starting main thread
		this.programThread = new Thread(this);
		this.programThread.start();
		
		audio.playMusic(0);
	}
	
	// Load data
	public static void loadData() {
		try {
			FileInputStream fis = new FileInputStream(dataPath);
	        ObjectInputStream ois = new ObjectInputStream(fis);

	        highScore = (HighScore) ois.readObject();
	        
	        ois.close();
	        fis.close();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Data could not be loaded");
			highScore = new HighScore(0);
		}
	}
	public static void saveData() {
		if (highScore != null) {
			if (highScore.highScore < GameState.SCORE) {
				highScore.highScore = GameState.SCORE;
				try {
					FileOutputStream fos = new FileOutputStream(dataPath);
			        ObjectOutputStream oos = new ObjectOutputStream(fos);

			        oos.writeObject(highScore);
			        
			        oos.close();
			        fos.close();
				} catch (IOException e) {
					System.out.println("Saving data error");
				}
			}
		}
	}
	// Update method 
	public void update() {
		if (PROGRAM_STATE == MENU_STATE) {
			if(menuState == null) {
				menuState = new MenuState();
			}
		}
		if (PROGRAM_STATE == GAME_STATE) {
			if (gameState == null) {
				gameState = new GameState(player, map);
				map.startMapThread();
			}
			gameState.update();
		}
	}
	
	// Paint method
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		if (PROGRAM_STATE == MENU_STATE) {
			menuState.draw(g2D);
		}
		if (PROGRAM_STATE == GAME_STATE) {
			if (gameState != null) {
				gameState.draw(g2D);
			}
		}
	}
	
	
	// Main thread method
	@Override
	public void run() {
		double delta = 0;
		long lastTime = System.nanoTime();
		long timer = 0;
		long currentTime;
		
		while(programThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
			
			if (timer >= 1000000000) {
				timer = 0;
			}
		}
	}

}
