package assignment1;

public class SocialDistanceModelFactory {
	static SocialDistanceModel singleton;
	
	public static SocialDistanceModel getSingleton() {
		if(singleton == null) {
			singleton = new ABaseModel();
		}
		return singleton;
	}
	public static void setSingleton(SocialDistanceModel newModel) {
		singleton = newModel;
	}
}
