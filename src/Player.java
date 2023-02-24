import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player {
	private int playerWidth;
	private int playerHeight;
	public int x, y;
	public Rectangle playerRectangle;
	
	public int playerCenteredX;
	
	public static int PLAYER_HEALTH = 4;
	
	public static boolean inAir = false;
	public static boolean obstacleOnRight = false;
	public static boolean obstacleOnLeft = false;
	public static boolean obstacleOnTop = false;
	// MOVE FORCES
	
	public static int ForceRight = 0;
	public static int ForceLeft = 0;
	public static int ForceUp = 0;
	
	private static final int MOVE_FORCE = 7;
	private static final int JUMP_FORCE = 36;
	public static final int GRAVITY_FORCE = 11;
	
	public Player() {
		inAir = false;
		obstacleOnRight = false;
		obstacleOnLeft = false;
		obstacleOnTop = false;
		playerWidth = Game.TILE_WIDTH;
		playerHeight = Game.TILE_HEIGHT;
		x = Game.TILE_WIDTH;
		y = Game.GROUND_LEVEL - playerHeight;
		playerRectangle = new Rectangle(x, y, playerWidth, playerHeight);
		
		playerCenteredX = (Game.WINDOW_WIDTH / 2) - (playerWidth / 2);
		PLAYER_HEALTH = 4;
		ForceRight = 0;
		ForceLeft = 0;
		ForceUp = 0;
	}
	
	private void moveRight() {
		ForceRight += MOVE_FORCE;
		if (ForceRight > MOVE_FORCE) {
			ForceRight = MOVE_FORCE;
		}
	}
	private void moveLeft() {
		ForceLeft += MOVE_FORCE;
		if (ForceLeft > MOVE_FORCE) {
			ForceLeft = MOVE_FORCE;
		}
	}
	public static void jump() {
		if (!inAir) {
			ForceUp += JUMP_FORCE;
			inAir = true;
		}
	}
	public void update() {
		if (KeyboardHandler.leftPressed) {
			moveLeft();
		}
		if (KeyboardHandler.rightPressed) {
			moveRight();
		}
		
		// Simple gravity
		if (inAir) {
			y += GRAVITY_FORCE;
		}
		// Checks collisions
		
		if (!obstacleOnTop) {
			if (ForceUp != 0) {
				y -= ForceUp;
				ForceUp--;
			}
		} else {
			ForceUp = 0;
		}
		
		if (!obstacleOnLeft) {
			if (ForceLeft != 0) {
				if (Map.mapX > 0) {
					Map.mapX -= ForceLeft;
					if (Map.mapX <= 0) {
						Map.mapX = 0;
					}
				} else {
					if (x-ForceLeft >= 0) {
						x -= ForceLeft;
					}
				}
				ForceLeft--;
			}
		} else {
			ForceLeft = 0;
		}
		if (!obstacleOnRight) {
			if (ForceRight != 0) {
				if (x == playerCenteredX) {
					Map.mapX += ForceRight;
					ForceRight--;
				} else {
					x += ForceRight;
					if (x >= playerCenteredX) {
						x = playerCenteredX;
					}
					ForceRight--;
					
				}
			}
		} else {
			ForceRight = 0;
		}
		
		playerRectangle.x = x;
		playerRectangle.y = y;
	}
	public void draw(Graphics2D g2D) {
		if (GameState.toRight) {
			g2D.drawImage(Game.santaImg, playerRectangle.x, playerRectangle.y, playerRectangle.width, playerRectangle.height, null);
		} else {
			g2D.drawImage(Game.santa2Img, playerRectangle.x, playerRectangle.y, playerRectangle.width, playerRectangle.height, null);
		}
	}
	
	public void reset() {
		inAir = false;
		obstacleOnRight = false;
		obstacleOnLeft = false;
		obstacleOnTop = false;
		playerWidth = Game.TILE_WIDTH;
		playerHeight = Game.TILE_HEIGHT;
		x = Game.TILE_WIDTH;
		y = Game.GROUND_LEVEL - playerHeight;
		playerRectangle = new Rectangle(x, y, playerWidth, playerHeight);
		
		playerCenteredX = (Game.WINDOW_WIDTH / 2) - (playerWidth / 2);
		PLAYER_HEALTH = 4;
		ForceRight = 0;
		ForceLeft = 0;
		ForceUp = 0;
	}
}
