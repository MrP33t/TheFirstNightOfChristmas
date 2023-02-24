import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class GameState {
	private Player player;
	private Map map;
	
	public static int INGAME_STATE = 0;
	public static final int INGAME_GAME = 0;
	public static final int INGAME_OVER = 1;
	
	public static int SCORE = 0;
	
	public static boolean playerCanThrowSnowball = true;
	public static int playerSnowballTimer = 0;
	
	public static boolean canGivePresent = false;
	
	public static boolean toRight = true;
	
	public static boolean displayHint1 = false;
	
	public GameState(Player p, Map m) {
		INGAME_STATE = 0;
		SCORE = 0;
		playerCanThrowSnowball = true;
		playerSnowballTimer = 0;
		canGivePresent = false;
		toRight = true;
		this.player = p;
		this.map = m;
	}
	public void throwSnowball(Rectangle r, boolean p, boolean goesToRight) {
		if (playerCanThrowSnowball) {
			Map.snowBalls.add(new SnowBall(r.x, r.y + (Game.TILE_WIDTH / 3), goesToRight, true));
			playerCanThrowSnowball = false;
		}
	}
	public void update() {
		if (!playerCanThrowSnowball) {
			playerSnowballTimer++;
		}
		if (playerSnowballTimer > 60) {
			playerSnowballTimer = 0;
			playerCanThrowSnowball = true;
		}
		if (INGAME_STATE == INGAME_GAME) {
			if (KeyboardHandler.rightPressed) {
				toRight = true;
			}
			if (KeyboardHandler.leftPressed) {
				toRight = false;
			}
			if (KeyboardHandler.ePressed) {
				throwSnowball(player.playerRectangle, true, toRight);
			}
			map.update();
			checkCollisions();
			player.update();
			if (player.y >= Game.WINDOW_HEIGHT) {
				Game.saveData();
				INGAME_STATE = INGAME_OVER;
				player.reset();
				map.reset();
			}
		} 
	}
	private void checkCollisions() {
		Rectangle p = player.playerRectangle;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		
		for (Obstacle o: map.obstacles) {
			Rectangle r = o.rectangle;
			// Check overall collision
			if (p.x - Player.ForceLeft <= r.x + r.width &&
				p.x + p.width + Player.ForceRight >= r.x &&
				p.y - Player.ForceUp <= r.y + r.height &&
				p.y + p.height + (Player.GRAVITY_FORCE - Player.ForceUp) >= r.y) {
				
				// Check obstacle top collision
				if (p.y + p.height  + (Player.GRAVITY_FORCE - Player.ForceUp) >= r.y && p.y + p.height <= r.y + (Player.GRAVITY_FORCE - Player.ForceUp)) {
					Player.inAir = false;
					flag1 = true;
					player.y = r.y - p.height;
					if(o.type == Obstacle.chimney) {
						if (!o.gavePresent) {
							displayHint1 = true;
							if (KeyboardHandler.enterPressed) {
								o.gavePresent = true;
								SCORE++;
							}
						} else {
							displayHint1 = false;
						}
					} else {
						displayHint1 = false;
					}
				}
				// Check obstacle left collision
				if (p.x + p.width + Player.ForceRight >= r.x && p.x + p.width <= r.x + Player.ForceRight) {
					Player.obstacleOnRight = true;
					flag2 = true;
					player.x = r.x - p.width;
				}
				// Check obstacle right collision
				if (p.x - Player.ForceLeft <= r.x + r.width && p.x >= r.x + r.width - Player.ForceLeft) {
					Player.obstacleOnLeft = true;
					flag3 = true;
					player.x = r.x + r.width;
				}
				// Check obstacle bottom collision
				if (p.y - Player.ForceUp <= r.y + r.height && p.y >= r.y + r.height - Player.ForceUp) {
					Player.obstacleOnTop = true;
					flag4 = true;
					player.y = r.y + r.height;
				}
			}	
		}
		if (!flag1) {
			Player.inAir = true;
		}
		if (!flag2) {
			Player.obstacleOnRight = false;
		}
		if (!flag3) {
			Player.obstacleOnLeft = false;
		}
		if (!flag4) {
			Player.obstacleOnTop = false;
		}
		// Collisions of snowballs
		
		for (SnowBall s: Map.snowBalls) {
			for (SnowMan m: map.snowMans) {
				if (s.r.intersects(m.r)) {
					if (s.belongsToPlayer) {
						s.toRemove = true;
						m.toRemove = true;
					}
				}
			}
			if (!s.belongsToPlayer) {
				if (s.r.intersects(player.playerRectangle)) {
					s.toRemove = true;
					Player.PLAYER_HEALTH--;
					if (Player.PLAYER_HEALTH <= 0) {
						Game.saveData();
						INGAME_STATE = INGAME_OVER;
						player.reset();
						map.reset();
					}
				}
			}
		}
	}
	public void draw(Graphics2D g2D) {
		if (INGAME_STATE == INGAME_GAME) {
			map.draw(g2D);
			player.draw(g2D);
		}
		if (INGAME_STATE == INGAME_OVER) {
			g2D.setColor(Color.RED);
			g2D.setFont(Map.ARIAL_30);
			g2D.drawString("GAME OVER", UtilityTool.XForCenteredText(g2D, "GAME OVER"), 500);
			g2D.setColor(Map.gold);
			g2D.drawString("YOUR SCORE: " + SCORE, UtilityTool.XForCenteredText(g2D, "YOUR SCORE: " + SCORE), 700);
			g2D.drawString("HIGHSCORE: " + Game.highScore.highScore, UtilityTool.XForCenteredText(g2D, "HIGHSCORE: " + Game.highScore.highScore), 900);
		}
	}
	public static void reset() {
		INGAME_STATE = INGAME_GAME;
	}
}
