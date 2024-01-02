package component;

import java.awt.Adjustable;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import net.miginfocom.swing.MigLayout;
import swing.ScrollBar;

public class Chat_Body extends javax.swing.JPanel {

    public Chat_Body() {
        initComponents();
        init();
        String img[] = {"LFIh4_NH~psRyCRk-;ae9]WAIVtR", "LNOViRwH?]xY_24oX7xtJUxuWCoz"};
        
        addItemRight("Simpletext started as a passion project because I couldn’t find what I was looking for. Most apps were trying to do too much and ended up bloated with features I don’t need. So I built Simpletext based on a simple premise — what if there’s an app that refuses to do more, choosing instead to do just one thing, and do it well? For Simpletext, that one thing is writing.");
        addItemLeft("hello\nerererew\newewe", "abc");
        addItemLeft("hello\nerererew\newewe", "abc");
        addItemLeft("Send a text message to a group of contacts. Include photos, personalize your texts, and track who clicked your links.", "abc");
        addItemRight("hello\nHi");
        addItemLeft("Simpletext started as a passion project because I couldn’t find what I was looking for. Most apps were trying to do too much and ended up bloated with features I don’t need. So I built Simpletext based on a simple premise — what if there’s an app that refuses to do more, choosing instead to do just one thing, and do it well? For Simpletext, that one thing is writing.", "abc");
        addItemLeft("hello\nerererew\newewe", "def");
        addItemRight("hello\nerererew\newewe");
        addDate("29/12/2023");
        addItemLeft("hello\nerererew\newewe", "def");
        
        addItemLeft("hello\nerererew\newewe", "def", img);
        addItemRight("hello\nerererew\newewe");
        addItemRight("Send a text message to a group of contacts. Include photos, personalize your texts, and track who clicked your links.");
        addItemRight("hello\nHi");
        addItemLeft("Simpletext started as a passion project because I couldn’t find what I was looking for. Most apps were trying to do too much and ended up bloated with features I don’t need. So I built Simpletext based on a simple premise — what if there’s an app that refuses to do more, choosing instead to do just one thing, and do it well? For Simpletext, that one thing is writing.", "abc", new ImageIcon(getClass().getResource("/image/pam2.jpg")), new ImageIcon(getClass().getResource("/image/man1.jpg")));
        addItemLeft("hello\nerererew\newewe", "def", new ImageIcon(getClass().getResource("/image/pam.jpg")), new ImageIcon(getClass().getResource("/image/man1.jpg")));
        addDate("Today");
        addItemRight("hello\nerererew\newewe", new ImageIcon(getClass().getResource("/image/man2.jpg")), new ImageIcon(getClass().getResource("/image/pam2.jpg")));
        addItemLeft("", "def", new ImageIcon(getClass().getResource("/image/man2.jpg")), new ImageIcon(getClass().getResource("/image/man1.jpg")));
        addItemRight("hello\nerererew\newewe");
//        addItemFileLeft("hello\nerererew\newewe", "dev", "my doc.pdf", "10 MB");
//        addItemFileRight("hello\nerererew\newewe", "my doc.pdf", "10 MB");
//        addItemFileRight("", "my doc.pdf", "10 MB");
    }

    private void init(){
        body.setLayout(new MigLayout("fillx", "", "5[]5"));
        sp.setVerticalScrollBar(new ScrollBar());
        sp.getVerticalScrollBar().setBackground(Color.WHITE);
    }
    
    public void addItemLeft(String text, String user, Icon... image){
        Chat_Left_With_Profile item = new Chat_Left_With_Profile();
        item.setText(text);
        item.setImage(image);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        body.repaint();
        body.revalidate();
    }  
    
    public void addItemRight(String text, Icon... image){
        Chat_Right item = new Chat_Right();
        item.setText(text); 
        item.setImage(image);
        item.setTime();
        item.setSeenStatus();
        body.add(item, "wrap, al right, w 100::80%");
        body.repaint();
        body.revalidate();
        scrollToBottom();
    } 
    
    public void addItemLeft(String text, String user, String[] image){
        Chat_Left_With_Profile item = new Chat_Left_With_Profile();
        item.setText(text);
        item.setImage(image);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        body.repaint();
        body.revalidate();
    }  
    
    public void addItemRight(String text, String[] image){
        Chat_Right item = new Chat_Right();
        item.setText(text); 
        item.setImage(image);
        item.setTime();
        item.setSeenStatus();
        body.add(item, "wrap, al right, w 100::80%");
        body.repaint();
        body.revalidate();  
        scrollToBottom();
    } 
    
    public void addItemFileLeft(String text, String user, String fileName, String fileSize){
        Chat_Left_With_Profile item = new Chat_Left_With_Profile();
        item.setText(text);
        item.setFile(fileName, fileSize);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        body.repaint();
        body.revalidate();
    } 
    
    public void addItemFileRight(String text, String fileName, String fileSize){
        Chat_Right item = new Chat_Right();
        item.setText(text); 
        item.setFile(fileName, fileSize);
        item.setTime();
        item.setSeenStatus();
        body.add(item, "wrap, al right, w 100::80%");
        body.repaint();
        body.revalidate();
    } 
    
    public void addDate(String date){
        Chat_Date item = new Chat_Date();
        item.setDate(date);
        body.add(item, "wrap, al center");
        body.repaint();
        body.revalidate();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        body = new javax.swing.JPanel();

        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        body.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 719, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );

        sp.setViewportView(body);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void scrollToBottom() {
        JScrollBar verticalBar = sp.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}
