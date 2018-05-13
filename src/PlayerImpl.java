import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerImpl {

	protected Character chosenCharacter;
	protected ArrayList<Character> characters;
	protected ArrayList<Attribute> allAttributes;
	
	public PlayerImpl(String gameFilename, String chosenName) {
		characters = new ArrayList<Character>();
    	allAttributes = new ArrayList<Attribute>();
        loadConfig(gameFilename, chosenName);
        for (Character c: characters) {
			if (c.compareTo(chosenName) == 0) {
				chosenCharacter = c.clone();
				break;
			}
        }
        if (chosenCharacter == null)
        	System.err.println("Could not find character: " + chosenName);
        System.out.println("Character: " + chosenCharacter.getName() + '\n');
	}
	

	private void loadConfig(String fileName, String chosenName) {
		String line = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            Character currCharacter = new Character();
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

	
	private void defineAttribute(String[] tokens) {
		String attributeName = tokens[0];
		Attribute attribute = new Attribute(attributeName);
		for (int i = 1; i < tokens.length; i++) {
			attribute.addValue(tokens[i]);
		}
		allAttributes.add(attribute);
	}

	
}
