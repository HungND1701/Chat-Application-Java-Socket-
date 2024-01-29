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
import java.util.ArrayList;
import java.util.Arrays;
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
    final static private String[] vn_offensive_words = {"buồi","buoi","dau buoi","daubuoi","caidaubuoi","nhucaidaubuoi","dau boi","bòi","dauboi","caidauboi","đầu bòy","đầu bùi","dau boy","dauboy","caidauboy","b`","cặc","cak","kak","kac","cac","concak","nungcak","bucak","caiconcac","caiconcak","cu","cặk","cak","dái","giái","zái","kiu","cứt","cuccut","cutcut","cứk","cuk","cười ỉa","cười ẻ","đéo","đếch","đếk","dek","đết","đệt","đách","dech","đ","deo","d","đel","đél","del","dell ngửi","dell ngui","dell chịu","dell chiu","dell hiểu","dell hieu","dellhieukieugi","dell nói","dell noi","dellnoinhieu","dell biết","dell biet","dell nghe","dell ăn","dell an","dell được","dell duoc","dell làm","dell lam","dell đi","dell di","dell chạy","dell chay","deohieukieugi","địt","đm","dm","đmm","dmm","đmmm","dmmm","đmmmm","dmmmm","đmmmmm","dmmmmm","đcm","dcm","đcmm","dcmm","đcmmm","dcmmm","đcmmmm","dcmmmm","đệch","đệt","dit","dis","diz","đjt","djt","địt mẹ","địt mịe","địt má","địt mía","địt ba","địt bà","địt cha","địt con","địt bố","địt cụ","dis me","disme","dismje","dismia","dis mia","dis mie","đis mịa","đis mịe","ditmemayconcho","ditmemay","ditmethangoccho","ditmeconcho","dmconcho","dmcs","ditmecondi","ditmecondicho","đụ","đụ mẹ","đụ mịa","đụ mịe","đụ má","đụ cha","đụ bà","đú cha","đú con mẹ","đú má","đú mẹ","đù cha","đù má","đù mẹ","đù mịe","đù mịa","đủ cha","đủ má","đủ mẹ","đủ mé","đủ mía","đủ mịa","đủ mịe","đủ mie","đủ mia","đìu","đờ mờ","đê mờ","đờ ma ma","đờ mama","đê mama","đề mama","đê ma ma","đề ma ma","dou","doma","duoma","dou má","duo má","dou ma","đou má","đìu má","á đù","á đìu","đậu mẹ","đậu má","đĩ","di~","đuỹ","điếm","cđĩ","cdi~","đilol","điloz","đilon","diloz","dilol","dilon","condi","condi~","dime","di me","dimemay","condime","condimay","condimemay","con di cho","con di cho","condicho","bitch","biz","bít chi","con bích","con bic","con bíc","con bít","phò","4`","lồn","l`","loz","lìn","nulo","ml","matlon","cailon","matlol","matloz","thml","thangmatlon","thangml","đỗn lì","tml","thml","diml","dml","hãm","xàm lol","xam lol","xạo lol","xao lol","con lol","ăn lol","an lol","mát lol","mat lol","cái lol","cai lol","lòi lol","loi lol","ham lol","củ lol","cu lol","ngu lol","tuổi lol","tuoi lol","mõm lol","mồm lol","mom lol","như lol","nhu lol","nứng lol","nung lol","nug lol","nuglol","rảnh lol","ranh lol","đách lol","dach lol","mu lol","banh lol","tét lol","tet lol","vạch lol","vach lol","cào lol","cao lol","tung lol","mặt lol","mát lol","mat lol","xàm lon","xam lon","xạo lon","xao lon","con lon","ăn lon","an lon","mát lon","mat lon","cái lon","cai lon","lòi lon","loi lon","ham lon","củ lon","cu lon","ngu lon","tuổi lon","tuoi lon","mõm lon","mồm lon","mom lon","như lon","nhu lon","nứng lon","nung lon","nug lon","nuglon","rảnh lon","ranh lon","đách lon","dach lon","mu lon","banh lon","tét lon","tet lon","vạch lon","vach lon","cào lon","cao lon","tung lon","mặt lon","mát lon","mat lon","cái lờ","cl","clgt","cờ lờ gờ tờ","cái lề gì thốn","đốn cửa lòng","sml","sapmatlol","sapmatlon","sapmatloz","sấp mặt","sap mat","vlon","vloz","vlol","vailon","vai lon","vai lol","vailol","nốn lừng","vcl","vl","vleu","chịch","chich","vãi","v~","đụ","nứng","nug","đút đít","chổng mông","banh háng","xéo háng","xhct","xephinh","la liếm","đổ vỏ","xoạc","xoac","chich choac","húp sò","fuck","fck","đụ","bỏ bú","buscu","ngu","óc chó","occho","lao cho","láo chó","bố láo","chó má","cờ hó","sảng","thằng chó","thang cho","thang cho","chó điên","thằng điên","thang dien","đồ điên","sủa bậy","sủa tiếp","sủa đi","sủa càn","mẹ bà","mẹ cha mày","me cha may","mẹ cha anh","mẹ cha nhà anh","mẹ cha nhà mày","me cha nha may","mả cha mày","mả cha nhà mày","ma cha may","ma cha nha may","mả mẹ","mả cha","kệ mẹ","kệ mịe","kệ mịa","kệ mje","kệ mja","ke me","ke mie","ke mia","ke mja","ke mje","bỏ mẹ","bỏ mịa","bỏ mịe","bỏ mja","bỏ mje","bo me","bo mia","bo mie","bo mje","bo mja","chetme","chet me","chết mẹ","chết mịa","chết mja","chết mịe","chết mie","chet mia","chet mie","chet mja","chet mje","thấy mẹ","thấy mịe","thấy mịa","thay me","thay mie","thay mia","tổ cha","bà cha mày","cmn","cmnl","tiên sư nhà mày","tiên sư bố","tổ sư"};
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
                data.setText(changeMessage(data.getText()));
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
                        data.setText(changeMessage(data.getText()));
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
                        data.setText(changeMessage(data.getText()));
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
                        message.setText(changeMessage(message.getText()));
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
                        message.setText(changeMessage(message.getText()));
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
                data.setText(changeMessage(data.getText()));
                chatBody.addItemRight(new Send_Message(data.getUserID(),0 ,data.getText(), data.getTime(), data.getMessageType()));
            }

            @Override
            public void sendMemberLeftMessage(int groupID, String userName) {
                chatBody.addMemberLeftMessage(groupID, userName);
            }

            @Override
            public void sendAddMemberMessage(int groupID, String userName) {
                chatBody.addNewMemberMessage(groupID, userName);
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
    private static String changeMessage(String message){
        String[] listWord = message.split(" ");
        String ms = "";
        for (String w : listWord) {
            if(!ms.equals("")) ms = ms+" ";
            if (Arrays.asList(vn_offensive_words).contains(w)) {
                String chuoi = new String(new char[w.length()]).replace('\0', '*');
                ms = ms + chuoi;
            } else {
                ms = ms + w;
            }
        }
        return ms;
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
