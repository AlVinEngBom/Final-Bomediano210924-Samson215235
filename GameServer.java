//This class contains the code that managesthe game server's functionality. It also
//contains themain method that instantiates and startsthe server.

//To connect to the EC2 instance:
// ssh -i linuxserverkey.pem ec2-user@54.89.201.72

//To upload GameServer file to the instance:
// scp -i linuxserverkey.pem GameServer.java ec2-user@54.89.201.72:~

//Remember to compile before running
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

                    // start the game: start threads
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

        public void sendStartSignal() {
            try {
                dataOut.writeUTF("Players have connected. GAME START!");
            } catch (IOException ex) {
                System.out.println("IOException from sendStartMsg()");
            }
        }
    }

    public static void main(String[] args){
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
    
}
