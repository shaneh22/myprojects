package assignment1;

public class AnInferringMain {

	public static void main(String[] args) {
		SocialDistanceModelFactory.setSingleton(new AnInferringModel());
		MVCSocialDistanceUtility.startSocialDistanceMVC();
	}

}
