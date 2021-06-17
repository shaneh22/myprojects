package mp.graphics;

import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
import util.models.PropertyListenerRegisterer;

@Tags("BridgeScene")
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Arthur","Lancelot","Galahad","Guard","Robin","Gorge","GuardArea","KnightArea","Occupied","KnightTurn","InteractingKnight"})
@EditablePropertyNames()
public interface BridgeScene extends PropertyListenerRegisterer{
	public void origin();
	public Avatar getArthur();
	public Avatar getLancelot();
	public Avatar getGalahad();
	public Avatar getGuard();
	public Avatar getRobin();
	public MPImage getGorge();
	public BoundedShape getGuardArea();
	public BoundedShape getKnightArea();
	public void approach(Avatar knight);
	@Tags("Occupied")
	public boolean getOccupied();
	public boolean getKnightTurn();
	public Avatar getInteractingKnight();
	public void say(String input);
	public void passed();
	public void failed();
	@Tags("scroll")
	public void scroll(int x, int y);
	//assertions
	public boolean preApproach();
	public boolean preSay();
	public boolean prePassed();
	public boolean preFailed();
}
