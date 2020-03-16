package jThello;

import Libraries.BackgroundPanel;
import Libraries.CustomButton;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class SetPlayerNameViewModel implements ModelInterface {
	public SetPlayerNameController setPlayerNameController;
	public BackgroundPanel setPlayerNameView;

	CustomButton okButton;
	public JTextField[] textInputs = new JTextField[2];
	
	SetPlayerNameViewModel(int _numPlayers) throws IOException {
		setPlayerNameController = new SetPlayerNameController();
		setPlayerNameController.numPlayers = _numPlayers;
		setPlayerNameView = new BackgroundPanel(ImageIO.read(new File("images/bg.png")), BackgroundPanel.TILED, 0.0f, 0.0f);
	}

	@Override
	public void initializePanel(MainWindow window) throws IOException {

		// Add elements to screen.
		buildFormPanel(window);
		buildOKPanel(window);

		setPlayerNameController.initializeEventHandlers(this, window);
	}

	private void buildOKPanel(MainWindow window) {
		okButton = window.initMenuButton("Submit", "bottom");

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
		lowerHalfInnerC.add(okButton);
		lowerHalfInnerC.add(Box.createRigidArea(new Dimension(15, 0)));

		lowerHalfLowerC.add(Box.createRigidArea(new Dimension(0, 5)));

		lowerHalf.add(lowerHalfUpperC);
		lowerHalf.add(lowerHalfInnerC);
		lowerHalf.add(lowerHalfLowerC);

		setPlayerNameView.add(lowerHalf, BorderLayout.SOUTH);
	}

	private void buildFormPanel(MainWindow window) {

		JPanel upperHalf = new JPanel();
		upperHalf.setLayout(new BoxLayout(upperHalf, BoxLayout.X_AXIS));
		upperHalf.setBackground( new Color(0, 0, 0, 0) );

		JPanel upperContainer = new JPanel();
		upperContainer.setLayout(new BoxLayout(upperContainer, BoxLayout.Y_AXIS));
		upperContainer.setBackground( new Color(0, 0, 0, 0) );

		JPanel titleContainer = new JPanel();
		titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.X_AXIS));
		titleContainer.setBackground( new Color(0, 0, 0, 0) );
		JLabel titleLabel = new JLabel("Player Names");
		titleLabel.setFont(window.fontTexBold.deriveFont(36f));
		titleLabel.setForeground(Color.WHITE);
		titleContainer.add(titleLabel);

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setBackground( new Color(0, 0, 0, 0) );

		for (int i = 0; i < setPlayerNameController.numPlayers; i++) {
			// Create container for these inputs.
			JPanel inputPanel = new JPanel();
			inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
			inputPanel.setBackground( new Color(0, 0, 0, 0) );

			// Create label and input element.
			JLabel tempLabel = new JLabel("Enter Player " + (i+1) + "'s " + "Name: ");
			tempLabel.setFont(window.fontTexBold.deriveFont(14f));
			tempLabel.setForeground(window.ColorHighlight);
			textInputs[i] = new JTextField();
			textInputs[i].setFont(window.fontTexBold.deriveFont(14f));
			textInputs[i].setPreferredSize(new Dimension(300,30));
			textInputs[i].setMinimumSize(new Dimension(300,30));
			textInputs[i].setMaximumSize(new Dimension(300,30));

			// Add components to container.
			inputPanel.add(Box.createHorizontalGlue());
			inputPanel.add(tempLabel);
			inputPanel.add(Box.createRigidArea(new Dimension(40, 0)));
			inputPanel.add(textInputs[i]);
			inputPanel.add(Box.createHorizontalGlue());

			// Add container to master panel.
			formPanel.add(inputPanel);

			if (setPlayerNameController.numPlayers == 2 && i == 0) {
				formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
			}
		}

		upperContainer.add(Box.createVerticalGlue());
		upperContainer.add(titleContainer);
		upperContainer.add(Box.createRigidArea(new Dimension(0, 60)));
		upperContainer.add(formPanel);
		upperContainer.add(Box.createVerticalGlue());

		upperHalf.add(Box.createHorizontalGlue());
		upperHalf.add(upperContainer);
		upperHalf.add(Box.createHorizontalGlue());

		setPlayerNameView.add(upperContainer, BorderLayout.CENTER);
	}

	@Override
	public JPanel getModel() {
		return setPlayerNameView;
	}
}
