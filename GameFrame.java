import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.text.DecimalFormat;

public class GameFrame extends JFrame{

    private int width, height, playerID;
    private JPanel cp;
    private GameCanvas gc;
    private Socket socket;
    private JLabel timerLabel, fighterHealthLabel;
    private int second, minute;
    private Timer timer;
    private String ddSecond,ddMinute;

    public GameFrame(int w, int h) {
        width = w;
        height = h;
        second = 0;
        minute = 0;
        // timerLabel.setText("00:00");
    }
    
    public void normalTimer(){
        timer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                second++;
                DecimalFormat dFormat = new DecimalFormat("00");
                ddSecond = dFormat.format(second);
                ddMinute = dFormat.format(minute);
                timerLabel.setText(ddMinute + ":" + ddSecond);

                if(second == 60){
                    second = 0;
                    minute++;
                    ddSecond = dFormat.format(second);
                    ddMinute = dFormat.format(minute);
                    timerLabel.setText(ddMinute + ":" + ddSecond);
                }
            }
        });
        timer.start();
    }

    public void setUpGUI(){
        this.setTitle("Player #" + playerID);
        gc = new GameCanvas(width,height, playerID);
        timerLabel = new JLabel("");
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBounds(486,0,50,30);
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        //work in a sysout but does not update on the JLabel
        fighterHealthLabel = new JLabel("Lives: " + gc.getFighterHealth());
        fighterHealthLabel.setForeground(Color.WHITE);
        fighterHealthLabel.setBounds(486,10,50,30);
        fighterHealthLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(timerLabel);
        this.add(fighterHealthLabel);
        this.add(gc);
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