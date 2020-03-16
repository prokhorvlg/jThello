package jThello;

import Libraries.CustomButton;

import java.awt.*;
import java.awt.image.BufferedImage;
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
	public ModelInterface setPlayerNameViewModel;
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
		highScoresModel.initializePanel(this);
		rulesModel.initializePanel(this);
	}
	
	public void Display() {
		this.pack();
		this.setSize(1200,700);
	}

	// Loads image from given path into a label return object.
	public JLabel loadImage(String filePath) throws IOException {
		BufferedImage myPicture = ImageIO.read(new File(filePath));
		return new JLabel(new ImageIcon(myPicture));
	}

	// Loads image from given path directly into returned ImageIcon object.
	public ImageIcon loadImageRaw(String filePath) throws IOException {
		BufferedImage myPicture = ImageIO.read(new File(filePath));
		return new ImageIcon(myPicture);
	}

	// Returns a custom button object with the appropriate label.
	public CustomButton initMenuButton(String text, String type) {
		CustomButton menuButton = new CustomButton(text, type);
		menuButton.setLayout(new BorderLayout());

		JPanel centerMenuButton = new JPanel();
		centerMenuButton.setLayout(new BoxLayout(centerMenuButton, BoxLayout.X_AXIS));
		centerMenuButton.setBackground( new Color(0, 0, 0, 0) );

		JLabel menuButtonText = new JLabel(text);
		menuButtonText.setForeground(Color.decode("#22d064"));
		menuButtonText.setFont(fontTexBold.deriveFont(14f));
		centerMenuButton.add(Box.createHorizontalGlue());
		centerMenuButton.add(menuButtonText);
		centerMenuButton.add(Box.createHorizontalGlue());

		menuButton.add(Box.createRigidArea(new Dimension(0, 8)), BorderLayout.NORTH);
		menuButton.add(centerMenuButton, BorderLayout.CENTER);
		menuButton.add(Box.createRigidArea(new Dimension(0, 8)), BorderLayout.SOUTH);

		return menuButton;
	}
	
	// GAME VIEW FRONT END
	
	// HIGH SCORES VIEW FRONT END
	
	// RULES VIEW FRONT END
	
	// ABOUT VIEW FRONT END
	
}
