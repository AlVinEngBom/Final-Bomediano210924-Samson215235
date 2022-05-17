import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BattleCanvas extends JComponent{
    private Enemy e1;
    private Enemy e2;
    private int width, height, playerCollision;
    private JFrame f;
    private JPanel cp;
    private Fighter fighter;
    // private ArrayList<AddEnemy> e;
    
    public BattleCanvas(int w, int h){
        
        animationTimer.start();
        e1 = new Enemy(10,250,50,50,3,3);
        e2 = new Enemy(100,50,50,50,3,3);
        width = w;
        height = h;
        playerCollision = 0;
        fighter = new Fighter(100, 100, 75, 75);

        f = new JFrame();
        cp = (JPanel) f.getContentPane();
        cp.setFocusable(true);
        
   }
   //draws the rectangles
    protected void paintComponent(Graphics g){
        g.setColor(Color.RED);
        e1.draw(g);

        g.setColor(Color.RED);
        e2.draw(g);

        g.setColor(Color.GREEN);
        fighter.draw(g);
        fighter.repaint(g);

   }
   public void addKeysFighter(){
        ActionMap am = cp.getActionMap();
        InputMap im = cp.getInputMap();

        // AbstractAction up = new AbstractAction(){
        //     public void actionPerformed(ActionEvent ae){
        //         fighter.move();
        //         repaint();
        //     }
        // };
        // AbstractAction down = new AbstractAction(){
        //     public void actionPerformed(ActionEvent ae){
        //         fighter.move();
        //         repaint();
        //     }
        // };
        // AbstractAction left = new AbstractAction(){
        //     public void actionPerformed(ActionEvent ae){
        //         fighter.move();
        //         repaint();
        //     }
        // };
        // AbstractAction right = new AbstractAction(){
        //     public void actionPerformed(ActionEvent ae){
        //         fighter.move();
        //         repaint();
        //     }
        // };

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

    public Fighter getFighter(){
        return fighter;
    }

    public void setUpBattleCanvas(){
        BattleCanvas bc = new BattleCanvas(512,768);
        bc.setPreferredSize(new Dimension(512,768));
        f.add(bc);
        f.setTitle("Battle!!");
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

   
    Timer animationTimer = new Timer(20, new ActionListener(){
        public void actionPerformed(ActionEvent ae){
            //move e1
            if(e1.getX() <= 0){ 
                e1.reverseX();
            }
            else if(e1.getX() + e1.getWidth() >= width){ 
                e1.reverseX();
            }
            if(e1.getY() <= 0){ 
                e1.reverseY();
            }
            else if(e1.getY() + e1.getHeight() >= height){ 
                e1.reverseY();
            }
    
            //move e2
            if(e2.getY() <= 0){ 
                e2.reverseY();
            }
            else if(e2.getY() + e2.getHeight() >= height){ 
                e2.reverseY();
            }
    
            if(e2.getX() <= 0){ 
                e2.reverseX();
            }
            else if(e2.getX() + e2.getWidth() >= width){ 
                e2.reverseX();
            }
    
            if(e1.isColliding(e2)){
                e1.reverseSpeed();
                e2.reverseSpeed();
            }
            
            e1.move();
            e2.move();
            repaint();

            fighter.move();
        }

        
   });

//    Timer instanceTimer = Timer();
//    instanceTimer.schedule();

    public static void main (String[] args){
        
        BattleCanvas bc = new BattleCanvas(512, 768);
        bc.setUpBattleCanvas();
        bc.addKeysFighter();
        
    }  
    
    
}
