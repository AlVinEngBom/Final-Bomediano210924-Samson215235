import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BattleCanvas extends JComponent{
    private Enemy currentEnemy;
    // private Enemy e2;
    private int width, height, playerCollision;
    private PlayerFighter fighter;
    private ArrayList<Enemy> Enemies;
    
    public BattleCanvas(int w, int h){
        width = w;
        height = h;
        setPreferredSize( new Dimension(width, height) );

        animationTimer.start();

        currentEnemy = new Enemy(10,250,50,50,3,3);
        // e2 = new Enemy(100,50,50,50,3,3);
        fighter = new PlayerFighter(100, 100, 75, 75);

        for(int i = 0; i < 5; i+=1){
            Enemy.add(currentEnemy);
        }

   }
   
    protected void paintComponent(Graphics g){
        
        // e2.draw(g);
        for(int i = 0; i < Enemies.size(); i+=1){
            g.setColor(Color.RED);
            Enemies.get(i).draw(g);
        }

        g.setColor(Color.GREEN);
        fighter.draw(g);

   }

    public PlayerFighter getFighter(){
        return fighter;
    }

    Timer animationTimer = new Timer(20, new ActionListener(){
        public void actionPerformed(ActionEvent ae){
            
            if(currentEnemy.getX() <= 0){ 
                currentEnemy.reverseX();
            }
            else if(currentEnemy.getX() + currentEnemy.getWidth() >= width){ 
                currentEnemy.reverseX();
            }
            if(currentEnemy.getY() <= 0){ 
                currentEnemy.reverseY();
            }
            else if(currentEnemy.getY() + currentEnemy.getHeight() >= height){ 
                currentEnemy.reverseY();
            }
    
            //move e2
            // if(e2.getY() <= 0){ 
            //     e2.reverseY();
            // }
            // else if(e2.getY() + e2.getHeight() >= height){ 
            //     e2.reverseY();
            // }
    
            // if(e2.getX() <= 0){ 
            //     e2.reverseX();
            // }
            // else if(e2.getX() + e2.getWidth() >= width){ 
            //     e2.reverseX();
            // }
    
            // if(currentEnemy.isColliding(e2)){
            //     currentEnemy.reverseSpeed();
            //     e2.reverseSpeed();
            // }
            
            for(int i = 0; i < Enemies.size(); i+=1){
                Enemies.get(i).move();
            }
            
            // e2.move();
            fighter.move();

            repaint();
        }

        
   });

//    Timer instanceTimer = Timer();
//    instanceTimer.schedule();    
}
