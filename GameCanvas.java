import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Image;
import javax.swing.ImageIcon;

public class GameCanvas extends JComponent{

    private int width, height, gameTime, playerID, fighterHealth;
    private Player fighter, solver;
    private ArrayList<Enemy> Enemies;
    private boolean win, lose;

    
    public GameCanvas(int w, int h, int pID){
        width = w;
        height = h;
        playerID = pID;
        setPreferredSize( new Dimension(width, height) );

        gameTime = 0;

        animationTimer.start();
        fighter = new Player(100, 100, 25, 25, width, height);
        solver = new Player(200, 200, 25, 25, width, height);

        Enemies = new ArrayList<Enemy>();
        Enemies.add(new Enemy(65,305,50,50,0.5,3));
        Enemies.add(new Enemy(100,400,50,50,1,2));

        fighterHealth = 3;
        win = lose = false;

   }
   
    protected void paintComponent(Graphics g){
        
        Image img = new ImageIcon("Sprites/GameBackground.png").getImage();
        g.drawImage(img, 0, 0, null);

        g.setColor(Color.RED);
        for(int i = 0; i < Enemies.size(); i+=1){
            Enemies.get(i).draw(g);
        }

        g.setColor(Color.GREEN);
        fighter.draw(g);

        g.setColor(Color.BLUE);
        solver.draw(g);
   }

    public Player getPlayer(){
        if (playerID == 1) {
            return fighter;
        } else {
            return solver;
        }
    }
    Timer animationTimer = new Timer(1, new ActionListener(){
        public void actionPerformed(ActionEvent ae){
            
            gameTime++;
            // System.out.println(gameTime/100);
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

            // PlayerFighter to Wall Collissions
            if(fighter.getX() <= 0){ 
                fighter.boundRight();
            }
            else if(fighter.getX() + fighter.getWidth() >= width){
                fighter.boundLeft();
            }
            if(fighter.getY() <= 0){
                fighter.boundBottom();
            }
            else if(fighter.getY() + fighter.getHeight() >= height){
                fighter.boundTop();
            }

            // Enemy to Enemy Collissions
            for(int i = 0; i < Enemies.size(); i++){
                for(int k = i; k < Enemies.size(); k++){
                    if(Enemies.get(i).isColliding(Enemies.get(k)) && (i != k)){
                        Enemies.get(i).reverseSpeed();
                        Enemies.get(k).reverseSpeed();
                    }
                }
            }

            // Enemy to PlayerFighter Collissions
            for(int i = 0; i < Enemies.size(); i++){
                if(fighter.isColliding(Enemies.get(i))){
                    Enemies.remove(i);
                    Enemies.get(i).reverseSpeed();
                    fighterHealth -= 1;
                    System.out.println(fighterHealth);
                    
                    if(fighterHealth == 0){
                        lose = true;
                        fighter.stop();
                    }
                }
                
            }

            fighter.move();
            solver.move();
            repaint();
        }
   });
   public int getFighterHealth(){
       return fighterHealth;
   }
   public boolean getWin(){
       return win;
   }
   public boolean getLose(){
       return lose;
}
}
