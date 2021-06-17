package assignment1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class AnAbstractSocialDistanceModel implements SocialDistanceModel{
	int duration;
	int distance;
	int exhalationLevel;
	PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	boolean isSafe;
	@Override
	public int getDuration() {
		return duration;
	}
	@Override
	public int getDistance() {
		return distance;
	}
	@Override
	public int getExhalationLevel() {
		return exhalationLevel;
	}
	@Override
	public boolean getIsSafe() {
		return isSafe;
	}
	@Override
	public void setValues(int newDuration, int newDistance, int newExhalationLevel) {
		duration = newDuration;
		distance = newDistance;
		exhalationLevel = newExhalationLevel;
		isSafe = isSafe();
		propertyChangeSupport.firePropertyChange(DISTANCE, null, distance);
		propertyChangeSupport.firePropertyChange(DURATION, null, duration);
		propertyChangeSupport.firePropertyChange(EXHALATION_LEVEL, null, exhalationLevel);
		propertyChangeSupport.firePropertyChange(IS_SAFE, null, isSafe);
	}
	public abstract boolean isSafe();
	@Override
	public void addPropertyChangeListener(PropertyChangeListener aListener) {
		propertyChangeSupport.addPropertyChangeListener(aListener);
	}
	
	
}
