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

//This class contains the code that manages the game server's functionality. 
//It also contains the main method that instantiates and starts the server.

//To run the GameServer from the AWS EC2 Instance:

//To connect to the EC2 instance:
// ssh -i linuxserverkey.pem ec2-user@3.91.154.236

//To upload GameServer file to the instance:
// scp -i linuxserverkey.pem GameServer.java ec2-user@3.91.154.236

//Remember to compile before running it in the server
// javac GameServer.java
// java GameServer


import java.io.*;
import java.net.*;

public class GameServer {

    private ServerSocket ss;
    private int numPlayers, maxPlayers, p1x, p1y, p2x, p2y;

    private Socket p1Socket, p2Socket;
    private ReadFromClient p1ReadRunnable, p2ReadRunnable;
    private WriteToClient p1WriteRunnable, p2WriteRunnable;

    //sets default values in server to send to clients
    public GameServer() {
        System.out.println("===== Game Server =====");
        numPlayers = 0;
        maxPlayers = 2;

        p1x = 231;
        p1y = 263;
        p2x = 743;
        p2y = 263;

        try {
            ss = new ServerSocket(55555);
        } catch(IOException ex) {
            System.out.println("IOException from GameServer constructor");
        }
    }

    //accepting clients to connect to respective sockets
    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections...");

            while(numPlayers < maxPlayers) {
                Socket s = ss.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                numPlayers++;
                out.writeInt(numPlayers);
                System.out.println("Player #" + numPlayers + " has connected.");

                ReadFromClient rfc = new ReadFromClient(numPlayers, in);
                WriteToClient wtc = new WriteToClient(numPlayers, out);

                if(numPlayers == 1) {
                    p1Socket = s;
                    p1ReadRunnable = rfc;
                    p1WriteRunnable = wtc;
                } else {
                    p2Socket = s;
                    p2ReadRunnable = rfc;
                    p2WriteRunnable = wtc;

                    // start the game: create and start threads upon sending start signal to clients
                    p1WriteRunnable.sendStartSignal();
                    p2WriteRunnable.sendStartSignal();

                    Thread readThread1 = new Thread(p1ReadRunnable);
					Thread readThread2 = new Thread(p2ReadRunnable);
					readThread1.start();
					readThread2.start();
					
					Thread writeThread1 = new Thread(p1WriteRunnable);
					Thread writeThread2 = new Thread(p2WriteRunnable);
					writeThread1.start();
					writeThread2.start();
                }
            }

            System.out.println("No longer accepting connections");
        } catch(IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }
    }

    //Thread for reading players coordinates received from the client.
    private class ReadFromClient implements Runnable {

        private int playerID;
        private DataInputStream dataIn;

        public ReadFromClient(int pid, DataInputStream in) {
            playerID = pid;
            dataIn = in;
            System.out.println("RFC" + playerID + " Runnable created ");
        }

        public void run() {
            try {
                while(true) {
                    if(playerID == 1) {
                        p1x = dataIn.readInt();
                        p1y = dataIn.readInt();
                    } else {
                        p2x = dataIn.readInt();
                        p2y = dataIn.readInt();
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from RFC run()");
            }
        }
    }

    //Thread for writing players coordinates sent to the client.
    private class WriteToClient implements Runnable {

        private int playerID;
        private DataOutputStream dataOut;

        public WriteToClient(int pid, DataOutputStream out) {
            playerID = pid;
            dataOut = out;
            System.out.println("WTC" + playerID + " Runnable created");
        }

        public void run() {
            try {
                while(true) {
                    if(playerID == 1) {
                        dataOut.writeInt(p2x);
                        dataOut.writeInt(p2y);
                        dataOut.flush();
                    } else {
                        dataOut.writeInt(p1x);
                        dataOut.writeInt(p1y);
                        dataOut.flush();
                    }
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTC run()");
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from WTC run()");
            }
        }

        //sends start signal to clients
        public void sendStartSignal() {
            try {
                dataOut.writeUTF("Players have connected. GAME START!");
            } catch (IOException ex) {
                System.out.println("IOException from sendStartMsg()");
            }
        }
    }

    //runs the server itself
    public static void main(String[] args){
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
    
}
