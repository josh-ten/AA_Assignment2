import java.util.ArrayList;

/**
 * Stores the name and value of attributes, as well as a list of all the possible
 * values of the attribute, that other characters hold
 * @author Josh Ten
 *
 */
public class Attribute implements Comparable<Attribute> {
	private String name;
	private String value;
	public ArrayList<String> possibleValues;
	
	public Attribute(String name) {
		this.name = name;
		possibleValues = new ArrayList<String>();
	}
	
	/**
	 * Add a new value if it doesn't already exist
	 * @param value
	 * @return if the value was added
	 */
	public boolean addValue(String value) {
		for (String v: possibleValues) {
			if (v.equals(value)) return false;
		}
		possibleValues.add(value);
		return true;
	}

	/**
	 * Adds the value if it doesn't exist and sets it as the value
	 * @param newValue
	 */
	public void setValue(String newValue) {
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
