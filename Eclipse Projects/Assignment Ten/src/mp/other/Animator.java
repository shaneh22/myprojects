package mp.other;

import mp.graphics.Avatar;
import util.annotations.Tags;

@Tags("Animator")
public interface Animator {
	@Tags("animateAvatar")
	public void animateAvatar(Avatar avatar);
}
