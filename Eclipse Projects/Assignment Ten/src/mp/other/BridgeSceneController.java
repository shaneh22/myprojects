package mp.other;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import util.annotations.Tags;
@Tags("BridgeSceneController")
public interface BridgeSceneController extends MouseListener, KeyListener{
	public void mouseClicked(MouseEvent e);
	public void mousePressed(MouseEvent e);
	public void mouseReleased(MouseEvent e);
	public void mouseEntered(MouseEvent e);
	public void mouseExited(MouseEvent e);	
	public void keyTyped(KeyEvent e);
	public void keyPressed(KeyEvent e);
	public void keyReleased(KeyEvent e);
}
