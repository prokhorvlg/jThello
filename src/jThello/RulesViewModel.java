package jThello;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
public class RulesViewModel implements ModelInterface {
	public RulesController rulesController;
	public JPanel rulesView;
	private JLabel jlabel;
	
	private JEditorPane contentPane;
	public JScrollPane contentPanel;
	
	public JPanel backButtonPanel;
	public JButton backButton;
	
	RulesViewModel() {
		rulesController = new RulesController();
		rulesView = new JPanel(new BorderLayout());
	}     

	@Override
	public void initializePanel(MainWindow window) {
		// Add elements to screen.
		buildTitlePanel();
		buildAboutPanel();
		buildBackPanel();
	    // Add event listeners to buttons.
		rulesController.initializeEventHandlers(this, window);
	}

	@Override
	public JPanel getModel() {
		return rulesView;
	}
	
	// Constructs the panel containing the title
	private void buildTitlePanel() {
		jlabel = new JLabel("Rules");
		jlabel.setHorizontalAlignment(JLabel.CENTER);
		rulesView.add(jlabel, BorderLayout.NORTH);
	}
	
	// Constructs the panel containing the About information
	private void buildAboutPanel() {
		String aboutText = getOthelloRules();
		contentPane = new JEditorPane();
		contentPane.setEditable(false);
		contentPane.setText(aboutText);
        JScrollPane scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rulesView.add(scrollPane, BorderLayout.CENTER);
	}
	
	private String getOthelloRules() {
		// TODO: Load rules from a text file
		String res = "THE OFFICIAL RULES OF OTHELLO\n";
		res += "These rules have been divided into ten separate sections as follows:\n";
		res += "                  1. Equipment\n";
		res += "                  2. The Starting Position\n";
		res += "                  3. The Move\n";
		res += "                  4. Determining the Winner and Scoring the Game\n";
		res += "                  5. The Timepiece\n";
		res += "                  6. Default\n";
		res += "                  7. Improper Moves\n";
		res += "                  8. General Conduct\n";
		res += "                  9. Penalties\n";
		res += "                  10. Miscellaneous\n";
		return res;
	}
	
	// Constructs the panel containing the back button
	private void buildBackPanel() {
		backButton = new JButton("OK");
		backButton.setPreferredSize(new Dimension(80, 40));
		JPanel backButtonPanel = new JPanel(new BorderLayout());
		backButtonPanel.add(backButton, BorderLayout.LINE_END);
		rulesView.add(backButtonPanel, BorderLayout.SOUTH);	
	}
}
