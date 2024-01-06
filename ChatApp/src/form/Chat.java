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
import java.util.List;
import model.Conversation;
import model.Receive_Message;
import model.Send_Message;
import model.User;
import net.miginfocom.swing.MigLayout;
import service.Service;

/**
 *
 * @author Acer
 */
public class Chat extends javax.swing.JPanel {

    private Chat_Title chatTitle;
    private Chat_Body chatBody;
    private Chat_Bottom chatBottom;
    
    public Chat() {
        initComponents();
        init();
    }

    
    private void init() {
        setLayout(new MigLayout("fillx", "0[fill]0", "0[]0[fill, 100%, bottom]0[shrink 0]0"));
        chatTitle = new Chat_Title();
        chatBody = new Chat_Body();
        chatBottom = new Chat_Bottom();
        PublicEvent.getInstance().addEventChat(new EventChat() {
            @Override
            public void sendMessage(Send_Message data) {
               chatBody.addItemRight(data);
            }

            @Override
            public void receiveMessage(Receive_Message data) {
                if(chatTitle.getUser().getID() == data.getFromUserID()){
                    chatBody.addItemLeft(data);
                }
            }

            @Override
            public void initMessage(List<Send_Message> list) {
                if(!list.isEmpty()){
                    for(Send_Message message: list){
                        if(message.getFromUserID()==Service.getInstance().getUser().getID()){
                            chatBody.addItemRight(message);
                        }else{
                            chatBody.addItemLeft(new Receive_Message(message.getFromUserID(), message.getText(), message.getTime()));
                        }
                    }
                } 
            }

            
        });
        add(chatTitle, "wrap");
        add(chatBody, "wrap");
        add(chatBottom, "h ::50%");
    }
    public void setUserChat(User user){
        chatTitle.setUserName(user);
        chatBottom.setUser(user);
        chatBody.clearChat();
    }
    public void updateUserChat(User user){
        chatTitle.updateUser(user);
    }
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
