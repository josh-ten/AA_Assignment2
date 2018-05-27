import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Stores the common code between binary and random guess players for loading files
 * and populating the data structures
 * @author Josh Ten
 *
 */
public class PlayerFileLoader {

	protected Character chosenCharacter;
	protected ArrayList<Character> characters;
	protected ArrayList<Character> candidates;
	protected ArrayList<Attribute> allAttributes;
	protected ArrayList<String> attributesCorrectlyGuessed;
	
	public PlayerFileLoader(String gameFilename, String chosenName) {
		characters = new ArrayList<Character>();
    	allAttributes = new ArrayList<Attribute>();
    	candidates = new ArrayList<Character>();
    	attributesCorrectlyGuessed = new ArrayList<String>();
    	
    	loadConfig(gameFilename, chosenName);
        candidates.addAll(characters);

        //Keep track of the chosen character 
        for (Character c: characters) {
			if (c.equals(chosenName)) {
				chosenCharacter = c.clone();
				break;
			}
        }
        if (chosenCharacter == null)
        	System.err.println("Could not find character: " + chosenName);
        
        System.out.println("Character: " + chosenCharacter.getName());
	}
	
	/**
	 * Load the config file
	 * @param fileName config file
	 * @param chosenName name of the chosen character
	 */
	private void loadConfig(String fileName, String chosenName) {
		String line = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            Character currCharacter = new Character();
            //Keep track of the first segment of the config file where AV are defined
            boolean defineAttributes = true;
            while((line = bufferedReader.readLine()) != null) {
                //Check for blank line
            	if (line.compareTo("") == 0) {
            		//Add the last character
            		if (!defineAttributes) characters.add(currCharacter.clone());
            		else defineAttributes = false;
            		//Create the new character
            		currCharacter = new Character();
            		continue;
            	}
            	
            	String[] tokens = line.split(" ");
            	//Defining all possible attributes
            	if (defineAttributes) 
	            	defineAttribute(tokens);
            	else { 
            		//Defining characters
            		if (currCharacter.getName() == null) {
            			currCharacter.setName(line);
            			continue;
            		}	
            		
            		//Add all the attributes to the character
            		String newAttr = tokens[0];
            		String newValue = tokens[1];
            		for (Attribute a: allAttributes) {
            			if (a.compareTo(newAttr) == 0) {
        					a.setValue(newValue);
        					currCharacter.addAttribute(a);
        					break;
            			}
            		}
            	}
            }

            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open " + fileName);                
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
	}

	/**
	 * Take a string array of Atribute, value, value, value... and create 
	 * an attribute instance of it
	 * @param tokens array
	 */
	private void defineAttribute(String[] tokens) {
		String attributeName = tokens[0];
		Attribute attribute = new Attribute(attributeName);
		for (int i = 1; i < tokens.length; i++) {
			attribute.addValue(tokens[i]);
		}
		allAttributes.add(attribute);
	}

	
}
