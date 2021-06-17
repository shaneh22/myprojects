package mp.commands;

import mp.graphics.Avatar;
import util.annotations.Tags;

@Tags("MoveCommand")
public class MoveCommand implements Runnable {
	Avatar avatar;
	int x;
	int y;
	public MoveCommand(Avatar iAvatar, int iX, int iY) {
		avatar=iAvatar;
		x=iX;
		y=iY;
	}
	public void run() {
		avatar.move(x, y);
	}

}
