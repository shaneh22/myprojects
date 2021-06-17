package mp.graphics;

import java.beans.PropertyChangeListener;

import util.annotations.EditablePropertyNames;
import util.annotations.ObserverRegisterer;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
import util.annotations.Visible;

@Tags("Angle")
//@StructurePattern(StructurePatternNames.BEAN_PATTERN)
//@PropertyNames({"LeftLine","RightLine"})
//@EditablePropertyNames()
public class AnAngle implements Angle{
	Line leftLine,rightLine=null;
	static final int INITIAL_RADIUS_LENGTH=40;
	static final double INITIAL_RIGHT_ANGLE=-5*Math.PI/4;
	static final double INITIAL_LEFT_ANGLE=-7*Math.PI/4;
	public AnAngle() {
		leftLine = new RotatingLine(INITIAL_RADIUS_LENGTH,INITIAL_RIGHT_ANGLE);
		rightLine= new RotatingLine(INITIAL_RADIUS_LENGTH,INITIAL_LEFT_ANGLE);
	}
	public AnAngle(Line left, Line right) {
		leftLine=left;
		rightLine=right;
	}
	public Line getLeftLine() {
		return leftLine;
	}
	public Line getRightLine() {
		return rightLine;
	}
	@Tags("move")
	public void move(int xOffset, int yOffset) {
		leftLine.setX(leftLine.getX()+xOffset);
		leftLine.setY(leftLine.getY()+yOffset);
		rightLine.setX(rightLine.getX()+xOffset);
		rightLine.setY(rightLine.getY()+yOffset);
	}
	@Visible(false)
	public static void addPropertyChangeListener(Angle anAngle,PropertyChangeListener aListener) {
		anAngle.getLeftLine().addPropertyChangeListener(aListener);
		anAngle.getRightLine().addPropertyChangeListener(aListener);
	}

}
