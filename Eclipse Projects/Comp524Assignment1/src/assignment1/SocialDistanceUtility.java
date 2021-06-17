package assignment1;

public interface SocialDistanceUtility {
	static final int DISTANCE_SMALL = 6;
	static final int DISTANCE_MEDIUM = 13;
	static final int DISTANCE_LARGE = 27;
	static final int DURATION_SMALL = 15;
	static final int DURATION_MEDIUM = 30;
	static final int DURATION_LARGE = 120;
	static final int EXHALATION_LEVEL_SMALL = 10;
	static final int EXHALATION_LEVEL_MEDIUM = 30;
	static final int EXHALATION_LEVEL_LARGE = 50;
	static final String COMPARE_HEADER = "Distance,Duration,Exhalation,Derived,Inferred";
	static final String IS_DERIVED_SAFE_HEADER = "Distance,Duration,Exhalation,IsSafe";
	static final String HYPHEN_SEPARATOR = "----------------";
	static final String TRUE = "true";
	static final String FALSE = "false";
	static final String DISTANCE = "distance";
	static final String DURATION = "duration";
	static final String EXHALATION_LEVEL = "exhalationLevel";
	static final int DISTANCE_INDEX = 0;
	static final int DURATION_INDEX = 1;
	static final int EXHALATION_LEVEL_INDEX = 2;
	static String resultAttributeName = "isSafe";
	static String[] resultValueNames = {TRUE, FALSE};
	static String[] featureNames = {DISTANCE, DURATION, EXHALATION_LEVEL};


	static final int[][] SAFE_VALUES = { { DISTANCE_MEDIUM, DURATION_MEDIUM, EXHALATION_LEVEL_MEDIUM },
			{ DISTANCE_SMALL, DURATION_MEDIUM, EXHALATION_LEVEL_SMALL },
			{ DISTANCE_LARGE, DURATION_MEDIUM, EXHALATION_LEVEL_LARGE },
			{ DISTANCE_MEDIUM, DURATION_SMALL, EXHALATION_LEVEL_LARGE },
			{ DISTANCE_MEDIUM, DURATION_LARGE, EXHALATION_LEVEL_SMALL },
			{ DISTANCE_LARGE, DURATION_LARGE, EXHALATION_LEVEL_MEDIUM },
			{ DISTANCE_SMALL, DURATION_SMALL, EXHALATION_LEVEL_MEDIUM } };
}
