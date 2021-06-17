package assignment1;

public class ADerivingModel extends AnAbstractSocialDistanceModel{
	@Override
	public boolean isSafe() {
		return ASocialDistanceUtility.isDerivedSafe(getDistance(), getDuration(), getExhalationLevel());
	}
}
