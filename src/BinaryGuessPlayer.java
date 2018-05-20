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
    	            if (labels.get(i).equals(a.getName() + " " + a.getValue())) { //Already exists
    	                exists = true;
    	                int oldVal = freqs.get(i);
    	                freqs.set(i, oldVal+1);
    	                break;
    	            }
    	        }
    	        if (!(exists || attributesCorrectlyGuessed.contains(a.getName()))) { //Adding a new one 
	                labels.add(a.getName() + " " + a.getValue());
	                freqs.add(1);
    	        }
    	    }
    	    
    		//Find the median common attribute value pair
    	    //so as to not ask a redundant question, halving the candidate list
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
    		String[] MCA = labels.get(minIndex).split(" "); //Most common attribute
    		String guessAttr = MCA[0];
    		String guessVal = MCA[1];
			
			guess = new Guess(Guess.GuessType.Attribute, guessAttr, guessVal);
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
		if (!personGuess) {
		    //update the list of candidates depending on the response
		    ArrayList<Character> toRemove = new ArrayList<Character>();
    		if (answer) {
    		    attributesCorrectlyGuessed.add(attribute);
    			for (Character c: candidates) {
    				if (!c.hasAttributeValue(attribute, value)) {
    					toRemove.add(c);
    				}
    			}
    		} else {
                for (Character c: candidates) {
                    if (c.hasAttributeValue(attribute, value))
                        toRemove.add(c);
                }
    		}
    		candidates.removeAll(toRemove);
		}
        return (answer && personGuess);
    } // end of receiveAnswer()

} // end of class BinaryGuessPlayer
