package assignment6;
import main.Main;

public class MyMain {

	public static void main(String[] args) {
		main.lisp.parser.terms.ExpressionFactory.setClass(Task1.class);
		new MyOperationRegisterer().registerOperations();
		main.lisp.evaluator.environment.EnvironmentFactory.setClass(MyEnvironment.class);
		main.lisp.parser.terms.IdentifierAtomFactory.setClass(MyEval.class);
		main.lisp.evaluator.ExpressionEvaluatorFactory.setClass(C.class);
		main.lisp.interpreter.InterpreterModelFactory.setClass(main.lisp.interpreter.ObservableLispInterpreterWithEnvironment.class);
		main.Main.main(args);
	}
	
}
