package mp.graphics;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags("Locatable")
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"X","Y","PropertyChangeListeners"})
@EditablePropertyNames({"X","Y"})
public class ALocatable implements Locatable{
	List<PropertyChangeListener> propertyChangeListeners= new ArrayList<PropertyChangeListener>();
	int x,y=0;
	public int getX() {
		return x;
	}
	public void setX(int input) {
		int oldInput=getX();
		x=input;
		notifyAllListeners(new PropertyChangeEvent(this,"X",oldInput,x));
	}
	public int getY() {
		return y;
	}
	public void setY(int input) {
		int oldInput=getY();
		y=input;
		notifyAllListeners(new PropertyChangeEvent(this,"Y",oldInput,y));
	}
	public ALocatable() {}
	public ALocatable(int inputX, int inputY) {
		x=inputX;
		y=inputY;
	}
	public List<PropertyChangeListener> getPropertyChangeListeners() {
		return propertyChangeListeners;
	}
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeListeners.add(listener);	
	}
	public void notifyAllListeners(PropertyChangeEvent evt) {
		for(int i=0;i<propertyChangeListeners.size();i++) {
			propertyChangeListeners.get(i).propertyChange(evt);
		}
	}
}
