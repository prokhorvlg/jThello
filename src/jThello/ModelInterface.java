package jThello;

import java.io.IOException;
import javax.swing.JPanel;

public interface ModelInterface {
	public void initializePanel(MainWindow window) throws IOException;
	public JPanel getModel();
}
