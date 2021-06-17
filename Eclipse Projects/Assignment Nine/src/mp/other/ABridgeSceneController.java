package mp.other;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import mp.graphics.BridgeScene;
import util.annotations.Tags;
@Tags("BridgeSceneController")
public class ABridgeSceneController implements BridgeSceneController {
	Component observable;
	Point mouseClicked;
	BridgeScene scene=SingletonsCreator.bridgeSceneFactory();
	public ABridgeSceneController(Component c) {
		observable=c;
		mouseClicked=null;
		observable.addMouseListener(this);
		observable.addKeyListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		mouseClicked=e.getPoint();
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	public void keyTyped(KeyEvent e) {
		if(mouseClicked!=null) {
		switch(e.getKeyChar()) {
			case 'a': 
				 	  scene.getArthur().place(mouseClicked);
					  break;
			case 'g': scene.getGalahad().place(mouseClicked);
					  break;
			case 'l': scene.getLancelot().place(mouseClicked);
					  break;
			case 'r': scene.getRobin().place(mouseClicked);
					  break;
		}
		}
		if(e.getKeyChar()=='o') {
			scene.origin();
		}
	}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

}
