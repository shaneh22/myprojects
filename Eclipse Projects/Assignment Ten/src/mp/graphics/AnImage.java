package mp.graphics;

import java.beans.PropertyChangeEvent;

import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.IMAGE_PATTERN)
public class AnImage extends ABoundedShape implements MPImage{
	String imageFileName="";
	public String getImageFileName() {
		return imageFileName;
	}  
	public void setImageFileName(String input) {
		String oldInput=getImageFileName();
		imageFileName = input;
		notifyAllListeners(new PropertyChangeEvent(this,"ImageFileName",oldInput,getImageFileName()));
	} 
	public AnImage() {}
	public AnImage(String name) {
		imageFileName=name;
	}
	public AnImage(String name, int inputX, int inputY) {
		super(inputX,inputY);
		imageFileName=name;
	}
	public AnImage(String name, int inputX, int inputY, int inputH, int inputW) {
		super(inputX,inputY,inputH,inputW);
		imageFileName=name;
	}
}
