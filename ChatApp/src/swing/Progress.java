package swing;

import javax.swing.JProgressBar;

public class Progress extends JProgressBar {
    
    private ProgressType progressType = ProgressType.NONE;

    public void setProgressType(ProgressType progressType) {
        this.progressType = progressType;
        repaint();
    }

    public ProgressType getProgressType() {
        return progressType;
    }
    
    
    
    public Progress() {
        setOpaque(false);
        setUI(new ProgressCircleUI(this));
    }
    
    public static enum ProgressType{
        NONE, DOWN_FILE, CANCEL, FILE  
    };
}
