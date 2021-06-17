package mp.tokens;

import util.annotations.Tags;

@Tags("Token")
public class AToken implements Token{
	protected String input;
	public AToken(String initInput) {
		input=initInput;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String initInput) {
		input=initInput;
	}
}
