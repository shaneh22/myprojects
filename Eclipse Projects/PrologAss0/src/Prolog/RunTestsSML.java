package Prolog;
import gradingTools.comp524f20.assignment3.F20Assignment3Suite;
import grader.basics.execution.sml.SMLCommandGeneratorSelector;

public class RunTestsSML {

	static final String PROJECT_LOCATION = "/Users/shaneh22/prolong_workspace/Assignment3";
	public static void main (String[] args) {
		//F20Assignment0_3Suite.smlIsBatFile(false);
		SMLCommandGeneratorSelector.getCommandGenerator().
		setUserBinary("/usr/local/smlnj/bin/sml");
//		Tracer.showInfo(true);
//		GraderBasicsTraceUtility.setBufferTracedMessages(false);	
		F20Assignment3Suite.setProjectLocation(PROJECT_LOCATION);
		F20Assignment3Suite.main(args);
	}
	
}
