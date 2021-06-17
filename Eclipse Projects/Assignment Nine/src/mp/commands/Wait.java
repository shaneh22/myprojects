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
@Tags({"Wait"})
public class Wait extends Word{
	public Wait(String initInput) {
		super(initInput);
	}
}
//How much longer until my exercise gives me huge muscles? I guess I'll just have to weight some more. 