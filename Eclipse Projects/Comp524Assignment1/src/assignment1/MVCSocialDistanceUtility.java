package assignment1;

public class MVCSocialDistanceUtility {
	public static void startSocialDistanceMVC() {
		SocialDistanceModelFactory.getSingleton().addPropertyChangeListener(SocialDistanceViewFactory.getSingleton());
		SocialDistanceControllerFactory.getSingleton().processInput();
	}
}
