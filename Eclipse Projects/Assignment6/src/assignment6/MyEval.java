package assignment6;

import main.lisp.evaluator.Environment;
import main.lisp.parser.terms.SExpression;
import main.lisp.scanner.tokens.Token;

public class MyEval extends main.lisp.parser.terms.IdentifierAtom {

	public MyEval(String string) {
		super(string);
	}
	public MyEval(Token token) {
		super(token);
	}
	
	public SExpression eval(Environment envr) {
		return envr.lookup(this).get().eval(envr);
	}
	
}
