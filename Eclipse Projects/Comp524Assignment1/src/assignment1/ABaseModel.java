package assignment1;

public class ABaseModel extends AnAbstractSocialDistanceModel {
	@Override
	public boolean isSafe() {
		return ASocialDistanceUtility.isGivenSafe(getDistance(), getDuration(), getExhalationLevel());
	}
}
