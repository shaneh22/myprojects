package assignment1;

import java.beans.PropertyChangeEvent;

public class ASocialDistanceView implements SocialDistanceView{
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println(evt);
		if(SocialDistanceModel.IS_SAFE == evt.getPropertyName()) {
			Boolean isSafe = (Boolean) evt.getNewValue();
			if(isSafe) {
				System.out.println(SAFE);
			}
			else {
				System.out.println(NOT_SAFE);
			}
		}
	}

}
