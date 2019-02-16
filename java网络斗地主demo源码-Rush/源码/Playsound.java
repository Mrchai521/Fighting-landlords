package game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Playsound {
	Playsound(){
	}
	public static void play(String filename) {
		try {
			InputStream in = new FileInputStream(filename);
			AudioStream bgmusic = new AudioStream(in);
			AudioPlayer.player.start(bgmusic);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
