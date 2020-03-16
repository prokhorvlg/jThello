package jThello;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SetPlayerNameController {
	private MainWindow window;
	public int numPlayers;
	public void initializeEventHandlers(SetPlayerNameViewModel model, MainWindow _window) {
		window = _window;
		model.okButton.addActionListener(new StartGameButtonListener());
	}
	
	private class StartGameButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (numPlayers == 1) {
					// AI
					window.gameViewModel = new GameViewModel(true);
					window.gameViewModel.initializePanel(window);
				}
				else if (numPlayers == 2) {
					//2 Player
					window.gameViewModel = new GameViewModel(false);
					window.gameViewModel.initializePanel(window);
				}
				window.openView(window.gameViewModel); // Start the game
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
