import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SnowBall {
	Rectangle r, bR;
	boolean toRight;
	int snowballSpeed = 8;
	public boolean toRemove = false;
	public boolean belongsToPlayer = false;
	
	public SnowBall(int x, int y, boolean toRight, boolean belongsToPlayer) {
		if (toRight) {
			this.bR = new Rectangle(x + (Game.TILE_WIDTH / 4), y, Game.TILE_HEIGHT / 4, Game.TILE_HEIGHT / 4);
		} else {
			this.bR = new Rectangle(x - (Game.TILE_WIDTH / 2), y, Game.TILE_HEIGHT / 4, Game.TILE_HEIGHT / 4);
		}
		this.r = this.bR;
		this.toRight = toRight;
		this.belongsToPlayer = belongsToPlayer;
	}
	
	public void update() {
		if (this.toRight) {
			if (Map.mapX == 0) {
				this.r.x += snowballSpeed;
			} else {
				this.r.x += (snowballSpeed - Player.ForceRight + Player.ForceLeft);
			}
		} else {
			if (Map.mapX == 0) {
				this.r.x -= snowballSpeed;
			} else {
				this.r.x -= (snowballSpeed + Player.ForceRight - Player.ForceLeft);
			}
		}
		if (this.r.x < 0 || this.r.x >= (Map.mapX + Game.WINDOW_WIDTH)) {
			this.toRemove = true;
		}
		
	}
	public void draw(Graphics2D g2D) {
		g2D.drawImage(Game.snowBallImg, r.x, r.y, r.width, r.height, null);
	}
}
