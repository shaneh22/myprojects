package assignment1;

public class ASocialDistanceUtilityTesterMain {

	public static void main(String[] args) {
		ASocialDistanceUtility.printGivenAndGeneratedCombinationsDerivedSafety();
		ASocialDistanceUtility.printGivenAndGeneratedCombinationsInferredSafety();
		ASocialDistanceUtility.compareSafetyComputations();
		ASocialDistanceUtility.printSafeDistancesAndDurations(SocialDistanceUtility.EXHALATION_LEVEL_MEDIUM);
		ASocialDistanceUtility.printSafeDistancesAndDurations(SocialDistanceUtility.EXHALATION_LEVEL_MEDIUM - 1);
		ASocialDistanceUtility.printSafeDistancesAndDurations(SocialDistanceUtility.EXHALATION_LEVEL_MEDIUM + 2);
	}

}
