package mp.graphics;

import java.beans.PropertyChangeListener;

import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
@Tags("Angle")
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
public interface Angle {
	public Line getLeftLine();
	public Line getRightLine();
	@Tags("move")
	public void move(int inputX, int inputY);
}
//if you're cold, just stand in a corner. They're 90 degrees!