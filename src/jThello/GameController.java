package jThello;

import java.awt.*;

public class GameController {

	public void initializeEventHandlers(GameViewModel gameViewModel, MainWindow window) {
		// TODO Auto-generated method stub
		
	}

	// Event handlers for each of the squares. Back end will handle bad input.

	// Display invalid move for 5 seconds.

	// Initialize the GUI display for players and names.
	public void initializePlayers(String name1, String name2) {

	}

	// Sets the current active player in the GUI based on the given input.
	// Essentially recolors the track/score elements and changes colors, and displays given string.
	// Keep string to under about 15 characters to prevent visual bugs.
	public void setCurrentPlayer(int player, String input) {

	}

	// Update scores on GUI.
	public void updateScores(int player1Score, int player2Score) {

	}

	// Set status of engine, visible to player.
	// Includes: Bad input, waiting for input, processing move, thinking...
	// Color sets color of label background, so an error would get Color.RED, for example.
	public void setStatus(String status, Color statusColor) {
		
	}

	// Updates the entire board GUI using gamestate of board.
	public void updateBoard(int[][] board) {

		// -1 = empty; 0 = player1 (black), 1 = player2 (white)

		//board = ;

	}

	// Resets board GUI to default configuration of pieces.
	public void resetBoard() {

	}

	// Goes to Game Over screen.
	public void gameOver() {

	}

	// Goes back to main menu.
	public void resign() {

	}
}
