package main;

import mp.tokens.Token;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags({"ScannerBean"})
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"ScannedString","Tokens","Errors"})
@EditablePropertyNames({"ScannedString"})
public interface ScannerI {
	public String getScannedString();
	public void setScannedString(String input);
	public int indexOf(String s, char ch, int index);
	public int indexOfNot(String s, char ch, int index);
	public void scanString(String s);
	public boolean isNumber(String token);
	public Token[] getTokens();
	public String getErrors();
}
