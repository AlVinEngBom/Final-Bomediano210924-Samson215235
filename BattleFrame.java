import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BattleFrame {
    private JFrame f;
    private BattleCanvas bc;
    private int width,height;
    
    
    public BattleFrame(int w, int h){
        width = w;
        height = h;
        f = new JFrame();
        bc = new BattleCanvas(w, h);
    }

    public void setUpGUI(){
        BattleCanvas bc = new BattleCanvas(512,768);
        bc.setPreferredSize(new Dimension(512,768));
        f.add(bc);
        f.setTitle("Battle!!");
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    public void addKeysFighter(){
        JPanel cp = (JPanel) f.getContentPane();
        cp.setFocusable(true);
        ActionMap am = cp.getActionMap();
        InputMap im = cp.getInputMap();

        am.put("up", new MoveAction("up"));
        am.put("down", new MoveAction("down"));
        am.put("left", new MoveAction("left"));
        am.put("right", new MoveAction("right"));
        am.put("stop", new MoveAction(""));

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0,false),"up");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0,false),"down");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,false),"left");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,false),"right");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0,false),"stop");

    }
    private class MoveAction extends AbstractAction{
        private String direction;

        public MoveAction(String dir){
            direction = dir;
        }

        public void actionPerformed(ActionEvent ae){
            bc.getFighter().setDirection(direction);
        }
    }   

    

}

