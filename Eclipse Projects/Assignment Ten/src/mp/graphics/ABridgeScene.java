package mp.graphics;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import util.annotations.EditablePropertyNames;
import util.annotations.ObserverRegisterer;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
import util.annotations.Visible;

@Tags("BridgeScene")
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"Arthur","Lancelot","Galahad","Guard","Robin","Gorge","GuardArea","KnightArea","Occupied","KnightTurn","propertyChangeListeners" })
@EditablePropertyNames()
public class ABridgeScene implements BridgeScene{
		static final int X_POS= 100;
		static final int Y_POS= 200;
		static final int GUARD_POS=300;
		static final int GORGE_X_POS=375;
		static final int GORGE_Y_POS=300;
		static final int DIAMETER=100;
		static final int KNIGHT_AREA_X = 150;
		static final int GUARD_AREA_X = 265;
		static final int AREA_Y= 400;
		static final int KNIGHT_X_POS=175;
		static final int KNIGHT_Y_POS=300;
		static final int PASSED_MOVE = 600;
		static final int FAILED_MOVE_X = 350;
		static final int FAILED_MOVE_Y = 225;
		static final double FAILED_SCALE=0.5;
		static final int X_INIT=10;
		BoundedShape guardArea= new Circle(GUARD_AREA_X,AREA_Y,DIAMETER);
		BoundedShape knightArea= new Circle(KNIGHT_AREA_X,AREA_Y,DIAMETER);
		MPImage gorge= new GorgeAndBridge(GORGE_X_POS,GORGE_Y_POS);
		Avatar arthur=new BaseAvatar(new ArthurHead(X_INIT,0));
		Avatar galahad=new BaseAvatar(new GalahadHead(X_POS,0));
		Avatar guard = new BaseAvatar(new GuardHead(GUARD_POS,GUARD_POS));
		Avatar lancelot = new BaseAvatar(new LancelotHead(X_POS,Y_POS));
		Avatar robin = new BaseAvatar(new RobinHead(X_INIT,Y_POS));
		List<PropertyChangeListener> propertyChangeListeners= new ArrayList<PropertyChangeListener>();
		
		public void origin() {
			arthur.place(new Point(X_INIT,0));
			galahad.place(new Point(X_POS,0));
			guard.place(new Point(GUARD_POS,GUARD_POS));
			lancelot.place(new Point(X_POS,Y_POS));
			robin.place(new Point(X_INIT,Y_POS));
		}
		
		Avatar interactingKnight = null;
		boolean occupied=false;
		boolean knightTurn=false;
		public ABridgeScene() {}
		public Avatar getArthur() {
			return arthur;
		}
		public Avatar getGalahad() {
			return galahad;
		}
		public Avatar getGuard() {
			return guard;
		}
		public Avatar getLancelot() {
			return lancelot;
		}
		public Avatar getRobin() {
			return robin;
		}
		public MPImage getGorge() {
			return gorge;
		}
		public BoundedShape getGuardArea() {
			return guardArea;
		}
		public BoundedShape getKnightArea() {
			return knightArea;
		}
		public boolean preApproach() {
			return !occupied;
		}
		public void approach(Avatar knight) {
			assert preApproach();
			int xpos=knight.getHead().getX();
			int ypos=knight.getHead().getY();
			//if(!occupied) {
				knight.move(KNIGHT_X_POS-xpos, KNIGHT_Y_POS-ypos);
				occupied=true;
				interactingKnight=knight;
				notifyAllListeners(new PropertyChangeEvent (this,"this", "approach", false));
				notifyAllListeners(new PropertyChangeEvent (this,"this", "say", true));
				notifyAllListeners(new PropertyChangeEvent (this,"this", "failed", true));
				if(!knightTurn) {
					notifyAllListeners(new PropertyChangeEvent (this,"this", "passed", true));
				}

			//}
		}
		@Tags("Occupied")
		public boolean getOccupied() {
			return occupied;
		}
		public boolean getKnightTurn() {
			return knightTurn;
		}
		@Visible(false)
		public Avatar getInteractingKnight() {
			if(occupied) {
				return interactingKnight;
			}
			return null;
		}
		public boolean preSay() {
			return occupied;
		}
		public void say(String input) {
			assert preSay();
			//if(occupied) {
				if(knightTurn) {
					interactingKnight.getStringShape().setText(input);
					knightTurn=false;
					notifyAllListeners(new PropertyChangeEvent (this,"this", "passed", true));
				}
				else {
					guard.getStringShape().setText(input);
					knightTurn=true;
					notifyAllListeners(new PropertyChangeEvent (this,"this", "passed", false));
				}
			//}
		}
		public boolean prePassed() {
			return occupied && !knightTurn;
		}
		public void passed() {
			assert prePassed();
			//if(occupied && !knightTurn) {
				interactingKnight.move(PASSED_MOVE, 0);
				occupied=false;
				notifyAllListeners(new PropertyChangeEvent (this,"this", "approach", true));
				notifyAllListeners(new PropertyChangeEvent (this,"this", "say", false));
				notifyAllListeners(new PropertyChangeEvent (this,"this", "failed", false));
				notifyAllListeners(new PropertyChangeEvent (this,"this", "passed", false));
			//}
		}
		public boolean preFailed() {
			return occupied;
		}
		public void failed() {
			assert preFailed();
			//if(occupied) {
				if(knightTurn) {
					guard.move(FAILED_MOVE_X, FAILED_MOVE_Y);
					//guard.scale(FAILED_SCALE);
				}
				else {
					interactingKnight.move(FAILED_MOVE_X, FAILED_MOVE_Y);
					//interactingKnight.scale(FAILED_SCALE);
					occupied=false;
					notifyAllListeners(new PropertyChangeEvent (this,"this", "approach", true));
					notifyAllListeners(new PropertyChangeEvent (this,"this", "say", false));
					notifyAllListeners(new PropertyChangeEvent (this,"this", "failed", false));
					notifyAllListeners(new PropertyChangeEvent (this,"this", "passed", false));
				}
			//}
		}
		@Tags("scroll")
		public void scroll(int x,int y) {
			//the scroll unit is just 1
			arthur.move(-x, -y);
			galahad.move(-x,-y);
			guard.move(-x, -y);
			lancelot.move(-x, -y);
			robin.move(-x, -y);
			gorge.setX(gorge.getX()-x);
			gorge.setY(gorge.getY()-y);
			knightArea.setX(knightArea.getX()-x);
			knightArea.setY(knightArea.getY()-y);
			guardArea.setX(guardArea.getX()-x);
			guardArea.setY(guardArea.getY()-y);
		}
		@Visible(false)
		public static void addPropertyChangeListener(BridgeScene bridgeScene,PropertyChangeListener aListener) {
			BaseAvatar.addPropertyChangeListener(bridgeScene.getArthur(),aListener);
			BaseAvatar.addPropertyChangeListener(bridgeScene.getGalahad(),aListener);
			BaseAvatar.addPropertyChangeListener(bridgeScene.getGuard(),aListener);
			BaseAvatar.addPropertyChangeListener(bridgeScene.getLancelot(),aListener);
			BaseAvatar.addPropertyChangeListener(bridgeScene.getRobin(),aListener);
			bridgeScene.getGorge().addPropertyChangeListener(aListener);
			bridgeScene.getGuardArea().addPropertyChangeListener(aListener);
			bridgeScene.getKnightArea().addPropertyChangeListener(aListener);
			bridgeScene.addPropertyChangeListener(aListener);
		}
		public List<PropertyChangeListener> getPropertyChangeListeners() {
			return propertyChangeListeners;
		}
		@Override
		public void addPropertyChangeListener(PropertyChangeListener listener) {
			propertyChangeListeners.add(listener);	
		}
		public void notifyAllListeners(PropertyChangeEvent evt) {
			for(int i=0;i<propertyChangeListeners.size();i++) {
				propertyChangeListeners.get(i).propertyChange(evt);
			}
		}
}
