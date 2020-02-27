package jThello;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MainMenuWindow extends JFrame {
	
	private static final long serialVersionUID = 21305503976948004L;
	
	JLabel jtfName = new JLabel("this shit dumb af");

	public void openMainMenu() {
		setTitle("jThello");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(0, 1, 5, 5));
		add(jtfName);
		pack();
		setSize(300, 300);
		setVisible(true);
	}
	
}
