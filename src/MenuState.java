import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MenuState {
	
	// MAIN MENU OPTIONS
	public static int OPTION = 0;
	
	public static final int START_OPTION = 0;
	public static final int MUSIC_OPTION = 1;
	public static final int EXIT_OPTION = 2;
	
	public static final int MAIN_MENU_OPTIONS_NUMBER = 3;
	
	public static final Color hover = new Color(50, 168, 82);
	
	
	// FONTS
	public static Font ARIAL_40 = new Font("Arial", Font.BOLD, 40);

	public MenuState() {
	}
	public static void select() {
		switch (OPTION) {
		case START_OPTION:
			Game.PROGRAM_STATE = Game.GAME_STATE;
			GameState.INGAME_STATE = GameState.INGAME_GAME;
			break;
		case MUSIC_OPTION:
			toggleMusic();
			break;
		case EXIT_OPTION:
			System.exit(0);
			break;
		}
	}
	private static void toggleMusic() {
		if (Game.audio.plays) {
			Game.audio.stopMusic();
		} else {
			Game.audio.playMusic(0);
		}
	}
	public static void mainMenuOptionsDown() {
		OPTION++;
		if (OPTION >= MAIN_MENU_OPTIONS_NUMBER) {
			OPTION = 0;
		}
	}
	public static void mainMenuOptionsUp() {
		OPTION--;
		if (OPTION < 0) {
			OPTION = MAIN_MENU_OPTIONS_NUMBER - 1;
		}
	}
	
	public void draw(Graphics2D g2D) {
		String text;
		int x, y;
		g2D.setFont(ARIAL_40);
		g2D.setColor(Color.WHITE);
		text = "Menu";
		x = UtilityTool.XForCenteredText(g2D, text);
		y = Game.TILE_HEIGHT * 2;
		g2D.drawImage(Game.titleImg, Game.WINDOW_WIDTH / 6, Game.TILE_HEIGHT, (Game.WINDOW_WIDTH / 3) * 2, (int) (Game.TILE_HEIGHT * 1.5), null);
		
		// DRAWING OPTIONS
		if (OPTION == START_OPTION) {
			g2D.setColor(hover);
			text = ">START";
		} else {
			text = "START";
			g2D.setColor(Color.WHITE);
		}
		x = UtilityTool.XForCenteredText(g2D, text);
		y = Game.TILE_HEIGHT * 4;
		g2D.drawString(text, x, y);
		
		if (OPTION == MUSIC_OPTION) {
			g2D.setColor(hover);
			if (Game.audio.plays) {
				text = ">MUSIC (ON)";
			} else {
				text = ">MUSIC (OFF)";
			}
		} else {
			g2D.setColor(Color.WHITE);
			if (Game.audio.plays) {
				text = "MUSIC (ON)";
			} else {
				text = "MUSIC (OFF)";
			}
		}
		x = UtilityTool.XForCenteredText(g2D, text);
		y = Game.TILE_HEIGHT * 5;
		g2D.drawString(text, x, y);
		
		if (OPTION == EXIT_OPTION) {
			g2D.setColor(hover);
			text = ">EXIT";
		} else {
			g2D.setColor(Color.WHITE);
			text = "EXIT";
		}
		x = UtilityTool.XForCenteredText(g2D, text);
		y = Game.TILE_HEIGHT * 6;
		g2D.drawString(text, x, y);
	}
}
