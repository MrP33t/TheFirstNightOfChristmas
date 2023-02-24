import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Audio {
	
	Clip clip;
	ArrayList<URL> audioURL = new ArrayList<URL>();
	
	boolean plays;
	
	public Audio() {
		audioURL.add(getClass().getResource("/res/music1.wav"));
	}
	
	public void setAudio(int x) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(audioURL.get(x));
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		clip.start();
		this.plays = true;
	}
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
	    gainControl.setValue(20f * (float) Math.log10(0.1));
	}
	public void stop() {
		clip.stop();
		this.plays = false;
	}
	
	public void playMusic(int x) {
		this.setAudio(x);
		this.play();
		this.loop();
	}
	public void stopMusic() {
		this.stop();
	}
	public void playSE(int x) {
		this.setAudio(x);
		this.play();
	}
}
