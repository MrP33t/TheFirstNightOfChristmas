

import java.io.Serializable;

public class HighScore implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int highScore;
	
	public HighScore(int x) {
		this.highScore = x;
	}
}
