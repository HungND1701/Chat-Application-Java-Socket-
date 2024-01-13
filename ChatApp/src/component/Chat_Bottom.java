package component;

import app.MessageType;
import event.PublicEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import model.Group;
import model.Send_Message;
import model.Send_Message_Group;
import model.User;
import net.miginfocom.swing.MigLayout;
import service.Service;
import swing.JIMSendTextPane;
import swing.ScrollBar;

public class Chat_Bottom extends javax.swing.JPanel {
    
    private User user;
    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.user = null;
        this.group = group;
        panelMore.setGroup(group);
    }
    

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.group = null;
        this.user = user;
        panelMore.setUser(user);
    }

    public Chat_Bottom() {
        initComponents();
        init();
    }

    private void init(){
        mig = new MigLayout("fillx, filly", "2[fill]0[]2", "2[fill]2[]0");
        setLayout(mig);
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        JIMSendTextPane txt = new JIMSendTextPane();
        txt.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e) {
                refresh();
                if(e.getKeyChar() == 10 && e.isControlDown()){
                    eventSend(txt);
                }
            }
            
        });
        txt.setBorder(new EmptyBorder(5, 5, 5, 5));
        txt.setHintText("Write Message Here ...");
        scroll.setViewportView(txt);
        ScrollBar sb = new ScrollBar();
        sb.setBackground(new Color(249,249,249));
        sb.setPreferredSize(new Dimension(2, 10));
        scroll.setVerticalScrollBar(sb);
        add(scroll, "w 100%");
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("filly", "0[]3[]0", "0[bottom]0"));
        panel.setPreferredSize(new Dimension(26, 24));
        panel.setBackground(Color.WHITE);
        JButton cmd = new JButton();
        cmd.setBorder(null);
        cmd.setContentAreaFilled(false);
        cmd.setCursor(new Cursor(Cursor.HAND_CURSOR) {
        });
        cmd.setIcon(new ImageIcon(getClass().getResource("/icon/messager_send_icon.png")));
        cmd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventSend(txt);
            }
            
        });
        JButton cmdMore = new JButton();
        cmdMore.setBorder(null);
        cmdMore.setContentAreaFilled(false);
        cmdMore.setCursor(new Cursor(Cursor.HAND_CURSOR) {
        });
        cmdMore.setIcon(new ImageIcon(getClass().getResource("/icon/more_disable.png")));
        cmdMore.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(panelMore.isVisible()){
                    cmdMore.setIcon(new ImageIcon(getClass().getResource("/icon/more_disable.png")));
                    panelMore.setVisible(false);
                    mig.setComponentConstraints(panelMore, "dock south, h 0!");
                    revalidate();
                }else{
                    cmdMore.setIcon(new ImageIcon(getClass().getResource("/icon/more.png")));
                    panelMore.setVisible(true);
                    mig.setComponentConstraints(panelMore, "dock south, h 178!");
                    revalidate();
                }
            }
            
        });
        panel.add(cmdMore);
        panel.add(cmd);
        add(panel, "wrap");
        panelMore = new Panel_More();
        panelMore.setVisible(false);
        add(panelMore, "dock south, h 0!");
    }
    private void send(Send_Message data){
        Service.getInstance().getClient().emit("send_to_user", data.toJSONObject());
    }
    private String getTimeNow(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentTime.format(formatter);
        return formattedDateTime;
    }
    private void eventSend(JIMSendTextPane txt){
        String text = txt.getText().trim();
        if(!text.equals("")) {
            if(user!=null){
                Send_Message message = new Send_Message(Service.getInstance().getUser().getID(), user.getID(), text, getTimeNow(), MessageType.TEXT);
                send(message);
                PublicEvent.getInstance().getEventChat().sendMessage(message);
                txt.setText("");
                txt.grabFocus();
                refresh();
            }else{
                List<Integer> userIdList = new ArrayList<>();
                for(User u: group.getListUser()){
                    userIdList.add(u.getID());
                }
                Send_Message_Group message = new Send_Message_Group(group.getId(), Service.getInstance().getUser().getID(), userIdList, text, getTimeNow(), MessageType.TEXT);
                Service.getInstance().getClient().emit("send_to_group", message.toJSONObject());
                PublicEvent.getInstance().getEventChat().sendMessageGroup(message);
                txt.setText("");
                txt.grabFocus();
                refresh();
            }  
        }else{
            txt.grabFocus();
        }
    }
    private void refresh(){
        revalidate();
    }
    private Panel_More panelMore;
    
    private MigLayout mig;
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(249, 249, 249));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
