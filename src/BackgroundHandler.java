import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class BackgroundHandler implements Runnable {

	Random rand = new Random();

	private ArrayList<SnowFlake> snowFlakes;
	
	Thread bgThread;
	
	public BackgroundHandler() {
		if (snowFlakes == null) {
			snowFlakes = new ArrayList<SnowFlake>();
		}
		snowFlakes.clear();
		if (bgThread != null) {
			bgThread = null;
		}
		bgThread = new Thread(this);
		bgThread.start();
	}
	public void draw(Graphics2D g2D) {
		for (SnowFlake s: this.snowFlakes) {
			s.draw(g2D);
		}
	}
	
	public void update() {
		for (SnowFlake s: snowFlakes) {
			s.update();
		}
	}
	@Override
	public void run() {
		double delta = 0;
		long lastTime = System.nanoTime();
		long timer = 0;
		long currentTime;
		int snowCounter = 0;
		int numOfSnow = 0;
		
		while(true) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / Game.drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				// Create snowFlake
				snowCounter++;
				delta--;
			}
			
			if (timer >= 1000000000) {
				timer = 0;
			}
			
			if (snowCounter >= 60) {
				snowFlakes.add(new SnowFlake());
				snowCounter = 0;
				numOfSnow++;
			}
			
			if (numOfSnow >= 120) {
				break;
			}
		}
	}
	
	public class SnowFlake {
		int x;
		int y = 0;
		int width = Game.TILE_WIDTH / 6;
		int height = Game.TILE_HEIGHT / 6;
		int snowFlakeSpeed;
		
		public SnowFlake() {
			this.x = rand.nextInt(Game.WINDOW_WIDTH);
			this.snowFlakeSpeed = rand.nextInt(3) + 1;
		}
		
		public void update() {
			this.y += snowFlakeSpeed;
			if (this.y >= Game.WINDOW_HEIGHT) {
				this.y = 0;
				this.x = rand.nextInt(Game.WINDOW_WIDTH);
			}
		}
		
		public void draw(Graphics2D g2D) {
			g2D.drawImage(Game.snowFlakeImg, this.x, this.y, this.width, this.height, null);
		}
	}

}
