package jThello;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoresController {
	private MainWindow window;
	
	public void initializeEventHandlers(HighScoresViewModel model, MainWindow _window) {
		window = _window;
		model.backButton.addActionListener(new backButtonListener());
	}
	
	private class backButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			window.openView(window.mainMenuModel);
		}
	}

	public List<Score> readCSVIntoList(String filePath) {
		List<Score> myScores = new ArrayList<>();

		// Read CSV into arraylist...
		Path filePathO = Paths.get(filePath);
		try (BufferedReader br = Files.newBufferedReader(filePathO,
				StandardCharsets.US_ASCII)) {

			String line = br.readLine();
			while (line != null) {
				String[] items = line.split(",");
				Score newScore = new Score(items[0], items[1], items[2]);
				myScores.add(newScore);
				line = br.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return myScores;
	}

	public void sortListOfScores(List<Score> myScores) {
		myScores.sort(Collections.reverseOrder());
	}

	public Object[][] convertListIntoObject(List<Score> myScores) {
		Object[][] myScoresObject = new Object[myScores.size()][];

		// Cycle through each score and convert into object array.
		int counter = 0;
		for (Score score : myScores) {
			myScoresObject[counter] = new Object[]{counter+1, score.name, score.score};
			counter++;
		}

		return myScoresObject;
	}
}
