//This class contains the code that managesthe game server's functionality. It also
//contains themain method that instantiates and startsthe server.
import java.io.*;
import java.net.*;

public class GameServer {

    private ServerSocket ss;
    private int numPlayers;
    private int maxPlayers;

    public GameServer() {
        System.out.println("===== Game Server =====");
        numPlayers = 0;
        maxPlayers = 2;

        try {
            ss = new ServerSocket(45371);
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
            }

            System.out.println("No longer accepting connections");
        } catch(IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }
    }

    public static void main(String[] args){
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
    
}
