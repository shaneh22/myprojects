package main;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JSlider;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import mp.graphics.ABridgeScene;
import mp.graphics.Avatar;
import mp.graphics.BridgeScene;
import mp.graphics.ConsoleSceneView;
import mp.other.ATable;
import mp.other.CommandInterpreter;
import mp.other.SingletonsCreator;
import util.annotations.Tags;
import util.misc.ThreadSupport;
import mp.other.Table;

public class Assignment10 {
	public static void main(String[] args) {
		JFrame frame=new JFrame();
		final int FRAME_WIDTH=800;
		final int FRAME_HEIGHT=600;
		final int OEFRAME_WIDTH=550;
		final int OEFRAME_HEIGHT=450;
		final int SLEEP=900;
		SingletonsCreator.delegatingBridgeSceneViewFactory();
		BridgeScene scene = SingletonsCreator.bridgeSceneFactory();
		CommandInterpreter console = SingletonsCreator.commandInterpreterFactory();
		SingletonsCreator.scannerFactory();
		SingletonsCreator.bridgeSceneControllerFactory();
		SingletonsCreator.consoleSceneViewFactory();
		frame.add((Component) SingletonsCreator.observableBridgeScenePainterFactory());
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setVisible(true);
		OEFrame editor= ObjectEditor.edit(scene);
		editor.hideMainPanel();
		editor.setSize(OEFRAME_WIDTH, OEFRAME_HEIGHT);
		ThreadSupport.sleep(SLEEP);
		scene.approach(scene.getArthur());
		ThreadSupport.sleep(SLEEP);
		console.animateArthur();
		console.animateGalahad();
		console.animateLancelot();
		console.animateRobin();
		console.animateGuard();
	}
	
}