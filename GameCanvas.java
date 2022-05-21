/**
    @authors Al Vincent E. Bomediano (210924), Jerril Nheo A. Samson (215235)
    @version May 21, 2022
**/
/*
    We have not discussed the Java language code in my program 
    with anyone other than my instructor or the teaching assistants 
    assigned to this course.
    We have not used Java language code obtained from another student, 
    or any other unauthorized source, either modified or unmodified.
    If any Java language code or documentation used in our program 
    was obtained from another source, such as a textbook or website, 
    that has been clearly noted with a proper citation in the comments 
    of our program.
*/

//This class extendsJComponent and overrides the paintComponent method to create the custom drawings.
//This class also contains all the sprites and gui elements interactivity and logic.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GameCanvas extends JComponent{

    private int width, height, gameTime, playerID, fighterHealth, second, minute, delaySecond;
    private String ddSecond,ddMinute, timerText;
    private Player fighter, solver;
    private ArrayList<Enemy> Enemies;
    private ArrayList<Wall> Walls;
    
    //Accepts width, height and playerID for the creation of the canvas
    public GameCanvas(int w, int h, int pID){
        width = w;
        height = h;
        playerID = pID;

        //Set frame size
        setPreferredSize( new Dimension(width, height) );

        //Game logic properties
        fighterHealth = 3;
        gameTime = 0;
        second = 0;
        minute = 0;

        //Start animation timer thread
        animationTimer.start();

        //create the two players
        fighter = new Player(231, 263, 35, 45, 5);
        solver = new Player(745, 263, 30, 35, 1);

        //store initial enemies in an arrayList
        Enemies = new ArrayList<Enemy>();
        Enemies.add(new Enemy(65,305,1,3));
        Enemies.add(new Enemy(100,400,1,2));

        //store all walls in an arrayList
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
   
   //for drawing in the game elements
    protected void paintComponent(Graphics g){

        //storing images in variables for drawing in
        Image gb = new ImageIcon("Sprites/GameBackground.png").getImage();
        Image enemyIcon = new ImageIcon("Sprites/EnemySprite.png").getImage();
        Image fighterIcon = new ImageIcon("Sprites/FighterSprite.png").getImage();
        Image solverIcon = new ImageIcon("Sprites/SolverSprite.png").getImage();

        //draw in the background
        g.drawImage(gb, 0, 0, null);
        
        //draw in the maze walls
        g.setColor(Color.BLACK);
        for(int i = 0; i < Walls.size(); i+=1){
            Walls.get(i).draw(g);
        }
        
        //draw in the enemies
        for(int i = 0; i < Enemies.size(); i+=1){
            Enemies.get(i).draw(g, enemyIcon);
        }

        //draw in both players
        fighter.draw(g, fighterIcon);
        solver.draw(g, solverIcon);

        //draw in the timer
        g.setColor(Color.WHITE);
        g.drawString(timerText,497,17);

        //draw in the playerFighter health
        g.setColor(Color.WHITE);
        g.drawString("Lives: " + fighterHealth,490,570);
   }

    //returns the correct player for the current client 
    public Player getPlayer(){
        if (playerID == 1) {
            return fighter;
        } else {
            return solver;
        }
    }

    //returns the other player for the current client
    public Player getOther(){
        if (playerID == 2) {
            return fighter;
        } else {
            return solver;
        }
    }

    //thread for the game process
    Timer animationTimer = new Timer(10, new ActionListener(){
        public void actionPerformed(ActionEvent ae){
            
            //ingame timer logic
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

            //enemy timer logic, for adding in enemies after a certain time passes
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
                if(fighter.enemyIsColliding(Enemies.get(i))){
                    Enemies.remove(i);
                    fighterHealth -= 1;

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
                }
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

            //move players, update canvas
            fighter.move();
            solver.move();
            repaint();
        }
   });
}
