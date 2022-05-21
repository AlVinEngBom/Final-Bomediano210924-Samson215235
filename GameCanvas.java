import java.awt.*;
import javax.swing.*;
import java.awt.Image;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.text.DecimalFormat;
import java.net.URL;


public class GameCanvas extends JComponent{

    private int width, height, gameTime, playerID, fighterHealth;
    private Player fighter, solver;
    private ArrayList<Enemy> Enemies;
   private ArrayList<Wall> Walls;
  
    private String ddSecond,ddMinute, timerText;
    private int second, minute, delaySecond;
    private Music m;
  
    public GameCanvas(int w, int h, int pID){

        m = new Music();
        width = w;
        height = h;
        playerID = pID;
        setPreferredSize( new Dimension(width, height) );

        gameTime = 0;
        second = 0;
        minute = 0;

        animationTimer.start();

        fighter = new Player(231, 263, 25, 25, 5);
        solver = new Player(745, 263, 25, 25, 1);

        Enemies = new ArrayList<Enemy>();
        Enemies.add(new Enemy(65,305,1,3));
        Enemies.add(new Enemy(100,400,1,2));

        fighterHealth = 3;


        Enemies = new ArrayList<Enemy>();
        Enemies.add(new Enemy(65,305,0.5,3));
        Enemies.add(new Enemy(100,400,1,2));

        Walls = new ArrayList<Wall>();
        Walls.add(new Wall(512+0, 0, 511, 6));
        Walls.add(new Wall(512+506, 0, 6, 576));
        Walls.add(new Wall(512+0, 0, 6, 576));
        Walls.add(new Wall(512+55, 570, 457, 6));
        Walls.add(new Wall(512+225, 0, 6, 178));
        Walls.add(new Wall(512+281, 0, 6, 120));
        Walls.add(new Wall(512+281, 113, 63, 7));
        Walls.add(new Wall(512+338, 57, 6, 63));
        Walls.add(new Wall(512+338, 57, 62, 6));
        Walls.add(new Wall(512+225, 172, 175, 6));
        Walls.add(new Wall(512+394, 113, 6, 347));
        Walls.add(new Wall(512+394, 113, 63, 6));
        Walls.add(new Wall(512+451, 56, 6, 63));
        Walls.add(new Wall(512+449, 170, 63, 6));
        Walls.add(new Wall(512+168, 226, 284, 6));
        Walls.add(new Wall(512+168, 56, 6, 176));
        Walls.add(new Wall(512+113, 113, 61, 6));
        Walls.add(new Wall(512+113, 113, 61, 6));
        Walls.add(new Wall(512+57, 57, 60, 6));
        Walls.add(new Wall(512+57, 57, 6, 121));
        Walls.add(new Wall(512+0, 172, 118, 6));
        Walls.add(new Wall(512+113, 172, 6, 288));
        Walls.add(new Wall(512+0, 340, 63, 6));
        Walls.add(new Wall(512+57, 226, 6, 120));
        Walls.add(new Wall(512+57, 396, 62, 6));
        Walls.add(new Wall(512+0, 453, 62, 6));
        Walls.add(new Wall(512+57, 453, 6, 62));
        Walls.add(new Wall(512+449, 283, 63, 6));
        Walls.add(new Wall(512+449, 283, 6, 120));
        Walls.add(new Wall(512+449, 453, 63, 6));
        Walls.add(new Wall(512+449, 453, 6, 62));
        Walls.add(new Wall(512+338, 509, 117, 6));
        Walls.add(new Wall(512+338, 396, 6, 119));
        Walls.add(new Wall(512+281, 453, 63, 6));
        Walls.add(new Wall(512+225, 396, 6, 180));
        Walls.add(new Wall(512+225, 509, 63, 6));
        Walls.add(new Wall(512+225, 396, 62, 6));
        Walls.add(new Wall(512+281, 340, 6, 62));
        Walls.add(new Wall(512+168, 340, 176, 6));
        Walls.add(new Wall(512+168, 283, 6, 232));
        Walls.add(new Wall(512+113, 509, 61, 6));
        Walls.add(new Wall(512+226, 283, 6, 63));
        Walls.add(new Wall(512+226, 283, 6, 63));
        Walls.add(new Wall(512+338, 283, 6, 63));
        Walls.add(new Wall(512+281, 283, 63, 6));
   }
   
    protected void paintComponent(Graphics g){
        
        Image gb = new ImageIcon("Sprites/GameBackground.png").getImage();
        g.drawImage(gb, 0, 0, null);

        Image enemyIcon = new ImageIcon("Sprites/EnemySprite.png").getImage();

        g.setColor(Color.BLACK);
        for(int i = 0; i < Walls.size(); i+=1){
            Walls.get(i).draw(g);
        }
        
        g.setColor(Color.RED);
        for(int i = 0; i < Enemies.size(); i+=1){
            Enemies.get(i).draw(g, enemyIcon);
        }

        Image fighterIcon = new ImageIcon("Sprites/PlayerFighterSprites/down/down1.png").getImage();
        fighter.draw(g, fighterIcon);

        Image solverIcon = new ImageIcon("Sprites/PlayerSolverSprites/down/down1.png").getImage();
        solver.draw(g, solverIcon);

        g.setColor(Color.WHITE);
        g.drawString(timerText,497,17);

        g.setColor(Color.WHITE);
        g.drawString("Lives: " + fighterHealth,490,570);

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

    Timer animationTimer = new Timer(10, new ActionListener(){
        public void actionPerformed(ActionEvent ae){

            // URL typeSoundURL = getClass().getResource("Encounter.wav");
            // m.setURL(typeSoundURL);
            // m.play();

            delaySecond++;
            second = delaySecond/100;
            DecimalFormat dFormat = new DecimalFormat("00");
            ddSecond = dFormat.format(second);
            ddMinute = dFormat.format(minute);
            timerText = ddMinute + ":" + ddSecond;

            if(second == 60){
                delaySecond = 0;
                second = 0;
                minute++;
                ddSecond = dFormat.format(second);
                ddMinute = dFormat.format(minute);
                timerText = ddMinute + ":" + ddSecond;
            } 

            gameTime++;
          
            if(gameTime == 1000){
                Enemies.add(new Enemy(5,5,3,1));
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
                Enemies.add(new Enemy(457,50,1,2));
            }
            else if(gameTime == 7000){
                Enemies.add(new Enemy(50,413,3,1));
            }
            else if(gameTime == 8000){
                Enemies.add(new Enemy(457,413,1,2));
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
                fighter.boundLeft();
            }
            else if(fighter.getX() + fighter.getWidth() >= width/2){
                fighter.boundRight();
            }
            if(fighter.getY() <= 0){
                fighter.boundTop();
            }
            else if(fighter.getY() + fighter.getHeight() >= height){
                fighter.boundBottom();
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
                    fighterHealth -= 1;
                    Enemies.remove(i);     
                  
                    //losing condition
                    if(fighterHealth == 0){
                        fighter.stop();
                        solver.stop();
                        animationTimer.stop();
                        ImageIcon loseIcon = new ImageIcon("Sprites/LoseEmoji.png");
                        Image loseImage = loseIcon.getImage();
                        Image modifiedLoseImage = loseImage.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
                        loseIcon = new ImageIcon(modifiedLoseImage);
                        JOptionPane.showMessageDialog(null, "Fighter ran out of lives!!","YOU LOST!", JOptionPane.INFORMATION_MESSAGE,loseIcon);
                        System.exit(0);
            }

            // Wall to PlayerSolver Collissions
            for(int i = 0; i < Walls.size(); i++){
                if(solver.wallIsColliding(Walls.get(i))){
                    
                    Walls.get(i).collided();

                    if(Walls.get(i).getX() + Walls.get(i).getWidth() - solver.getX() <= 5){ 
                        solver.boundLeft();
                    }
                    else if(Walls.get(i).getX() - solver.getX() + solver.getWidth() > 5){
                        solver.boundRight();
                    }
                    if(Walls.get(i).getY() + Walls.get(i).getHeight() - solver.getY() <= 5){ 
                        solver.boundTop();
                    }
                    else if(Walls.get(i).getY() - solver.getY() + solver.getHeight() > 5){
                        solver.boundBottom();
                    }
                }
            }
            //winning condition
            if(solver.getY() + solver.getHeight() >= height){
                animationTimer.stop();
                solver.stop();
                fighter.stop();
                ImageIcon winIcon = new ImageIcon("Sprites/WinEmoji.png");
                Image winImage = winIcon.getImage();
                Image modifiedWinImage = winImage.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
                winIcon = new ImageIcon(modifiedWinImage);
                JOptionPane.showMessageDialog(null, "Solver escaped the maze!!","YOU WON!", JOptionPane.INFORMATION_MESSAGE,winIcon);
                System.exit(0);
            }
            
            fighter.move();
            solver.move();
            repaint();
        }
   });
}
