package mp.commands;

import mp.graphics.BridgeScene;
import util.annotations.Tags;

@Tags("SayCommand")
public class SayCommand implements Runnable {
	BridgeScene scene;
	String quote;
	public SayCommand(BridgeScene inputScene, String inputQuote) {
		scene=inputScene;
		quote=inputQuote;
	}
	public void run() {
		scene.say(quote);
	}
}
