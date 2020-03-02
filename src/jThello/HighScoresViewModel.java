package jThello;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class HighScoresViewModel implements ModelInterface {
	
	public HighScoresController highScoresController;
	public JPanel highScoresView;
	public JLabel jlabel;
	private JTable jtable;
	
	public JEditorPane contentPane;
	public JScrollPane contentPanel;
	
	public JPanel backButtonPanel;
	public JButton backButton;
	
	HighScoresViewModel() {
		highScoresController = new HighScoresController();
		highScoresView = new JPanel(new BorderLayout());
	}     

	@Override
	public void initializePanel(MainWindow window) {
		// Add elements to screen.
		buildTitlePanel();
		buildHighScoresPanel();
		buildBackPanel();
	    // Add event listeners to buttons.
		highScoresController.initializeEventHandlers(this, window);
	}

	@Override
	public JPanel getModel() {
		return highScoresView;
	}
	
	// Constructs the panel containing the title
	private void buildTitlePanel() {
		jlabel = new JLabel("High Scores");
		jlabel.setHorizontalAlignment(JLabel.CENTER);
		highScoresView.add(jlabel, BorderLayout.NORTH);
	}
	
	// Constructs the panel containing the About information
	private void buildHighScoresPanel() {
		//TODO: Add JTable
        JScrollPane scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        highScoresView.add(scrollPane, BorderLayout.CENTER);
	}
	
	// Constructs the panel containing the back button
	private void buildBackPanel() {
		backButton = new JButton("OK");
		backButton.setPreferredSize(new Dimension(80, 40));
		JPanel backButtonPanel = new JPanel(new BorderLayout());
		backButtonPanel.add(backButton, BorderLayout.LINE_END);
		highScoresView.add(backButtonPanel, BorderLayout.SOUTH);	
	}
	
	private void generateTable() {
		// TODO
	}
}

