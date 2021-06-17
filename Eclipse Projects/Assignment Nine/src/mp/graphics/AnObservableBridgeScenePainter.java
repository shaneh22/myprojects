package mp.graphics;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;


import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
import util.annotations.Visible;

@Tags("ObservableBridgeScenePainter")
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
public class AnObservableBridgeScenePainter extends Component implements ObservableBridgeScenePainter{
	List<PaintListener> paintListeners = new ArrayList<PaintListener>();
	@Visible(false)
	public List<PaintListener> getPaintListeners(){
		return paintListeners;
	}
	@Tags("addPaintListener")
	public void addPaintListener(PaintListener pl) {
		paintListeners.add(pl);
	}
	@Override
	public void paint(Graphics g) {
		for(int i=0;i<paintListeners.size();i++) {
			paintListeners.get(i).paint((Graphics2D) g);
		}
	}
	public AnObservableBridgeScenePainter() {
		setFocusable(true);
	}
}
