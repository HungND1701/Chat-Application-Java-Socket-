/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import app.MessageType;
import component.Chat_Body;
import component.Chat_Bottom;
import component.Chat_Title;
import event.EventChat;
import event.PublicEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Conversation;
import model.Group;
import model.Receive_Message;
import model.Send_Message;
import model.Send_Message_Group;
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
    private String lastDate; 
    
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
                String time = data.getTime();
                int spaceIndex = time.indexOf(" ");
                String datePart = time.substring(0, spaceIndex);
                String timePart = time.substring(spaceIndex + 1, spaceIndex+6);
                if(!datePart.equals(lastDate)){
                    lastDate = datePart;
                    chatBody.addDate("Today");
                }
                data.setTime(timePart);
                chatBody.addItemRight(data);
            }

            @Override
            public void receiveMessage(Receive_Message data) {
                if(chatTitle.getUser()!=null){
                    if(chatTitle.getUser().getID() == data.getFromUserID()){
                        String time = data.getTime();
                        int spaceIndex = time.indexOf(" ");
                        String datePart = time.substring(0, spaceIndex);
                        String timePart = time.substring(spaceIndex + 1, spaceIndex+6);
                        if(!datePart.equals(lastDate)){
                            lastDate = datePart;
                            chatBody.addDate("Today");
                        }
                        data.setTime(timePart);
                        chatBody.addItemLeft(data);
                    } 
                }
            }
            @Override
            public void receiveMessageGroup(int conversation_id, Receive_Message data) {
                if(chatTitle.getUser()== null && chatTitle.getGroup() !=null){
                    if(chatTitle.getGroup().getId() == conversation_id){
                        String time = data.getTime();
                        int spaceIndex = time.indexOf(" ");
                        String datePart = time.substring(0, spaceIndex);
                        String timePart = time.substring(spaceIndex + 1, spaceIndex+6);
                        if(!datePart.equals(lastDate)){
                            lastDate = datePart;
                            chatBody.addDate("Today");
                        }
                        data.setTime(timePart);
                        chatBody.addItemLeft(data, true);
                    } 
                }
            }

            @Override
            public void initMessage(List<Send_Message> list) {
                if(!list.isEmpty()){
                    String date = "";
                    LocalDate currentDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYY-MM-dd");
                    String today = currentDate.format(formatter);
                    for(Send_Message message: list){
                        if(message.getFromUserID()==Service.getInstance().getUser().getID()){
                            String time = message.getTime();
                            int spaceIndex = time.indexOf(" ");
                            String datePart = time.substring(0, spaceIndex);
                            String timePart = time.substring(spaceIndex + 1, spaceIndex+6);
                            if(!date.equals(datePart)){
                                date = datePart;
                                if(date.equals(today)){
                                    chatBody.addDate("Today");
                                }else{
                                    chatBody.addDate(date);
                                }
                                message.setTime(timePart);
                            }else{
                                message.setTime(timePart);
                            }
                            chatBody.addItemRight(message);
                        }else{
                            String time = message.getTime();
                            int spaceIndex = time.indexOf(" ");
                            String datePart = time.substring(0, spaceIndex);
                            String timePart = time.substring(spaceIndex + 1, spaceIndex+6);
                            if(!date.equals(datePart)){
                                date = datePart;
                                if(date.equals(today)){
                                    chatBody.addDate("Today");
                                }else{
                                    chatBody.addDate(date);
                                }
                                message.setTime(timePart);
                            }else{
                                message.setTime(timePart);
                            }
                            chatBody.addItemLeft(new Receive_Message(message.getFromUserID(), message.getText(), message.getTime(), message.getMessageType()));
                        }
                    }
                    lastDate = date;
                } 
            }
            
            @Override
            public void initMessageGroup(List<Send_Message_Group> list) {
                if(!list.isEmpty()){
                    String date = "";
                    LocalDate currentDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYY-MM-dd");
                    String today = currentDate.format(formatter);
                    for(Send_Message_Group message: list){
                        if(message.getUserID()==Service.getInstance().getUser().getID()){
                            String time = message.getTime();
                            int spaceIndex = time.indexOf(" ");
                            String datePart = time.substring(0, spaceIndex);
                            String timePart = time.substring(spaceIndex + 1, spaceIndex+6);
                            if(!date.equals(datePart)){
                                date = datePart;
                                if(date.equals(today)){
                                    chatBody.addDate("Today");
                                }else{
                                    chatBody.addDate(date);
                                }
                                message.setTime(timePart);
                            }else{
                                message.setTime(timePart);
                            }
                            chatBody.addItemRight(new Send_Message(message.getUserID(),0 ,message.getText(), message.getTime(), message.getMessageType()));
                        }else{
                            String time = message.getTime();
                            int spaceIndex = time.indexOf(" ");
                            String datePart = time.substring(0, spaceIndex);
                            String timePart = time.substring(spaceIndex + 1, spaceIndex+6);
                            if(!date.equals(datePart)){
                                date = datePart;
                                if(date.equals(today)){
                                    chatBody.addDate("Today");
                                }else{
                                    chatBody.addDate(date);
                                }
                                message.setTime(timePart);
                            }else{
                                message.setTime(timePart);
                            }
                            chatBody.addItemLeft(new Receive_Message(message.getUserID(), message.getText(), message.getTime(), message.getMessageType()), true);
                        }
                    }
                    lastDate = date;
                } 
            }

            @Override
            public void sendMessageGroup(Send_Message_Group data) {
                String time = data.getTime();
                int spaceIndex = time.indexOf(" ");
                String datePart = time.substring(0, spaceIndex);
                String timePart = time.substring(spaceIndex + 1, spaceIndex+6);
                if(!datePart.equals(lastDate)){
                    lastDate = datePart;
                    chatBody.addDate("Today");
                }
                data.setTime(timePart);
                chatBody.addItemRight(new Send_Message(data.getUserID(),0 ,data.getText(), data.getTime(), data.getMessageType()));
            }

            
        });
        add(chatTitle, "wrap");
        add(chatBody, "wrap");
        add(chatBottom, "h ::50%");
    }
    public void setUserChat(User user){
        chatTitle.setUserName(user);
        chatBody.setUser(user);
        chatBottom.setUser(user);
        chatBody.clearChat();
    }
    public void setGroupChat(Group group){
        chatTitle.setGroupName(group);
        chatBody.setGroup(group);
        chatBottom.setGroup(group);
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
