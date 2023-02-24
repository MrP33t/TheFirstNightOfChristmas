import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Obstacle {
	private Rectangle basicRectangle;
	public Rectangle rectangle;
	public int type = 0;
	public final static int roof = 0;
	public final static int chimney = 1;
	
	public boolean gavePresent = false;
	public boolean toRemove = false;
	
	public Obstacle(int x, int y, int width, int height, int type) {
		this.basicRectangle = new Rectangle(x, y, width, height);
		this.rectangle = new Rectangle(x, y, width, height);
		this.type = type;
	}
	
	public void update() {
		this.rectangle.x = this.basicRectangle.x - Map.mapX;
		
		if (this.rectangle.x + this.rectangle.width < 0 - Game.WINDOW_WIDTH) {
			this.toRemove = true;
		}
	}
	public void draw(Graphics2D g2D) {
		if (this.type == roof) {
			g2D.drawImage(Game.roofImg, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
		}
		if (this.type == chimney) {
			g2D.drawImage(Game.chimneyImg, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
		}
	}
}
