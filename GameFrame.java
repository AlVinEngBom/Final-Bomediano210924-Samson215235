import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

import javax.swing.ImageIcon;

public class GameFrame extends JFrame{

    private int width, height, playerID;
    private JPanel cp;
    private GameCanvas gc;
    private Socket socket;

    private ImageIcon backgroundImage;
    private JLabel background;

    public GameFrame(int w, int h) {
        width = w;
        height = h;
    }

    public void setUpGUI(){
        gc = new GameCanvas(width,height, playerID);
        try{
            backgroundImage = new ImageIcon(getClass().getResource("PlayerFighterBackground.png"));
            background = new JLabel(backgroundImage);
            this.add(background);
        } catch(Exception ex){
            System.out.println("Background Image not found!");
        }
        this.add(gc);
        this.setTitle("Player #" + playerID);
        
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void addKeyBindings(){
        cp = (JPanel) this.getContentPane();
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
            gc.getPlayer().setDirection(direction);
        }
    }   

    public void connectToServer() {
        try {
            socket = new Socket("localhost", 45371);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            playerID = in.readInt();
            System.out.println("You are player #" + playerID);
            if(playerID == 1) {
                System.out.println("Waiting for player #2 to connect...");
            }
        } catch(IOException ex) {
            System.out.println("IOException from connectToServer()");
        }
    }
}