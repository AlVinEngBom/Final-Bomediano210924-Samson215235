import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BattleStarter {
    private JFrame frame;
    private JPanel cp;
    private BattleCanvas bc;

    public void setUpGUI(){
        frame = new JFrame();
        bc = new BattleCanvas(512,768);

        frame.add(bc);
        frame.setTitle("Battle!!");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void addKeyBindings(){
        cp = (JPanel) frame.getContentPane();
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

    public static void main (String[] args){
        BattleStarter bs = new BattleStarter();
        bs.setUpGUI();
        bs.addKeyBindings();
    }
}
