package assignment6;

import main.lisp.evaluator.Environment;
import main.lisp.evaluator.Evaluator;
import main.lisp.parser.terms.DecimalAtom;
import main.lisp.parser.terms.IntegerAtom;
import main.lisp.parser.terms.NilAtom;
import main.lisp.parser.terms.SExpression;
import main.lisp.parser.terms.TAtom;

public class MyLessThanEvaluator implements Evaluator {

	@Override
	public SExpression eval(SExpression expr, Environment envr) {
		expr = expr.getTail();
		if (expr instanceof NilAtom || expr.getHead() instanceof NilAtom || expr.getTail() instanceof NilAtom) {
			throw new IllegalStateException("Missing arguments for operator '<'");
		}
		if (!(expr.getTail().getTail() instanceof NilAtom)) {
			throw new IllegalStateException("Too many arguments for operator '<'");
		}
		
		SExpression firstEvaled = expr.getHead().eval(envr);
		SExpression secondEvaled = expr.getTail().getHead().eval(envr);
		
		IntegerAtom firstInt = null;
		IntegerAtom secondInt = null;
		DecimalAtom firstDec = null;
		DecimalAtom secondDec = null;
		
		int correctArgs = 0;
		
		if (firstEvaled instanceof IntegerAtom) {
			firstInt = (IntegerAtom)firstEvaled;
			correctArgs++;
		}
		if (firstEvaled instanceof DecimalAtom) {
			firstDec = (DecimalAtom)firstEvaled;
			correctArgs++;
		}
		
		if (secondEvaled instanceof IntegerAtom) {
			secondInt = (IntegerAtom)secondEvaled;
			correctArgs++;
		}
		if (secondEvaled instanceof DecimalAtom) {
			secondDec = (DecimalAtom)secondEvaled;
			correctArgs++;
		}
		
		if (correctArgs != 2) {
			throw new IllegalStateException("Arguments for operator '<' must both evaluate to numbers");
		}
		
		if(firstInt != null && secondInt != null) {
			boolean result = firstInt.getValue() < secondInt.getValue();
			return result ? new TAtom() : new NilAtom();
		} 
		else {
			boolean result = false;
			if(firstInt != null) {
				if(secondDec != null) {
					result = firstInt.getValue() < secondDec.getValue();
				}
			}
			else if(secondInt != null){
				if(firstDec != null) {
					result = firstDec.getValue() < secondInt.getValue();
				}
			}
			else if(secondDec != null && firstDec != null) {
				result = firstDec.getValue() < secondDec.getValue();
			}
			return result ? new TAtom() : new NilAtom();
		}
	}

}
