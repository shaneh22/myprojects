package assignment1;

public class SocialDistanceViewFactory {
	static SocialDistanceView singleton;
	public static SocialDistanceView getSingleton() {
		if(singleton == null) {
			singleton = new ASocialDistanceView();
		}
		return singleton;
	}
	public static void setSingleton(SocialDistanceView newView) {
		singleton = newView;
	}
}
