package mp.other;

import main.ScannerI;
import mp.commands.Move;
import mp.commands.MoveCommand;
import mp.commands.Say;
import mp.commands.SayCommand;
import mp.graphics.Avatar;
import mp.graphics.BridgeScene;
import mp.tokens.Quote;
import mp.tokens.Token;
import mp.tokens.Word;
import mp.tokens.NumberToken;
import mp.tokens.Plus;
import mp.tokens.Minus;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
import util.annotations.Visible;

@Tags({"CommandInterpreter","SignedMoveCommandInterpreter","ErrorResilientCommandInterpreter"})
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Errors","Command"})
@EditablePropertyNames("Command")
public class ACommandInterpreter implements CommandInterpreter {
	BridgeScene bridgeScene; 
	int xmove=0;
	int ymove=0;
	ScannerI scanner;
	Table table;
	String errors="";
	int nextToken = 0;
	String command="";
	Animator animator=new AnAnimator();
	static final String ARTHUR= "arthur";
	static final String GALAHAD= "galahad";
	static final String GUARD= "guard";
	static final String LANCELOT="lancelot";
	static final String ROBIN= "robin";
	static final int ARTHUR_INDEX=1;
	static final int GALAHAD_INDEX=2;
	static final int GUARD_INDEX=3;
	static final int LANCELOT_INDEX=4;
	static final int ROBIN_INDEX=5;
	static final int SAY_MIN=2;
	static final int INT_MIN=4;
	static final int MINUS=-1;
	static final int SECOND_TOKEN_INDEX=1;
	static final int THIRD_TOKEN_INDEX=2;
	static final int FOUR_TOK_INDEX=3;
	public ACommandInterpreter() {
		bridgeScene= SingletonsCreator.bridgeSceneFactory();
		scanner=SingletonsCreator.scannerFactory();
		table=SingletonsCreator.avatarTableFactory();
		table.put(ARTHUR,SingletonsCreator.bridgeSceneFactory().getArthur());
		table.put(GALAHAD,SingletonsCreator.bridgeSceneFactory().getGalahad());
		table.put(GUARD,SingletonsCreator.bridgeSceneFactory().getGuard());
		table.put(LANCELOT,SingletonsCreator.bridgeSceneFactory().getLancelot());
		table.put(ROBIN,SingletonsCreator.bridgeSceneFactory().getRobin());
	}
	public String getErrors() {
		return errors;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String input) {
		command = input;
		xmove=0;
		ymove=0;
		nextToken=0;
		errors="";
		scanner.setScannedString(input);
		Token[] tokens= scanner.getTokens();
		Token t=tokens[0];
		if(tokens.length<SAY_MIN) {
			errors="Error. Unexpected end of input.";
			return;
		}
		if(t instanceof Say) {
			t=tokens[SECOND_TOKEN_INDEX];
			if(t instanceof Quote) {
				parseSay(bridgeScene,((Quote) t).getInput()).run();
				//bridgeScene.say(((Quote) t).getInput());
			}
			else {
				errors="Error. Unexpected token" + t +"entered. Quote token was expected.";
				return;
			}
		}
		else if(t instanceof Move){
			if(tokens.length<INT_MIN) {
				errors="Error. Unexpected end of input.";
				return;
			}
			t=tokens[SECOND_TOKEN_INDEX];
			nextToken=THIRD_TOKEN_INDEX;
			if(t instanceof Word) {
				if(table.get(((Word) t).getValue())==null) {
					errors="Error. No avatar with the name: "+t+" arthur, galahad, guard, lancelot, or robin expected.";
					return;
				}
				t=tokens[THIRD_TOKEN_INDEX];
				nextToken=FOUR_TOK_INDEX;
				if(t instanceof NumberToken || t instanceof Plus || t instanceof Minus) {
					xmove=parseSignInt(t,tokens[nextToken]);
					if(tokens.length<nextToken-SECOND_TOKEN_INDEX) {
						errors="Error. Unexpected end of input.";
						return;
					}
					t=tokens[nextToken];
					nextToken++;
					if(t instanceof NumberToken) {
						ymove=((NumberToken) t).getValue();
						//((Avatar) table.get(((Word)tokens[SECOND_TOKEN_INDEX]).getValue())).move(xmove,ymove);
						parseMove((Avatar) table.get(((Word)tokens[SECOND_TOKEN_INDEX]).getValue()),xmove,ymove).run();
					}
					else if(t instanceof Plus || t instanceof Minus) {
						if(tokens.length<nextToken) {
							errors="Error. Unexpected end of input.";
							return;
						}
						ymove= parseSignInt(t,tokens[nextToken]);
						//((Avatar) table.get(((Word)tokens[SECOND_TOKEN_INDEX]).getValue())).move(xmove,ymove);
						parseMove((Avatar) table.get(((Word)tokens[SECOND_TOKEN_INDEX]).getValue()),xmove,ymove).run();
					}
					else {
						errors="Error. Unexpected token: "+t+" Number, Plus, or Minus token expected";
						return;
					}
				}
				else {
					errors="Error. Unexpected token: "+t+" Number, Plus, or Minus token expected";
					return;
				}
			}
			else {
				errors="Error. Unexpected token: "+t+ " Word token expected.";
				return;
			}
		}
		else {
			errors="Error. Unexpected token: "+t+" Move or Say token expected.";
		}
	}
	public int parseSignInt(Token t1,Token t2) {
		if(t1 instanceof Plus) {
			nextToken++;
			return ((NumberToken) t2).getValue();
		}
		else if(t1 instanceof Minus) {
			nextToken++;
			return (MINUS*((NumberToken) t2).getValue());
		}
		else {
			return ((NumberToken)t1).getValue();
		}
	}
	@Visible(false)
	public Table getTable() {
		return table;
	}
	@Tags("parseSay")
	public SayCommand parseSay(BridgeScene scene, String say) {
		return new SayCommand(scene, say);
	}
	@Tags("parseMove")
	public MoveCommand parseMove(Avatar avatar,int x,int y) {
		return new MoveCommand(avatar,x,y);
	}
	@Tags("asynchronousArthur")
	public void animateArthur() {
		Thread thread = new Thread(new AnimatingCommand(animator,(Avatar)table.get(ARTHUR)));
		thread.start();
	}
	@Tags("asynchronousGalahad")
	public void animateGalahad() {
		Thread thread = new Thread(new AnimatingCommand(animator,(Avatar)table.get(GALAHAD)));
		thread.start();
	}
	@Tags("asynchronousLancelot")
	public void animateLancelot() {
		Thread thread = new Thread(new AnimatingCommand(animator,(Avatar)table.get(LANCELOT)));
		thread.start();
	}
	@Tags("asynchronousRobin")
	public void animateRobin() {
		Thread thread = new Thread(new AnimatingCommand(animator,(Avatar)table.get(ROBIN)));
		thread.start();
	}
	@Tags("asynchronousGuard")
	public void animateGuard() {
		Thread thread = new Thread(new AnimatingCommand(animator,(Avatar)table.get(GUARD)));
		thread.start();
	}
}






