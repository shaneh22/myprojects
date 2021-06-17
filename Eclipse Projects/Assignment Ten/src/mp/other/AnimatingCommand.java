package mp.other;

import mp.graphics.Avatar;
import util.annotations.Tags;

@Tags("AnimatingCommand")
public class AnimatingCommand implements Runnable{
	Animator animator;
	Avatar avatar;
	public AnimatingCommand(Animator anAnimator,Avatar anAvatar) {
		animator=anAnimator;
		avatar=anAvatar;
	}
	public void run() {
		animator.animateAvatar(avatar);
	}
}
