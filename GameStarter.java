public class GameStarter {

    public static void main (String[] args){
        GameFrame gf = new GameFrame(1024, 576);
        gf.connectToServer();
        gf.setUpGUI();
        gf.addKeyBindings();
        gf.normalTimer();
    }
}
