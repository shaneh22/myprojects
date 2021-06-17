package mp.graphics;

import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags({"RotatingLine"})
@StructurePattern(StructurePatternNames.LINE_PATTERN)
public interface Line extends Locatable{
	public int getHeight();
	public int getWidth();
	public double getRadius();
	public void setRadius(double radius);
	public double getAngle();
	public void setAngle(double angle);
	@Tags({"rotate"})
	public void rotate(int units);
}
//What's the king of the jungle? A line (lion)