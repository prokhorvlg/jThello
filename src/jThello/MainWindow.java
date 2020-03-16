package jThello;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 8222884759290371299L;

	// Different front end views.
	public ModelInterface mainMenuModel;
	public ModelInterface aboutViewModel;
	public ModelInterface gameViewModel;
	public ModelInterface highScoresModel;
	public ModelInterface rulesModel;

	// Universally-used fonts and typographical settings.
	public File fontTexBoldFile;
	public Font fontTexBold;

	public Color ColorPrimary;
	public Color ColorHighlight;

	// Initialize the window at start of program.
	public MainWindow() throws IOException, FontFormatException {

		// Initialize typographical constants.
		fontTexBoldFile = new File("fonts/texgyreadventor-bold.ttf");
		fontTexBold = Font.createFont(Font.TRUETYPE_FONT, fontTexBoldFile);

		ColorPrimary = Color.white;
		ColorHighlight = Color.decode("#22d064");

		mainMenuModel = new MainMenuModel();
		aboutViewModel = new AboutViewModel();
		gameViewModel = new GameViewModel();
		highScoresModel = new HighScoresViewModel();
		rulesModel = new RulesViewModel();
		
		// Initialize the content for all of the screens.
		initializePanels();
		
		// Set default parameters for window.
		setTitle("jThello");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Open main menu view.
		openView(mainMenuModel);
		this.Display();
	    this.setVisible(true);	
	};
	
	// Opens a given view in this window.
	public void openView(ModelInterface model) {
		// Scrub the window of existing view.
		getContentPane().removeAll();
		// Add desired view.
		getContentPane().add(model.getModel());
		this.Display();
	};
	
	// Initializes all of the views with elements, content, and event listeners.
	private void initializePanels() throws IOException {
		mainMenuModel.initializePanel(this);
		aboutViewModel.initializePanel(this);
		gameViewModel.initializePanel(this);
		highScoresModel.initializePanel(this);
		rulesModel.initializePanel(this);
	}
	
	public void Display() {
		this.pack();
		this.setSize(1200,700);
	}
	
	// GAME VIEW FRONT END
	
	// HIGH SCORES VIEW FRONT END
	
	// RULES VIEW FRONT END
	
	// ABOUT VIEW FRONT END
	
}
