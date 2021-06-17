package assignment6;

import main.lisp.evaluator.BasicExpressionEvaluator;
import main.lisp.evaluator.BuiltinOperationManagerSingleton;
import main.lisp.evaluator.Environment;
import main.lisp.evaluator.Evaluator;
import main.lisp.evaluator.function.Lambda;
import main.lisp.parser.terms.IdentifierAtom;
import main.lisp.parser.terms.SExpression;

public class C extends BasicExpressionEvaluator{
	
	@Override
	public SExpression eval(SExpression expr, Environment environment) {
		if (expr.getHead() instanceof IdentifierAtom) {
			String operator = ((IdentifierAtom)expr.getHead()).getValue();
			Evaluator eval = BuiltinOperationManagerSingleton.get().getEvaluator(operator);
			if (eval == null) {
				throw new IllegalStateException("No evaluator registered for operator '" + operator + "'");
			}
			return eval.eval(expr, environment);
		} 
		else if(expr.getHead().eval(environment) instanceof Lambda) {
			SExpression lambda = BuiltinOperationManagerSingleton.get().getEvaluator(expr.getHead().getHead().toString()).eval(expr.getHead(), environment);
			MyEnvironment child = (MyEnvironment) environment.newChild();
			IdentifierAtom []  argNames = ((Lambda) lambda).getArgumentNames();
			for(int i = 0; i < argNames.length; i++) {
				if(expr.getTail() == null) {
					throw new IllegalStateException("Not enough arguments");
				}
				expr = expr.getTail();
				environment.assign(argNames[i], expr.getHead().eval(environment));
				child.assign(argNames[i], expr.getHead().eval(environment));
			}
			return lambda.eval(child);
		}
		else {
			throw new IllegalStateException("Expression does not start with an operator");
		}
	}
	
}
