package assignment6;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import main.lisp.evaluator.Environment;
import main.lisp.evaluator.Evaluator;
import main.lisp.parser.terms.NilAtom;
import main.lisp.parser.terms.SExpression;
import main.lisp.parser.terms.StringAtom;
import main.lisp.parser.terms.TAtom;

public class MyLoadEvaluator implements Evaluator{

	@Override
	public SExpression eval(SExpression expr, Environment envr) {
		expr = expr.getTail();
		if(expr instanceof NilAtom) {
			return new NilAtom();
		}
		if (!(expr.getTail() instanceof NilAtom)) {
			throw new IllegalStateException("Too many arguments for operator 'load'");
		}
		StringAtom evaledExpr = (StringAtom) expr.getHead().eval(envr);
		String pathDestination = evaledExpr.getValue();
		Path path = Paths.get("", pathDestination);
		Charset charset = Charset.forName("ISO-8859-1");
		try {
			List<String> lines = Files.readAllLines(path, charset);
			for (String line : lines) {
		        try {
		        	main.lisp.interpreter.InterpreterModelSingleton.get().newInput(line);
		        }
		        catch(IllegalStateException e) {
		        	return new NilAtom();
		        }
		    }
			return new TAtom();
		}
		catch (IllegalStateException | IOException e) {
			return new NilAtom();
		}
	}

}
