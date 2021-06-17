package mp.graphics;

import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.OVAL_PATTERN)
public class Circle extends ABoundedShape implements BoundedShape {
	public Circle() {}
	public Circle(int inputX,int inputY,int inputD) {
		super(inputX,inputY,inputD,inputD);
	}
}
