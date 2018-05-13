import java.util.ArrayList;

public class PossibleCharacter {
	public ArrayList<Attribute> attributes;

	public PossibleCharacter(ArrayList<Character> characters) {
		attributes = new ArrayList<Attribute>();
		for (Character c: characters) {
			for (Attribute a: c.attributes) {
				if (!this.attributes.contains(a))
					this.attributes.add(a.clone());
			}
		}
	}
}
