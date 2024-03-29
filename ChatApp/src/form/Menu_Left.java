/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import component.Item_Group;
import component.Item_People;
import event.EventMenuLeft;
import event.PublicEvent;
import java.awt.Component;
import java.awt.Label;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Group;
import model.User;
import net.miginfocom.swing.MigLayout;
import service.Service;
import swing.ScrollBar;

/**
 *
 * @author Acer
 */
public class Menu_Left extends javax.swing.JPanel {
    private List<User> otherUser;
    private List<User> userHaveConversation;
    private List<User> friendList;
    private List<Group> groupList;
    public Menu_Left() {
        initComponents();
        init();
    }
    
    public void init(){
        sp.setVerticalScrollBar(new ScrollBar());
        menuList.setLayout(new MigLayout("fillx","0[fill]0","0[]1" ));
        userHaveConversation = new ArrayList<>();
        friendList = new ArrayList<>();
        otherUser = new ArrayList<>();
        PublicEvent.getInstance().addEventMenuLeft(new EventMenuLeft() {
            @Override
            public void newUser(List<User> users) {
                for(User user : users){
                    userHaveConversation.add(user);
                    menuList.add(new Item_People(user), "wrap");
                    refreshMenuList();
                }
            }

            @Override
            public void userConnect(User u1) {
                int userID = u1.getID();
                for(User u : userHaveConversation){
                    if(u.getID()==userID){
                        u.setLast_online(u1.getLast_online());
                        u.setOnline(true);
                        PublicEvent.getInstance().getEventMain().updateUserChat(u);
                        break;
                    }
                }
                for(User u : friendList){
                    if(u.getID()==userID){
                        u.setLast_online(u1.getLast_online());
                        u.setOnline(true);
                        PublicEvent.getInstance().getEventMain().updateUserChat(u);
                        break;
                    }
                }
                for(User u : otherUser){
                    if(u.getID()==userID){
                        u.setLast_online(u1.getLast_online());
                        u.setOnline(true);
                        PublicEvent.getInstance().getEventMain().updateUserChat(u);
                        break;
                    }
                }
                if(menuMessage.isSelected()){
                    for(Component com: menuList.getComponents()){
                        Item_People item = (Item_People)com;
                        if(item.getUser().getID()== userID){
                            item.updateStatus(true);
                            break;
                        }
                    }
                }
                if(menuFriend.isSelected()){
                    for(Component com: menuList.getComponents()){
                        if(com instanceof  Item_People item){
                            if(item.getUser().getID()== userID){
                                item.updateStatus(true);
                                break;
                            }
                        }                       
                    }
                }
            }

            @Override
            public void userDisconnect(User u1) {
                int userID = u1.getID();
                for(User u : userHaveConversation){
                    if(u.getID()==userID){
                        u.setLast_online(u1.getLast_online());
                        u.setOnline(false);
                        PublicEvent.getInstance().getEventMain().updateUserChat(u);
                        break;
                    }
                }
                for(User u : friendList){
                    if(u.getID()==userID){
                        u.setLast_online(u1.getLast_online());
                        u.setOnline(false);
                        PublicEvent.getInstance().getEventMain().updateUserChat(u);
                        break;
                    }
                }
                for(User u : otherUser){
                    if(u.getID()==userID){
                        u.setLast_online(u1.getLast_online());
                        u.setOnline(false);
                        PublicEvent.getInstance().getEventMain().updateUserChat(u);
                        break;
                    }
                }
                if(menuMessage.isSelected()){
                    for(Component com: menuList.getComponents()){
                        Item_People item = (Item_People)com;
                        if(item.getUser().getID()== userID){
                            item.updateStatus(false);
                            break;
                        }
                    }
                }
                if(menuFriend.isSelected()){
                    for(Component com: menuList.getComponents()){
                        if(com instanceof  Item_People item){
                            if(item.getUser().getID()== userID){
                                item.updateStatus(false);
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void setFriend(List<User> users) {
                friendList = new ArrayList<>();
                for(User user : users){
                    friendList.add(user);
                }
            }

            @Override
            public void setOtherUser(List<User> users) {
                otherUser = new ArrayList<>();
                for(User user : users){
                    otherUser.add(user);
                }
            }

            @Override
            public void newConversation(List<User> users) {
                for(User user : users){
                    System.out.println(user.toString());
                    userHaveConversation.add(user);
                    if(menuMessage.isSelected()){
                        menuList.add(new Item_People(user), "wrap");
                        refreshMenuList();
                    }
                }
            }

            @Override
            public void updateAddFriend(int userID) {
                Iterator<User> iterator = otherUser.iterator();
                while (iterator.hasNext()) {
                    User u = iterator.next();
                    if (u.getID() == userID) {
                        iterator.remove();
                        friendList.add(u);
                        break;
                    }
                }
                if(menuFriend.isSelected()){
                    showFriend();
                }
                
            }

            @Override
            public void updateUnfriend(int userID) {
                Iterator<User> iterator = friendList.iterator();
                while (iterator.hasNext()) {
                    User u = iterator.next();
                    if (u.getID() == userID) {
                        iterator.remove();
                        otherUser.add(u);
                        break;
                    }
                }
                if(menuFriend.isSelected()){
                    showFriend();
                }
            }

            @Override
            public void setGroup(List<Group> groups) {
                groupList = new ArrayList<>();
                for(Group gr : groups){
                    groupList.add(gr);
                }
            }
            
        });
        showMessage();
    }

    private void showMessage(){
        menuList.removeAll();
        for(User user : userHaveConversation){
            menuList.add(new Item_People(user), "wrap");
        }
        refreshMenuList();
    }
    
    private void showGroup(){
        menuList.removeAll();
        for(Group group : groupList){
            menuList.add(new Item_Group(group), "wrap");
        }
        refreshMenuList();
    }
    
    private void showFriend(){
        menuList.removeAll();
        Label friendLabel = new Label("List friends");
        menuList.add(friendLabel, "wrap");
        for(User user : friendList){
            menuList.add(new Item_People(user), "wrap");
        }
        Label notFriendLabel = new Label("Other users");
        menuList.add(notFriendLabel, "wrap");
        for(User user : otherUser){
            menuList.add(new Item_People(user), "wrap");
        }
        refreshMenuList();
    }
    
    private void refreshMenuList(){
        menuList.repaint();
        menuList.revalidate();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JLayeredPane();
        menuMessage = new component.MenuButton();
        menuGroup = new component.MenuButton();
        menuFriend = new component.MenuButton();
        sp = new javax.swing.JScrollPane();
        menuList = new javax.swing.JLayeredPane();

        setBackground(new java.awt.Color(249, 249, 249));

        menu.setBackground(new java.awt.Color(229, 229, 229));
        menu.setOpaque(true);
        menu.setLayout(new javax.swing.BoxLayout(menu, javax.swing.BoxLayout.LINE_AXIS));

        menuMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/messages_icon.png"))); // NOI18N
        menuMessage.setMargin(new java.awt.Insets(2, 16, 3, 16));
        menuMessage.setMinimumSize(new java.awt.Dimension(45, 39));
        menuMessage.setPreferredSize(new java.awt.Dimension(39, 39));
        menuMessage.setSelected(true);
        menuMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMessageActionPerformed(evt);
            }
        });
        menu.add(menuMessage);

        menuGroup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/group_people_icon.png"))); // NOI18N
        menuGroup.setMargin(new java.awt.Insets(2, 15, 3, 15));
        menuGroup.setPreferredSize(new java.awt.Dimension(39, 39));
        menuGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGroupActionPerformed(evt);
            }
        });
        menu.add(menuGroup);

        menuFriend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add friend_basic_outline_ui_icon.png"))); // NOI18N
        menuFriend.setMargin(new java.awt.Insets(2, 15, 3, 15));
        menuFriend.setPreferredSize(new java.awt.Dimension(39, 39));
        menuFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFriendActionPerformed(evt);
            }
        });
        menu.add(menuFriend);

        sp.setBackground(new java.awt.Color(249, 249, 249));
        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        menuList.setBackground(new java.awt.Color(249, 249, 249));
        menuList.setOpaque(true);

        javax.swing.GroupLayout menuListLayout = new javax.swing.GroupLayout(menuList);
        menuList.setLayout(menuListLayout);
        menuListLayout.setHorizontalGroup(
            menuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        menuListLayout.setVerticalGroup(
            menuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        sp.setViewportView(menuList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sp)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void menuMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMessageActionPerformed
        if(!menuMessage.isSelected()){
            menuMessage.setSelected(true);
            menuGroup.setSelected(false);
            menuFriend.setSelected(false);
            showMessage();
        }
    }//GEN-LAST:event_menuMessageActionPerformed

    private void menuGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGroupActionPerformed
        if(!menuGroup.isSelected()){
            menuMessage.setSelected(false);
            menuGroup.setSelected(true);
            menuFriend.setSelected(false);
            showGroup();
        }
    }//GEN-LAST:event_menuGroupActionPerformed

    private void menuFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFriendActionPerformed
        if(!menuFriend.isSelected()){
            menuMessage.setSelected(false);
            menuGroup.setSelected(false);
            menuFriend.setSelected(true);
            showFriend();
        }
    }//GEN-LAST:event_menuFriendActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane menu;
    private component.MenuButton menuFriend;
    private component.MenuButton menuGroup;
    private javax.swing.JLayeredPane menuList;
    private component.MenuButton menuMessage;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}
