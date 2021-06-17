package mp.graphics;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
import util.models.PropertyListenerRegisterer;

@Tags("Locatable")
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"X","Y","PropertyChangeListeners"})
@EditablePropertyNames({"X","Y"})
public interface Locatable extends PropertyListenerRegisterer{
	public int getX();
	public void setX(int input);
	public int getY();
	public void setY(int input);
	public List<PropertyChangeListener> getPropertyChangeListeners();
	public void notifyAllListeners(PropertyChangeEvent evt);
}
