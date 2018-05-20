import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Store the data for one of the game characters for the player to 
 * select from
 * @author Joshua Tencic, 2018
 *
 */
public class Character implements Comparable<Character> {
	public HashMap<Integer, Attribute> attributes;
	private String name;

	public Character() {
		attributes = new HashMap<Integer, Attribute>();
	}
	
	public void addAttribute(Attribute attribute) {
	    int key = attribute.hashCode();
		attributes.put(key, attribute);
	}
		
	public String toString() {
		String output = name;
		for (Entry<Integer, Attribute> entry: attributes.entrySet()) {
		    Attribute a = (Attribute) entry.getValue();
			output += '\n' + a.getName() + " " + a.getValue();
		}
		return output;
	}

	public void setName(String name) { this.name = name; }
	public String getName() { return name; }

	@Override
	public int compareTo(Character other) {
		return name.compareTo(other.getName());
	}
	public int compareTo(String otherName) {
		return name.compareTo(otherName);
	}
	
	public Character clone() {
		Character clone = new Character();
		clone.setName(name);
		for (Entry<Integer, Attribute> entry: attributes.entrySet()) {
            Attribute a = (Attribute) entry.getValue();
			clone.addAttribute(a.clone());
		}
		return clone;
	}
	
	@Override
    public int hashCode() {
        int hash = 1;
        hash *= 17 + name.hashCode();
        hash *= 31 + attributes.hashCode();
        return hash;
    }

	public Attribute getAttribute(String name) {
        for (Entry<Integer, Attribute> entry: attributes.entrySet()) {
            Attribute a = (Attribute) entry.getValue();
            if (a.compareTo(name) == 0) return a;
		}
		return null;
	}
	public boolean hasAttributeValue(Attribute attribute, String value) {
		return attribute.getValue().compareTo(value) == 0;
	}
	public boolean hasAttributeValue(String attribute, String value) {
		return hasAttributeValue(getAttribute(attribute), value);
	}

}
