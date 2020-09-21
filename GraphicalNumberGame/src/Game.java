
public class Game {
	int numToGuess;
	int numTries;
	
	public Game() {
		this.newGame(0, 100);
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
	
	public void newGame(int min, int max) {
		// generate random integer to guess between min and max
		this.numToGuess = (int)(Math.random()*(max + 1) + min);
		this.numTries = 0;
	}

	public int getNumToGuess() {
		return numToGuess;
	}

	public int getNumTries() {
		return numTries;
	}
	
}
