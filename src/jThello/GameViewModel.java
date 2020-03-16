package jThello;

import Libraries.BackgroundPanel;
import Libraries.CustomButton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

public class GameViewModel implements ModelInterface {
	
	GameController gameController;
	private boolean vsAI;

	BackgroundPanel gameView;
	
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
	
	JPanel[][] boardPieces = new JPanel[20][20];
	JPanel[] boardRows = new JPanel[9];

	JLabel[][] boardIcons = new JLabel[8][8];
	ImageIcon[] boardIconsRaw = new ImageIcon[3];
	
	// 1.3 Right side (score trackers, turn tracker)
	
	JPanel rightSide = new JPanel();
	public JPanel rightStatus;
	public JLabel rightStatusActual;
	
	// 1.3.1 Turn Tracker (Your turn, their turn)
	
	JLabel turnTracker = new JLabel("YOUR TURN");
	public JPanel rightTurnPInner;
	
	// 1.3.2 Score Trackers
		
	JPanel tracker1Panel = new JPanel();
	JLabel tracker1Name = new JLabel("P1");
	
	JPanel tracker1LowerPanel = new JPanel();
	JLabel tracker1Piece = new JLabel("O");
	public JLabel tracker1Score = new JLabel("x NUM");
	
	JPanel tracker2Panel = new JPanel();
	JLabel tracker2Name = new JLabel("P2");
	
	JPanel tracker2LowerPanel = new JPanel();
	JLabel tracker2Piece = new JLabel("O");
	public JLabel tracker2Score = new JLabel("x NUM");
	
	// 2. Lower half
	
	JPanel lowerHalf = new JPanel();
	
	// 2.1 Button Container
	
	CustomButton rulesButton;
	CustomButton resignButton;
	
	GameViewModel(boolean _vsAI) throws IOException {
		gameController = new GameController();
		gameView = new BackgroundPanel(ImageIO.read(new File("images/bg.png")), BackgroundPanel.TILED, 0.0f, 0.0f);
		vsAI = _vsAI;
	}
	
	private void initializeBoardRow(JPanel init) {
		init.setLayout(new GridLayout(0, 10, 0, 0));
		init.setBackground( new Color(0, 0, 0, 0) );
		//init.setLayout(new BoxLayout(init, BoxLayout.X_AXIS));
	}

	private JPanel boardEmptySpace() {
		JPanel emptySpace = new JPanel();
		emptySpace.add(Box.createRigidArea(new Dimension(0, 0)));
		emptySpace.setBackground(new Color(0, 0, 0, 0));
		return emptySpace;
	}

	// Returns a piece icon label with appropriate image attached.
	public ImageIcon loadPieceImage(int pieceCode) {
		return boardIconsRaw[pieceCode+1];
	}

	public void initializePanel(MainWindow window) throws IOException {
		boardIconsRaw = new ImageIcon[]{
				window.loadImageRaw("images/jThello-piece-blank.png"),
				window.loadImageRaw("images/jThello-piece-black.png"),
				window.loadImageRaw("images/jThello-piece-white.png")
		};

		// Add elements to screen.
		
		// - LEFT SIDE
		
		leftSide = new JPanel();
		leftSide.setLayout(new BoxLayout(leftSide, BoxLayout.Y_AXIS));
		leftSide.setBackground( new Color(0, 0, 0, 0) );

		JPanel leftLogoP = new JPanel();
		JPanel leftTitleP = new JPanel();
		JPanel leftSubP = new JPanel();
		JPanel leftInvisibleBlock = new JPanel();

		leftLogoP.setBackground( new Color(0, 0, 0, 0) );
		leftTitleP.setBackground( new Color(0, 0, 0, 0) );
		leftSubP.setBackground( new Color(0, 0, 0, 0) );
		leftInvisibleBlock.setBackground( new Color(0, 0, 0, 0) );
		
		// -- LOGO
		mainLogo = window.loadImage("images/jThello_logo.png");
		leftLogoP.setLayout(new BoxLayout(leftLogoP, BoxLayout.X_AXIS));
		leftLogoP.add(Box.createHorizontalGlue());
		leftLogoP.add(mainLogo);
		leftLogoP.add(Box.createHorizontalGlue());

		// -- LABEL

		leftTitleP.setLayout(new BoxLayout(leftTitleP, BoxLayout.X_AXIS));
		leftTitleP.add(Box.createHorizontalGlue());
		mainLabel.setFont(window.fontTexBold.deriveFont(60f));
		mainLabel.setForeground(window.ColorPrimary);
		leftTitleP.add(mainLabel);
		leftTitleP.add(Box.createHorizontalGlue());

		leftSubP.setLayout(new BoxLayout(leftSubP, BoxLayout.X_AXIS));
		leftSubP.add(Box.createHorizontalGlue());
		JLabel subLabel = new JLabel("A classic board game.");
		subLabel.setFont(window.fontTexBold.deriveFont(14f));
		subLabel.setForeground(window.ColorHighlight);
		leftSubP.add(subLabel);
		leftSubP.add(Box.createHorizontalGlue());

		leftInvisibleBlock.add(Box.createRigidArea(new Dimension(250, 0)));

		leftSide.add(leftLogoP);
		leftSide.add(leftTitleP);
		leftSide.add(leftSubP);
		leftSide.add(leftInvisibleBlock);
		
		upperHalf.add(leftSide, BorderLayout.WEST);
		
		// - GAME BOARD
		gameBoard = new JPanel();
		gameBoard.setLayout(new BoxLayout(gameBoard, BoxLayout.Y_AXIS));
		gameBoard.setBackground( new Color(0, 0, 0, 0) );
		
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
					if (y == 0) {
						// First piece is a special empty which contains a board corner point.
						JPanel cornerPanel = new JPanel();
						cornerPanel.setLayout(new BorderLayout());
						cornerPanel.setBackground( new Color(0, 0, 0, 0) );

						cornerPanel.add(window.loadImage("images/jThello-board-corner.png"), BorderLayout.SOUTH);

						boardPieces[i][y] = cornerPanel;
					} else if (y == 9) {
						boardPieces[i][y] = boardEmptySpace();
					} else {
						// Set some default parameters.
						JPanel numCoordPanel = new JPanel();
						numCoordPanel.setLayout(new BorderLayout());
						numCoordPanel.setBackground( new Color(0, 0, 0, 0) );

						JPanel coordP = new JPanel();
						coordP.setLayout(new BoxLayout(coordP, BoxLayout.X_AXIS));
						coordP.setBackground( new Color(0, 0, 0, 0) );

						coordP.add(Box.createHorizontalGlue());
						JLabel coordLabel = new JLabel("<html><span style='padding: 2px; text-align: center;'>" + coordsX[y-1] + "</span></html>", SwingConstants.CENTER);
						coordLabel.setFont(window.fontTexBold.deriveFont(14f));
						coordLabel.setForeground(window.ColorHighlight);
						coordP.add(coordLabel);
						coordP.add(Box.createHorizontalGlue());

						JPanel horizP = new JPanel();
						horizP.setLayout(new BorderLayout());
						horizP.add(window.loadImage("images/jThello-horiz.png"), BorderLayout.CENTER);
						horizP.setBackground( new Color(0, 0, 0, 0) );

						numCoordPanel.add(coordP, BorderLayout.NORTH);
						numCoordPanel.add(Box.createRigidArea(new Dimension(0, 18)), BorderLayout.CENTER);
						numCoordPanel.add(horizP, BorderLayout.SOUTH);

						boardPieces[i][y] = numCoordPanel;
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
						JPanel numCoordPanel = new JPanel();
						numCoordPanel.setLayout(new BorderLayout());
						numCoordPanel.setBackground( new Color(0, 0, 0, 0) );

						JPanel coordP = new JPanel();
						coordP.setLayout(new BoxLayout(coordP, BoxLayout.Y_AXIS));
						coordP.setBackground( new Color(0, 0, 0, 0) );

						coordP.add(Box.createVerticalGlue());
						JLabel coordLabel = new JLabel("<html><span style='padding: 20px;'>" + coordsY[i-1] + "</span></html>");
						coordLabel.setFont(window.fontTexBold.deriveFont(14f));
						coordLabel.setForeground(window.ColorHighlight);
						coordP.add(coordLabel);
						coordP.add(Box.createVerticalGlue());

						JPanel horizP = new JPanel();
						horizP.setLayout(new BorderLayout());
						horizP.add(window.loadImage("images/jThello-vert.png"), BorderLayout.CENTER);
						horizP.setBackground( new Color(0, 0, 0, 0) );

						numCoordPanel.add(coordP, BorderLayout.CENTER);
						numCoordPanel.add(horizP, BorderLayout.EAST);

						boardPieces[i][y] = numCoordPanel;

					} else if (y == 9) {
						boardPieces[i][y] = boardEmptySpace();
					} else {
						JPanel piecePanel = new JPanel();
						piecePanel.setLayout(new BorderLayout());
						//piecePanel.setBackground( new Color(0, 0, 0, 0) );
						piecePanel.setBackground( new Color(39, 141, 77, 255) );

						JPanel pieceP = new JPanel();
						pieceP.setLayout(new BoxLayout(pieceP, BoxLayout.X_AXIS));
						pieceP.setBackground( new Color(0, 0, 0, 0) );
						pieceP.add(Box.createHorizontalGlue());
						JLabel myPiece = new JLabel(loadPieceImage(1));
						boardIcons[i-1][y-1] = myPiece;
						pieceP.add(boardIcons[i-1][y-1]);
						pieceP.add(Box.createHorizontalGlue());

						JPanel vertP = new JPanel();
						vertP.setLayout(new BorderLayout());
						vertP.add(window.loadImage("images/jThello-vert-short.png"), BorderLayout.CENTER);
						vertP.setBackground( new Color(0, 0, 0, 0) );

						JPanel horizP = new JPanel();
						horizP.setLayout(new BorderLayout());
						horizP.add(window.loadImage("images/jThello-horiz.png"), BorderLayout.CENTER);
						horizP.setBackground( new Color(0, 0, 0, 0) );

						piecePanel.add(pieceP, BorderLayout.CENTER);
						piecePanel.add(vertP, BorderLayout.EAST);
						piecePanel.add(horizP, BorderLayout.SOUTH);

						boardPieces[i][y] = piecePanel;
					}
					
					// Add this piece to this row.
					boardRows[i].add(boardPieces[i][y]);
				}
				
				// Add this row to board.
				gameBoard.add(boardRows[i]);
			}
		}
		
		upperHalf.add(gameBoard, BorderLayout.CENTER);
		
		// Right side

		rightSide = new JPanel();
		rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));
		rightSide.setBackground( new Color(0, 0, 0, 0) );

		JPanel rightTurnP = new JPanel();
		JPanel rightScoreP1 = new JPanel();
		JPanel rightScoreP2 = new JPanel();
		rightStatus = new JPanel();
		JPanel rightInvisibleBlock = new JPanel();

		rightTurnP.setBackground( new Color(0, 0, 0, 0) );
		rightInvisibleBlock.setBackground( new Color(0, 0, 0, 0) );

		// -- TURN (label which displays who's turn it is)

		rightTurnP.setLayout(new BoxLayout(rightTurnP, BoxLayout.X_AXIS));
		rightTurnP.add(Box.createHorizontalGlue());

		rightTurnPInner = new JPanel();
		rightTurnPInner.setLayout(new BoxLayout(rightTurnPInner, BoxLayout.X_AXIS));
		rightTurnPInner.add(Box.createRigidArea(new Dimension(20, 0)));
		turnTracker.setFont(window.fontTexBold.deriveFont(30f));
		turnTracker.setForeground(window.ColorPrimary);
		rightTurnPInner.add(turnTracker);
		rightTurnPInner.add(Box.createRigidArea(new Dimension(20, 0)));
		rightTurnPInner.setBackground(Color.BLACK);
		rightTurnP.add(rightTurnPInner);

		rightTurnP.add(Box.createHorizontalGlue());

		// -- SCORE 1

		// Set up primary container.
		rightScoreP1.setLayout(new BoxLayout(rightScoreP1, BoxLayout.Y_AXIS));
		rightScoreP1.setBackground(Color.WHITE);

		// Set up "upper container", which contains the player name.
		tracker1Panel.setLayout(new BoxLayout(tracker1Panel, BoxLayout.X_AXIS));
		tracker1Panel.setBackground( new Color(0, 0, 0, 0) );
		tracker1Name.setFont(window.fontTexBold.deriveFont(30f));
		tracker1Name.setForeground(Color.BLACK);
		tracker1Panel.add(Box.createRigidArea(new Dimension(20, 0)));
		tracker1Panel.add(tracker1Name);
		tracker1Panel.add(Box.createHorizontalGlue());

		// Set up "lower container", which contains piece and score count.
		tracker1LowerPanel.setLayout(new BoxLayout(tracker1LowerPanel, BoxLayout.X_AXIS));
		tracker1LowerPanel.setBackground( new Color(0, 0, 0, 0) );
		tracker1Piece = window.loadImage("images/jThello-piece-black-nobg.png");
		tracker1Score.setFont(window.fontTexBold.deriveFont(20f));
		tracker1Score.setForeground(Color.BLACK);
		tracker1LowerPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		tracker1LowerPanel.add(tracker1Piece);
		tracker1LowerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		tracker1LowerPanel.add(tracker1Score);
		tracker1LowerPanel.add(Box.createHorizontalGlue());

		// Combine all elements to create final score panel.
		rightScoreP1.add(tracker1Panel);
		rightScoreP1.add(Box.createRigidArea(new Dimension(0, 5)));
		rightScoreP1.add(tracker1LowerPanel);
		rightScoreP1.add(Box.createRigidArea(new Dimension(0, 15)));

		// -- SCORE 2

		// Set up primary container.
		rightScoreP2.setLayout(new BoxLayout(rightScoreP2, BoxLayout.Y_AXIS));
		rightScoreP2.setBackground(Color.BLACK);

		// Set up "upper container", which contains the player name.
		tracker2Panel.setLayout(new BoxLayout(tracker2Panel, BoxLayout.X_AXIS));
		tracker2Panel.setBackground( new Color(0, 0, 0, 0) );
		tracker2Name.setFont(window.fontTexBold.deriveFont(30f));
		tracker2Name.setForeground(Color.WHITE);
		tracker2Panel.add(Box.createRigidArea(new Dimension(20, 0)));
		tracker2Panel.add(tracker2Name);
		tracker2Panel.add(Box.createHorizontalGlue());

		// Set up "lower container", which contains piece and score count.
		tracker2LowerPanel.setLayout(new BoxLayout(tracker2LowerPanel, BoxLayout.X_AXIS));
		tracker2LowerPanel.setBackground( new Color(0, 0, 0, 0) );
		tracker2Piece = window.loadImage("images/jThello-piece-white-nobg.png");
		tracker2Score.setFont(window.fontTexBold.deriveFont(20f));
		tracker2Score.setForeground(Color.WHITE);
		tracker2LowerPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		tracker2LowerPanel.add(tracker2Piece);
		tracker2LowerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		tracker2LowerPanel.add(tracker2Score);
		tracker2LowerPanel.add(Box.createHorizontalGlue());

		// Combine all elements to create final score panel.
		rightScoreP2.add(tracker2Panel);
		rightScoreP2.add(Box.createRigidArea(new Dimension(0, 5)));
		rightScoreP2.add(tracker2LowerPanel);
		rightScoreP2.add(Box.createRigidArea(new Dimension(0, 15)));

		// -- STATUS BOX (displays status to player)

		// Set up primary container.
		rightStatus.setLayout(new BoxLayout(rightStatus, BoxLayout.Y_AXIS));
		rightStatus.setBackground(Color.RED);

		// Set up "upper container", which contains the "status" label".
		JPanel rightStatusUpper = new JPanel();
		rightStatusUpper.setLayout(new BoxLayout(rightStatusUpper, BoxLayout.X_AXIS));
		rightStatusUpper.setBackground( new Color(0, 0, 0, 0) );
		JLabel rightStatusName = new JLabel("GAME STATUS");
		rightStatusName.setFont(window.fontTexBold.deriveFont(18f));
		rightStatusName.setForeground(Color.WHITE);
		rightStatusUpper.add(Box.createRigidArea(new Dimension(20, 0)));
		rightStatusUpper.add(rightStatusName);
		rightStatusUpper.add(Box.createHorizontalGlue());

		// Set up "lower container", which contains the actual status message.
		JPanel rightStatusLower = new JPanel();
		rightStatusLower.setLayout(new BoxLayout(rightStatusLower, BoxLayout.X_AXIS));
		rightStatusLower.setBackground( new Color(0, 0, 0, 0) );
		rightStatusActual = new JLabel("Script loading.");
		rightStatusActual.setFont(window.fontTexBold.deriveFont(12f));
		rightStatusActual.setForeground(Color.WHITE);
		rightStatusLower.add(Box.createRigidArea(new Dimension(20, 0)));
		rightStatusLower.add(rightStatusActual);
		rightStatusLower.add(Box.createHorizontalGlue());

		// Combine all elements to create final score panel.
		rightStatus.add(Box.createRigidArea(new Dimension(0, 7)));
		rightStatus.add(rightStatusUpper);
		rightStatus.add(Box.createRigidArea(new Dimension(0, 5)));
		rightStatus.add(rightStatusLower);
		rightStatus.add(Box.createRigidArea(new Dimension(0, 15)));

		// -- INVISIBLE BLOCK (set up container min width)
		rightInvisibleBlock.add(Box.createRigidArea(new Dimension(250, 0)));

		// Add to right side, then to upper half.

		rightSide.add(rightTurnP);
		rightSide.add(Box.createRigidArea(new Dimension(0, 20)));
		rightSide.add(rightScoreP1);
		rightSide.add(rightScoreP2);
		rightSide.add(Box.createRigidArea(new Dimension(0, 20)));
		rightSide.add(rightStatus);
		rightSide.add(rightInvisibleBlock);

		upperHalf.add(rightSide, BorderLayout.EAST);

		// LOWER HALF (the two buttons)

		lowerHalf.setLayout(new BoxLayout(lowerHalf, BoxLayout.Y_AXIS));
		lowerHalf.setBackground( new Color(0, 0, 0, 0) );

		JPanel lowerHalfUpperC = new JPanel();
		JPanel lowerHalfInnerC = new JPanel();
		JPanel lowerHalfLowerC = new JPanel();

		lowerHalfUpperC.setBackground( new Color(0, 0, 0, 0) );
		lowerHalfInnerC.setBackground( new Color(0, 0, 0, 0) );
		lowerHalfLowerC.setBackground( new Color(0, 0, 0, 0) );

		lowerHalfUpperC.add(Box.createRigidArea(new Dimension(0, 5)));

		rulesButton = window.initMenuButton("View Rules", "bottom");
		resignButton = window.initMenuButton("Resign", "bottom");

		lowerHalfInnerC.setLayout(new BoxLayout(lowerHalfInnerC, BoxLayout.X_AXIS));
		lowerHalfInnerC.add(Box.createHorizontalGlue());
		lowerHalfInnerC.add(rulesButton);
		lowerHalfInnerC.add(Box.createRigidArea(new Dimension(10, 0)));
		lowerHalfInnerC.add(resignButton);
		lowerHalfInnerC.add(Box.createRigidArea(new Dimension(15, 0)));

		lowerHalfLowerC.add(Box.createRigidArea(new Dimension(0, 5)));

		lowerHalf.add(lowerHalfUpperC);
		lowerHalf.add(lowerHalfInnerC);
		lowerHalf.add(lowerHalfLowerC);

		// Add primary panels to window

		gameView.setLayout(new BorderLayout());

		// Empty top for spacing.

		JPanel northEmpty = new JPanel();
		northEmpty.add(Box.createRigidArea(new Dimension(100, 50)));
		gameView.add(northEmpty, BorderLayout.NORTH);

		gameView.add(upperHalf, BorderLayout.CENTER);
		gameView.add(lowerHalf, BorderLayout.SOUTH);
		
	    // Add event listeners to buttons.
	    gameController.initializeEventHandlers(this, window);

	    // Initialize default states for all board elements.
		gameController.resetBoard();
		gameController.setStatus("Loading game...", Color.BLACK);
		gameController.updateScores(0,0);
		gameController.initializePlayers("Alice", "Bob");
		gameController.setCurrentPlayer(0, "Alice's Turn");
	}

	public JPanel getModel() {
		return gameView;
	}
}
