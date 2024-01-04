/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.User;

public class Item_People extends javax.swing.JPanel {

    private final User user;

    public User getUser() {
        return user;
    }
    
    public Item_People(User user) {
        this.user = user;
        initComponents();
        lb.setText(user.getUsername());
        activeStatus.setActive(user.isOnline());
        init();
    }
    
    public void updateStatus(){
        activeStatus.setActive(user.isOnline());
    }
    
    private void init(){
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                setBackground(new Color(230,230,230));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                setBackground(new Color(249,249,249));
            }
            
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbStatus = new javax.swing.JLabel();
        imageAvatar2 = new swing.ImageAvatar();
        lb = new javax.swing.JLabel();
        activeStatus = new swing.ActiveStatus();

        setBackground(new java.awt.Color(249, 249, 249));

        lbStatus.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lbStatus.setForeground(new java.awt.Color(137, 134, 134));
        lbStatus.setText("Name");

        imageAvatar2.setBorderSize(1);
        imageAvatar2.setImage(new javax.swing.ImageIcon(getClass().getResource("/icon/profile.png"))); // NOI18N

        lb.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lb.setText("Name");

        activeStatus.setActive(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(imageAvatar2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbStatus)
                        .addGap(3, 3, 3)
                        .addComponent(activeStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(imageAvatar2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(activeStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.ActiveStatus activeStatus;
    private swing.ImageAvatar imageAvatar2;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lbStatus;
    // End of variables declaration//GEN-END:variables
}
