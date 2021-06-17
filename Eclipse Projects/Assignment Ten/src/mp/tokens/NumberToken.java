package mp.tokens;

import util.annotations.Tags;

@Tags({"Number"})
public interface NumberToken extends Token{
	public int getValue();
}
