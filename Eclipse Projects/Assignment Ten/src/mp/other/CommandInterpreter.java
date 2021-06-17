package mp.other;

import mp.commands.MoveCommand;
import mp.commands.SayCommand;
import mp.graphics.Avatar;
import mp.graphics.BridgeScene;
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
	@Tags("parseSay")
	public SayCommand parseSay(BridgeScene scene, String say);
	@Tags("parseMove")
	public MoveCommand parseMove(Avatar avatar,int x,int y);
	@Tags("asynchronousArthur")
	public void animateArthur();
	@Tags("asynchronousGalahad")
	public void animateGalahad();
	@Tags("asynchronousLancelot")
	public void animateLancelot();
	@Tags("asynchronousRobin")
	public void animateRobin();
	@Tags("asynchronousGuard")
	public void animateGuard();
}
