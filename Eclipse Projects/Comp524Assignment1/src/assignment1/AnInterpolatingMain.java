package assignment1;

public class AnInterpolatingMain {

	public static void main(String[] args) {
		SocialDistanceModelFactory.setSingleton(new AnInterpolatingModel());
		MVCSocialDistanceUtility.startSocialDistanceMVC();
	}

}
