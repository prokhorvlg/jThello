package jThello;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameController {

	GameViewModel gameViewModel;
	MainWindow window;

	public void initializeEventHandlers(GameViewModel _gameViewModel, MainWindow _window) {
		gameViewModel = _gameViewModel;
		window = _window;

		gameViewModel.rulesButton.addActionListener(new rulesButtonListener());
	}

	private class rulesButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				resetBoard();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	// Event handlers for each of the squares. Back end will handle bad input.

	// Initialize the GUI display for players and names.
	public void initializePlayers(String name1, String name2) {
		gameViewModel.tracker1Name.setText(name1);
		gameViewModel.tracker2Name.setText(name2);
	}

	// Sets the current active player in the GUI based on the given input.
	// Essentially recolors the track/score elements and changes colors, and displays given string.
	// Keep string to under about 15 characters to prevent visual bugs.
	public void setCurrentPlayer(int player, String input) {
		if (player == 0) {
			gameViewModel.rightTurnPInner.setBackground(Color.WHITE);
			gameViewModel.turnTracker.setForeground(Color.BLACK);
		} else {
			gameViewModel.rightTurnPInner.setBackground(Color.BLACK);
			gameViewModel.turnTracker.setForeground(Color.WHITE);
		}
		gameViewModel.turnTracker.setText(input);
	}

	// Update scores on GUI.
	public void updateScores(int player1Score, int player2Score) {
		gameViewModel.tracker1Score.setText("x " + Integer.toString(player1Score));
		gameViewModel.tracker2Score.setText("x " + Integer.toString(player2Score));
	}

	// Set status of engine, visible to player.
	// Includes: Bad input, waiting for input, processing move, thinking...
	// Color sets color of label background. use Color.RED for error, Color.GREEN for player input, Color.BLACK for computer thinking.
	public void setStatus(String status, Color statusColor) {
		gameViewModel.rightStatus.setBackground(statusColor);
		gameViewModel.rightStatusActual.setText(status);
	}

	// Updates the entire board GUI using game state of board.
	public void updateBoard(int[][] board) throws IOException {
		// -1 = empty; 0 = player1 (black), 1 = player2 (white)

		// Assign corresponding images.
		// For each row...
		for (int x = 0; x < 8; x++) {
			// For each column...
			for (int y = 0; y < 8; y++) {
				gameViewModel.boardIcons[x][y].setIcon(gameViewModel.loadPieceImage(board[x][y]));
			}
		}
	}

	// Resets board GUI to default configuration of pieces.
	public void resetBoard() throws IOException {
		int[][] resetBoard = new int[8][8];

		// Generate blank data.
		for (int x = 0; x < 8; x++) {
			// For each column...
			for (int y = 0; y < 8; y++) {
				resetBoard[x][y] = -1;
			}
		}

		// Place in the 4 starting pieces.
		resetBoard[3][3] = 1;
		resetBoard[4][4] = 1;
		resetBoard[3][4] = 0;
		resetBoard[4][3] = 0;

		// Run the board.
		updateBoard(resetBoard);
	}

	// Goes to Game Over screen.
	public void gameOver() {

	}

	// Goes back to main menu.
	public void resign() {

	}
}
