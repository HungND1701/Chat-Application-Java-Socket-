package component;

import app.MessageType;
import swing.WrapLayout;
import emoji.Emoji;
import emoji.Model_Emoji;
import event.PublicEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import main.Main;
import model.Send_Message;
import model.User;
import net.miginfocom.swing.MigLayout;
import service.Service;
import swing.ScrollBar;

public class Panel_More extends javax.swing.JPanel {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Panel_More() {
        initComponents();
        init();
    }
    
    private void init(){
        setLayout(new MigLayout("fillx"));
        panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.LINE_AXIS));
        panelHeader.add(getButtonFile());
        panelHeader.add(getEmojiStyle());
        add(panelHeader, "w 100%,h 30!, wrap");
        panelDetail = new JPanel();
        panelDetail.setLayout(new WrapLayout(WrapLayout.LEFT));
        JScrollPane ch = new JScrollPane(panelDetail);
        ch.setBorder(null);
        ch.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ch.setVerticalScrollBar(new ScrollBar());
        add(ch, "w 100%, h 100%");
    }
    private JButton getButtonFile(){
        OptionButton cmd = new OptionButton();
        cmd.setIcon(new ImageIcon(getClass().getResource("/icon/link.png")));
        cmd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSelected();
                cmd.setSelected(true);
                JFileChooser ch = new JFileChooser();
                ch.showOpenDialog(Main.getFrames()[0]);
                
            }
            
        });
        return cmd;
    }
    
    private JButton getEmojiStyle(){
        OptionButton cmd = new OptionButton();
        cmd.setIcon(new ImageIcon(getClass().getResource("/icon/add_emoji.png")));   
        cmd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSelected();
                cmd.setSelected(true);
                panelDetail.removeAll();
                for(Model_Emoji emoji : Emoji.getInstance().getStyle()){
                    panelDetail.add(getButton(emoji));
                }
                panelDetail.repaint();
                panelDetail.revalidate();
            }
            
        });
        return cmd;
    }
    
    private JButton getButton(Model_Emoji emoji){
        JButton button = new JButton(emoji.getIcon());
        button.setName(emoji.getId()+"");
        button.setBorder(new EmptyBorder(3,3,3,3));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Send_Message message = new Send_Message(Service.getInstance().getUser().getID(), user.getID(), emoji.getId()+"", getTimeNow(), MessageType.EMOJI);
                sendMessage(message);
                PublicEvent.getInstance().getEventChat().sendMessage(message);
            }
            
        });
        return button;
    }
    private String getTimeNow(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentTime.format(formatter);
        return formattedDateTime;
    }
    
    private void sendMessage(Send_Message data){
        Service.getInstance().getClient().emit("send_to_user", data.toJSONObject());
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
            .addGap(0, 70, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    private void clearSelected(){
        for(Component c : panelHeader.getComponents()){
            if(c instanceof OptionButton){
                ((OptionButton) c).setSelected(false);
            }
        }
    }
    
    private JPanel panelHeader;
    private JPanel panelDetail;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
