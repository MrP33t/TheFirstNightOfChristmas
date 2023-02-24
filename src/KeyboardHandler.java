import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener{

	public static boolean leftPressed = false;
	public static boolean rightPressed = false;
	public static boolean ePressed = false;
	public static boolean enterPressed = false;
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (Game.PROGRAM_STATE) {
		case Game.MENU_STATE:
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				MenuState.mainMenuOptionsUp();
				break;
			case KeyEvent.VK_DOWN:
				MenuState.mainMenuOptionsDown();
				break;
			case KeyEvent.VK_ENTER:
				MenuState.select();
				break;
			}
			break;
		case Game.GAME_STATE:
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				leftPressed = true;
				break;
			case KeyEvent.VK_D:
				rightPressed = true;
				break;
			case KeyEvent.VK_SPACE:
				Player.jump();
				break;
			case KeyEvent.VK_E:
				ePressed = true;
				break;
			case KeyEvent.VK_ENTER:
				if (GameState.INGAME_STATE == GameState.INGAME_OVER) {
					Game.PROGRAM_STATE = Game.MENU_STATE;
					GameState.SCORE = 0;
					break;
				}
				enterPressed = true;
				break;
			}
			
			break;
		}
		if (e.getKeyCode() == KeyEvent.VK_F10) {
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (Game.PROGRAM_STATE) {
		case Game.MENU_STATE:
			break;
		case Game.GAME_STATE:
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				leftPressed = false;
				break;
			case KeyEvent.VK_D:
				rightPressed = false;
				break;
			case KeyEvent.VK_E:
				ePressed = false;
				break;
			case KeyEvent.VK_ENTER:
				enterPressed = false;
				break;
			}
			break;
		}
	}

}
