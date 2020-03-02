package jThello;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenuModel implements ModelInterface {
	
	MainMenuController mainMenuController = new MainMenuController();
	
	JPanel mainMenuView = new JPanel();
	
	// MAIN MENU VIEW FRONT END
	
	JLabel tagline = new JLabel("JThello");
	
	public JButton newGameAIButton = new JButton("New Game vs AI");
	public JButton newGamePlayerButton = new JButton("New Game vs Player");
	public JButton highScoresButton = new JButton("High Scores");
	public JButton rulesButton = new JButton("Rules");
	public JButton aboutButton = new JButton("About");
	public JButton exitButton = new JButton("Exit");
	
	@Override
	public void initializePanel(MainWindow window) {
		// Add elements to screen.
		mainMenuView.add(tagline);
		mainMenuView.add(newGameAIButton);
		mainMenuView.add(newGamePlayerButton);
		mainMenuView.add(highScoresButton);
		mainMenuView.add(rulesButton);
		mainMenuView.add(aboutButton);
		mainMenuView.add(exitButton);
		
		// Add event listeners to buttons.
		mainMenuController.initializeEventHandlers(this, window);
		
		// Set layout of this panel.
		mainMenuView.setLayout(new GridLayout(0, 1, 5, 0));
	}
	
	@Override
	public JPanel getModel() {
		return mainMenuView;
	}
}
