import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BattleCanvas extends JComponent{
    private Enemy e1;
    private Enemy e2;
    private int width, height, playerCollision;
    private PlayerFighter fighter;
    // private ArrayList<AddEnemy> e;
    
    public BattleCanvas(int w, int h){
        width = w;
        height = h;
        setPreferredSize( new Dimension(width, height) );
        animationTimer.start();
        e1 = new Enemy(10,250,50,50,3,3);
        e2 = new Enemy(100,50,50,50,3,3);
        playerCollision = 0;
        fighter = new PlayerFighter(100, 100, 75, 75);

   }
   //draws the rectangles
    protected void paintComponent(Graphics g){
        g.setColor(Color.RED);
        e1.draw(g);
        e2.draw(g);

        g.setColor(Color.GREEN);
        fighter.draw(g);
   }

    public PlayerFighter getFighter(){
        return fighter;
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
            fighter.move();
            repaint();
        }
   });
}
