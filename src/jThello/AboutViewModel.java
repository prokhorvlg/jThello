package jThello;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutViewModel implements ModelInterface {
	
	AboutController aboutController;
	JPanel aboutView;
	JLabel jlabel;
	JButton backButton;
	
	AboutViewModel() {
		aboutController = new AboutController();
		aboutView = new JPanel();
		jlabel = new JLabel("About");
		backButton = new JButton("OK");
	}     

	@Override
	public void initializePanel(MainWindow window) {
		// Add elements to screen.
	    aboutView.add(jlabel);
	    aboutView.add(backButton);
	    // Add event listeners to buttons.
	    aboutController.initializeEventHandlers(this, window);
	}

	@Override
	public JPanel getModel() {
		return aboutView;
	}
}
