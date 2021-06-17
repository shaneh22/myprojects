package mp.graphics;

import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.STRING_PATTERN)
public interface Text extends Locatable{
	public String getText();
	public void setText(String input);
}
