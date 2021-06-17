package assignment6;

import main.lisp.evaluator.Environment;
import main.lisp.parser.terms.ExpressionFactory;
import main.lisp.parser.terms.NilAtom;
import main.lisp.parser.terms.SExpression;
import main.lisp.evaluator.Evaluator;

public class MyListEvaluator implements Evaluator{
	
	@Override
	public SExpression eval(SExpression expr, Environment envr) {
		expr = expr.getTail();
		return recursiveListGenerator(expr, envr);
	}
	
	public SExpression recursiveListGenerator(SExpression expr, Environment envr) {
		if(expr instanceof NilAtom) {
			return new NilAtom();
		}
		SExpression head = expr.getHead().eval(envr);
		SExpression tail = recursiveListGenerator(expr.getTail(), envr);
		return ExpressionFactory.newInstance(head, tail);
	}
}