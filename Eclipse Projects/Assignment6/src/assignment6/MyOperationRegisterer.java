package assignment6;

import main.lisp.evaluator.Evaluator;

public class MyOperationRegisterer implements main.lisp.evaluator.OperationRegisterer {

	@Override
	public void registerOperations() {
		Evaluator lessThan = new MyLessThanEvaluator();
		Evaluator greaterThan = new MyGreaterThanEvaluator();
		Evaluator greaterEq = new MyGreaterThanOrEqualEvaluator();
		Evaluator lessEq = new MyLessThanOrEqualEvaluator();
		Evaluator and = new MyAndEvaluator();
		Evaluator or = new MyOrEvaluator();
		Evaluator not = new MyNotEvaluator();
		Evaluator quote = new MyQuoteEvaluator();
		Evaluator eval = new MyEvalEvaluator();
		Evaluator cond = new MyCondEvaluator();
		Evaluator list = new MyListEvaluator();
		Evaluator load = new MyLoadEvaluator();
		Evaluator setq = new MySetQEvaluator();
		Evaluator lambda = new MyLambdaEvaluator();
		Evaluator funcall = new FuncallEvaluator();
		main.lisp.evaluator.BuiltinOperationManagerSingleton.get().registerEvaluator("<", lessThan);
		main.lisp.evaluator.BuiltinOperationManagerSingleton.get().registerEvaluator(">", greaterThan);
		main.lisp.evaluator.BuiltinOperationManagerSingleton.get().registerEvaluator(">=", greaterEq);
		main.lisp.evaluator.BuiltinOperationManagerSingleton.get().registerEvaluator("<=", lessEq);
		main.lisp.evaluator.BuiltinOperationManagerSingleton.get().registerEvaluator("and", and);
		main.lisp.evaluator.BuiltinOperationManagerSingleton.get().registerEvaluator("or", or);
		main.lisp.evaluator.BuiltinOperationManagerSingleton.get().registerEvaluator("not", not);
		main.lisp.evaluator.BuiltinOperationManagerSingleton.get().registerEvaluator("quote", quote);
		main.lisp.evaluator.BuiltinOperationManagerSingleton.get().registerEvaluator("eval", eval);
		main.lisp.evaluator.BuiltinOperationManagerSingleton.get().registerEvaluator("cond", cond);
		main.lisp.evaluator.BuiltinOperationManagerSingleton.get().registerEvaluator("list", list);
		main.lisp.evaluator.BuiltinOperationManagerSingleton.get().registerEvaluator("load", load);
		main.lisp.evaluator.BuiltinOperationManagerSingleton.get().registerEvaluator("setq", setq);
		main.lisp.evaluator.BuiltinOperationManagerSingleton.get().registerEvaluator("lambda", lambda);
		main.lisp.evaluator.BuiltinOperationManagerSingleton.get().registerEvaluator("funcall", funcall);
	}

}
