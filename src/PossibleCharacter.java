import java.util.ArrayList;

public class PossibleCharacter {
	public ArrayList<Attribute> attributes;

	public PossibleCharacter(ArrayList<Attribute> attributes) {
		for (Attribute a: attributes) {			
			this.attributes.add(a.clone());
		}
	}
}
