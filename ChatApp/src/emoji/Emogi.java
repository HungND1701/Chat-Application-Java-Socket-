package emoji;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

public class Emogi {
    private static Emogi instance;
    public static Emogi getInstance(){
        if (instance == null){
            instance = new Emogi();
        }
        return instance;
    }

    public Emogi() {
    }
    
    public List<Emoji> getStyle1(){
        List<Emoji> list = new ArrayList<>();
        for (int i = 1; i < 20; i++){
            list.add(new Emoji(i, new ImageIcon(getClass().getResource("/emoji/icon/" + i + ".png"))));
        }
        return list;
    }
    
    public List<Emoji> getStyle2(){
        List<Emoji> list = new ArrayList<>();
        for (int i = 21; i < 40; i++){
            list.add(new Emoji(i, new ImageIcon(getClass().getResource("/emoji/icon/" + i + ".png"))));
        }
        return list;
    }
    
    public Emoji getImoji(int id){
        return new Emoji(id,new ImageIcon(getClass().getResource("/emoji/icon/" + id + ".png")));
    }
}
