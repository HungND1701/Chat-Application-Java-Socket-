/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import model.User;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Acer
 */
public class Home extends javax.swing.JLayeredPane {

    private Chat chat;
    
    public Home() {
        initComponents();
        init();
    }
    
    private void init()
    {
        setLayout(new MigLayout("fillx, filly", "5[200!]5[fill, 100%]5[200!]5", "0[fill]5"));
        this.add(new Menu_Left());
        chat = new Chat();
        this.add(chat);
        this.add(new Menu_Right());
        chat.setVisible(false);
    }
    
    public void setUserChat(User user){
        chat.setUserChat(user);
        chat.setVisible(true);
    }
    public void updateUserChat(User user){
        chat.updateUserChat(user);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
