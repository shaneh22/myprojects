package assignment6;

import java.util.Optional;

import main.lisp.evaluator.Environment;
import main.lisp.evaluator.function.Function;
import main.lisp.parser.terms.IdentifierAtom;
import main.lisp.parser.terms.SExpression;

public class MyEnvironment extends main.lisp.evaluator.environment.AbstractEnvironment {

	public MyEnvironment() {
		super();
	}
	
	public MyEnvironment(Environment parent) {
		super(parent);
	}
	
	@Override
	public void assign(IdentifierAtom ida, SExpression SEx) {
		put(ida, SEx);
	}

	@Override
	public void assignFun(IdentifierAtom arg0, Function arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Environment copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SExpression> lookup(IdentifierAtom ida) {
		Optional<SExpression> retVal = get(ida);
		return (retVal.isPresent() && getParent() != null) ? getParent().lookup(ida) : retVal;
	}

	@Override
	public Optional<Function> lookupFun(IdentifierAtom arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Environment newChild() {
		return new MyEnvironment(this);
	}

}
