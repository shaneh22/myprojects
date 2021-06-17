package mp.other;

import java.util.ArrayList;

import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
@Tags("Table")
@StructurePattern(StructurePatternNames.MAP_PATTERN)
public class ATable implements Table{
	ArrayList<String> keys = new ArrayList<String>();
	ArrayList<Object> values = new ArrayList<>();
	public ATable(){}
	public ATable(ArrayList<String> inputK, ArrayList<Object> inputV) {
		keys=inputK;
		values=inputV;
	}
	public void put(String key, Object val) {
		if(!(key==null) && !(val==null) ) {
			if(get(key)==null) {
				keys.add(key);
				values.add(val);
			}
			else {
				values.set(keys.indexOf(key),val);
			}
		}
	}
	public Object get(String key) {
		if(keys.contains(key)) {
			return values.get(keys.indexOf(key));
		}
		return null;
	}
}
