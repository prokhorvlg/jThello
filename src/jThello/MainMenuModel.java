package jThello;

import Libraries.BackgroundPanel;
import Libraries.CustomButton;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MainMenuModel implements ModelInterface {
	
	MainMenuController mainMenuController;
	BackgroundPanel mainMenuView;
	
	// MAIN MENU VIEW FRONT END

	ImageIcon buttonImage;
	public CustomButton newGameAIButton;
	public CustomButton newGamePlayerButton;
	public CustomButton highScoresButton;
	public CustomButton rulesButton;
	public CustomButton aboutButton;
	public CustomButton exitButton;

	Font fontTexBold;

	MainMenuModel() throws IOException {
		mainMenuController = new MainMenuController();
		mainMenuView = new BackgroundPanel(ImageIO.read(new File("images/bg.png")), BackgroundPanel.TILED, 0.0f, 0.0f);
	}
	
	@Override
	public void initializePanel(MainWindow window) throws IOException {

		fontTexBold = window.fontTexBold;

		newGameAIButton = window.initMenuButton("New Game vs AI", "main");
		newGamePlayerButton = window.initMenuButton("New Game vs Player", "main");
		highScoresButton = window.initMenuButton("High Scores", "main");
		rulesButton = window.initMenuButton("Rules", "main");
		aboutButton = window.initMenuButton("About", "main");
		exitButton = window.initMenuButton("Exit", "main");

		JPanel fullMainMenuContainer = new JPanel();
		fullMainMenuContainer.setLayout(new BoxLayout(fullMainMenuContainer, BoxLayout.X_AXIS));
		fullMainMenuContainer.setBackground(new Color(0, 0, 0, 0));

		fullMainMenuContainer.add(Box.createHorizontalGlue());
		JPanel menuCenteredColumn = new JPanel();
		fullMainMenuContainer.add(Box.createHorizontalGlue());

		menuCenteredColumn.setLayout(new BoxLayout(menuCenteredColumn, BoxLayout.Y_AXIS));
		menuCenteredColumn.setBackground(new Color(0, 0, 0, 0));
		//menuCenteredColumn.setMaximumSize(new Dimension(200, 60));


		// -- LOGO
		JLabel mainLogo = window.loadImage("images/jThello_logo.png");
		JPanel mainLogoContainer = new JPanel();
		mainLogoContainer.setLayout(new BoxLayout(mainLogoContainer, BoxLayout.X_AXIS));
		mainLogoContainer.add(Box.createHorizontalGlue());
		mainLogoContainer.add(mainLogo);
		mainLogoContainer.add(Box.createHorizontalGlue());
		mainLogoContainer.setBackground(new Color(0, 0, 0, 0));

		// -- LABEL

		JPanel mainTitleContainer = new JPanel();
		mainTitleContainer.setLayout(new BoxLayout(mainTitleContainer, BoxLayout.X_AXIS));
		mainTitleContainer.add(Box.createHorizontalGlue());
		JLabel mainTitle = new JLabel("jThello");
		mainTitle.setFont(window.fontTexBold.deriveFont(60f));
		mainTitle.setForeground(window.ColorPrimary);
		mainTitleContainer.add(mainTitle);
		mainTitleContainer.add(Box.createHorizontalGlue());
		mainTitleContainer.setBackground(new Color(0, 0, 0, 0));

		JPanel mainSubContainer = new JPanel();
		mainSubContainer.setLayout(new BoxLayout(mainSubContainer, BoxLayout.X_AXIS));
		mainSubContainer.add(Box.createHorizontalGlue());
		JLabel subLabel = new JLabel("A classic board game.");
		subLabel.setFont(window.fontTexBold.deriveFont(14f));
		subLabel.setForeground(window.ColorHighlight);
		mainSubContainer.add(subLabel);
		mainSubContainer.add(Box.createHorizontalGlue());
		mainSubContainer.setBackground(new Color(0, 0, 0, 0));

		int heightDiff = 2;

		// Add elements to screen.
		menuCenteredColumn.add(Box.createVerticalGlue());
		menuCenteredColumn.add(mainLogoContainer);
		menuCenteredColumn.add(mainTitleContainer);
		menuCenteredColumn.add(mainSubContainer);
		menuCenteredColumn.add(Box.createRigidArea(new Dimension(0, 30)));
		menuCenteredColumn.add(newGameAIButton);
		menuCenteredColumn.add(Box.createRigidArea(new Dimension(0, heightDiff)));
		menuCenteredColumn.add(newGamePlayerButton);
		menuCenteredColumn.add(Box.createRigidArea(new Dimension(0, heightDiff)));
		menuCenteredColumn.add(highScoresButton);
		menuCenteredColumn.add(Box.createRigidArea(new Dimension(0, heightDiff)));
		menuCenteredColumn.add(rulesButton);
		menuCenteredColumn.add(Box.createRigidArea(new Dimension(0, heightDiff)));
		menuCenteredColumn.add(aboutButton);
		menuCenteredColumn.add(Box.createRigidArea(new Dimension(0, heightDiff)));
		menuCenteredColumn.add(exitButton);
		menuCenteredColumn.add(Box.createVerticalGlue());

		mainMenuView.add(menuCenteredColumn);

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
