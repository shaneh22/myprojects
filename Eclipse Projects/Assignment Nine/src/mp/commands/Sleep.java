package mp.commands;

import mp.tokens.Word;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.Tags;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Input", "Value"})
@EditablePropertyNames({"Input"})
@Tags({"Sleep"})
public class Sleep extends Word{
	public Sleep(String initInput) {
		super(initInput);
	}
}
