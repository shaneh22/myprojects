package mp.graphics;

import java.beans.PropertyChangeEvent;

import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.STRING_PATTERN)
public class TextBox extends ALocatable implements Text{
	String text="";
	public TextBox() {}
	public TextBox(int inputX,int inputY, String inputText) {
		super(inputX,inputY);
		text=inputText;
	}
	public String getText() {
		return text;
	}  
	public void setText(String input) {
		String oldInput=getText();
		text = input;
		notifyAllListeners(new PropertyChangeEvent(this,"Text",oldInput,text));
	} 
}
