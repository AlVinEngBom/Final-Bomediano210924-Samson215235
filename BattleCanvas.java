import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class BattleCanvas extends JComponent{
  
    private int width, height, gameTime;
    // private int playerHealth;
    private PlayerFighter fighter;
    private ArrayList<Enemy> Enemies;

    
    public BattleCanvas(int w, int h){
        width = w;
        height = h;
        setPreferredSize( new Dimension(width, height) );

        gameTime = 0;

        animationTimer.start();

        fighter = new PlayerFighter(100, 100, 25, 25);
        Enemies = new ArrayList<Enemy>();
        Enemies.add(new Enemy(65,305,50,50,0.5,3));
        Enemies.add(new Enemy(100,400,50,50,1,2));
   }
   
    protected void paintComponent(Graphics g){
        
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

    public void setImages(){
        // Image down1 = new ImageIcon("down1.jpg");
    }
    Timer animationTimer = new Timer(1, new ActionListener(){
        public void actionPerformed(ActionEvent ae){
            
            gameTime++;
            System.out.println(gameTime/100);
            if(gameTime == 1000){
                Enemies.add(new Enemy(5,5,50,50,3,0.5));

            }
            else if(gameTime == 2000){
                Enemies.add(new Enemy(457,5,50,50,1,4));
            }
            else if(gameTime == 3000){
                Enemies.add(new Enemy(5,413,50,50,1,1));
            }
            else if(gameTime == 4000){
                Enemies.add(new Enemy(457,413,50,50,3,2));
            }
            else if(gameTime == 5000){
                Enemies.add(new Enemy(5,5,50,50,2,3));
            }
            else if(gameTime == 6000){
                Enemies.add(new Enemy(457,50,50,50,0.5,2));
            }
            else if(gameTime == 7000){
                Enemies.add(new Enemy(50,413,50,50,3,1));
            }
            else if(gameTime == 8000){
                Enemies.add(new Enemy(457,413,50,50,0,2));
            }
            

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

            //collission for each
            for(int i = 0; i < Enemies.size(); i++){
                for(int k = i; k < Enemies.size(); k++){
                    if(Enemies.get(i).isColliding(Enemies.get(k)) && (i != k)){
                        Enemies.get(i).reverseSpeed();
                        Enemies.get(k).reverseSpeed();
                    }
                }
            }

            fighter.move();
            repaint();
        }
   });
}
