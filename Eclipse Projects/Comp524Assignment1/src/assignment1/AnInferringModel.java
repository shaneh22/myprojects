package assignment1;

public class AnInferringModel extends AnAbstractSocialDistanceModel{
	@Override
	public boolean isSafe() {
		return ASocialDistanceUtility.isInferredSafe(getDistance(),getDuration(),getExhalationLevel());
	}
}
