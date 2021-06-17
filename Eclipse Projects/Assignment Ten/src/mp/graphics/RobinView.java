package mp.graphics;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import mp.other.SingletonsCreator;
import util.annotations.Tags;
@Tags("PaintListener")
public class RobinView extends Component implements PaintListener{
	BridgeScene scene=SingletonsCreator.bridgeSceneFactory();
	Avatar avatar=scene.getRobin();
	public RobinView() {
		BaseAvatar.addPropertyChangeListener(avatar,this);
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		SingletonsCreator.observableBridgeScenePainterFactory().repaint();
	}
	@Override
	public void paint(Graphics2D g) {
		draw(g,avatar.getBack());
		draw(g,avatar.getArms().getLeftLine());
		draw(g,avatar.getArms().getRightLine());
		draw(g,avatar.getLegs().getLeftLine());
		draw(g,avatar.getLegs().getRightLine());
		draw(g,avatar.getHead());
		draw(g,avatar.getStringShape());
	}
	public static void draw(Graphics2D g, Line aLine) { 
		g.drawLine(aLine.getX(), aLine.getY(), aLine.getX()+aLine.getWidth(), aLine.getY()+aLine.getHeight());
	}
	 public  void draw(Graphics2D g, MPImage anImage) {
		 Image img = Toolkit.getDefaultToolkit().getImage(anImage.getImageFileName());
		 g.drawImage(img, anImage.getX(), anImage.getY(), this); 
	}
	public void draw(Graphics2D g, Text aText) {
		g.drawString(aText.getText(),aText.getX(),aText.getY());
	}
}
