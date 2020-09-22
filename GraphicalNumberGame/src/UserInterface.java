import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserInterface extends Application {
	
	// instantiate game variables. min and max should be nonnegative integers
	private int min = 0;
	private int max = 100;
	private Game game = new Game(min, max);
	private String promptText = "Enter a number between " + min + " and " + max + " and hit Enter to play.";

	@Override
	public void start(Stage primaryStage) {
		
		// create pane and set display properties
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(25, 25, 25, 25));

		
		// --------------------- nodes ------------------------------- //
		
		// input field
		TextField inputField = new TextField();

		// output field
		Text outputText = new Text();
		
		// input button
		Button submitButton = new Button();
		submitButton.setText("Enter");
		
		// start over / play again button
		Button startGameButton = new Button();
		startGameButton.setText("Start over");
		
		// number of tries
		Text displayNumTries = new Text();
		displayNumTries.setText(promptText);

		// add nodes to pane
		root.getChildren().add(outputText);
		root.getChildren().add(inputField);
		root.getChildren().add(displayNumTries);
		root.getChildren().add(startGameButton);
		
		// hide game buttons to start
		startGameButton.setVisible(false);
		submitButton.setVisible(false);
		
		// ---------------- event listeners------------------------------- //
		
		// submit guess
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				handleSubmitGuess(inputField, outputText, startGameButton, displayNumTries, submitButton);
			}
		});
		
		// set enter key to run submit button code.
		inputField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					handleSubmitGuess(inputField, outputText, startGameButton, displayNumTries, submitButton);
				}
			}
		});
		
		// start new game
		startGameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
				// start new game. generates new number the resets number of tries
				game.newGame(min, max);
				
				// alter nodes to be back in game mode
				inputField.setText("");
				outputText.setText("");
				displayNumTries.setText(promptText);
				startGameButton.setText("Start over");
				startGameButton.setVisible(false);
				submitButton.setVisible(true);
				
				// set focus back to input field
				inputField.requestFocus();
			}
		});

		
		// ---------------------- run display ------------------------- //
		
		// create scene with root pane
		Scene scene = new Scene(root, 350, 400);
		
		// set scene and show stage
		primaryStage.setScene(scene);
		primaryStage.setTitle("Number Guessing Game!");
		primaryStage.show();
	}
	
	
	// use regex to check for only integer values in the input string.
	private boolean validateInput(String text) {
		if (text.equals("")) return false; // return false if text is empty string
		return text.matches("[0-9]*");
	}
	
	// run each time a guess is submitted. takes nodes to read from and edit as arguments
	private void handleSubmitGuess(TextField inputField, Text outputText, Button startGameButton, Text displayNumTries, Button submitButton) {
		
		// get input text
		String input = inputField.getText();
		
		// check for invalid input
		if (!validateInput(input)) {
			
			// if invalid, print an error message and exit method
			outputText.setText("You must enter a non-negative integer");
			inputField.selectAll();
			return;
		}
			
		// grab the text from the textfield and convert to an integer.
		int guess = Integer.parseInt(input);
		
		// select the guess to make typing next guess easier
		inputField.selectAll();
		
		// increment number of tries
		game.incrementNumTries();
		displayNumTries.setText("Number of guesses: " + game.getNumTries());
		
		// if this was the first guess, make game buttons visible
		if (game.getNumTries() == 1) {
			startGameButton.setVisible(true);
		}
		
		// run the guess by the game
		int result = game.checkGuess(guess);
		
		// print the result - too high, too low, or correct
		if (result < 0) {
			outputText.setText("Too low! Try again.");
			
		} else if (result > 0) {
			outputText.setText("Too high! Try again.");
			
		} else if (result == 0) {
			outputText.setText("Correct! Well done.");
			
			// hide submit guess button and alter text to indicate game is over
			submitButton.setVisible(false);
			displayNumTries.setText("Total guesses: " + game.getNumTries());
			startGameButton.setText("Play again");
			
			// highlight play again button
			startGameButton.requestFocus();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
