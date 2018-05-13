import java.util.ArrayList;

/**
 * Store the data for one of the game characters for the player to 
 * select from
 * @author Joshua Tencic, 2018
 *
 */
public class Character implements Comparable<Character> {
	private ArrayList<Attribute> attributes;
	private String name;

	public Character() {
		attributes = new ArrayList<Attribute>();
	}
	
	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
	}
		
	public String toString() {
		String output = name;
		for (Attribute a: attributes) {
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
		for (Attribute a: attributes) {
			clone.addAttribute(a.clone());
		}
		return clone;
	}

	public Attribute getAttribute(String name) {
		for (Attribute a: attributes) {
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
