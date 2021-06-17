package mp.tokens;

import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.Tags;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Input", "Value"})
@EditablePropertyNames({"Input"})
@Tags({"Word"})
public class Word extends AToken implements WordToken{
	protected String value;
	public Word(String initInput) {
		super(initInput);
	}
	public String getValue() {
		value=input.toLowerCase();
		return value;
	}
}
