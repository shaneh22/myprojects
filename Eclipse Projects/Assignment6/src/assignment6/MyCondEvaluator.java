package assignment6;

import main.lisp.evaluator.Environment;
import main.lisp.parser.terms.NilAtom;
import main.lisp.parser.terms.SExpression;
import main.lisp.evaluator.Evaluator;

public class MyCondEvaluator implements Evaluator{
	
	@Override
	public SExpression eval(SExpression expr, Environment envr) {
		expr = expr.getTail();
		SExpression tuple = expr.getHead();
		while (! (tuple instanceof NilAtom)) {
			if(!(tuple.getHead().eval(envr) instanceof NilAtom)) {
				SExpression tail = tuple.getTail();
				if(tail instanceof NilAtom){
					return tuple.getHead();
				}
				return tail.getHead().eval(envr);
			}
			expr = expr.getTail();
			if(expr instanceof NilAtom) {
				break;
			}
			tuple = expr.getHead();
		}
		return new NilAtom();
	}
}
