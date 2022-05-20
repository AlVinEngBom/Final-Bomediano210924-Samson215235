import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameCanvas extends JComponent{

    private int width, height, gameTime, playerID;
    private Player fighter, solver;
    private ArrayList<Enemy> Enemies;
    private ArrayList<Wall> Walls;

    
    public GameCanvas(int w, int h, int pID){
        width = w;
        height = h;
        playerID = pID;
        setPreferredSize( new Dimension(width, height) );

        gameTime = 0;

        animationTimer.start();

        fighter = new Player(231, 263, 25, 25);
        solver = new Player(743, 263, 25, 25);

        Enemies = new ArrayList<Enemy>();
        Enemies.add(new Enemy(65,305,0.5,3));
        Enemies.add(new Enemy(100,400,1,2));

        Walls = new ArrayList<Wall>();
        Walls.add(new Wall(512+0, 0, 511, 6));
        Walls.add(new Wall(512+506, 0, 6, 576));
        Walls.add(new Wall(512+0, 0, 6, 576));
        Walls.add(new Wall(512+46, 570, 467, 6));
   }
   
    protected void paintComponent(Graphics g){
        
        g.setColor(Color.BLACK);
        for(int i = 0; i < Walls.size(); i+=1){
            Walls.get(i).draw(g);
        }
        
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

    public Player getOther(){
        if (playerID == 2) {
            return fighter;
        } else {
            return solver;
        }
    }

    public

    Timer animationTimer = new Timer(10, new ActionListener(){
        public void actionPerformed(ActionEvent ae){
            
            gameTime++;
            // System.out.println(gameTime/100);
            if(gameTime == 1000){
                Enemies.add(new Enemy(5,5,3,0.5));

            }
            else if(gameTime == 2000){
                Enemies.add(new Enemy(457,5,1,4));
            }
            else if(gameTime == 3000){
                Enemies.add(new Enemy(5,413,1,1));
            }
            else if(gameTime == 4000){
                Enemies.add(new Enemy(457,413,3,2));
            }
            else if(gameTime == 5000){
                Enemies.add(new Enemy(5,5,2,3));
            }
            else if(gameTime == 6000){
                Enemies.add(new Enemy(457,50,0.5,2));
            }
            else if(gameTime == 7000){
                Enemies.add(new Enemy(50,413,3,1));
            }
            else if(gameTime == 8000){
                Enemies.add(new Enemy(457,413,0,2));
            }
            
            // Enemy to Bound Collissions
            for(int i = 0; i < Enemies.size(); i+=1){
                if(Enemies.get(i).getX() <= 0){ 
                    Enemies.get(i).reverseX();
                }
                else if(Enemies.get(i).getX() + Enemies.get(i).getWidth() >= width/2){ 
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

            // PlayerFighter to Bound Collissions
            if(fighter.getX() <= 0){ 
                fighter.boundLeft(0);
            }
            else if(fighter.getX() + fighter.getWidth() >= width/2){
                fighter.boundRight(width/2);
            }
            if(fighter.getY() <= 0){
                fighter.boundTop(0);
            }
            else if(fighter.getY() + fighter.getHeight() >= height){
                fighter.boundBottom(height);
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
                if(fighter.enemyIsColliding(Enemies.get(i))){
                    Enemies.remove(i);
                    fighter.stop();
                }
            }

            // Wall to PlayerSolver Collissions
            for(int i = 0; i < Walls.size(); i++){
                if(solver.wallIsColliding(Walls.get(i))){
                    if(Walls.get(i).getX() + Walls.get(i).getWidth() - solver.getX() <= 5){ 
                        solver.boundLeft(Walls.get(i).getX() + Walls.get(i).getWidth());
                    }
                    else if(Walls.get(i).getX() - solver.getX() + solver.getWidth() >= 5){
                        solver.boundRight(Walls.get(i).getX());
                    }
                    
                    
                    
                    // if(solver.getY() <= 0){
                    //     // solver.boundTop(0);
                    //     System.out.println("3");
                    // }
                    // else if(solver.getY() + solver.getHeight() >= height){
                    //     // solver.boundBottom(height);
                    //     System.out.println("4");
                    // } 
                }
            }

            fighter.move();
            solver.move();
            repaint();
        }
   });
}
