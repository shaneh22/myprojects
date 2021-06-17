package mp.graphics;

import java.awt.Graphics;
import java.util.List;

import util.annotations.Tags;

@Tags("ObservableBridgeScenePainter")
public interface ObservableBridgeScenePainter{
	public List<PaintListener> getPaintListeners();
	@Tags("addPaintListener")
	public void addPaintListener(PaintListener pl);
	public void paint(Graphics g);
	public void repaint();
}
