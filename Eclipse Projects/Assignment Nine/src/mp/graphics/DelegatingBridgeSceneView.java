package mp.graphics;

import mp.other.SingletonsCreator;
import util.annotations.Tags;
@Tags("DelegatingBridgeSceneView")
public class DelegatingBridgeSceneView implements DelegatingBridgeSceneViewI{
	ObservableBridgeScenePainter painter = SingletonsCreator.observableBridgeScenePainterFactory();
	public DelegatingBridgeSceneView() {
		painter.addPaintListener(new BackgroundView());
		painter.addPaintListener(new ArthurView());
		painter.addPaintListener(new GalahadView());
		painter.addPaintListener(new GuardView());
		painter.addPaintListener(new LancelotView());
		painter.addPaintListener(new RobinView());
	}
}
