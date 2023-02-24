import java.awt.Graphics2D;

public class UtilityTool {

	public static int XForCenteredText(Graphics2D g2D, String text) {
		return ((Game.WINDOW_WIDTH/2) - (((int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth())/2));
	}
}
