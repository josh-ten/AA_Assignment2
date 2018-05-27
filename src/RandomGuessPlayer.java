import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Random guessing player.
 * This player is for task B.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class RandomGuessPlayer extends PlayerFileLoader implements Player
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
    public RandomGuessPlayer(String gameFilename, String chosenName) throws IOException {
    	super(gameFilename, chosenName);
    } // end of RandomGuessPlayer()


    public Guess guess() {
    	Guess guess = null;
    	//While there are more than 2 candidates to choose from
    	if (candidates.size() > 2) {
			Random rand = new Random();
			//Choose a random person
			int randInd = rand.nextInt(candidates.size());
			Character randChar = candidates.get(randInd);
			//Choose a random attribute that has not yet been guessed
			Attribute randAttr = null;
			do {
				randInd = rand.nextInt(randChar.attributes.size());
				randAttr = randChar.attributes.get(randInd);
			} while (attributesCorrectlyGuessed.contains(randAttr.getName()));
			//Get the value for this attribute
			String val = randAttr.getValue();
			
			guess = new Guess(Guess.GuessType.Attribute, randAttr.getName(), val);
    	} else { 
    		//Narrowed it down to 2
    		guess = new Guess(Guess.GuessType.Person, "", candidates.get(0).getName());
    	}
    	
		return guess;
    } // end of guess()


    public boolean answer(Guess currGuess) {
        //Player guess
    	String correctValue = chosenCharacter.getName();
    	String guessedValue = currGuess.getValue();
    	//AV guess
    	if (currGuess.getType() == Guess.GuessType.Attribute) {
	    	Attribute attribute = chosenCharacter.getAttribute(currGuess.getAttribute());
	    	correctValue = attribute.getValue();
    	}
    	return correctValue.equals(guessedValue);
    } // end of answer()


	public boolean receiveAnswer(Guess currGuess, boolean answer) {
		String attribute = currGuess.getAttribute();
		String value = currGuess.getValue();
		boolean personGuess = currGuess.getType() == Guess.GuessType.Person;
		    
		if (answer && !personGuess) {
			attributesCorrectlyGuessed.add(attribute);
		} else {
			//If the guess was wrong, keep track of that
			ArrayList<Character> toRemove = new ArrayList<Character>();
			for (Character c: candidates) {
			    if (personGuess && c.getName().equals(currGuess.mValue)) {
		            candidates.remove(c);		                
			    } else if (c.hasAttributeValue(attribute, value)) {
					toRemove.add(c);
				}
			}
			candidates.removeAll(toRemove);
		} 
        return (answer && personGuess);
    } // end of receiveAnswer()
	

} // end of class RandomGuessPlayer
