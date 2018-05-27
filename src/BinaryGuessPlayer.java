import java.io.*;
import java.util.ArrayList;

/**
 * Binary-search based guessing player.
 * This player is for task C.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class BinaryGuessPlayer extends PlayerFileLoader implements Player
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

    	//While there are more than 2 candidates, make attribute guesses
    	if (candidates.size() > 2) {
    	    ArrayList<Attribute> candidateAttr = new ArrayList<Attribute>();
    	    //Create a list of all the attribute value pairs of the candidates
    	    for (Character c: candidates) {
    	        candidateAttr.addAll(c.attributes);
    	    }
    	    //Find the frequency of each attribute value pair
    	    ArrayList<String> labels = new ArrayList<String>();
    	    ArrayList<Integer> freqs = new ArrayList<Integer>();
    	    for (Attribute a: candidateAttr) {
    	        boolean exists = false;
    	        for (int i = 0; i < labels.size(); i++) {
    	            //If it already exists, increment the counter for that AV pair
    	            if (labels.get(i).equals(a.getName() + " " + a.getValue())) { 
    	                exists = true;
    	                int oldVal = freqs.get(i);
    	                freqs.set(i, oldVal+1);
    	                break;
    	            }
    	        }
    	        //If it doesn't exist, create a new entry in the AV list and counter list
    	        if (!(exists || attributesCorrectlyGuessed.contains(a.getName()))) { 
	                labels.add(a.getName() + " " + a.getValue());
	                freqs.add(1);
    	        }
    	    }
    	    int indexOfMedian = getMedianIndex(freqs);
    	    
            String[] MCA = labels.get(indexOfMedian).split(" "); //Median occuring attribute
    		String guessAttr = MCA[0];
    		String guessVal = MCA[1];
			
			guess = new Guess(Guess.GuessType.Attribute, guessAttr, guessVal);
    	} else { 
    		//Narrowed it down to 2, makea person guess
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
		//Update the list of candidates depending on the response
		//If it was a correct AV guess, remember guessed attributes 
		if (answer && !personGuess) {
            attributesCorrectlyGuessed.add(attribute);
        } else if (!answer) {
            //If guess was incorrect
		    ArrayList<Character> toRemove = new ArrayList<Character>();
            for (Character c: candidates) {
                //If it was an incorrect person guess, remove from candidates
                if (personGuess && c.getName().equals(currGuess.mValue)) {
                    candidates.remove(c);                       
                } 
                //If it was an incorrect AV guess, remove all 
                //characters with the guessed AV pair
                else if (c.hasAttributeValue(attribute, value)) {
                    toRemove.add(c);
                }
            }
		
    		candidates.removeAll(toRemove);
		}
        return (answer && personGuess);
    } // end of receiveAnswer()
	
	/**
	 * Find the median occuring attribute value pair split the candidate list
	 * @param freqs
	 * @return index of the AV pair
	 */
	private int getMedianIndex(ArrayList<Integer> freqs) {
        int half = Math.round(candidates.size()/2);
        int[] distFromHalf = new int[freqs.size()];
        for (int i = 0; i < freqs.size(); i++) {
            distFromHalf[i] = Math.abs(half - freqs.get(i));
        }
        int min = candidates.size();
        int minIndex = -1;
        for (int i = 0; i < distFromHalf.length; i++) {
            if (distFromHalf[i] < min) {
                min = distFromHalf[i];
                minIndex = i;
            }
        }
        return minIndex;
	}

} // end of class BinaryGuessPlayer
