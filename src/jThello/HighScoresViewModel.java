package jThello;

import Libraries.BackgroundPanel;
import Libraries.CustomButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class HighScoresViewModel implements ModelInterface {
	
	public HighScoresController highScoresController;
	public BackgroundPanel highScoresView;
	public JLabel jlabel;

	public JEditorPane contentPane;
	JTable scoresTable;

	public CustomButton backButton;
	
	HighScoresViewModel() throws IOException {
		highScoresController = new HighScoresController();
		highScoresView = new BackgroundPanel(ImageIO.read(new File("images/bg.png")), BackgroundPanel.TILED, 0.0f, 0.0f);
	}     

	@Override
	public void initializePanel(MainWindow window) {
		// Add elements to screen.
		buildUpperPanel(window);
		buildLowerPanel(window);
	    // Add event listeners to buttons.
		highScoresController.initializeEventHandlers(this, window);
	}

	@Override
	public JPanel getModel() {
		// Update high scores table while getting model.
		updateHighScores();

		// Return the view.
		return highScoresView;
	}

	// Constructs the panel containing the information, and the title label.
	private void buildUpperPanel(MainWindow window) {
		JPanel upperHalf = new JPanel();
		upperHalf.setLayout(new BoxLayout(upperHalf, BoxLayout.X_AXIS));
		upperHalf.setBackground( new Color(0, 0, 0, 0) );

		JPanel upperContainer = new JPanel();
		upperContainer.setLayout(new BoxLayout(upperContainer, BoxLayout.Y_AXIS));
		upperContainer.setBackground( new Color(0, 0, 0, 0) );

		// Create title label/panel.
		JPanel titleContainer = new JPanel();
		titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.X_AXIS));
		titleContainer.setBackground( new Color(0, 0, 0, 0) );
		JLabel titleLabel = new JLabel("High Scores");
		titleLabel.setFont(window.fontTexBold.deriveFont(36f));
		titleLabel.setForeground(Color.WHITE);
		titleContainer.add(titleLabel);
		titleContainer.add(Box.createHorizontalGlue());

		// Create scrollable information field.
		List<Score> myScores = highScoresController.readCSVIntoList("text-assets/scores.csv");
		highScoresController.sortListOfScores(myScores);
		Object[][] scoresData = highScoresController.convertListIntoObject(myScores);
		String[] columnNames = {"Rank", "Name", "Score"};

		scoresTable = new JTable(scoresData, columnNames);

		scoresTable.setShowGrid(false);
		scoresTable.setIntercellSpacing(new Dimension(10, 10));

		scoresTable.setColumnSelectionAllowed(false);
		scoresTable.setRowSelectionAllowed(false);
		scoresTable.setRowHeight(40);

		scoresTable.setEnabled(false);

		scoresTable.setOpaque(true);
		scoresTable.setFont(window.fontTexBold.deriveFont(14f));
		scoresTable.setForeground(window.ColorHighlight);
		scoresTable.setBackground(Color.decode("#0a2a16"));

		JScrollPane scrollPane = new JScrollPane(scoresTable);
		scrollPane.getViewport().setBackground(Color.decode("#0a2a16"));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Compile components.
		upperContainer.add(Box.createRigidArea(new Dimension(0, 40)));
		upperContainer.add(titleContainer);
		upperContainer.add(Box.createRigidArea(new Dimension(0, 30)));
		upperContainer.add(scrollPane);
		upperContainer.add(Box.createRigidArea(new Dimension(0, 40)));

		upperHalf.add(Box.createRigidArea(new Dimension(40, 0)));
		upperHalf.add(upperContainer);
		upperHalf.add(Box.createRigidArea(new Dimension(40, 0)));

		highScoresView.add(upperHalf, BorderLayout.CENTER);
	}

	// Constructs the panel containing the back button
	private void buildLowerPanel(MainWindow window) {
		backButton = window.initMenuButton("OK", "bottom");

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
		lowerHalfInnerC.add(backButton);
		lowerHalfInnerC.add(Box.createRigidArea(new Dimension(15, 0)));

		lowerHalfLowerC.add(Box.createRigidArea(new Dimension(0, 5)));

		lowerHalf.add(lowerHalfUpperC);
		lowerHalf.add(lowerHalfInnerC);
		lowerHalf.add(lowerHalfLowerC);

		highScoresView.add(lowerHalf, BorderLayout.SOUTH);
	}
	
	private void updateHighScores() {
		List<Score> myScores = highScoresController.readCSVIntoList("text-assets/scores.csv");
		highScoresController.sortListOfScores(myScores);
		Object[][] scoresData = highScoresController.convertListIntoObject(myScores);
		String[] columnNames = {"Rank", "Name", "Score"};

		TableModel model = new DefaultTableModel(scoresData, columnNames);
		scoresTable.setModel( model );
	}
}

