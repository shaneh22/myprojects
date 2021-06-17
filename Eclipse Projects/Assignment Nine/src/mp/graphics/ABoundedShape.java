package mp.graphics;

import java.beans.PropertyChangeEvent;

import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags("BoundedShape")
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"X","Y","Height","Width","PropertyChangeListeners"})
@EditablePropertyNames({"X","Y","Height","Width"})
public class ABoundedShape extends ALocatable implements Locatable{
		int height, width=0;
		public ABoundedShape() {}
		public ABoundedShape(int inputX, int inputY) {
			super(inputX,inputY);
		}
		public ABoundedShape(int inputX, int inputY, int inputH, int inputW) {
			super(inputX, inputY);
			height=inputH;
			width=inputW;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int input) {
			int oldInput=getHeight();
			height=input;
			notifyAllListeners(new PropertyChangeEvent(this,"Height",oldInput,height));
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int input) {
			int oldInput=getWidth();
			width=input;
			notifyAllListeners(new PropertyChangeEvent(this,"Width",oldInput,width));
		}
	}