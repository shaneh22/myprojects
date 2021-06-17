package mp.graphics;

import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"X","Y","Angle","Radius"})
@EditablePropertyNames()
public interface PolarPoint{
	public int getX();
	public int getY();
	public double getAngle();
	public double getRadius();
}
//Broken pencils are the worst. They have no point!