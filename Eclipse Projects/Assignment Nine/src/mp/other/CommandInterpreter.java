package mp.other;

import mp.tokens.Token;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
import util.annotations.Visible;
@Tags({"CommandInterpreter"})
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Errors","Command"})
@EditablePropertyNames("Command")
public interface CommandInterpreter {
	public String getErrors();
	public String getCommand();
	public void setCommand(String input);
	public int parseSignInt(Token t1,Token t2);
	@Visible(false)
	public Table getTable();
}
