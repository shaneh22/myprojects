package mp.other;

import mp.graphics.Avatar;
import util.annotations.Tags;
import util.misc.ThreadSupport;

@Tags("Animator")
public class AnAnimator implements Animator{
	public static final int SLEEP_TIME=100;
	public static final int ANGLE=3;
	public static final double CLAP=.1;
	@Tags("animateAvatar")
	public void animateAvatar(Avatar avatar) {
		while(true) {
			ThreadSupport.sleep(SLEEP_TIME);
			avatar.getArms().getRightLine().setAngle(avatar.getArms().getLeftLine().getAngle()+.1);
			ThreadSupport.sleep(SLEEP_TIME);
			avatar.getArms().getRightLine().setAngle(ANGLE);
		}
	}
}
