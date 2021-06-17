package;

import gradingTools.comp524f20.assignment0_2.F20Assignment0_2Suite;
import trace.grader.basics.GraderBasicsTraceUtility;
import util.trace.Tracer;
public class RunF20A0_2Tests {
	static final String PROJECT_LOCATION = "/Users/shaneh22/prolong_workspace/Greetings";
	public static void main (String[] args) {
//		Tracer.showInfo(true);
//		GraderBasicsTraceUtility.setBufferTracedMessages(false);	
		F20Assignment0_2Suite.setProjectLocation(PROJECT_LOCATION);
		F20Assignment0_2Suite.main(args);
	}
}
