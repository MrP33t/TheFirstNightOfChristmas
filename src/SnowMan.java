import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SnowMan {

	private Rectangle bR;
	public Rectangle r;
	
	private boolean canThrow = true;
	private int throwTimer = 0;
	
	public boolean toRemove = false;
	private boolean goesRight = true;
	private static int snowManSpeed = 4;
	private int snowManPath;
	private int lengthOfPath;
	private final int snowManY = Game.GROUND_LEVEL - Game.TILE_HEIGHT;
	private final int snowManWidth = Game.TILE_WIDTH;
	private final int snowManHeight = Game.TILE_HEIGHT;
	
	public SnowMan(int x, int path) {
		this.bR = new Rectangle(x, snowManY, snowManWidth, snowManHeight);
		this.r = new Rectangle(x, snowManY, snowManWidth, snowManHeight);
		this.snowManPath = path;
		this.lengthOfPath = path;
	}
	
	public void update() {
		if (canThrow) {
			if (this.goesRight) {
				Map.snowBalls.add(new SnowBall(this.r.x + this.r.width, this.r.y, this.goesRight, false));
			} else {
				Map.snowBalls.add(new SnowBall(this.r.x, this.r.y, this.goesRight, false));
			}
			canThrow = false;
		}
		if (!canThrow) {
			if (throwTimer > 160) {
				canThrow = true;
				throwTimer = 0;
			} else {
				throwTimer++;
			}
		}
		if (this.goesRight) {
			this.bR.x += snowManSpeed;
			this.snowManPath -= snowManSpeed;
			if (this.snowManPath - snowManSpeed <= 0) {
				this.goesRight = false;
			}
		} else {
			this.bR.x -= snowManSpeed;
			this.snowManPath += snowManSpeed;
			if (this.snowManPath + snowManSpeed >= this.lengthOfPath) {
				this.goesRight = true;
			}
		}
		this.r.x = this.bR.x - Map.mapX;
	}
	
	public void draw(Graphics2D g2D) {
		g2D.drawImage(Game.snowManImg, r.x, r.y, r.width, r.height, null);
	}
	
}
