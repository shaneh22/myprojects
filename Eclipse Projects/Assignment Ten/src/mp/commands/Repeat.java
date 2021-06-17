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
@Tags({"Repeat"})
public class Repeat extends Word{
	public Repeat(String initInput) {
		super(initInput);
	}
}
//peat and repeat were sitting on a wall. peat falls off. who's left on the wall?
//peat and repeat were sitting on a wall. peat falls off. who's left on the wall?
//peat and repeat were sitting on a wall. peat falls off. who's left on the wall?
//peat and repeat were sitting on a wall. peat falls off. who's left on the wall?
//peat and repeat were sitting on a wall. peat falls off. who's left on the wall?