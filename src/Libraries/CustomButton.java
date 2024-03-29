package Libraries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

// ORIGINAL CODE HERE: https://coderanch.com/t/342333/java/Custom-Buttons
// Thanks to Ko Wey for the original code.

public class CustomButton extends JPanel implements MouseListener {

        String title = null;
        String type = null;
        private Vector<ActionListener> listeners = null;
        boolean hit = false;
        boolean hover = false;

        Dimension buttonSize = null;

        public CustomButton (String title, String type) {
            super();
            this.title = title;
            this.type = type;
            listeners = new Vector<>();
            addMouseListener(this);

            if (type.equals("main")) {
                buttonSize = new Dimension(300,40);
            } else if (type.equals("bottom")) {
                buttonSize = new Dimension(200,40);
            } else {
                buttonSize = new Dimension(200,40);
            }
        }

        public Dimension getMinimumSize(){
            return buttonSize;
        }

        public Dimension getPreferredSize(){
            return buttonSize;
        }

        public Dimension getMaximumSize(){
            return buttonSize;
        }

        public void paintComponent(Graphics g) {
            // Create a 2d graphics object.
            Graphics2D g2D = (Graphics2D)g;

            // Draw the background of the button.
            g2D.setColor(Color.decode("#0a2a16"));
            g2D.fillRect(0, 0, getWidth(), getHeight());

            // Draw the stroke of the button (changes color based on hit/no hit, and hover/no hover).
            if (hit) {
                g2D.setColor(Color.decode("#000000"));
            } else if (hover) {
                g2D.setColor(Color.decode("#ffffff"));
            } else {
                g2D.setColor(Color.decode("#22d064"));
            }
            g2D.setStroke(new BasicStroke(3));
            g2D.drawRect(2,2,getWidth()-3,getHeight()-3);
        };

        public void mousePressed(MouseEvent e){
            hit = true;
            repaint();
        }

        public void mouseReleased(MouseEvent e){
            hit = false;
            repaint();
        }

        public void mouseClicked(MouseEvent e){
            fireEvent(new ActionEvent(this,0, title));
            hover = false;
            hit = false;
        }

        public void mouseEntered(MouseEvent e){
            hover = true;
            repaint();
        }

        public void mouseExited(MouseEvent e){
            hover = false;
            repaint();
        }

        public void addActionListener(ActionListener listener) {
            listeners.addElement(listener);
        }

        public void removeActionListener(ActionListener listener) {
            listeners.removeElement(listener);
        }

        private void fireEvent(ActionEvent event) {
            for (int i = 0; i < listeners.size(); i++){
                ActionListener listener = listeners.elementAt(i);
                listener.actionPerformed(event);
            };
        }
}
