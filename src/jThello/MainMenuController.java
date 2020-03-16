package jThello;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainMenuController {
	
	private MainWindow window;
	
	public void initializeEventHandlers(MainMenuModel model, MainWindow _window) {
		window = _window;
		model.newGameAIButton.addActionListener(new newGameAIButtonListener());
		model.newGamePlayerButton.addActionListener(new newGamePlayerButtonListener());
		model.highScoresButton.addActionListener(new highScoresButtonListener());
		model.rulesButton.addActionListener(new rulesButtonListener());
		model.aboutButton.addActionListener(new aboutButtonListener());
		model.exitButton.addActionListener(new exitButtonListener());
	}
	
	private class newGameAIButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				window.setPlayerNameViewModel = new SetPlayerNameViewModel(1);
				window.setPlayerNameViewModel.initializePanel(window);
				window.openView(window.setPlayerNameViewModel);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class newGamePlayerButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				window.setPlayerNameViewModel = new SetPlayerNameViewModel(2);
				window.setPlayerNameViewModel.initializePanel(window);
				window.openView(window.setPlayerNameViewModel);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class highScoresButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			window.openView(window.highScoresModel);
		}
	}
	
	private class rulesButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			window.openView(window.rulesModel);
		}
	}
	
	private class aboutButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			window.openView(window.aboutViewModel);
		}
	}
	
	private class exitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
}
