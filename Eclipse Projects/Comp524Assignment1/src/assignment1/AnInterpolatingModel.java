package assignment1;

public class AnInterpolatingModel extends AnAbstractSocialDistanceModel{
	@Override
	public boolean isSafe() {
		return ASocialDistanceUtility.isInterpolatedSafe(getDistance(), getDuration(), getExhalationLevel());
	}
}
