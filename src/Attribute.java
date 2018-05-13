import java.util.ArrayList;

public class Attribute implements Comparable<Attribute> {
	private String name;
	private String value;
	public ArrayList<String> possibleValues;
	public ArrayList<Boolean> ruledOut;
	
	public Attribute(String name) {
		this.name = name;
		possibleValues = new ArrayList<String>();
		ruledOut = new ArrayList<Boolean>();
	}
	
	public boolean addValue(String value) {
		for (String v: possibleValues) {
			if (v.compareTo(value) == 0) return false;
		}
		possibleValues.add(value);
		ruledOut.add(false);
		return true;
	}
	
	public void setValue(String newValue) {
		//Adds the value if it doesn't already exist
		addValue(newValue);
		value = newValue;
	}
	
	@Override
	public String toString() {
		String output = name + " (" + value + "): ";
		for (String v: possibleValues) {
			output += v + " ";
		}
		return output;
	}
	
	public Attribute clone() {
		Attribute clone = new Attribute(name);
		for (String s: possibleValues) {
			clone.addValue(s);
		}
		clone.setValue(value);
		return clone;
	}
	
	@Override
	public int compareTo(Attribute other) {
		return name.compareTo(other.name);
	}

	public int compareTo(String otherName) {
		return name.compareTo(otherName);
	}

	public String getName() { return name; }
	public String getValue() { return value; }
	
}
