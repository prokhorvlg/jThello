package jThello;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AboutViewModel implements ModelInterface {
	
	public AboutController aboutController;
	public JPanel aboutView;
	private JLabel jlabel;
	
	private JEditorPane contentPane;
	public JScrollPane contentPanel;
	
	public JPanel backButtonPanel;
	public JButton backButton;
	
	AboutViewModel() {
		aboutController = new AboutController();
		aboutView = new JPanel(new BorderLayout());
	}     

	@Override
	public void initializePanel(MainWindow window) {
		// Add elements to screen.
		buildTitlePanel();
		buildAboutPanel();
		buildBackPanel();
	    // Add event listeners to buttons.
	    aboutController.initializeEventHandlers(this, window);
	}

	@Override
	public JPanel getModel() {
		return aboutView;
	}
	
	// Constructs the panel containing the title
	private void buildTitlePanel() {
		jlabel = new JLabel("About");
		jlabel.setHorizontalAlignment(JLabel.CENTER);
		aboutView.add(jlabel, BorderLayout.NORTH);
	}
	
	// Constructs the panel containing the About information
	private void buildAboutPanel() {
		String aboutText = "Welcome to JThello!";
		contentPane = new JEditorPane();
		contentPane.setEditable(false);
		contentPane.setText(aboutText);
        JScrollPane scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        aboutView.add(scrollPane, BorderLayout.CENTER);
	}
	
	// Constructs the panel containing the back button
	private void buildBackPanel() {
		backButton = new JButton("OK");
		backButton.setPreferredSize(new Dimension(80, 40));
		JPanel backButtonPanel = new JPanel(new BorderLayout());
		backButtonPanel.add(backButton, BorderLayout.LINE_END);
		aboutView.add(backButtonPanel, BorderLayout.SOUTH);	
	}
}
