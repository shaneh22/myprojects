package mp.graphics;

import java.awt.Point;
import java.beans.PropertyChangeListener;

import util.annotations.EditablePropertyNames;
import util.annotations.ObserverRegisterer;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
import util.annotations.Visible;

@Tags("Avatar")
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"StringShape","Head","Arms","Legs","Back"})
@EditablePropertyNames()
public class BaseAvatar implements Avatar {
	static final double ASSEMBLE_SCALAR=.5;
	Text stringShape = new TextBox();
	Head head;
	Angle arms=new AnAngle();
	Angle legs=new AnAngle();
	static final int BACK_RADIUS = 60;
	static final double BACK_ANGLE= -3*Math.PI/2;
	Line back= new RotatingLine(BACK_RADIUS,BACK_ANGLE);
	public BaseAvatar(Head type) {
		head=type;
		assemble();
	}
	public BaseAvatar(Head type, int x, int y) {
		head=type;
		head.setX(x);
		head.setY(y);
		assemble();
	}
	public Text getStringShape() {
		return stringShape;
	}
	public Head getHead() {
		return head;
	}
	public Angle getArms() {
		return arms;
	}
	public Angle getLegs() {
		return legs;
	}
	public Line getBack() {
		return back;
	}
	public void assemble() {
		stringShape.setX(head.getX()+head.getWidth());;
		stringShape.setY(head.getY());
		back.setX(head.getX()+(int)(ASSEMBLE_SCALAR*head.getWidth()));
		back.setY(head.getY()+head.getHeight());
		arms.move(head.getX()+(int)(ASSEMBLE_SCALAR*head.getWidth())-arms.getRightLine().getX(), head.getY()+head.getHeight()-arms.getRightLine().getY());
		legs.move(head.getX()+(int)(ASSEMBLE_SCALAR*head.getWidth())-legs.getRightLine().getX(), head.getY()+head.getHeight()+back.getHeight()-legs.getRightLine().getY());
	}
	@Tags("move")
	public void move(int x, int y) {
		head.setX(head.getX()+x);
		head.setY(head.getY()+y);
		assemble();
	}
	@Tags("scale")
	public void scale(double scale) {
		head.setHeight((int)(head.getHeight()*scale));
		head.setWidth((int)(head.getWidth()*scale));
		back.setRadius(back.getRadius()*scale);
		arms.getLeftLine().setRadius(arms.getLeftLine().getRadius()*scale);
		arms.getRightLine().setRadius(arms.getRightLine().getRadius()*scale);
		legs.getLeftLine().setRadius(legs.getLeftLine().getRadius()*scale);
		legs.getRightLine().setRadius(legs.getRightLine().getRadius()*scale);
		assemble();
	}
	@Visible(false)
	public static void addPropertyChangeListener(Avatar anAvatar,PropertyChangeListener aListener) {
		AnAngle.addPropertyChangeListener(anAvatar.getArms(),aListener);
		AnAngle.addPropertyChangeListener(anAvatar.getLegs(),aListener);
		anAvatar.getBack().addPropertyChangeListener(aListener);
		anAvatar.getHead().addPropertyChangeListener(aListener);
		anAvatar.getStringShape().addPropertyChangeListener(aListener);
	}
	@Visible(false)
	public void place (Point p) {
		head.setX(p.x);
		head.setY(p.y);
		assemble();
	}
}
