package mp.graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.IMAGE_PATTERN)
public class GuardHead extends AnImage implements Head{
	static final String INIT_IMAGE_FILE_NAME="guard.jpg";
	static final Icon ICON = new ImageIcon(INIT_IMAGE_FILE_NAME);
	static final int ICON_HEIGHT= ICON.getIconHeight();
	static final int ICON_WIDTH= ICON.getIconWidth();
	public GuardHead() {
		super(INIT_IMAGE_FILE_NAME,0,0,ICON_HEIGHT,ICON_WIDTH);
	}
	public GuardHead(int inputX,int inputY) {
		super(INIT_IMAGE_FILE_NAME,inputX,inputY,ICON_HEIGHT,ICON_WIDTH);
	}
}