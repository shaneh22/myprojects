package assignment1;

import weka.classifiers.Classifier;

import gradingTools.comp524f20.assignment1.WekaUtil;

public class SocialDistanceClassifierFactory {
	static Classifier singleton;
	static final String DATA = "SafeSocialization.txt";
	public static Classifier getSingleton() {
		if(singleton == null) {
			singleton = new weka.classifiers.trees.J48();
			WekaUtil.buildTreeModel(singleton, DATA);
		}
		return singleton;
	}
}
