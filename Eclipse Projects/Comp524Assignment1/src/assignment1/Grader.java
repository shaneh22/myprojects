package assignment1;

public class Grader implements gradingTools.comp524f20.assignment1.SocialDistanceClassRegistry{

	@Override
	public Class<?> getBasicSocialDistanceUtility() {
		return ASocialDistanceUtility.class;
	}

	@Override
	public Class<?> getSocialDistancDerivingModel() {
		return ADerivingModel.class;
	}

	@Override
	public Class<?> getSocialDistancInferringModel() {
		return AnInferringModel.class;
	}

	@Override
	public Class<?> getSocialDistanceBasicModel() {
		return ABaseModel.class;
	}

	@Override
	public Class<?> getSocialDistanceClassifierFactory() {
		return SocialDistanceClassifierFactory.class;
	}

	@Override
	public Class<?> getSocialDistanceController() {
		return ASocialDistanceController.class;
	}

	@Override
	public Class<?> getSocialDistanceControllerFactory() {
		return SocialDistanceControllerFactory.class;
	}

	@Override
	public Class<?> getSocialDistanceInterpolatingModel() {
		return AnInterpolatingModel.class;
	}

	@Override
	public Class<?> getSocialDistanceMVCBasicMain() {
		return ABasicMain.class;
	}

	@Override
	public Class<?> getSocialDistanceMVCDerivingMain() {
		return ADerivingMain.class;
	}

	@Override
	public Class<?> getSocialDistanceMVCInferringMain() {
		return AnInferringMain.class;
	}

	@Override
	public Class<?> getSocialDistanceMVCInterpolatingMain() {
		return AnInterpolatingMain.class;
	}

	@Override
	public Class<?> getSocialDistanceModelFactory() {
		return SocialDistanceModelFactory.class;
	}

	@Override
	public Class<?> getSocialDistanceUilityTesterMain() {
		return ASocialDistanceUtilityTesterMain.class;
	}

	@Override
	public Class<?> getSocialDistanceView() {
		return ASocialDistanceView.class;
	}

	@Override
	public Class<?> getSocialDistanceViewFactory() {
		return SocialDistanceViewFactory.class;
	}

}
