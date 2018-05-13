import java.io.*;
import java.util.Random;

/**
 * Random guessing player.
 * This player is for task B.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class RandomGuessPlayer extends PlayerImpl implements Player
{
	PossibleCharacter possibleChar = null;
	
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
    	possibleChar = new PossibleCharacter(allAttributes);
    } // end of RandomGuessPlayer()


    public Guess guess() {
    	Random rand = new Random();
    	//Choose a random person
    	int randInd = rand.nextInt(characters.size());
    	Character randChar = characters.get(randInd);
    	//Choose a random attribute
    	randInd = rand.nextInt(possibleChar.attributes.size());
    	Attribute randAttr = possibleChar.attributes.get(randInd);
    	//Choose from the list of available values
    	
    	
    	
//    	//Make sure the value hasn't already been guessed
//    	do {
//    		randInd = rand.nextInt(randAttr.possibleValues.size());    		
//    	} while (randAttr.ruledOut.get(randInd) == true);
//    	
//    	String randVal = randAttr.possibleValues.get(randInd);
//    	Guess randGuess = new Guess(
//    			Guess.GuessType.Attribute,
//    			randAttr.getName(),
//    			randVal);
//    	
//    	return randGuess;
        return new Guess(Guess.GuessType.Person, "", null);
    	
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
		if (!answer) {
			//If the guess was wrong, keep track of that
			Attribute attribute = chosenCharacter.getAttribute(currGuess.getAttribute());
			for (int i = 0; i < attribute.possibleValues.size(); i++) {
				String value = attribute.possibleValues.get(i);
				if (currGuess.getValue().compareTo(value) == 0) {
					attribute.ruledOut.set(i, true);
					break;
				}
			}
//			System.out.println(attribute.ruledOut);
		}
        return (answer && currGuess.getType() == Guess.GuessType.Person);
    } // end of receiveAnswer()
	

} // end of class RandomGuessPlayer
