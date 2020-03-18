package jThello;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SetPlayerNameController {
	private MainWindow window;
	public int numPlayers;

	public void initializeEventHandlers(SetPlayerNameViewModel model, MainWindow _window) {
		window = _window;
		model.okButton.addActionListener(new StartGameButtonListener(model.textInputs));
	}
	
	private class StartGameButtonListener implements ActionListener {
		JTextField[] textInputs;
		StartGameButtonListener(JTextField[] _textInputs) {
			textInputs = _textInputs;
		}
		public void actionPerformed(ActionEvent e) {
			try {
				if (numPlayers == 1) {
					// Grab the player's name and put it into a new array. Handle an empty field properly.
					String myPlayer = textInputs[0].getText();
					if (myPlayer.equals("")) {
						myPlayer = "Player";
					}
					String[] playerNames = { myPlayer, "AI" };

					// AI
					window.gameViewModel = new GameViewModel(true, numPlayers, playerNames);
					window.gameViewModel.initializePanel(window);
				}
				else if (numPlayers == 2) {
					// Grab the player's names and put it into a new array.
					String myPlayer1 = textInputs[0].getText();
					if (myPlayer1.equals("")) {
						myPlayer1 = "Player 1";
					}

					String myPlayer2 = textInputs[1].getText();
					if (myPlayer2.equals("")) {
						myPlayer2 = "Player 2";
					}

					String[] playerNames = { myPlayer1, myPlayer2 };

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
}
