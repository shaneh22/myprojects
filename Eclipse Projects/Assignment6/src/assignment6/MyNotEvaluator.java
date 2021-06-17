package assignment6;

import main.lisp.evaluator.Environment;
import main.lisp.evaluator.Evaluator;
import main.lisp.parser.terms.NilAtom;
import main.lisp.parser.terms.SExpression;
import main.lisp.parser.terms.TAtom;

public class MyNotEvaluator implements Evaluator {

	@Override
	public SExpression eval(SExpression expr, Environment envr) {
		expr = expr.getTail();
		if (!(expr.getTail() instanceof NilAtom)) {
			throw new IllegalStateException("Too many arguments for operator 'not'");
		}
		
		SExpression evaledExpr = expr.getHead().eval(envr);
		
		if (evaledExpr instanceof NilAtom)
			return new TAtom();
		else
			return new NilAtom();

	}
}
