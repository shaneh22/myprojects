package mp.graphics;
import java.beans.PropertyChangeEvent;

import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
@Tags({"RotatingLine"})
@StructurePattern(StructurePatternNames.LINE_PATTERN)
public class RotatingLine extends ALocatable implements Line{
	int height, width=0;
	double radius, angle =0;
	PolarPoint endPoint = new APolarPoint(radius, angle);
	public RotatingLine() {}
	public RotatingLine(double inputR, double inputA) {
		radius=inputR;
		angle=inputA;
		endPoint = new APolarPoint(radius, angle);
	}
	public RotatingLine(double inputR, double inputA, int inputX, int inputY) {
		super(inputX,inputY);
		radius=inputR;
		angle=inputA;
		endPoint = new APolarPoint(radius, angle);
	}
	public int getHeight() {
		height=endPoint.getY();
		return height;
	}
	public int getWidth() {
		width=endPoint.getX();
		return width;
	}
	public double getRadius() {
		return radius;
	}
	public double getAngle() {
		return angle;
	}
	public void setRadius(double absoluteRadius) {
		int oldHeight=getHeight();
		int oldWidth=getWidth();
		radius=absoluteRadius;
		endPoint= new APolarPoint(radius, angle);
		notifyAllListeners(new PropertyChangeEvent(this,"Height",oldHeight,getHeight()));
		notifyAllListeners(new PropertyChangeEvent(this,"Width",oldWidth,getWidth()));
	}
	public void setAngle(double absoluteAngle) {
		int oldHeight=getHeight();
		int oldWidth=getWidth();
		angle=absoluteAngle;
		endPoint= new APolarPoint(radius, angle);
		notifyAllListeners(new PropertyChangeEvent(this,"Height",oldHeight,getHeight()));
		notifyAllListeners(new PropertyChangeEvent(this,"Width",oldWidth,getWidth()));
	}
	@Tags({"rotate"})
	public void rotate(int units) {
		setAngle(angle + units*(Math.PI/16));
	}
}
