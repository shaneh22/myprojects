package mp.graphics;

import java.awt.Point;

import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
import util.annotations.Visible;
@Tags("Avatar")
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"StringShape","Head","Arms","Legs","Back"})
@EditablePropertyNames()

public interface Avatar {
	public Text getStringShape();
	public Head getHead();
	public Angle getArms();
	public Angle getLegs();
	public Line getBack();
	public void assemble();
	@Tags("move")
	public void move(int x, int y);
	@Tags("scale")
	public void scale(double scale);
	@Visible(false)
	public void place(Point p);
}
//the Last Airbender