package mp.other;

import javax.swing.JSlider;

import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags("ProgressBarCreator")
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames("Slider")
public class ProgressBarCreator implements ProgressBarCreatorI{
	static JSlider slider=new JSlider();
	public JSlider getSlider() {
		return slider;
	}
}
