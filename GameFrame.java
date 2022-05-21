/**
    @authors Al Vincent E. Bomediano (210924), Jerril Nheo A. Samson (215235)
    @version May 21, 2022
**/
/*
    We have not discussed the Java language code in my program 
    with anyone other than my instructor or the teaching assistants 
    assigned to this course.
    We have not used Java language code obtained from another student, 
    or any other unauthorized source, either modified or unmodified.
    If any Java language code or documentation used in our program 
    was obtained from another source, such as a textbook or website, 
    that has been clearly noted with a proper citation in the comments 
    of our program.
*/

//This class contains the code that sets up the mainJFrame for the moving game elements.
//This class also sets up the GUI, keybindings and connection to the server.

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

    //accepts width and height for the frame
    public GameFrame(int w, int h) {
        width = w;
        height = h;
    }

    //sets up game gui
    public void setUpGUI(){
        //create game canvas
        gc = new GameCanvas(width,height, playerID);

        //setting up the gui elements
        this.add(gc);
        this.setTitle("Player #" + playerID);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    //sets up the key bindings for current client
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

    //keypress movement communication to the game canvas' player
    private class MoveAction extends AbstractAction{
        private String direction;
        
        public MoveAction(String dir){
            direction = dir;
        }

        public void actionPerformed(ActionEvent ae){
            gc.getPlayer().setDirection(direction);
        }
    }   

    //Connecting to the server
    public void connectToServer() {
        try {
            socket = new Socket("54.221.143.73", 55555);
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

    //Thread for reading players coordinates received from the server.
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

        //this method makes the client wait for a signal given by the server before starting
        public void waitForGameStart() {
            try {
                String startGame = dataIn.readUTF();
                System.out.println("Message from server: " + startGame);

                // start the game: create and start threads
                Thread readThread = new Thread(rfsRunnable);
                Thread writeThread = new Thread(wtsRunnable);
                readThread.start();
                writeThread.start();
            } catch (IOException ex) {
                System.out.println("IOException from waitForGameStart()");
            }
        }
    }

    //Thread for writing players coordinates sent to the server.
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