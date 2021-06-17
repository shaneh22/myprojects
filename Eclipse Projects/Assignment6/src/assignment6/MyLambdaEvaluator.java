package assignment6;


import main.lisp.evaluator.Environment;
import main.lisp.evaluator.Evaluator;
import main.lisp.evaluator.function.LambdaFactory;
import main.lisp.parser.terms.IdentifierAtom;
import main.lisp.parser.terms.NilAtom;
import main.lisp.parser.terms.SExpression;

public class MyLambdaEvaluator implements Evaluator {

	@Override
	public SExpression eval(SExpression expr, Environment envr) {
		expr = expr.getTail();
		IdentifierAtom[] heads = headsHelper(expr.getHead());
		return LambdaFactory.newInstance(heads, expr.getTail());
	}
	
	public IdentifierAtom [] headsHelper(SExpression head) {
		if(head.isNIL() || head.getHead().isNIL()) {
			IdentifierAtom [] result = {};
			return result;
		}
		else {
			SExpression [] result = headsHelper(head.getTail());
			if (result == null) {
				IdentifierAtom[] array = {(IdentifierAtom) head.getHead()};
				return array;
			}
			IdentifierAtom [] array = new IdentifierAtom[result.length + 1];
			array[0] = (IdentifierAtom) head.getHead();
			for(int i = 0; i < result.length; i++) {
				array[i+1] = (IdentifierAtom) result[i];
			}
			return array;
		}
	}

}
