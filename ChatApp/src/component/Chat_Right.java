package component;

import java.awt.Color;
import javax.swing.Icon;

public class Chat_Right extends javax.swing.JLayeredPane {


    public Chat_Right() {
        initComponents();
        txt.setBackground(new Color(179,233,255));
    }
    
    public void setText(String text){
        if(text.equals("")){
            txt.hideText();
        }else{
            txt.setText(text);
        }
    }
    
    public void setImage(Icon... image){
        txt.setImage(true, image);
    }
    
    public void setImage(String... image){
        txt.setImage(true, image);
    }
    
    public void setTime(String time){ // add time later
        txt.setTime(time);
    }
    
    public void setSeenStatus(){ // add time later
        txt.seen();
    }
    public void setEmoji(Icon icon){
        txt.hideText();
        txt.setEmoji(true, icon);
    }
    public void setFile(String fileName, String fileSize){
        txt.setFile(fileName, fileSize);
    }
    public void setAddFriend(int userID, boolean responded){
        txt.hideText();
        txt.setAddFriend(userID, true, responded);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt = new component.Chat_Item();

        setLayer(txt, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private component.Chat_Item txt;
    // End of variables declaration//GEN-END:variables
}
