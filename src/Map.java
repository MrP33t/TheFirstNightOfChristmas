import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class Map implements Runnable{
	
	public static int mapX = 0;
	public static int endOfLastObstacle = 0;
	Random rand = new Random();
	private final static double drawInterval = 1000000000 / 40;
	private final int roofHeight = 300;
	private final int chimneyY = Game.GROUND_LEVEL - Game.TILE_HEIGHT;
	private final int chimneyHeight = Game.TILE_HEIGHT * 2;
	private final int chimneyWidth = Game.TILE_WIDTH;
	
	
	public static final Color gold = new Color(255, 187, 0);
	public static final Font ARIAL_30 = new Font("Arial", Font.BOLD, 30);
	Thread mapThread;
	public ArrayList<Obstacle> obstacles;
	public static ArrayList<SnowBall> snowBalls;
	public ArrayList<SnowMan> snowMans;
	
	private BackgroundHandler bh;
	public Map() {
		this.obstacles = new ArrayList<Obstacle>();
		snowBalls = new ArrayList<SnowBall>();
		this.snowMans = new ArrayList<SnowMan>();
		
		this.obstacles.clear();
		snowBalls.clear();
		this.snowMans.clear();
		
		mapX = 0;
		endOfLastObstacle = 0;

		if (this.bh == null) {
			this.bh = new BackgroundHandler();
		}
		this.obstacles.add(new Obstacle(0, Game.GROUND_LEVEL, 800, roofHeight, Obstacle.roof));
		this.obstacles.add(new Obstacle(1200, Game.GROUND_LEVEL, 700, roofHeight, Obstacle.roof));
		
		endOfLastObstacle = obstacles.get(obstacles.size() - 1).rectangle.x + obstacles.get(obstacles.size() - 1).rectangle.width;
		
	}
	public void startMapThread() {
		if (mapThread == null) {
			mapThread = new Thread(this);
			mapThread.start();
		}
	}
	public void update() {
		// Error of concurrent modification is not Important here, to fix it there is need to rewrite whole update and draw process for whole program
		for(Obstacle o: obstacles) {
			o.update();
		}
		for(SnowBall s: snowBalls) {
			s.update();
		}
		for(SnowMan s: snowMans) {
			s.update();
		}
		for(Obstacle o: obstacles) {
			if (o.toRemove) {
				obstacles.remove(o);
				break;
			}
		}
		for(SnowMan m: snowMans) {
			if (m.toRemove) {
				snowMans.remove(m);
				break;
			}
		}
		for(SnowBall s: snowBalls) {
			if (s.toRemove) {
				snowBalls.remove(s);
				break;
			}
		}
	}
	
	
	public void draw(Graphics2D g2D) {
		bh.draw(g2D);
		
		int x = 80;
		for (int i = 1; i <= Player.PLAYER_HEALTH; i++) {
			g2D.drawImage(Game.hearthImg, x * i, 100, 70, 70, null);
		}
		g2D.setColor(gold);
		g2D.setFont(ARIAL_30);
		
		g2D.drawString("Delivered Presents: " + GameState.SCORE, 1200, 150);
		
		if (GameState.displayHint1) {
			g2D.drawString("Use 'ENTER' to deliver present", UtilityTool.XForCenteredText(g2D, "Use 'ENTER' to deliver present"), Game.WINDOW_HEIGHT / 2);
		}
		for(Obstacle o: obstacles) {
			o.draw(g2D);
		}
		for(SnowBall s: snowBalls) {
			s.draw(g2D);
		}
		for(SnowMan s: snowMans) {
			s.draw(g2D);
		}
	}
	@Override
	public void run() {
		double delta = 0;
		long lastTime = System.nanoTime();
		long timer = 0;
		long currentTime;
		
		while(mapThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				threadUpdate();
				delta--;
			}
			
			if (timer >= 1000000000) {
				timer = 0;
			}
		}
	}
	
	private void threadUpdate() {
		bh.update();
		int a = mapX + Game.WINDOW_WIDTH;
		if (a > endOfLastObstacle) {
			int n = rand.nextInt(200) + 200;
			int x = endOfLastObstacle + n;
			int width = rand.nextInt(800) + 500;
			this.obstacles.add(new Obstacle(x, Game.GROUND_LEVEL, width, roofHeight, Obstacle.roof));
			int chimneyX = x + rand.nextInt(width - chimneyWidth - 50) + 50;
			obstacles.add(new Obstacle(chimneyX, chimneyY, chimneyWidth, chimneyHeight, Obstacle.chimney));
			if (chimneyX - x > Game.TILE_WIDTH * 2) {
				snowMans.add(new SnowMan(x, chimneyX - x - chimneyWidth));
			}
			endOfLastObstacle = x + width;
		}
	}
	
	public void reset() {
		this.obstacles = new ArrayList<Obstacle>();
		snowBalls = new ArrayList<SnowBall>();
		this.snowMans = new ArrayList<SnowMan>();
		
		this.obstacles.clear();
		snowBalls.clear();
		this.snowMans.clear();
		
		mapX = 0;
		endOfLastObstacle = 0;

		if (this.bh == null) {
			this.bh = new BackgroundHandler();
		}
		this.obstacles.add(new Obstacle(0, Game.GROUND_LEVEL, 800, roofHeight, Obstacle.roof));
		this.obstacles.add(new Obstacle(1200, Game.GROUND_LEVEL, 700, roofHeight, Obstacle.roof));
		
		endOfLastObstacle = obstacles.get(obstacles.size() - 1).rectangle.x + obstacles.get(obstacles.size() - 1).rectangle.width;
		
	}
}
