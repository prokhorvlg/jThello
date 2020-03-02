package jThello;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 8222884759290371299L;

	// Different front end views.
	public ModelInterface mainMenuModel;
	public ModelInterface aboutViewModel;
	public ModelInterface highScoresModel;
	public ModelInterface rulesModel;
	
	// Initialize the window at start of program.
	public MainWindow() {
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
	private void initializePanels() {
		mainMenuModel.initializePanel(this);
		aboutViewModel.initializePanel(this);
		highScoresModel.initializePanel(this);
		rulesModel.initializePanel(this);
	}
	
	public void Display() {
		this.pack();
		this.setSize(550,525);
	}
	
	// GAME VIEW FRONT END
	
	// HIGH SCORES VIEW FRONT END
	
	// RULES VIEW FRONT END
	
	// ABOUT VIEW FRONT END
	
}
