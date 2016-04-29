package rpg_simulator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class SoundPlayer {
	
	private AudioStream audioStream, swordClash;
	private long time = System.currentTimeMillis();
	
	public SoundPlayer() throws IOException {
		audioStream = new AudioStream(new FileInputStream("src/rpg_simulator/res/rpg_music.wav"));
	}

	public void fightSound() throws IOException{
		
		if(time + 2000 <= System.currentTimeMillis()) {
			int nextClip = ThreadLocalRandom.current().nextInt(0,4);
			if (nextClip == 0) swordClash = new AudioStream(new FileInputStream("src/rpg_simulator/res/swordsound1.wav"));
			if (nextClip == 1) swordClash = new AudioStream(new FileInputStream("src/rpg_simulator/res/swordsound2.wav"));
			if (nextClip == 2) swordClash = new AudioStream(new FileInputStream("src/rpg_simulator/res/swordsound3.wav"));
			if (nextClip == 3) swordClash = new AudioStream(new FileInputStream("src/rpg_simulator/res/swordsound4.wav"));
		    time = System.currentTimeMillis();
		}
		
		if (Game.musicOn) {
		    AudioPlayer.player.start(swordClash);
		}
	}
	
	public void musicPlayer(){
		if (Game.musicOn) {
		    AudioPlayer.player.start(audioStream);
		} else {
			AudioPlayer.player.stop(audioStream);
		}
	}
}
