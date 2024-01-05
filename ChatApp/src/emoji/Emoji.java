package emoji;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Emoji {
    private int id;
    private Icon icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Emoji() {
    }

    public Emoji(int id, Icon icon) {
        this.id = id;
        this.icon = icon;
    }
    
    public Emoji toSize(int x, int y){
        return new Emoji(id, new ImageIcon(((ImageIcon)icon).getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH)));
    }
}
