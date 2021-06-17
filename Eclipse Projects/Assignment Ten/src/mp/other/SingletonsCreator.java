package mp.other;

import main.ScannerI;

import java.awt.Component;
import java.beans.PropertyChangeListener;

import main.ScannerBean;
import mp.graphics.ABridgeScene;
import mp.graphics.AnObservableBridgeScenePainter;
import mp.graphics.BridgeScene;
import mp.graphics.ConsoleSceneView;
import mp.graphics.DelegatingBridgeSceneView;
import mp.graphics.DelegatingBridgeSceneViewI;
import mp.graphics.ObservableBridgeScenePainter;
import util.annotations.Tags;

@Tags("SingletonsCreator")
public class SingletonsCreator {
	static ScannerI scanner;
	static BridgeScene bridgeScene;
	static Table table;
	static CommandInterpreter interpreter;
	static PropertyChangeListener view;
	static ObservableBridgeScenePainter painter;
	static DelegatingBridgeSceneViewI delegatingView;
	static BridgeSceneController controller;
	@Tags({"scannerFactoryMethod"})
	public static ScannerI scannerFactory() {
		if(scanner==null) {
			scanner = new ScannerBean();
		}
		return scanner;
	}
	@Tags("bridgeSceneFactoryMethod")
	public static BridgeScene bridgeSceneFactory() {
		if(bridgeScene == null) {
			bridgeScene=new ABridgeScene();
		}
		return bridgeScene;
	}
	@Tags("avatarTableFactoryMethod")
	public static Table avatarTableFactory() {
		if(table == null) {
			table=new ATable();
		}
		return table;
	}
	@Tags("commandInterpreterFactoryMethod")
	public static CommandInterpreter commandInterpreterFactory() {
		if(interpreter==null) {
			interpreter=new ACommandInterpreter();
		}
		return interpreter;
	}
	@Tags("consoleSceneViewFactoryMethod")
	public static PropertyChangeListener consoleSceneViewFactory() {
		if(view==null) {
			view=new ConsoleSceneView();
		}
		return view;
	}
	@Tags("observableBridgeScenePainterFactoryMethod")
	public static ObservableBridgeScenePainter observableBridgeScenePainterFactory() {
		if(painter==null) {
			painter=new AnObservableBridgeScenePainter();
		}
		return painter;
	}
	@Tags("delegatingBridgeSceneViewFactoryMethod")
	public static DelegatingBridgeSceneViewI delegatingBridgeSceneViewFactory() {
		if(delegatingView==null) {
			delegatingView=new DelegatingBridgeSceneView();
		}
		return delegatingView;
	}
	@Tags("bridgeSceneControllerFactoryMethod")
	public static BridgeSceneController bridgeSceneControllerFactory() {
		if(controller==null) {
			controller=new ABridgeSceneController((Component) observableBridgeScenePainterFactory());
		}
		return controller;
	}
}
