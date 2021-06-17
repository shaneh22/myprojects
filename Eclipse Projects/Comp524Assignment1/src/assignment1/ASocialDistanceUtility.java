package assignment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import gradingTools.comp524f20.assignment1.WekaUtil;

public class ASocialDistanceUtility implements SocialDistanceUtility {

	/**
	 * If the combination of the method parameters is safe, based on the given data, the function returns true. Otherwise, it returns false.
	 */
	public static boolean isGivenSafe(int distance, int duration, int exhalationLevel) {
		int[] value = { distance, duration, exhalationLevel };
		for (int i = 0; i < SAFE_VALUES.length; i++) {
			if (Arrays.equals(value, SAFE_VALUES[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Given distance, duration, and exhalationLevel, interpolates the distance low and the duration and exhalationLevel high to calculate if the combination is safe.
	 */
	public static boolean isInterpolatedSafe(int distance, int duration, int exhalationLevel) {
		return isGivenSafe(interpolateDistance(distance), interpolateDuration(duration),
				interpolateExhalationLevel(exhalationLevel));
	}

	/**
	 * Given distance, duration, interpolates the distance low and the duration and uses medium exhalation level to calculate if the combination is safe.
	 */
	public static boolean isInterpolatedSafe(int distance, int duration) {
		return isGivenSafe(interpolateDistance(distance), interpolateDuration(duration), EXHALATION_LEVEL_MEDIUM);
	}

	/**
	 * Given distance, interpolates the distance low and uses medium duration and uses medium exhalation level to calculate if the combination is safe.
	 */
	public static boolean isInterpolatedSafe(int distance) {
		return isGivenSafe(interpolateDistance(distance), DURATION_MEDIUM, EXHALATION_LEVEL_MEDIUM);
	}

	/**
	 * Interpolates the distance low, returning zero or one of the three safety parameters.
	 */
	public static int interpolateDistance(int distance) {
		if (distance < DISTANCE_SMALL) {
			return 0;
		} else if (distance < DISTANCE_MEDIUM) {
			return DISTANCE_SMALL;
		} else if (distance < DISTANCE_LARGE) {
			return DISTANCE_MEDIUM;
		} else {
			return DISTANCE_LARGE;
		}
	}

	/**
	 * Interpolates the duration high, returning one of the three safety parameters or the max integer value.
	 */
	public static int interpolateDuration(int duration) {
		if (duration > DURATION_LARGE) {
			return Integer.MAX_VALUE;
		} else if (duration > DURATION_MEDIUM) {
			return DURATION_LARGE;
		} else if (duration > DURATION_SMALL) {
			return DURATION_MEDIUM;
		} else {
			return DURATION_SMALL;
		}
	}

	/**
	 * Interpolates the exhalation level high, returning one of the three safety parameters or the max integer value.
	 */
	public static int interpolateExhalationLevel(int exhalationLevel) {
		if (exhalationLevel > EXHALATION_LEVEL_LARGE) {
			return Integer.MAX_VALUE;
		} else if (exhalationLevel > EXHALATION_LEVEL_MEDIUM) {
			return EXHALATION_LEVEL_LARGE;
		} else if (exhalationLevel > EXHALATION_LEVEL_SMALL) {
			return EXHALATION_LEVEL_MEDIUM;
		} else {
			return EXHALATION_LEVEL_SMALL;
		}
	}

	/**
	 * Calculates random variables for distance, duration, and exhalation level with a max range of twice the large value. Using these random variables, it determines if the combination is safe. Then, it prints as a 4-tuple the random distance, duration, exhalationLevel, and if it's safe separated by commas.
	 */
	public static void printGeneratedCombinationDerivedSafety() {
		int[] randomCombination = generateRandomCombination();
		boolean isSafe = isDerivedSafe(randomCombination[DISTANCE_INDEX], randomCombination[DURATION_INDEX],
				randomCombination[EXHALATION_LEVEL_INDEX]);
		System.out.println(randomCombination[DISTANCE_INDEX] + "," + randomCombination[DURATION_INDEX] + ","
				+ randomCombination[EXHALATION_LEVEL_INDEX] + "," + isSafe);
	}

	/**
	 * Prints a header with categories: distance, duration, exhalation level, and if
	 * it's safe. Then, the 4-tuples of the known safe values, followed by a
	 * separator, and then the 4-tuples of ten randomly generated combinations of
	 * distance, duration, and exhalation value with whether it's derived safe or
	 * not.
	 */
	public static void printGivenAndGeneratedCombinationsDerivedSafety() {
		printSafeValues();
		for (int i = 0; i < 10; i++) {
			printGeneratedCombinationDerivedSafety();
		}
	}

	/**
	 * Identifies if a combination of distance, duration, and exhalationLevel is safe by comparing if it's safer than one of the known safe values. 
	 * If it's safer than one of the known safe values, it returns true, otherwise return false.
	 */
	public static boolean isDerivedSafe(int distance, int duration, int exhalationLevel) {
		int[] combination = { distance, duration, exhalationLevel };
		for (int i = 0; i < SAFE_VALUES.length; i++) {
			if (isCombinationSafer(combination, SAFE_VALUES[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Inputs two combinations of distance, duration, and exhalation level in that order and returns true if combination1 is safer than combination2 and false otherwise.
	 */
	public static boolean isCombinationSafer(int[] combination1, int[] combination2) {
		return combination1[0] >= combination2[0] && combination1[1] <= combination2[1]
				&& combination1[2] <= combination2[2];
	}

	/**
	 * Using the trainer classifier on the SafeSocialization weka file, predicts if the combination of inputted distance, duration, exhalationLevel is safe.
	 */
	public static boolean isInferredSafe(int distance, int duration, int exhalationLevel) {
		double[] inputFeatureValues = { distance, duration, exhalationLevel };
		String resultString = WekaUtil.predictString(SocialDistanceClassifierFactory.getSingleton(), featureNames,
				inputFeatureValues, resultAttributeName, resultValueNames);
		return TRUE.equals(resultString);
	}

	/**
	 * Prints a header with categories: distance, duration, exhalation level, and if
	 * it's safe. Then, the 4-tuples of the known safe values, followed by a
	 * separator, and then the 4-tuples of ten randomly generated combinations of
	 * distance, duration, and exhalation value with whether it's inferred safe or
	 * not.
	 */
	public static void printGivenAndGeneratedCombinationsInferredSafety() {
		printSafeValues();
		for (int i = 0; i < 10; i++) {
			printGeneratedCombinationInferredSafety();
		}
	}

	/**
	 * Prints a header with categories: distance, duration, exhalation level, and if it's safe. Then, the 4-tuples of the known safe values, followed by a hyphen separator.
	 */
	public static void printSafeValues() {
		System.out.println(IS_DERIVED_SAFE_HEADER);
		for (int i = 0; i < SAFE_VALUES.length; i++) {
			System.out.println(SAFE_VALUES[i][DISTANCE_INDEX] + "," + SAFE_VALUES[i][DURATION_INDEX] + ","
					+ SAFE_VALUES[i][EXHALATION_LEVEL_INDEX] + "," + "true");
		}
		System.out.println(HYPHEN_SEPARATOR);
	}

	/**
	 * Calculates random variables for distance, duration, and exhalation level with
	 * a max range of twice the large value. Using these random variables, it
	 * determines if the combination is safe. Then, it prints as a 4-tuple the
	 * random distance, duration, exhalationLevel, and if it's inferred safe
	 * separated by commas.
	 */
	public static void printGeneratedCombinationInferredSafety() {
		int[] randomCombination = generateRandomCombination();
		boolean isSafe = isInferredSafe(randomCombination[DISTANCE_INDEX], randomCombination[DURATION_INDEX],
				randomCombination[EXHALATION_LEVEL_INDEX]);
		System.out.println(randomCombination[DISTANCE_INDEX] + "," + randomCombination[DURATION_INDEX] + ","
				+ randomCombination[EXHALATION_LEVEL_INDEX] + "," + isSafe);
	}

	/**
	 * Generates random numbers for distance, duration, and exhalationLevel and returns it in an int[].
	 */
	public static int[] generateRandomCombination() {
		int randomDistance = (int) (Math.random() * 2 * DISTANCE_LARGE);
		int randomDuration = (int) (Math.random() * 2 * DURATION_LARGE);
		int randomExhalationLevel = (int) (Math.random() * 2 * EXHALATION_LEVEL_LARGE);
		return new int[] { randomDistance, randomDuration, randomExhalationLevel };
	}

	/**
	 * Prints ten random combinations of random distance, duration, and
	 * exhalationLevel along with if the combination is derivedSafe and if it's
	 * inferredSafe. It returns the number of times the prediction by inferredSafe
	 * was correct.
	 */
	public static int compareSafetyComputations() {
		System.out.println(COMPARE_HEADER);
		int correctGuesses = 0;
		for (int i = 0; i < 10; i++) {
			int[] randomCombination = generateRandomCombination();
			boolean derivedSafe = isDerivedSafe(randomCombination[DISTANCE_INDEX], randomCombination[DURATION_INDEX],
					randomCombination[EXHALATION_LEVEL_INDEX]);
			boolean inferredSafe = isInferredSafe(randomCombination[DISTANCE_INDEX], randomCombination[DURATION_INDEX],
					randomCombination[EXHALATION_LEVEL_INDEX]);
			System.out.println(randomCombination[DISTANCE_INDEX] + "," + randomCombination[DURATION_INDEX] + ","
					+ randomCombination[EXHALATION_LEVEL_INDEX] + "," + derivedSafe + "," + inferredSafe);
			if (derivedSafe == inferredSafe) {
				correctGuesses++;
			}
		}
		return correctGuesses;
	}

	/**
	 * Given a exhalation level, it interpolates it to Integer.max or one of the
	 * small, medium, or large exhalation values, and then searches the list of
	 * known safe combinations with that exhalation value. It returns a list of all
	 * the safe distance and duration combinations it finds.
	 */
	public static List<Integer[]> generateSafeDistancesAndDurations(int exhalationLevel) {
		int interpolatedExhalationLevel = interpolateExhalationLevel(exhalationLevel);
		List<Integer[]> safeDistancesAndDurations = new ArrayList<Integer[]>();
		for (int i = 0; i < SAFE_VALUES.length; i++) {
			if (interpolatedExhalationLevel == SAFE_VALUES[i][2]) {
				Integer[] safeDistanceAndDuration = { SAFE_VALUES[i][0], SAFE_VALUES[i][1] };
				safeDistancesAndDurations.add(safeDistanceAndDuration);
			}
		}
		return safeDistancesAndDurations;
	}

	/**
	 * Prints the safe distances and durations with the given exhalation level interpolated.
	 */
	public static void printSafeDistancesAndDurations(int exhalationLevel) {
		List<Integer[]> safeDistancesAndDurations = generateSafeDistancesAndDurations(exhalationLevel);
		String listToString = ",[";
		for (Integer[] i : safeDistancesAndDurations) {
			listToString += "{" + i[0] + "," + i[1] + "}";
		}
		listToString += "]";
		System.out.println(exhalationLevel + listToString);
	}

}
