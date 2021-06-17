package mp.graphics;

import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags("BoundedShape")
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"X","Y","Height","Width","PropertyChangeListeners"})
@EditablePropertyNames({"X","Y","Height","Width"})
public interface BoundedShape extends Locatable{
	public int getHeight();
	public void setHeight(int input);
	public int getWidth();
	public void setWidth(int input);
}
