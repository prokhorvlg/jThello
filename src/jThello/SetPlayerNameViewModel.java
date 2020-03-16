package jThello;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SetPlayerNameViewModel implements ModelInterface {
	public SetPlayerNameController setPlayerNameController;
	public JPanel setPlayerNameView;
	private JLabel titleLabel;
	private JPanel formPanel;
	JButton okButton;
	
	SetPlayerNameViewModel(int _numPlayers) {
		setPlayerNameController = new SetPlayerNameController();
		setPlayerNameController.numPlayers = _numPlayers;
		setPlayerNameView = new JPanel(new BorderLayout());
	}

	@Override
	public void initializePanel(MainWindow window) throws IOException {
		// Add elements to screen.
		buildTitlePanel();
		buildFormPanel();
		buildOKPanel();
		setPlayerNameController.initializeEventHandlers(this, window);
	}

	private void buildOKPanel() {
		okButton = new JButton("OK");
		okButton.setPreferredSize(new Dimension(80, 40));
		JPanel backButtonPanel = new JPanel(new BorderLayout());
		backButtonPanel.add(okButton, BorderLayout.LINE_END);
		setPlayerNameView.add(backButtonPanel, BorderLayout.SOUTH);	
	}

	private void buildFormPanel() {
		//TODO: Beautify form input fields
		formPanel = new JPanel(new BorderLayout());
		JPanel inputPanel = new JPanel(new GridLayout(setPlayerNameController.numPlayers,2));
		System.out.println(setPlayerNameController.numPlayers);
		for (int i = 0; i < setPlayerNameController.numPlayers; i++) {
			JLabel tempLabel = new JLabel("Enter Player" +(i+1) + " " + "Name: ");
			JTextField textField = new JTextField(15);
			inputPanel.add(tempLabel);
			inputPanel.add(textField);
			formPanel.add(inputPanel);
		}
		setPlayerNameView.add(formPanel, BorderLayout.CENTER);
	}

	private void buildTitlePanel() {
		titleLabel = new JLabel("Set Player Names");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		setPlayerNameView.add(titleLabel, BorderLayout.NORTH);
	}

	@Override
	public JPanel getModel() {
		return setPlayerNameView;
	}
}
