package mp.graphics;

import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.IMAGE_PATTERN)
public interface MPImage extends BoundedShape {
	public String getImageFileName();
	public void setImageFileName(String input);
}
