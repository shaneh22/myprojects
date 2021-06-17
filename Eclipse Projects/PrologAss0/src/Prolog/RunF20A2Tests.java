package Prolog;

import gradingTools.comp524f20.assignment2.F20Assignment2Suite;
import trace.grader.basics.GraderBasicsTraceUtility;
import util.trace.Tracer;
import grader.basics.execution.prolog.PrologCommandGeneratorSelector;

public class RunF20A2Tests {
	static final String PROJECT_LOCATION = "/Users/shaneh22/prolong_workspace/Assignment2";
	
	public static void main (String[] args) {
		PrologCommandGeneratorSelector.getCommandGenerator().
		setUserBinary("/usr/local/bin/swipl");
//		Tracer.showInfo(true);
//		GraderBasicsTraceUtility.setBufferTracedMessages(false);	
		F20Assignment2Suite.setProjectLocation(PROJECT_LOCATION);
		F20Assignment2Suite.main(args);
	}
}
