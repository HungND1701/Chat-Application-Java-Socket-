/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import component.Chat_Body;
import component.Chat_Bottom;
import component.Chat_Title;
import event.EventChat;
import event.PublicEvent;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Acer
 */
public class Chat extends javax.swing.JPanel {

    /**
     * Creates new form Menu_Left
     */
    public Chat() {
        initComponents();
        init();
    }

    
    private void init() {
        setLayout(new MigLayout("fillx", "0[fill]0", "0[]0[fill, 100%, bottom]0[shrink 0]0"));
        Chat_Title chatTitle = new Chat_Title();
        Chat_Body chatBody = new Chat_Body();
        Chat_Bottom chatBottom = new Chat_Bottom();
        PublicEvent.getInstance().addEventChat(new EventChat() {
            @Override
            public void sendMessage(String text) {
                chatBody.addItemRight(text);
            }
        });
        add(chatTitle, "wrap");
        add(chatBody, "wrap");
        add(chatBottom, "h ::50%");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(249, 249, 249));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 707, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
