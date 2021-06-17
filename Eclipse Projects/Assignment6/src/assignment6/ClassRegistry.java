package assignment6;

import main.lisp.evaluator.Environment;
import main.lisp.evaluator.Evaluator;
import main.lisp.evaluator.OperationRegisterer;
import main.lisp.evaluator.parallel.args.ArgumentEvaluator;
import main.lisp.evaluator.parallel.pool.ThreadPool;
import main.lisp.evaluator.parallel.pool.ThreadPoolWorker;
import main.lisp.parser.terms.IdentifierAtom;
import main.lisp.parser.terms.SExpression;
import main.util.parallel.Joiner;
import main.util.parallel.MutableJoiner;

public class ClassRegistry implements main.ClassRegistryA4{

	@Override
	public Class<? extends Evaluator> getAndEvaluator() {
		return MyAndEvaluator.class;
	}

	@Override
	public Class<? extends Evaluator> getCondEvaluator() {
		return MyCondEvaluator.class;
	}

	@Override
	public Class<? extends OperationRegisterer> getCustomOperationRegisterer() {
		return MyOperationRegisterer.class;
	}

	@Override
	public Class<? extends Evaluator> getEvalEvaluator() {
		return MyEvalEvaluator.class;
	}

	@Override
	public Class<? extends Evaluator> getGTEEvaluator() {
		return MyGreaterThanOrEqualEvaluator.class;
	}

	@Override
	public Class<? extends Evaluator> getGTEvaluator() {
		return MyGreaterThanEvaluator.class;
	}

	@Override
	public Class<? extends Evaluator> getLTEEvaluator() {
		return MyLessThanOrEqualEvaluator.class;
	}

	@Override
	public Class<? extends Evaluator> getLTEvaluator() {
		return MyLessThanEvaluator.class;
	}

	@Override
	public Class<? extends Evaluator> getListEvaluator() {
		return MyListEvaluator.class;
	}

	@Override
	public Class<? extends Evaluator> getLoadEvaluator() {
		return MyLoadEvaluator.class;
	}

	@Override
	public Class<?> getMain() {
		return MyMain.class;
	}

	@Override
	public Class<? extends Evaluator> getNotEvaluator() {
		return MyNotEvaluator.class;
	}

	@Override
	public Class<? extends Evaluator> getOrEvaluator() {
		return MyOrEvaluator.class;
	}

	@Override
	public Class<? extends Evaluator> getQuoteEvaluator() {
		return MyQuoteEvaluator.class;
	}

	@Override
	public Class<? extends SExpression> getStringFormattingSExpression() {
		return Task1.class;
	}

	@Override
	public Class<? extends OperationRegisterer> getAdditionalStatefulOperationRegisterer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Evaluator> getDefparameterEvaluator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Evaluator> getDefvarEvaluator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Evaluator> getFunctionEvaluator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Environment> getNestedDynamicEnvironment() {
		return MyEnvironment.class;
	}

	@Override
	public Class<? extends Evaluator> getBasicFuncallEvaluator() {
		return FuncallEvaluator.class;
	}

	@Override
	public Class<? extends IdentifierAtom> getIdentifierAtomWithLookup() {
		return MyEval.class;
	}

	@Override
	public Class<? extends SExpression> getLambdaCallingSExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Evaluator> getLambdaEvaluator() {
		return MyLambdaEvaluator.class;
	}

	@Override
	public Class<? extends Environment> getNestedLexicalEnvironment() {
		return MyEnvironment.class;
	}

	@Override
	public Class<? extends Evaluator> getSetqEvaluator() {
		return MySetQEvaluator.class;
	}

	@Override
	public Class<? extends OperationRegisterer> getStatefulOperationRegisterer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends ArgumentEvaluator> getArgumentEvaluator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getClientMain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Evaluator> getCompleteFuncallEvaluator() {
		return FuncallEvaluator.class;
	}

	@Override
	public Class<? extends Evaluator> getCurryEvaluator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Evaluator> getDefcurryEvaluator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Evaluator> getDefunEvaluator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Evaluator> getEagerAnd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Evaluator> getEagerCurry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Evaluator> getEagerDefcurry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Evaluator> getEagerFuncall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Evaluator> getEagerList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends OperationRegisterer> getEagerOperationRegisterer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Evaluator> getEagerOr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends SExpression> getEagerSExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends SExpression> getFunctionCallingSExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Joiner> getImmutableJoiner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Evaluator> getLetEvaluator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends MutableJoiner> getMutableJoiner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getServerMain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends ThreadPool> getThreadPool() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends ThreadPoolWorker> getThreadPoolWorker() {
		// TODO Auto-generated method stub
		return null;
	}

}
