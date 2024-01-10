package component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import javax.swing.JButton;

public class MenuButton extends JButton{
    
    public MenuButton() {
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void setSelected(boolean b) {
        super.setSelected(b); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        if(isSelected()){
            g.setColor(new Color(85,186,255));
            g.fillRect(0, getHeight()-5, getWidth(), getHeight());
        }
        
    }
    
    
    
}
