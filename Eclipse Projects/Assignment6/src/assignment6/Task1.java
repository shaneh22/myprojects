package assignment6;

import main.lisp.parser.terms.BasicExpression;
import main.lisp.parser.terms.SExpression;
import main.lisp.parser.terms.AbstractSExpression;


public class Task1 extends BasicExpression {

	public Task1(SExpression head, SExpression tail) {
		super(head, tail);
	}

	@Override
	public boolean isList() {
		if(this.getTail() == null) {
			return true;
		}
		else {
			return this.getTail().isList();
		}
	}
	
	@Override
	public String toStringAsSExpressionDeep() {
		return "(" + this.getHead().toStringAsSExpressionDeep() + " " + "." + " " + this.getTail().toStringAsSExpressionDeep() + ")";
	}
	
	@Override 
	public String toStringAsSExpression() {
		return "(" + this.getHead().toString() + " " + "." + " " + this.getTail().toString() + ")";
	}
	
	@Override 
	public String toStringAsList() {
		return "(" + this.toStringAsListHelperPublic() + ")";
	}
	
	@Override
	public String toStringAsListHelperPublic() {
		return this.getTail().isNIL() ? this.getHead().toString() : this.getHead().toString() + " " + ((AbstractSExpression) this.getTail()).toStringAsListHelperPublic(); 
	}
	
	@Override
	public String toString() {
		return this.isList() ? this.toStringAsList() : this.toStringAsSExpression();
	}
	
}
