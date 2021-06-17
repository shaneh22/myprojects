package assignment1;
import java.beans.PropertyChangeListener; 

public interface SocialDistanceModel {
	static final String DISTANCE = "Distance";
	static final String DURATION = "Duration";
	static final String EXHALATION_LEVEL = "ExhalationLevel";
	static final String IS_SAFE = "Safe";
	public int getDistance();
	public int getDuration();
	public int getExhalationLevel();
	public boolean getIsSafe();
	public void setValues(int newDuration, int newDistance, int newExhalationLevel);
	
	void addPropertyChangeListener(PropertyChangeListener aListener);
}
