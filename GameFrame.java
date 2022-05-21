import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class GameFrame extends JFrame{

    private int width, height, playerID;
    private JPanel cp;
    private GameCanvas gc;
    private Socket socket;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;

    public GameFrame(int w, int h) {
        width = w;
        height = h;
    }

    public void setUpGUI(){
        gc = new GameCanvas(width,height, playerID);

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
            socket = new Socket("localhost", 55555); // 54.89.201.72
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            playerID = in.readInt();
            System.out.println("You are player #" + playerID);
            if(playerID == 1) {
                System.out.println("Waiting for player #2 to connect...");
            }
            rfsRunnable = new ReadFromServer(in);
            wtsRunnable = new WriteToServer(out);

            rfsRunnable.waitForGameStart();
        } catch(IOException ex) {
            System.out.println("IOException from connectToServer()");
        }
    }

    private class ReadFromServer implements Runnable {

        private DataInputStream dataIn;

        public ReadFromServer(DataInputStream in) {
            dataIn = in;
            System.out.println("RFS Runnable created");
        }

        public void run() {
            try {
                while(true) {
                    try {
                        gc.getOther().setX(dataIn.readInt());
                        gc.getOther().setY(dataIn.readInt());
                    } catch (NullPointerException ex) {
                        System.out.println("NullPointerException ex");
                    }   
                }
            } catch (IOException ex) {
               System.out.println("IOException from RFS run()");
            }
        }

        public void waitForGameStart() {
            try {
                String startGame = dataIn.readUTF();
                System.out.println("Message from server: " + startGame);

                // start the game: start threads
                Thread readThread = new Thread(rfsRunnable);
                Thread writeThread = new Thread(wtsRunnable);
                readThread.start();
                writeThread.start();
            } catch (IOException ex) {
                System.out.println("IOException from waitForGameStart()");
            }
        }
    }

    private class WriteToServer implements Runnable {

        private DataOutputStream dataOut;

        public WriteToServer(DataOutputStream out) {
            dataOut = out;
            System.out.println("WTS Runnable created");
        }

        public void run() {
            try {
                while(true) {
                    if(gc != null) {
                        dataOut.writeInt(gc.getPlayer().getX());
                        dataOut.writeInt(gc.getPlayer().getY());
                        dataOut.flush();
                    }
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException ex) {
                        System.out.println("Interrupted Exception from WTS run()");
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from WTS run()");
            }
        }
    }
}