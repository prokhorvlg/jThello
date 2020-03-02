package jThello;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameViewModel implements ModelInterface {
	
	GameController gameController;
	
	JPanel gameView;
	
	// 1. Upper half
	
	JPanel upperHalf = new JPanel();
	
	// 1.1 Left side
	
	JPanel leftSide = new JPanel();
	
	// Main logo
	JLabel mainLogo;
	// Main label
	JLabel mainLabel = new JLabel("jThello");
	
	// 1.2 Middle (board)
	
	JPanel gameBoard = new JPanel();
	
	// 10x9 grid of elements represent the game board
	// x - represent empty spaces for filler
	// numbers and letters - represent board coordinate labels
	// o - represents game pieces (images)
	
	// x A B C D E F G H x
	// 1 o o o o o o o o x
	// 2 o o o o o o o o x
	// 3 o o o o o o o o x
	// 4 o o o o o o o o x
	// 5 o o o o o o o o x
	// 6 o o o o o o o o x
	// 7 o o o o o o o o x
	// 8 o o o o o o o o x
	
	JLabel[][] boardPieces = new JLabel[20][20];
	
	JPanel[] boardRows = new JPanel[9];
	
	// 1.3 Right side (score trackers, turn tracker)
	
	JPanel rightSide = new JPanel();
	
	// 1.3.1 Turn Tracker (Your turn, their turn)
	
	JLabel turnTracker = new JLabel("YOUR TURN");
	
	// 1.3.2 Score Trackers
		
	JPanel tracker1Panel = new JPanel();
	JLabel tracker1Name = new JLabel("YOU");
	
	JPanel tracker1LowerPanel = new JPanel();
	JLabel tracker1Piece = new JLabel("O");
	JLabel tracker1Score = new JLabel("x 7");
	
	JPanel tracker2Panel = new JPanel();
	JLabel tracker2Name = new JLabel("THEM");
	
	JPanel tracker2LowerPanel = new JPanel();
	JLabel tracker2Piece = new JLabel("O");
	JLabel tracker2Score = new JLabel("x 4");
	
	// 2. Lower half
	
	JPanel lowerHalf = new JPanel();
	
	// 2.1 Button Container
	
	JButton rulesButton = new JButton("Rules");
	JButton resignButton = new JButton("Resign");
	
	GameViewModel() {
		gameController = new GameController();
		gameView = new JPanel();
	}
	
	// Loads image from given path into a label return object.
	private JLabel loadImage(String filePath) throws IOException {
		BufferedImage myPicture = ImageIO.read(new File(filePath));
		return new JLabel(new ImageIcon(myPicture));
	}
	
	private void initializeBoardRow(JPanel init) {
		init.setLayout(new GridLayout(0, 9, 30, 5));
	}

	public void initializePanel(MainWindow window) throws IOException {
		// Add elements to screen.
		
		// - LEFT SIDE
		
		leftSide = new JPanel();
		leftSide.setLayout(new BoxLayout(leftSide, BoxLayout.Y_AXIS));
		
		// -- LOGO
		mainLogo = loadImage("images/placeholder.png");
		leftSide.add(mainLogo);
		
		// -- LABEL
		Box box = new Box(BoxLayout.Y_AXIS);
		//mainLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		box.add( Box.createHorizontalGlue() );
		//box.add(mainLabel);
		box.add( Box.createHorizontalGlue() );
		
		leftSide.add(mainLabel);
		
		upperHalf.add(leftSide, BorderLayout.WEST);
		
		// - GAME BOARD
		gameBoard = new JPanel();
		gameBoard.setLayout(new BoxLayout(gameBoard, BoxLayout.Y_AXIS));
		
		String[] coordsX = {"A", "B", "C", "D", "E", "F", "G", "H"};
		String[] coordsY = {"1", "2", "3", "4", "5", "6", "7", "8"};
		
		// For each row...
		for (int i = 0; i < 9; i++) {
			// For the first row (special one).
			if (i == 0) {
				// Initialize board row panel.
				boardRows[i] = new JPanel();
				initializeBoardRow(boardRows[i]);
				
				// For each piece in the first row...
				for (int y = 0; y < 10; y++) {
					System.out.println("ay" + i + y);
					if (y == 0) {
						// First piece is an empty.
						boardPieces[i][y] = new JLabel();
					} else if (y == 9) {
						boardPieces[i][y] = new JLabel();
					} else {
						// Set some default parameters.
						boardPieces[i][y] = new JLabel(coordsX[y-1]);
					}
					
					// Add this piece to this row.
					boardRows[i].add(boardPieces[i][y]);
				}
				
				// Add this row to board.
				gameBoard.add(boardRows[i]);
			}
			// Otherwise for each normal row...
			else {
				// Initialize board row panel.
				boardRows[i] = new JPanel();
				initializeBoardRow(boardRows[i]);
				
				// For each piece...
				for (int y = 0; y < 10; y++) {
					if (y == 0) {
						// First piece is a number coordinate.
						boardPieces[i][y] = new JLabel(coordsY[i-1]);
					} else if (y == 9) {
						boardPieces[i][y] = new JLabel();
					} else {
						// Set some default parameters.
						boardPieces[i][y] = new JLabel("O");
					}
					
					// Add this piece to this row.
					boardRows[i].add(boardPieces[i][y]);
				}
				
				// Add this row to board.
				gameBoard.add(boardRows[i]);
			}
		}
		
		upperHalf.add(gameBoard, BorderLayout.CENTER);
		
		
		
		upperHalf.add(rightSide, BorderLayout.EAST);
		
		gameView.setLayout(new BorderLayout());
		gameView.add(upperHalf, BorderLayout.NORTH);
		gameView.add(lowerHalf, BorderLayout.SOUTH);
		
	    // Add event listeners to buttons.
	    gameController.initializeEventHandlers(this, window);
	}

	public JPanel getModel() {
		return gameView;
	}
}
