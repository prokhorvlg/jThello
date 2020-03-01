package jThello;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutController {
	MainWindow window;
	
	public void initializeEventHandlers(AboutViewModel model, MainWindow _window) {
		window = _window;
		model.backButton.addActionListener(new backButtonListener());
	}
	
	private class backButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			window.openView(window.mainMenuModel);
		}
	}
}
