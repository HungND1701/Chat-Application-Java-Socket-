package component;

import app.MessageType;
import emoji.Emoji;
import java.awt.Adjustable;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import model.Group;
import model.Receive_Message;
import model.Send_Message;
import model.User;
import net.miginfocom.swing.MigLayout;
import swing.ScrollBar;

public class Chat_Body extends javax.swing.JPanel {
    private User user;
    private Group group;

    public void setUser(User user) {
        this.user = user;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Chat_Body() {
        initComponents();
        init();
    }

    private void init(){
        body.setLayout(new MigLayout("fillx", "", "5[]5"));
        sp.setVerticalScrollBar(new ScrollBar());
        sp.getVerticalScrollBar().setBackground(Color.WHITE);
    }
    
    public void addItemLeft(Receive_Message data){
        if(data.getMessageType()== MessageType.TEXT){
            Chat_Left item = new Chat_Left();
            item.setText(data.getText());
            item.setTime(data.getTime());
            body.add(item, "wrap, w 100::80%");
        }else if(data.getMessageType()== MessageType.EMOJI){
            Chat_Left item = new Chat_Left();
            item.setEmoji(Emoji.getInstance().getEmoji(Integer.valueOf(data.getText())).getIcon());
            item.setTime(data.getTime());
            body.add(item, "wrap, w 100::80%");
        }else if(data.getMessageType()== MessageType.ADDFRIEND){
            Chat_Left item = new Chat_Left();
            item.setAddFriend(user.getID(), false);
            item.setTime(data.getTime());
            body.add(item, "wrap, w 100::80%");
        }else if(data.getMessageType()== MessageType.ADDFRIENDED){
            Chat_Left item = new Chat_Left();
            item.setAddFriend(user.getID(), true);
            item.setTime(data.getTime());
            body.add(item, "wrap, w 100::80%");
        }else{
            //send file
        }
        repaint();
        revalidate();
    }  
    
    public void addItemRight(Send_Message data){
        if(data.getMessageType()== MessageType.TEXT){
            Chat_Right item = new Chat_Right();
            item.setText(data.getText()); 
            item.setTime(data.getTime());
            item.setSeenStatus();
            body.add(item, "wrap, al right, w 100::80%");
        }else if(data.getMessageType()== MessageType.EMOJI){
            Chat_Right item = new Chat_Right();
            item.setEmoji(Emoji.getInstance().getEmoji(Integer.valueOf(data.getText())).getIcon());
            item.setTime(data.getTime());
            body.add(item, "wrap, al right, w 100::80%");
        }else if(data.getMessageType()== MessageType.ADDFRIEND){
            Chat_Right item = new Chat_Right();
            item.setAddFriend(user.getID(), false);
            item.setTime(data.getTime());
            body.add(item, "wrap, al right, w 100::80%");
        }else if(data.getMessageType()== MessageType.ADDFRIENDED){
            Chat_Right item = new Chat_Right();
            item.setAddFriend(user.getID(), true);
            item.setTime(data.getTime());
            body.add(item, "wrap, al right, w 100::80%");
        }else{
            //send file
        }
        
        repaint();
        revalidate();
        scrollToBottom();
    } 
    
    public void addItemLeft(Receive_Message data, Boolean isGroup){
        if(isGroup){
            User u= new User();
            boolean inListUser = false;
            for(User u1 : group.getListUser()){
                if(u1.getID()==data.getFromUserID()){
                    u= u1;
                    inListUser = true;
                    break;
                }
            }
            if(!inListUser){
                for(User u1 : group.getListUserLeft()){
                    if(u1.getID()==data.getFromUserID()){
                        u= u1;
                        break;
                    }
                }
            }
            if(u.getID()!=0){
                if(data.getMessageType()== MessageType.TEXT){
                    Chat_Left_With_Profile item = new Chat_Left_With_Profile();
                    item.setUserProfile(u.getUsername());
                    item.setImageProfile(new ImageIcon(getClass().getResource("/avatar/"+u.getAvatar()+".png")));
                    item.setText(data.getText());
                    item.setTime(data.getTime());
                    body.add(item, "wrap, w 100::80%");
                }else if(data.getMessageType()== MessageType.EMOJI){
                    Chat_Left_With_Profile item = new Chat_Left_With_Profile();
                    item.setUserProfile(u.getUsername());
                    item.setImageProfile(new ImageIcon(getClass().getResource("/avatar/"+u.getAvatar()+".png")));
                    item.setEmoji(Emoji.getInstance().getEmoji(Integer.valueOf(data.getText())).getIcon());
                    item.setTime(data.getTime());
                    body.add(item, "wrap, w 100::80%");
                }else{
                    //send file
                }
                repaint();
                revalidate();
                scrollToBottom();
            }           
        }
    }
    
    public void addItemRight(String text, String[] image){
        Chat_Right item = new Chat_Right();
        item.setText(text); 
        item.setImage(image);
//        item.setTime();
        item.setSeenStatus();
        body.add(item, "wrap, al right, w 100::80%");
        body.repaint();
        body.revalidate();  
        scrollToBottom();
    } 
    
//    public void addItemFileLeft(String text, String user, String fileName, String fileSize){
//        Chat_Left_With_Profile item = new Chat_Left_With_Profile();
//        item.setText(text);
//        item.setFile(fileName, fileSize);
//        item.setUserProfile(user);
//        body.add(item, "wrap, w 100::80%");
//        body.repaint();
//        body.revalidate();
//    } 
    
    public void addItemFileRight(String text, String fileName, String fileSize){
        Chat_Right item = new Chat_Right();
        item.setText(text); 
        item.setFile(fileName, fileSize);
//        item.setTime();
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
    public void addMemberLeftMessage(int groupID, String userName){
        if(group.getId()== groupID){
            addDate(userName + " left the group.");
        }
    }
    public void addNewMemberMessage(int groupID, String userName){
        if(group.getId()== groupID){
            addDate(userName + " join the group.");
        }
    }
    
    public void clearChat(){
        body.removeAll();
        repaint();
        revalidate();
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
