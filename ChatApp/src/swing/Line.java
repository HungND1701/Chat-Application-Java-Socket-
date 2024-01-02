package swing;

import java.awt.Graphics;
import javax.swing.JLabel;

public class Line extends JLabel{

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        g.setColor(getForeground());
        g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
    }
    
    
    
}
