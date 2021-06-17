package mp.graphics;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import mp.other.SingletonsCreator;
import util.annotations.Tags;

@Tags("ConsoleSceneView")
public class ConsoleSceneView implements PropertyChangeListener{
	BridgeScene scene=SingletonsCreator.bridgeSceneFactory();
	public ConsoleSceneView() {
		ABridgeScene.addPropertyChangeListener(scene,this);
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println(evt);
	}
}
