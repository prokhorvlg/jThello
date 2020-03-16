package jThello;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameOverController {
	private MainWindow window;

	public void initializeEventHandlers(GameOverViewModel model, MainWindow _window) {
		window = _window;
		model.restartGameButton.addActionListener(new RestartGameButtonListener(model.numPlayers, model.playerNames));
		model.exitToMenuButton.addActionListener(new ExitToMenuButtonListener());
	}
	
	private class RestartGameButtonListener implements ActionListener {
		int numPlayers;
		String[] playerNames;
		RestartGameButtonListener(int _numPlayers, String[] _playerNames) {
			numPlayers = _numPlayers;
			playerNames = _playerNames;
		}
		public void actionPerformed(ActionEvent e) {
			try {
				if (numPlayers == 1) {
					// AI
					window.gameViewModel = new GameViewModel(true, numPlayers, playerNames);
					window.gameViewModel.initializePanel(window);
				}
				else if (numPlayers == 2) {
					// 2 Player
					window.gameViewModel = new GameViewModel(false, numPlayers, playerNames);
					window.gameViewModel.initializePanel(window);
				}
				window.openView(window.gameViewModel); // Start the game
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class ExitToMenuButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			window.openView(window.mainMenuModel);
		}
	}
}
