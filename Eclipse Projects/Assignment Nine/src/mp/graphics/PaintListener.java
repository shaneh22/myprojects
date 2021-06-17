package mp.graphics;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.beans.PropertyChangeListener;

import util.annotations.Tags;

@Tags({"PaintListener"})
public interface PaintListener extends PropertyChangeListener{
	void paint(Graphics2D g);
}
