package jThello;

import Libraries.BackgroundPanel;
import Libraries.CustomButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameOverViewModel implements ModelInterface {
	public GameOverController gameOverController;
	public BackgroundPanel gameOverView;

	int numPlayers;
	String winner;
	Color winnerColor;
	String[] playerNames;
	int[] playerScores;

	CustomButton restartGameButton;
	CustomButton exitToMenuButton;

	GameOverViewModel(int _numPlayers, String[] _playerNames, String _winner, Color _winnerColor, int[] _playerScores) throws IOException {
		numPlayers = _numPlayers;
		playerNames = _playerNames;
		winner = _winner;
		winnerColor = _winnerColor;
		playerScores = _playerScores;

		gameOverController = new GameOverController();
		gameOverView = new BackgroundPanel(ImageIO.read(new File("images/bg.png")), BackgroundPanel.TILED, 0.0f, 0.0f);
	}

	@Override
	public void initializePanel(MainWindow window) throws IOException {

		// Add elements to screen.
		buildFormPanel(window);
		buildOKPanel(window);

		gameOverController.initializeEventHandlers(this, window);
	}

	private void buildOKPanel(MainWindow window) {
		restartGameButton = window.initMenuButton("Restart Game", "bottom");
		exitToMenuButton = window.initMenuButton("Exit to Menu", "bottom");

		JPanel lowerHalf = new JPanel();
		lowerHalf.setLayout(new BoxLayout(lowerHalf, BoxLayout.Y_AXIS));
		lowerHalf.setBackground( new Color(0, 0, 0, 0) );

		JPanel lowerHalfUpperC = new JPanel();
		JPanel lowerHalfInnerC = new JPanel();
		JPanel lowerHalfLowerC = new JPanel();

		lowerHalfUpperC.setBackground( new Color(0, 0, 0, 0) );
		lowerHalfInnerC.setBackground( new Color(0, 0, 0, 0) );
		lowerHalfLowerC.setBackground( new Color(0, 0, 0, 0) );

		lowerHalfUpperC.add(Box.createRigidArea(new Dimension(0, 5)));

		lowerHalfInnerC.setLayout(new BoxLayout(lowerHalfInnerC, BoxLayout.X_AXIS));
		lowerHalfInnerC.add(Box.createHorizontalGlue());
		lowerHalfInnerC.add(restartGameButton);
		lowerHalfInnerC.add(Box.createRigidArea(new Dimension(10, 0)));
		lowerHalfInnerC.add(exitToMenuButton);
		lowerHalfInnerC.add(Box.createRigidArea(new Dimension(15, 0)));

		lowerHalfLowerC.add(Box.createRigidArea(new Dimension(0, 5)));

		lowerHalf.add(lowerHalfUpperC);
		lowerHalf.add(lowerHalfInnerC);
		lowerHalf.add(lowerHalfLowerC);

		gameOverView.add(lowerHalf, BorderLayout.SOUTH);
	}

	private void buildFormPanel(MainWindow window) {

		JPanel upperHalf = new JPanel();
		upperHalf.setLayout(new BoxLayout(upperHalf, BoxLayout.X_AXIS));
		upperHalf.setBackground( new Color(0, 0, 0, 0) );

		JPanel upperContainer = new JPanel();
		upperContainer.setLayout(new BoxLayout(upperContainer, BoxLayout.Y_AXIS));
		upperContainer.setBackground( new Color(0, 0, 0, 0) );

		// Title of view
		JPanel titleContainer = new JPanel();
		titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.X_AXIS));
		titleContainer.setBackground( new Color(0, 0, 0, 0) );
		JLabel titleLabel = new JLabel("Game Over");
		titleLabel.setFont(window.fontTexBold.deriveFont(36f));
		titleLabel.setForeground(Color.WHITE);
		titleContainer.add(titleLabel);

		// Win label (you win!, you lose!, player 1 won!, player 2 won!)
		JPanel winLabelCenter = new JPanel();
		winLabelCenter.setLayout(new BoxLayout(winLabelCenter, BoxLayout.X_AXIS));
		winLabelCenter.setBackground( new Color(0, 0, 0, 0) );

		winLabelCenter.add(Box.createHorizontalGlue());

		JLabel winLabel = new JLabel(winner + " won!");

		JPanel winLabelInner = new JPanel();
		winLabelInner.setLayout(new BoxLayout(winLabelInner, BoxLayout.X_AXIS));
		winLabelInner.add(Box.createRigidArea(new Dimension(20, 0)));
		winLabel.setFont(window.fontTexBold.deriveFont(20f));
		winLabel.setForeground(winnerColor);
		winLabelInner.add(winLabel);
		winLabelInner.add(Box.createRigidArea(new Dimension(20, 0)));
		winLabelInner.setBackground(Color.BLACK);
		winLabelCenter.add(winLabelInner);

		winLabelCenter.add(Box.createHorizontalGlue());

		// Score label (Name's Score: 15)
		// If there were two players, do two lines, as such (Name's Score: x; Other's Score: y);
		JPanel scoresPanel = new JPanel();
		scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.Y_AXIS));
		scoresPanel.setBackground( new Color(0, 0, 0, 0) );

		for (int i = 0; i < 2; i++) {
			// Create container for these inputs.
			JPanel scorePanel = new JPanel();
			scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.X_AXIS));
			scorePanel.setBackground( new Color(0, 0, 0, 0) );
			scorePanel.setPreferredSize(new Dimension(300,30));
			scorePanel.setMinimumSize(new Dimension(300,30));
			scorePanel.setMaximumSize(new Dimension(300,30));

			// Create label elements.
			JLabel playerNameLabel = new JLabel(playerNames[i] + "'s Score: ");
			playerNameLabel.setFont(window.fontTexBold.deriveFont(14f));
			playerNameLabel.setForeground(window.ColorHighlight);

			JLabel playerScoreLabel = new JLabel(String.valueOf(playerScores[i]));
			playerScoreLabel.setFont(window.fontTexBold.deriveFont(14f));
			playerScoreLabel.setForeground(window.ColorHighlight);

			// Add components to container.
			scorePanel.add(playerNameLabel);
			scorePanel.add(Box.createHorizontalGlue());
			scorePanel.add(playerScoreLabel);

			// Add container to master panel.
			scoresPanel.add(scorePanel);

			if (numPlayers == 2 && i == 0) {
				scoresPanel.add(Box.createRigidArea(new Dimension(0, 0)));
			}
		}

		upperContainer.add(Box.createVerticalGlue());
		upperContainer.add(titleContainer);
		upperContainer.add(Box.createRigidArea(new Dimension(0, 40)));
		upperContainer.add(winLabelCenter);
		upperContainer.add(Box.createRigidArea(new Dimension(0, 30)));
		upperContainer.add(scoresPanel);
		upperContainer.add(Box.createVerticalGlue());

		upperHalf.add(Box.createHorizontalGlue());
		upperHalf.add(upperContainer);
		upperHalf.add(Box.createHorizontalGlue());

		gameOverView.add(upperContainer, BorderLayout.CENTER);
	}

	@Override
	public JPanel getModel() {
		return gameOverView;
	}
}
