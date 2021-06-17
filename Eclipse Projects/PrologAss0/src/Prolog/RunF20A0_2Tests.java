package Prolog;

import gradingTools.comp524f20.assignment0_2.F20Assignment0_2Suite;
import trace.grader.basics.GraderBasicsTraceUtility;
import util.trace.Tracer;
import grader.basics.execution.prolog.PrologCommandGeneratorSelector;

public class RunF20A0_2Tests {
	static final String PROJECT_LOCATION = "/Users/shaneh22/prolong_workspace/Greetings";
	
	public static void main (String[] args) {
		PrologCommandGeneratorSelector.getCommandGenerator().
		setUserBinary("/usr/local/bin/swipl");
//		Tracer.showInfo(true);
//		GraderBasicsTraceUtility.setBufferTracedMessages(false);	
		F20Assignment0_2Suite.setProjectLocation(PROJECT_LOCATION);
		F20Assignment0_2Suite.main(args);
	}
}
