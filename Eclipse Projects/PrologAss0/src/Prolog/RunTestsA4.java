package Prolog;
import grader.basics.execution.lisp.LispCommandGeneratorSelector;
import gradingTools.comp524f20.assignment4.F20Assignment4Suite;
import trace.grader.basics.GraderBasicsTraceUtility;
import util.trace.Tracer;

public class RunTestsA4 {

	static final String PROJECT_LOCATION = "/Users/shaneh22/prolong_workspace/Assignment4";
	public static void main (String[] args) {
		LispCommandGeneratorSelector.getCommandGenerator().
		setUserBinary("/usr/local/bin/clisp");
		//Tracer.showInfo(true);
		//GraderBasicsTraceUtility.setBufferTracedMessages(false);	
		F20Assignment4Suite.setProjectLocation(PROJECT_LOCATION);
		F20Assignment4Suite.main(args);
	}
	
}

