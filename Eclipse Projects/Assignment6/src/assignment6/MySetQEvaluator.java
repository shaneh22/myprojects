package assignment6;

import main.lisp.evaluator.Environment;
import main.lisp.evaluator.Evaluator;
import main.lisp.parser.terms.IdentifierAtom;
import main.lisp.parser.terms.SExpression;

public class MySetQEvaluator implements Evaluator{

	@Override
	public SExpression eval(SExpression expr, Environment envr) {
		expr = expr.getTail();
		IdentifierAtom e1 = (IdentifierAtom) expr.getHead();
		//SExpression evaledE2 = expr.getTail().getHead().eval(envr);
		SExpression evaledE2 = expr.getTail().getHead();
		envr.assign(e1, evaledE2);
		return evaledE2; 
	}

}
