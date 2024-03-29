/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import app.MessageType;
import component.Item_People;
import component.MenuButton;
import event.EventMenuRight;
import event.PublicEvent;
import io.socket.client.Ack;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import model.Friend;
import model.Group;
import model.Send_Message;
import model.User;
import model.User_Group;
import net.miginfocom.swing.MigLayout;
import service.Service;
import swing.ScrollBar;

/**
 *
 * @author Acer
 */
public class Menu_Right extends javax.swing.JPanel {

    private User user;
    private Group group;
    private List<User> listUser;
    private List<User> otherUsers;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
    
    
    public Menu_Right() {
        initComponents();
        init();
    }

    public void init(){
        spSetting.setVerticalScrollBar(new ScrollBar());
        spMember.setVerticalScrollBar(new ScrollBar());
        listMember.setLayout(new MigLayout("fillx","0[fill]0","0[]1" ));
        otherUsers = new ArrayList<>();
        
        PublicEvent.getInstance().addEventMenuRight(new EventMenuRight(){
            @Override
            public void newUser(User user, List<User> listMem) {
                setGroup(null);
                setUser(user);
                imageAvatar.setImage(new ImageIcon(getClass().getResource("/avatar/"+user.getAvatar()+".png")));
                name.setText(user.getUsername());
                status.setText(user.isOnline()?"Is Active" : "Not Active");
                status.setForeground(user.isOnline()? new Color(40, 147, 59): new Color(160, 160, 160));
                changeButton(user);
                listMember.removeAll();
                repaint();
                revalidate();
            }
            @Override
            public void newGroup(Group gr) {
                setUser(null);
                setGroup(gr);
                imageAvatar.setImage(new ImageIcon(getClass().getResource("/avatar/group.png")));
                name.setText(group.getName());
                status.setText("Is Active");
                status.setForeground(new Color(40, 147, 59));
                listUser = new ArrayList<>();
                listMember.removeAll();
                for(User u : group.getListUser()){
                    listUser.add(u);
                    listMember.add(new Item_People(u), "wrap");
                    refreshMemList();
                }
                addOptionGroup();
                repaint();
                revalidate();
            }

            @Override
            public void userConnect(User u1) {
                int userID = u1.getID();
                if(user!= null && userID == user.getID()){
                    status.setText("Is Active");
                }
                if(listUser!=null){
                    for(User u : listUser){
                        if(u.getID()==userID){
                            u.setOnline(true);
                            break;
                        }
                    }
                }
                for(Component com: listMember.getComponents()){
                    Item_People item = (Item_People)com;
                    if(item.getUser().getID()== userID){
                        item.updateStatus(true);
                        break;
                    }
                }
            }

            @Override
            public void userDisconnect(User u1) {
                int userID = u1.getID();
                if(userID == user.getID()){
                    status.setText("Not Active");
                }
                if(listUser!=null){
                    for(User u : listUser){
                        if(u.getID()==userID){
                            u.setOnline(false);
                            break;
                        }
                    }
                }
                for(Component com: listMember.getComponents()){
                    Item_People item = (Item_People)com;
                    if(item.getUser().getID()== userID){
                        item.updateStatus(false);
                        break;
                    }
                }
            }

            @Override
            public void setOtherUser(List<User> Users) {
                otherUsers = new ArrayList<>();
                for(User u : Users){
                    otherUsers.add(u);
                }
            }

            @Override
            public void removeOtherUserList(int userID) {
                Iterator<User> iterator = otherUsers.iterator();
                while (iterator.hasNext()) {
                    User u = iterator.next();
                    if (u.getID() == userID) {
                        iterator.remove();
                        break;
                    }
                }
                changeButton(user);
            }

            @Override
            public void addOtherUserList(User user1) {
                otherUsers.add(user1);
                changeButton(user);          
            }

            @Override
            public void removeUserGroup(int userID, int groupID) {
                if(group != null && group.getId()==groupID){
                    listMember.removeAll();
                    for(User u : group.getListUser()){
                        if(u.getID()== userID){
                            listUser.remove(u);
                        }else{
                            listMember.add(new Item_People(u), "wrap");
                        }
                    }
                    refreshMemList();
                }
            }

            @Override
            public void addUserGroup(User user, int groupID) {
                if(group != null && group.getId()==groupID){
                    listUser.add(user);
                    group.setListUser(listUser);
                    listMember.add(new Item_People(user), "wrap");
                    refreshMemList();
                }
            }
            
        });
                
    }
    
    private void changeButton(User user){
        boolean isFriend = true;
        if(user.getID() != Service.getInstance().getUser().getID()){
            for(User u: otherUsers){
                if(u.getID() == user.getID()){
                    isFriend = false;
                    break;
                }
            }
            if(!isFriend){
                addAddFriendButton();
            }else{
                addUnfriendButton();
            }
        }
    }
    private void addOptionGroup(){
        setting.removeAll();
        MenuButton btn = new MenuButton();
        btn.setText("Leave the group");
        btn.setIcon(new ImageIcon(getClass().getResource("/icon/leave.png")));
        btn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Service.getInstance().getClient().emit("leave_group", new User_Group(Service.getInstance().getUser().getID(), group.getId()).toJSONObject(), new Ack(){
                    @Override
                    public void call(Object... os) {
                        boolean bl = (boolean) os[0];
                        if(bl){
                            setting.removeAll();
                            listMember.removeAll();
                            JLabel statusLeave = new JLabel("Successfully left the group");
                            statusLeave.setForeground(new java.awt.Color(51, 204, 51));
                            setting.add(statusLeave);
                            refreshSetting();
                            refreshMemList();
                        }else{
                            JLabel statusLeave = new JLabel("Failed to leave the group");
                            statusLeave.setForeground(new java.awt.Color(255, 51, 0));
                            setting.add(statusLeave);
                            refreshSetting();
                        }
                    }
                    
                });
            }
        });
        JTextField jTF = new JTextField();
        jTF.setMinimumSize(new Dimension(0,25));
        MenuButton btn2 = new MenuButton();
        btn2.setText("Add user");
        btn2.setIcon(new ImageIcon(getClass().getResource("/icon/add_friend.png")));
        btn2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String usernameInsert = jTF.getText();
                JLabel status1 = new JLabel("Enter username user");
                status1.setForeground(new java.awt.Color(255, 51, 0));
                status1.setVisible(false);
                setting.add(status1);
                refreshSetting();
                if(usernameInsert.equals("") || usernameInsert == null){
                    status1.setVisible(true);
                    refreshSetting();
                }else{
                    status1.setVisible(false);
                    refreshSetting();
                    Service.getInstance().getClient().emit("add_user_group", new User_Group(usernameInsert, group.getId()).toJSONObject(), new Ack(){
                        @Override
                        public void call(Object... os) {
                            boolean bl = (boolean) os[0];
                            if(bl){
                                status1.setText("Successfully add "+ usernameInsert+" the group");
                                status1.setForeground(new java.awt.Color(51, 204, 51));
                                status1.setVisible(true);
                                refreshSetting();
                            }else{
                                status1.setText("Failed to add "+ usernameInsert+" into group");
                                status1.setForeground(new java.awt.Color(51, 204, 51));
                                status1.setVisible(true);
                                refreshSetting();
                            }
                        }

                    });
                }
  
            }
        });
        setting.add(btn);
        setting.add(btn2);
        setting.add(jTF);
        refreshSetting();
    }
    
    private void addUnfriendButton(){
        setting.removeAll();
        MenuButton btn = new MenuButton();
        btn.setText("Unfriend");
        btn.setIcon(new ImageIcon(getClass().getResource("/icon/remove-user.png")));
        btn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Service.getInstance().getClient().emit("unfriend", new Friend(Service.getInstance().getUser().getID(), user.getID()).toJSONObject());
            }
        });
        setting.add(btn);
        refreshSetting();
    }
    private void addAddFriendButton(){
        setting.removeAll();
        MenuButton btn = new MenuButton();
        btn.setText("Add friend");
        btn.setIcon(new ImageIcon(getClass().getResource("/icon/add_friend.png")));
        btn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendAddFriend();
            }
        });
        setting.add(btn);
        refreshSetting();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        imageAvatar = new swing.ImageAvatar();
        name = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        spSetting = new javax.swing.JScrollPane();
        setting = new javax.swing.JLayeredPane();
        unfriendButton = new component.MenuButton();
        jLabel4 = new javax.swing.JLabel();
        spMember = new javax.swing.JScrollPane();
        listMember = new javax.swing.JLayeredPane();

        setBackground(new java.awt.Color(249, 249, 249));

        imageAvatar.setBorderSize(1);
        imageAvatar.setImage(new javax.swing.ImageIcon(getClass().getResource("/icon/profile.png"))); // NOI18N

        javax.swing.GroupLayout imageAvatarLayout = new javax.swing.GroupLayout(imageAvatar);
        imageAvatar.setLayout(imageAvatarLayout);
        imageAvatarLayout.setHorizontalGroup(
            imageAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 75, Short.MAX_VALUE)
        );
        imageAvatarLayout.setVerticalGroup(
            imageAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 75, Short.MAX_VALUE)
        );

        name.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name.setText("Name");

        status.setForeground(new java.awt.Color(137, 137, 137));
        status.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        status.setText("Status");

        jLayeredPane1.setLayer(imageAvatar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(name, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(status, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                            .addGap(56, 56, 56)
                            .addComponent(imageAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(57, 57, 57))
                        .addComponent(status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
                .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 158, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addComponent(imageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(name)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(status)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(61, 61, 61));
        jLabel3.setText("Settings");

        spSetting.setBackground(new java.awt.Color(249, 249, 249));
        spSetting.setBorder(null);
        spSetting.setForeground(new java.awt.Color(249, 249, 249));
        spSetting.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        setting.setBackground(new java.awt.Color(249, 249, 249));
        setting.setOpaque(true);
        setting.setLayout(new javax.swing.BoxLayout(setting, javax.swing.BoxLayout.Y_AXIS));

        unfriendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/remove-user.png"))); // NOI18N
        unfriendButton.setText("  Unfriend");
        unfriendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unfriendButtonActionPerformed(evt);
            }
        });
        setting.add(unfriendButton);

        spSetting.setViewportView(setting);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(61, 61, 61));
        jLabel4.setText("Members");

        spMember.setBackground(new java.awt.Color(249, 249, 249));
        spMember.setBorder(null);
        spMember.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        listMember.setBackground(new java.awt.Color(249, 249, 249));
        listMember.setOpaque(true);

        javax.swing.GroupLayout listMemberLayout = new javax.swing.GroupLayout(listMember);
        listMember.setLayout(listMemberLayout);
        listMemberLayout.setHorizontalGroup(
            listMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        listMemberLayout.setVerticalGroup(
            listMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 295, Short.MAX_VALUE)
        );

        spMember.setViewportView(listMember);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
            .addComponent(spSetting)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(136, 136, 136))
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(spMember, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spSetting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(4, 4, 4)
                .addComponent(spMember, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void unfriendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unfriendButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_unfriendButtonActionPerformed
    
    private void sendAddFriend(){
        Send_Message message = new Send_Message(Service.getInstance().getUser().getID(), user.getID(), "add friend", getTimeNow(), MessageType.ADDFRIEND);
        Service.getInstance().getClient().emit("send_to_user", message.toJSONObject());
        PublicEvent.getInstance().getEventChat().sendMessage(message);
    }
    private String getTimeNow(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentTime.format(formatter);
        return formattedDateTime;
    }
    
    private void refreshMemList(){
        listMember.repaint();
        listMember.revalidate();
    }
    private void refreshSetting(){
        setting.repaint();
        setting.revalidate();
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.ImageAvatar imageAvatar;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane listMember;
    private javax.swing.JLabel name;
    private javax.swing.JLayeredPane setting;
    private javax.swing.JScrollPane spMember;
    private javax.swing.JScrollPane spSetting;
    private javax.swing.JLabel status;
    private component.MenuButton unfriendButton;
    // End of variables declaration//GEN-END:variables
}
