
public class Game {
	
	// instance variables
	private int numToGuess;
	private int numTries;
	
	// constructor starts new game with provided min and max 
	public Game(int min, int max) {
		newGame(min, max);
	}
	
	// start new game with random integer between min and max (inclusive)
	public void newGame(int min, int max) {
		// if min or max provided was negative, set to 0
		if (min < 0) {
			min = 0;
		}
		if (max < 0) {
			max = 0;
		}
		// generate random integer between min and max (inclusive)
		this.numToGuess = (int)(Math.random()*(max + 1) + min);
		// set number of tries to zero
		this.numTries = 0;
	}
	
	// returns 0 if guess is correct, 1 if guess is too high, and -1 otherwise (if guess is too low).
	public int checkGuess(int guess) {
		if (guess == numToGuess) {
			return 0;
		} else if (guess > numToGuess) {
			return 1;
		} else {
			return -1;
		}
	}

	// increments number of tries by 1.
	public void incrementNumTries() {
		numTries++;
	}
	
	public int getNumTries() {
		return numTries;
	}
	
}
