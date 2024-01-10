package component;

import app.MessageType;
import event.PublicEvent;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.Friend;
import model.Send_Message;
import service.Service;

public class Chat_Add_Friend extends javax.swing.JLayeredPane {

    private int userID;

    public int getUserID() {
        return userID;
    }
    
    public Chat_Add_Friend(int userID, boolean isRight, boolean responded) {
        this.userID = userID;
        initComponents();
        setOpaque(false);
        if(isRight||responded){
            acceptButton.setEnabled(false);
            rejectButton.setEnabled(false);
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        acceptButton = new component.MenuButton();
        rejectButton = new component.MenuButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel1.setText("Let's make friends");

        jLayeredPane1.setLayout(new java.awt.GridLayout(1, 2));

        acceptButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/accept.png"))); // NOI18N
        acceptButton.setText("Accept");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });
        jLayeredPane1.add(acceptButton);

        rejectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reject.png"))); // NOI18N
        rejectButton.setText("Reject");
        rejectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rejectButtonActionPerformed(evt);
            }
        });
        jLayeredPane1.add(rejectButton);

        setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        setLayer(jLayeredPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptButtonActionPerformed
        Service.getInstance().getClient().emit("add_friend", new Friend(Service.getInstance().getUser().getID(), userID).toJSONObject());  
        acceptButton.setSelected(true);
        rejectButton.setSelected(false);
        acceptButton.setEnabled(false);
        rejectButton.setEnabled(false);
    }//GEN-LAST:event_acceptButtonActionPerformed

    private void rejectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rejectButtonActionPerformed
        Service.getInstance().getClient().emit("reject_add_friend", new Friend(Service.getInstance().getUser().getID(), userID).toJSONObject());
        Send_Message message = new Send_Message(Service.getInstance().getUser().getID(), userID, "Reject", getTimeNow(), MessageType.TEXT);
        Service.getInstance().getClient().emit("send_to_user", message.toJSONObject());
        PublicEvent.getInstance().getEventChat().sendMessage(message);
        rejectButton.setSelected(true);
        acceptButton.setSelected(false);
        acceptButton.setEnabled(false);
        rejectButton.setEnabled(false);
    }//GEN-LAST:event_rejectButtonActionPerformed
    private String getTimeNow(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentTime.format(formatter);
        return formattedDateTime;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private component.MenuButton acceptButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private component.MenuButton rejectButton;
    // End of variables declaration//GEN-END:variables
}
