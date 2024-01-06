package component;

import java.awt.Color;
import model.User;

public class Chat_Title extends javax.swing.JPanel {

    private User user;

    public User getUser() {
        return user;
    }
    
    public Chat_Title() {
        initComponents();
    }

    public void setUserName(User user){
        this.user = user;
        lbName.setText(user.getUsername());
        if(user.isOnline()){
            statusActive();
        }else{
            setStatusText("Offline");
        }
    }
    public void updateUser(User user){
        if(this.user==user){ // neu title user la user thay doi trang thai thi cap nhat 
            lbName.setText(user.getUsername());
            if(user.isOnline()){
                statusActive();
            }else{
                setStatusText("Offline");
            }
        }
    }
    private void statusActive(){
        lbStatus.setText("Active Now");
        lbStatus.setForeground(new Color(40, 147, 59));
    }
    
    private void setStatusText(String status){
        lbStatus.setText(status);
        lbStatus.setForeground(new Color(160, 160, 160));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        layer = new javax.swing.JLayeredPane();
        lbName = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();

        setBackground(new java.awt.Color(249, 249, 249));

        layer.setLayout(new java.awt.GridLayout(0, 1));

        lbName.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lbName.setForeground(new java.awt.Color(61, 61, 61));
        lbName.setText("Name");
        layer.add(lbName);

        lbStatus.setForeground(new java.awt.Color(0, 204, 0));
        lbStatus.setText("Action Now");
        layer.add(lbStatus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(layer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(331, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(layer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane layer;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbStatus;
    // End of variables declaration//GEN-END:variables
}
