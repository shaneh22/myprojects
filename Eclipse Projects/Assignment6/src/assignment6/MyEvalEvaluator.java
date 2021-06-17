package assignment6;

import main.lisp.evaluator.Environment;
import main.lisp.parser.terms.NilAtom;
import main.lisp.parser.terms.SExpression;
import main.lisp.evaluator.Evaluator;

public class MyEvalEvaluator implements Evaluator{
	
	@Override
	public SExpression eval(SExpression expr, Environment envr) {
		expr = expr.getTail();
		if (!(expr.getTail() instanceof NilAtom)) {
			throw new IllegalStateException("Too many arguments for operator 'eval'");
		}
		
		SExpression evaledExpression = expr.getHead().eval(envr);
		
		return evaledExpression.eval(envr);

	}
}