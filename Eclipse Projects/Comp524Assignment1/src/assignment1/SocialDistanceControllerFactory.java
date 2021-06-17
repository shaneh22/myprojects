package assignment1;

public class SocialDistanceControllerFactory {
	static SocialDistanceController singleton;
	public static SocialDistanceController getSingleton() {
		if(singleton == null) {
			singleton = new ASocialDistanceController();
		}
		return singleton;
	}
	public static void setSingleton(SocialDistanceController newController) {
		singleton = newController;
	}
}