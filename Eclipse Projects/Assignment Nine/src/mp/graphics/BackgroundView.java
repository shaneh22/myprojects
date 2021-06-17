package mp.graphics;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import mp.other.SingletonsCreator;
import util.annotations.Tags;
@Tags("PaintListener")
public class BackgroundView extends Component implements PaintListener{
	BridgeScene scene=SingletonsCreator.bridgeSceneFactory();
	public BackgroundView() {
		scene.getGorge().addPropertyChangeListener(this);
		scene.getGuardArea().addPropertyChangeListener(this);
		scene.getKnightArea().addPropertyChangeListener(this);
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		SingletonsCreator.observableBridgeScenePainterFactory().repaint();
	}
	@Override
	public void paint(Graphics2D g) {
		draw(g,scene.getGorge());
		g.drawOval(scene.getGuardArea().getX(), scene.getGuardArea().getY(), scene.getGuardArea().getWidth(), scene.getGuardArea().getHeight());
		g.drawOval(scene.getKnightArea().getX(), scene.getKnightArea().getY(), scene.getKnightArea().getWidth(), scene.getKnightArea().getHeight());
	}
	public  void draw(Graphics2D g, MPImage anImage) {
		 Image img = Toolkit.getDefaultToolkit().getImage(anImage.getImageFileName());
		 g.drawImage(img, anImage.getX(), anImage.getY(), this); 
	}
}