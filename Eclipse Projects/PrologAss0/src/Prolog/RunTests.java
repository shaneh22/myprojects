package Prolog;
import grader.basics.execution.lisp.LispCommandGeneratorSelector;
import gradingTools.comp524f20.assignment0_4.F20Assignment0_4Suite;

public class RunTests {

	static final String PROJECT_LOCATION = "/Users/shaneh22/prolong_workspace/LispGreetings";
	public static void main (String[] args) {
		LispCommandGeneratorSelector.getCommandGenerator().
		setUserBinary("/usr/local/bin/clisp");
//		Tracer.showInfo(true);
//		GraderBasicsTraceUtility.setBufferTracedMessages(false);	
		F20Assignment0_4Suite.setProjectLocation(PROJECT_LOCATION);
		F20Assignment0_4Suite.main(args);
	}
	
}
