package jThello;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class GameController {

	GameViewModel gameViewModel;
	GameState gameState;
	MainWindow window;
	Player[] players;
	boolean vsAI;
	
	GameController() {
		gameState = new GameState(8);
		players = new Player[2];
	}
	
	public void initializeEventHandlers(GameViewModel _gameViewModel, MainWindow _window) {
		gameViewModel = _gameViewModel;
		window = _window;

		gameViewModel.rulesButton.addActionListener(new rulesButtonListener());
		gameViewModel.resignButton.addActionListener(new resignButtonListener());
		initializeBoardEventHandlers();
	}

	private class rulesButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gameOver();
		}
	}

	private class resignButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			resign();
		}
	}

	private void initializeBoardEventHandlers() {
		// Cycle through each icon in the array, and attach the event listener.
		// For each row...
		for (int x = 0; x < 8; x++) {
			// For each column...
			for (int y = 0; y < 8; y++) {
				gameViewModel.boardIcons[y][x].addMouseListener(new boardIconListener(x, y));
			}
		}
	}

	private class boardIconListener extends MouseAdapter {
		private int x, y;
		boardIconListener(int _x, int _y) {
			x = _x;
			y = _y;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			// THIS IS THE EVENT HANDLER FOR BOARD PIECES.
			// When this is run, the variables x and y are stored in order for the back end to know what was clicked.

			if ((vsAI) && (gameState.nextPlayerToMove == GameState.PLAYER2)) return;
			// Generate a list of possible moves from the current game state
			List<Move> possibleMoves = gameState.allMoves();
			// Quit the game when there are no possible moves
			if (possibleMoves.size() == 0) {
				gameOver();
				return;
			}
			// Creates the move from user input
			Move m = new Move(gameState.nextPlayerToMove, x, y);
			// Make the move if it is a legal move
			if (possibleMoves.contains(m)) {
				setStatus(m.toString(), Color.BLACK);
				gameState = gameState.applyMoveAndClone(m);
				try {
					// Update the board GUI
					updateBoard(gameState.board);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				// Set the current player to the next player
				setCurrentPlayer(gameState.nextPlayerToMove);
				// Update the score tracker
				updateScores(gameState.score(GameState.PLAYER1), gameState.score(GameState.PLAYER2));
				if (vsAI) {
					// Make the AI's move
					gameState = gameState.applyMoveAndClone(players[1].getMove(gameState));
					try {
						// Update the board GUI
						updateBoard(gameState.board);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					// Set the current player to the next player
					setCurrentPlayer(gameState.nextPlayerToMove);
					// Update the score tracker
					updateScores(gameState.score(GameState.PLAYER1), gameState.score(GameState.PLAYER2));
				}
				// Quit the game if either player has no legal moves
				if (gameState.gameOver()) {
					gameOver();
				}
			}
			else {
				setStatus("Ilegal Move", Color.RED);
			}
		}
	}


	// Event handlers for each of the squares. Back end will handle bad input.

	// Initialize the GUI display for players and names.
	public void initializePlayers(boolean _vsAI, String name1, String name2) {
		gameViewModel.tracker1Name.setText(name1);
		gameViewModel.tracker2Name.setText(name2);
		players[0] = new HumanPlayer();
		vsAI = _vsAI;
		if (_vsAI) players[1] = new AIPlayer(5);
		else players[1] = new HumanPlayer();
	}

	// Sets the current active player in the GUI based on the given input.
	// Essentially recolors the track/score elements and changes colors, and displays given string.
	// Keep string to under about 15 characters to prevent visual bugs.
	public void setCurrentPlayer(int player) {
		if (player == 0) {
			gameViewModel.rightTurnPInner.setBackground(Color.WHITE);
			gameViewModel.turnTracker.setForeground(Color.BLACK);
			gameViewModel.turnTracker.setText(gameViewModel.tracker1Name.getText() + "'s Turn");
		} else {
			gameViewModel.rightTurnPInner.setBackground(Color.BLACK);
			gameViewModel.turnTracker.setForeground(Color.WHITE);
			gameViewModel.turnTracker.setText(gameViewModel.tracker2Name.getText() + "'s Turn");
		}
		// gameViewModel.turnTracker.setText(input);
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
		gameViewModel.rightStatusActual.setBackground(statusColor);
	}

	// Updates the entire board GUI using game state of board.
	public void updateBoard(int[][] board) throws IOException {
		// -1 = empty; 0 = player1 (black), 1 = player2 (white)

		// Assign corresponding images.
		// For each row...
		for (int x = 0; x < 8; x++) {
			// For each column...
			for (int y = 0; y < 8; y++) {
				gameViewModel.boardIcons[y][x].setIcon(gameViewModel.loadPieceImage(board[x][y]));
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
		resetBoard[3][3] = 0;
		resetBoard[4][4] = 0;
		resetBoard[3][4] = 1;
		resetBoard[4][3] = 1;

		// Run the board.
		updateBoard(resetBoard);
	}

	// Goes to Game Over screen.
	public void gameOver() {
		try {
			// TODO: UPLOAD TO HIGH SCORES HERE.

			// Open the game over view.
			int score1 = gameState.score(GameState.PLAYER1);
			int score2 = gameState.score(GameState.PLAYER2);
			int[] playerScores = {score1,score2};
			int winner = 0;
			// Figure out the winner
			if (score2 > score1) winner = 1;
			window.gameOverModel = new GameOverViewModel(
					gameViewModel.numPlayers, 
					gameViewModel.playerNames, 
					gameViewModel.playerNames[winner], 
					gameViewModel.winnerColor, 
					playerScores);
			window.gameOverModel.initializePanel(window);
			window.openView(window.gameOverModel);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void resign() {
		gameOver();
	}
}
