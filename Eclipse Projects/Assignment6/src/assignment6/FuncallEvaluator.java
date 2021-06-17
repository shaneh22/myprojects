package assignment6;

import main.lisp.evaluator.BuiltinOperationManagerSingleton;
import main.lisp.evaluator.Environment;
import main.lisp.evaluator.Evaluator;
import main.lisp.evaluator.function.Lambda;
import main.lisp.parser.terms.IdentifierAtom;
import main.lisp.parser.terms.SExpression;

public class FuncallEvaluator implements Evaluator {

	@Override
	public SExpression eval(SExpression expr, Environment envr) {
		expr = expr.getTail();
		//System.out.println(expr + "head : "+ expr.getHead());
		SExpression lambda = expr.getHead().eval(envr);
		Environment child = envr.newChild();
		IdentifierAtom []  argNames = ((Lambda) lambda).getArgumentNames();
		for(int i = 0; i < argNames.length; i++) {
			if(expr.getTail() == null) {
				throw new IllegalStateException("Not enough arguments");
			}
			expr = expr.getTail();
			envr.assign(argNames[i], expr.getHead().eval(envr));
			child.assign(argNames[i], expr.getHead().eval(envr));
		}
		return lambda.eval(child);
	}
	
}