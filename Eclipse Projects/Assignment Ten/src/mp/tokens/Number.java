package mp.tokens;


import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.Tags;
import util.annotations.StructurePatternNames;

@Tags({"Number"})
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Input", "Value"})
@EditablePropertyNames({"Input"})
public class Number extends AToken implements NumberToken {
	int value;
	public Number(String initInput) {
		super(initInput);
	}
	public int getValue() {
		value= Integer.parseInt(input);
		return value;
	}
}
