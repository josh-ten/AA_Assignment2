import java.io.*;
import java.util.ArrayList;

/**
 * Binary-search based guessing player.
 * This player is for task C.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class BinaryGuessPlayer extends PlayerImpl implements Player
{
    /**
     * Loads the game configuration from gameFilename, and also store the chosen
     * person.
     *
     * @param gameFilename Filename of game configuration.
     * @param chosenName Name of the chosen person for this player.
     * @throws IOException If there are IO issues with loading of gameFilename.
     *    Note you can handle IOException within the constructor and remove
     *    the "throws IOException" method specification, but make sure your
     *    implementation exits gracefully if an IOException is thrown.
     */
    public BinaryGuessPlayer(String gameFilename, String chosenName) throws IOException {
    	super(gameFilename, chosenName);
    } // end of BinaryGuessPlayer()


    public Guess guess() {
    	Guess guess = null;
    	if (candidates.size() > 1) {
    		//Find the frequency of each attribute value pair double up
    		ArrayList<ArrayList<Integer>> freqs = new ArrayList<ArrayList<Integer>>();
    		for (Character c: candidates) {
    			for (Attribute a: c.attributes) {
    				//Find the attribute in allAttributes
    				
    			}
    		}
    		for (int i = 0; i < freqs.size(); i++)
    			System.out.println(freqs.get(i));
			//Choose a random person
//			int randInd = rand.nextInt(candidates.size());
//			Character randChar = candidates.get(randInd);
//			//Choose a random attribute that is not yet known
//			Attribute randAttr = null;
//			do {
//				randInd = rand.nextInt(randChar.attributes.size());
//				randAttr = randChar.attributes.get(randInd);
//			} while (attributesCorrectlyGuessed.contains(randAttr.getName()));
//			//Get the value for this attribute
//			String val = randAttr.getValue();
//			
//			guess = new Guess(Guess.GuessType.Attribute, randAttr.getName(), val);
    	} else if (candidates.size() == 1) { 
    		//Narrowed it down to 1
    		guess = new Guess(Guess.GuessType.Person, "", candidates.get(0).getName());
    	}
    	
		return guess;
    } // end of guess()


	public boolean answer(Guess currGuess) {
		String correctValue = chosenCharacter.getName();
    	String guessedValue = currGuess.getValue();
    	if (currGuess.getType() == Guess.GuessType.Attribute) {
	    	Attribute attribute = chosenCharacter.getAttribute(currGuess.getAttribute());
	    	correctValue = attribute.getValue();
    	}
    	return correctValue.compareTo(guessedValue) == 0;
    } // end of answer()


	public boolean receiveAnswer(Guess currGuess, boolean answer) {
		String attribute = currGuess.getAttribute();
		String value = currGuess.getValue();
		boolean personGuess = currGuess.getType() == Guess.GuessType.Person;
		if (answer) {
			attributesCorrectlyGuessed.add(attribute);
		}
		else {
			//If the guess was wrong, keep track of that
			ArrayList<Character> toRemove = new ArrayList<Character>();
			for (Character c: candidates) {
				if (c.hasAttributeValue(attribute, value)) {
					toRemove.add(c);
				}
			}
			candidates.removeAll(toRemove);
		}
        return (answer && personGuess);
    } // end of receiveAnswer()

} // end of class BinaryGuessPlayer
