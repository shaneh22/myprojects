package assignment1;

public class ABasicMain {
	public static void main(String[] args) {
		SocialDistanceModelFactory.setSingleton(new ABaseModel());
		MVCSocialDistanceUtility.startSocialDistanceMVC();
	}
}
