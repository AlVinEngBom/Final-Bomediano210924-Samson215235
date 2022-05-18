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
        fighter = new PlayerFighter(100, 100, 75, 75);
        Enemies = new ArrayList<Enemy>();

        for(int i = 0; i < 5; i+=1){
            Enemies.add(new Enemy(10+(55*i),250+(55*i),50,50,3,3));
        }

   }
   
    protected void paintComponent(Graphics g){
        
        // e2.draw(g);
        g.setColor(Color.RED);
        for(int i = 0; i < Enemies.size(); i+=1){
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
            
            for(int i = 0; i < Enemies.size(); i+=1){
                if(Enemies.get(i).getX() <= 0){ 
                    Enemies.get(i).reverseX();
                }
                else if(Enemies.get(i).getX() + Enemies.get(i).getWidth() >= width){ 
                    Enemies.get(i).reverseX();
                }
                if(Enemies.get(i).getY() <= 0){ 
                    Enemies.get(i).reverseY();
                }
                else if(Enemies.get(i).getY() + Enemies.get(i).getHeight() >= height){ 
                    Enemies.get(i).reverseY();
                }

                Enemies.get(i).move();
            }

            fighter.move();
            repaint();
        }

        
   });
}
