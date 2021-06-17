package assignment6;

import main.lisp.evaluator.Environment;
import main.lisp.evaluator.Evaluator;
import main.lisp.parser.terms.NilAtom;
import main.lisp.parser.terms.SExpression;

public class MyAndEvaluator implements Evaluator {

	@Override
	public SExpression eval(SExpression expr, Environment envr) {
		expr = expr.getTail();
		if (expr instanceof NilAtom || expr.getHead() instanceof NilAtom || expr.getTail() instanceof NilAtom) {
			return new NilAtom();
		}
/*		if (!(expr.getTail().getTail() instanceof NilAtom)) {
			throw new IllegalStateException("Too many arguments for operator 'and'");
		}*/
		
		SExpression firstEvaled = expr.getHead().eval(envr);
		SExpression secondEvaled = expr.getTail().getHead().eval(envr);
		
		if (!(firstEvaled instanceof NilAtom) && !(secondEvaled instanceof NilAtom)) {
			if(expr.getTail().getTail().isNIL()) {
				return secondEvaled;
			}
			else {
				return eval(expr, envr);
			}
		}
		else
			return new NilAtom();

	}
}
