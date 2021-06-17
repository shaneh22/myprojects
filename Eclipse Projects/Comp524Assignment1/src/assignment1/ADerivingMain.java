package assignment1;

public class ADerivingMain {
	public static void main(String[] args) {
		SocialDistanceModelFactory.setSingleton(new ADerivingModel());
		MVCSocialDistanceUtility.startSocialDistanceMVC();
	}

}
