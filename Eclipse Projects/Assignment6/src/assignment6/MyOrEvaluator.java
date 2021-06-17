package assignment6;

import main.lisp.evaluator.Environment;
import main.lisp.evaluator.Evaluator;
import main.lisp.parser.terms.NilAtom;
import main.lisp.parser.terms.SExpression;

public class MyOrEvaluator implements Evaluator {

	@Override
	public SExpression eval(SExpression expr, Environment envr) {
		expr = expr.getTail();
		if (expr instanceof NilAtom) {
			return new NilAtom();
		}
		/*
		if (!(expr.getTail().getTail() instanceof NilAtom)) {
			throw new IllegalStateException("Too many arguments for operator 'or'");
		}*/
		
		SExpression firstEvaled = expr.getHead().eval(envr);
		SExpression secondEvaled = expr.getTail().getHead().eval(envr);
		
		if (!(firstEvaled instanceof NilAtom))
			return firstEvaled;
		else if(!(secondEvaled instanceof NilAtom)) 
			return secondEvaled;
		else {
			if(expr.getTail().getTail().isNIL()) {
				return new NilAtom();
			}
			else {
				return eval(expr, envr);
			}
		}

	}
}
