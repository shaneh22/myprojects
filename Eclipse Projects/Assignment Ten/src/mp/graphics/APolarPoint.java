package mp.graphics;

import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

//from lectures.graphics.extra
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"X","Y","Angle","Radius"})
@EditablePropertyNames()
public class APolarPoint implements PolarPoint {
	double radius, angle;
	public APolarPoint(double theRadius, double theAngle) {
		radius = theRadius;
		angle = theAngle;
	}
	public APolarPoint(int theX, int theY) {
		radius = Math.sqrt(theX*theX + theY*theY);
		angle = Math.atan((double) theY/theX);
	}
	public int getX() { return (int) (radius*Math.cos(angle)); }
	public int getY() { return (int) (radius*Math.sin(angle)); }
	public double getAngle() { return angle; } 
	public double getRadius() { return radius;}	
}
//what's the coolest type of point? a polar point! 